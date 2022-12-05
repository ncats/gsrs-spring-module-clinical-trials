package gov.hhs.gsrs.clinicaltrial.base.models;

import gsrs.BackupEntityProcessorListener;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import gsrs.model.AbstractGsrsTablePerClassEntity;
import ix.core.models.*;
import lombok.*;
// Simple Builder annotation won't work here.
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.InheritanceType;


@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@Backup
@EntityListeners({AuditingEntityListener.class, GsrsEntityProcessorListener.class, IndexerEntityListener.class, BackupEntityProcessorListener.class})
public abstract class ClinicalTrialBase extends
        AbstractGsrsTablePerClassEntity
                implements FetchableEntity, ForceUpdateDirtyMakerMixin {
    @Id
    // Apply sortable on getter method since this is an id
    @Column(name="TRIAL_NUMBER", length=255)
    public String trialNumber;

    @Column(name = "KIND", length=100)
    public String kind;

    @Indexable(sortable = true)
    @Column(name = "TITLE", length=4000)
    public String title;

    @Column(name = "URL", length=2000)
    public String url;

    // Be careful when making field with same name in subclasses; probably should not do that.
    // also note that a private field in parent/base class will not be visible to subclasses.
    // https://stackoverflow.com/questions/9414990/if-you-overwrite-a-field-in-a-subclass-of-a-class-the-subclass-has-two-fields-w
    // Discussion on when to include fields in base class
    // https://softwareengineering.stackexchange.com/questions/384980/when-to-move-a-common-field-into-a-base-class


    // for this to work and have facets that work
    // accross source we need to make commonly implemented
    // fields.
    // think of basic fields that should be captured and
    // how the could be uniform for all sources but
    // still be true to each source

    // conditions
    // interventions
    // ageGroups
    // creationDateAtSource
    // lastModifiedDateAtSource

   // I think @Version will only work if it's in the base class. Got an error that said something like that.
    @Version
    @Column(name = "INTERNAL_VERSION", nullable = false)
    public Long internalVersion = 1L;



    @Override
    public String fetchGlobalId() {
        if (this.trialNumber!=null) return this.getClass().getName() + ":" + this.trialNumber;
        return null;
    }

}