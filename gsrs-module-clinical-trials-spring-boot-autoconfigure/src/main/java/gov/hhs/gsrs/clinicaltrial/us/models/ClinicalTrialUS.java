package gov.hhs.gsrs.clinicaltrial.us.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gsrs.security.GsrsSecurityUtils;
import ix.core.models.*;
import gov.hhs.gsrs.clinicaltrial.base.models.ClinicalTrialBase;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
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
@ToString
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

    @Column(name = "RECRUITMENT", length=4000)
    public String recruitment;

    @Column(name = "RESULTS_FIRST_RECEIVED", length=4000)
    public String resultsFirstReceived;

    @Column(name = "CONDITIONS", length=4000)
    public String conditions;

    @Column(name = "INTERVENTION", length=4000)
    public String intervention;

    @Column(name = "SPONSOR", length=4000)
    public String sponsor;

    @Column(name = "PHASES", length=2000)
    public String phases;

    @Column(name = "FUNDED_BYS", length=2000)
    public String fundedBys;

    @Indexable(facet = true, name = "Study Types")
    @Column(name = "STUDY_TYPES", length=2000)
    public String studyTypes;

    @Column(name = "STUDY_DESIGNS", length=2000)
    public String studyDesigns;

    @Column(name = "STUDY_RESULTS", length=4000)
    @Indexable(facet= true, name = "Study Results")
    public String studyResults;

    @Column(name = "AGE_GROUPS", length=50)
    public String ageGroups;

    @Column(name = "GENDER", length=50)
    @Indexable(facet= true, name = "Gender")
    public String gender;

    @Column(name = "ENROLLMENT", length=2000)
    public String enrollment;

    @Column(name = "OTHER_IDS", length=500)
    public String otherIds;

    @Column(name = "ACRONYM", length=4000)
    public String acronym;

    @Column(name = "STATUS", length=500)
    @Indexable(name="Trial Status", facet=true)
    public String status;

    @Column(name = "START_DATE")
    @Indexable(name = "Start Date at Source", sortable=true)
    public Date startDate;

    @Column(name = "LAST_VERIFIED")
    @Indexable(name = "Last Verified Date at Source", sortable=true)
    public Date lastVerified;

    @Column(name = "COMPLETION_DATE")
    @Indexable(name = "Completion Date at Source", sortable=true)
    public Date completionDate;

    @Column(name = "PRIMARY_COMPLETION_DATE")
    @Indexable(name = "Primary Completion Date at Source", sortable=true)
    public Date primaryCompletionDate;

    @Column(name = "FIRST_RECEIVED")
    @Indexable(name = "First Received Date at Source", sortable=true)
    public Date firstReceived;

    @Column(name = "LAST_UPDATED")
    @Indexable(name = "Last Updated at Source", sortable=true)
    public Date lastUpdated;

    @Column(name = "OUTCOME_MEASURES", length=4000)
    public String outcomeMeasures;

    @Column(name = "LOCATIONS", length=4000)
    public String locations;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    // had to add this or I got circular references when string building.
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<ClinicalTrialUSDrug> clinicalTrialUSDrug = new ArrayList<ClinicalTrialUSDrug>();

    public void setClinicalTrialUSDrug(List<ClinicalTrialUSDrug> clinicalTrialUSDrugs) {
        System.out.println("Running setClinicalTrialUSDrug");
        this.clinicalTrialUSDrug = clinicalTrialUSDrugs;
        if(clinicalTrialUSDrugs !=null) {
            for ( ClinicalTrialUSDrug ctd : clinicalTrialUSDrugs) {
                ctd.setOwner(this);
            }
        }
        System.out.println("Finished setClinicalTrialUSDrug");
    }

    @Column(name = "GSRS_MATCHING_COMPLETE")
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
        System.out.println("==== CREATION DATE ====" + creationDate.toString());
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
    @Column(name = "CREATED_BY")
    private String createdBy;

    @Indexable(facet = true, name = "Record Last Edited By")
    @Column(name = "MODIFIED_BY")
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
}
