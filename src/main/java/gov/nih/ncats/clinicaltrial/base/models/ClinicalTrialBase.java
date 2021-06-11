package gov.nih.ncats.clinicaltrial.base.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.nih.ncats.clinicaltrial.inheritance.AbstractGsrsEntityAlt;
import ix.core.models.Indexable;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
// Simple Builder annotation won't work here.
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.util.*;
import javax.persistence.InheritanceType;


@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString


public abstract class ClinicalTrialBase extends AbstractGsrsEntityAlt {
    @Id
    public String trialNumber;

    @Column(name = "KIND", length=100)
    public String kind;

    @Indexable
    @Column(name = "TITLE", length=4000)
    public String title;

    @Column(name = "URL", length=4000)
    public String url;

    // Be careful when making field with same name in subclasses; probably should not do that.
    // also node than a private field in base class will not be visisble to subsclass.
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
    public Long internalVersion = 0L;

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @LastModifiedDate
    @Indexable( name = "Last Modified Date", sortable=true)

    public Date lastModifiedDate;
    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @CreatedDate
    @Indexable( name = "Create Date", sortable=true)
    public Date creationDate;

    public String getTrialNumber() {
        return this.trialNumber;
    }
}