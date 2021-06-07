package gov.nih.ncats.clinicaltrial.base.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.nih.ncats.clinicaltrial.inheritance.AbstractGsrsEntityAlt;
import ix.core.models.Indexable;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.util.*;
import javax.persistence.InheritanceType;


@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Builder
@AllArgsConstructor
@NoArgsConstructor
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


    @Version
    @Column(name = "INTERNAL_VERSION", nullable = false)
    public Long internalVersion = 0L;

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @LastModifiedDate
    @Indexable( name = "Last Modified Date", sortable=true)

    private Date lastModifiedDate;
    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @CreatedDate
    @Indexable( name = "Create Date", sortable=true)
    private Date creationDate;

    public String getTrialNumber() {
        return this.trialNumber;
    }
}