package gov.nih.ncats.clinicaltrial.base.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.nih.ncats.common.util.TimeUtil;
import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Indexable;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;


@Data
// @Entity
@MappedSuperclass
// @Builder
@AllArgsConstructor
// @NoArgsConstructor
// @Table(name="clinical_trial_base")
@ToString
public class ClinicalTrialBase extends AbstractGsrsEntity {
    @Id
    // @GeneratedValue
    // public String trialNumber;
    @Column(name="TRIAL_NUMBER")
    public String trialNumber;

    // commenting discriminator type for now
    // public String dtype;

    @Indexable
    @Column(name = "TITLE", length=4000)
    public String title;

    @Indexable
    @Column(name = "SPONSOR_NAME", length=4000)
    public String sponsorName;

    public String url;

    @Indexable
    @Column(name = "COUNTRY", length=50)
    public String country;

    public ClinicalTrialBase () {}

    @JsonIgnore
    public String ctRegion() {
        return "BASE";
    }

    @JsonIgnore
    public String ctId() {
        return this.trialNumber;
    }

    @Version
    @Column(name = "INTERNAL_VERSION", nullable = false)
    public Long internalVersion = 0L;

    @Column(name = "GSRS_MATCHING_COMPLETE")
    public boolean gsrsMatchingComplete;

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