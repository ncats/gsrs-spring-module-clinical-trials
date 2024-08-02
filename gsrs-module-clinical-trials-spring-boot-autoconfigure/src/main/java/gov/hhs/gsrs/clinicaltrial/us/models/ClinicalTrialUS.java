package gov.hhs.gsrs.clinicaltrial.us.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gsrs.security.GsrsSecurityUtils;
import ix.core.models.*;
import gov.hhs.gsrs.clinicaltrial.base.models.ClinicalTrialBase;
import ix.core.util.EntityUtils;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.*;

@Data
@Entity
// @NoArgsConstructor
@AllArgsConstructor
// @ToString
@SuperBuilder
@Backup
@IndexableRoot
@Table(name="ctrial_us")
@EqualsAndHashCode(exclude="clinicalTrialUSDrug")
@Slf4j
public class ClinicalTrialUS extends ClinicalTrialBase {
    /*
    To do
    Make the entity link IVM use a SubstanceKeyResolver once that's available
    */


    // see base class for basic fields
    public ClinicalTrialUS() {
        this.setKind("US");
    }

    @Lob
    @Column(name = "RECRUITMENT", length=4000)
    public String recruitment;

    @Lob
    @Column(name = "RESULTS_FIRST_RECEIVED", length=4000)
    public String resultsFirstReceived;

    @Lob
    @Indexable(sortable = true)
    @Column(name = "conditions", length=4000)
    public String conditions;

    @Lob
    @Column(name = "intervention", length=4000)
    public String intervention;

    @Lob
    @Indexable(sortable = true)
    @Column(name = "sponsor", length=4000)
    public String sponsor;

    @Lob
    @Column(name = "phases", length=2000)
    public String phases;

    @Lob
    @Column(name = "funded_bys", length=2000)
    public String fundedBys;

    @Lob
    @Indexable(facet = true, name = "Study Types")
    @Column(name = "study_types", length=2000)
    public String studyTypes;

    @Lob
    @Column(name = "study_designs", length=2000)
    public String studyDesigns;

    @Lob
    @Column(name = "study_results", length=4000)
    @Indexable(facet= true, name = "Study Results")
    public String studyResults;

    @Column(name = "age_groups", length=50)
    public String ageGroups;

    @Column(name = "gender", length=50)
    @Indexable(facet= true, name = "Gender")
    public String gender;

    @Lob
    @Column(name = "enrollment", length=2000)
    public String enrollment;

    @Lob
    @Column(name = "other_ids", length=500)
    public String otherIds;

    @Lob
    @Column(name = "acronym", length=4000)
    public String acronym;

    @Column(name = "status", length=500)
    @Indexable(name="Trial Status", facet=true)
    public String status;

    @Column(name = "start_date")
    @Indexable(name = "Start Date at Source", sortable=true)
    public Date startDate;

    @Column(name = "last_verified")
    @Indexable(name = "Last Verified Date at Source", sortable=true)
    public Date lastVerified;

    @Column(name = "completion_date")
    @Indexable(name = "Completion Date at Source", sortable=true)
    public Date completionDate;

    @Column(name = "primary_completion_date")
    @Indexable(name = "Primary Completion Date at Source", sortable=true)
    public Date primaryCompletionDate;

    @Column(name = "first_received")
    @Indexable(name = "First Received Date at Source", sortable=true)
    public Date firstReceived;

    @Column(name = "last_updated")
    @Indexable(name = "Last Updated at Source", sortable=true)
    public Date lastUpdated;

    @Lob
    @Column(name = "outcome_measures", length=4000)
    public String outcomeMeasures;

    @Lob
    @Column(name = "locations", length=4000)
    public String locations;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    // had to add this or I got circular references when string building.
    // @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<ClinicalTrialUSDrug> clinicalTrialUSDrug = new ArrayList<ClinicalTrialUSDrug>();

    public void setClinicalTrialUSDrug(List<ClinicalTrialUSDrug> clinicalTrialUSDrugs) {
        this.clinicalTrialUSDrug = clinicalTrialUSDrugs;
        if(clinicalTrialUSDrugs !=null) {
            for ( ClinicalTrialUSDrug ctd : clinicalTrialUSDrugs) {
                ctd.setOwner(this);
            }
        }
    }

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    // @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<OutcomeResultNote> outcomeResultNotes = new ArrayList<OutcomeResultNote>();

    public void setOutcomeResultNotes(List<OutcomeResultNote> outcomeResultNotes) {
        this.outcomeResultNotes = outcomeResultNotes;
        if(outcomeResultNotes !=null) {
            for (OutcomeResultNote note : outcomeResultNotes) {
                note.setOwner(this);
            }
        }
    }

    @Column(name = "gsrs_matching_complete")
    public boolean gsrsMatchingComplete;


    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @LastModifiedDate
    @Indexable( name = "Last Modified Date", sortable=true)
    public Date lastModifiedDate;

    // try to remove
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // try to remove
    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    @JsonSerialize(using = GsrsDateSerializer.class)
    @JsonDeserialize(using = GsrsDateDeserializer.class)
    @CreatedDate
    @Indexable( name = "Create Date", sortable=true)
    public Date creationDate;

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;

    }
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Indexable(sortable = true)
    public String getTrialNumber() {
        return this.trialNumber;
    }

    @JsonIgnore
    @Indexable(facet=true, name="Deprecated")
    public String getDeprecated(){
        return "Not Deprecated";
    }


    // I want to make this reusable. How?
    @Indexable(facet = true, name = "Record Created By")
    @Column(name = "created_by", length=500)
    private String createdBy;

    @Indexable(facet = true, name = "Record Last Edited By")
    @Column(name = "modified_by", length=500)
    private String modifiedBy;

    @PrePersist
    public void prePersist() {
        try {
            UserProfile profile = (UserProfile) GsrsSecurityUtils.getCurrentUser();
            if (profile != null) {
                Principal p = profile.user;
                if (p != null) {
                    this.createdBy = p.username;
                    this.modifiedBy = p.username;
                }
            }
        }catch (Exception ex) {
            log.error("Exception while setting user in prePersist.", ex);
        }
    }

    @PreUpdate
    public void preUpdate() {
        try {
            UserProfile profile = (UserProfile) GsrsSecurityUtils.getCurrentUser();
            if (profile != null) {
                Principal p = profile.user;
                if (p != null) {
                    this.modifiedBy = p.username;
                }
            }
        }catch (Exception ex) {
            log.error("Exception while setting user in preUpdate.", ex);
        }
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this);
        rtsb.setExcludeNullValues(true);
        return rtsb.toString();
    }
    public JsonNode toInternalJsonNode(){
        return EntityUtils.EntityWrapper.of(this).toInternalJsonNode();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
