package gov.hhs.gsrs.clinicaltrial.us.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.MixinUtil;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import gov.hhs.gsrs.clinicaltrial.base.models.ClinicalTrialBase;
import ix.core.models.IndexableRoot;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.*;

import gov.nih.ncats.common.util.TimeUtil;

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
public class ClinicalTrialUS extends ClinicalTrialBase {
    /*
    To do
    Try to fix the ID thing to work in-situ if possible. Probably add a getter with a special @Indexable() annotation.
    Make a specific IVM for all the convenience facets, remove the logic from the model.
    Make a specific IVM for the entity links.
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
    @Indexable(name="Trial Status")
    public String status;

    @Column(name = "START_DATE")
    public Date startDate;

    @Column(name = "LAST_VERIFIED")
    public Date lastVerified;

    @Column(name = "COMPLETION_DATE")
    public Date completionDate;

    @Column(name = "PRIMARY_COMPLETION_DATE")
    public Date primaryCompletionDate;

    @Column(name = "FIRST_RECEIVED")
    public Date firstReceived;

    @Column(name = "LAST_UPDATED")
    public Date lastUpdated;

    @Column(name = "OUTCOME_MEASURES", length=4000)
    public String outcomeMeasures;

    @Column(name = "LOCATIONS", length=4000)
    public String locations;
    // had to add orphan removal or would not delete.
    // long term should find a better solution.
    //  orphanRemoval = true
    // after wip_changes trying without
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    // had to add this or I got circular references when string building.
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<ClinicalTrialUSDrug> clinicalTrialUSDrug = new ArrayList<ClinicalTrialUSDrug>();

    public void setClinicalTrialUSDrug(List<ClinicalTrialUSDrug> clinicalTrialUSDrugs) {
        System.out.println("Running setClinicalTrialUSDrug");
        // System.out.println("HERE1");
        this.clinicalTrialUSDrug = clinicalTrialUSDrugs;
        // System.out.println("HERE2");
        if(clinicalTrialUSDrugs !=null) {
            // System.out.println("HERE3");
            for ( ClinicalTrialUSDrug ctd : clinicalTrialUSDrugs)
            {
                // System.out.println("HERE4" + ctd.getSubstanceKeyType());
                // System.out.println("this.getClass" + this.getClass());
                // System.out.println("this.title" + this.getTitle());
                // System.out.println("this.trialNumber" + this.getTrialNumber());
                // System.out.println("this.toString" + this.toString());
                ctd.setOwner(this);
                // System.out.println("HERE5");
                // ctd.setIsDirty("clinicalTrialDrug");
                // ctd.setIsDirty("substanceKey");

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

    // this added during a debugging session could prob be removed.
    public void setLastModifiedDate(Date lastModifiedDate) {
        System.out.println("==== LAST MODIFIED DATE ====" + lastModifiedDate.toString());
        this.lastModifiedDate = lastModifiedDate;

    }

    // this added during a debugging session could prob be removed.
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


    @Indexable()
    public String getTrialNumber() {
        return this.trialNumber;
    }

    // ******

    // TODO ... move all these things to index value maker

    @JsonIgnore
    @Indexable(facet=true, name="Deprecated")
    public String getDeprecated(){
        return "Not Deprecated";
    }

    @JsonIgnore
    @Indexable(facet= true, name = "CT Matching Complete")
    public String getCTMatchingComplete() {
        if (this.gsrsMatchingComplete == true) {
            return "Complete";
        } else {
            return "Not complete";
        }
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Has Substances")
    public String getHasSubstances() {
        if (this.clinicalTrialUSDrug != null && !this.clinicalTrialUSDrug.isEmpty()) {
            return "Has Substances";
        }
        return "No Substances";
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Last Updated Year")
    public String getLastUpdatedYear() {
        if (this.lastUpdated!=null) {
            return String.valueOf(TimeUtil.asLocalDate(lastUpdated).getYear());
        } else {
            return "No Year";
        }
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Primary Completion Year")
    public String getPrimaryCompletionYear() {
        if (this.primaryCompletionDate!=null) {
            return String.valueOf(TimeUtil.asLocalDate(primaryCompletionDate).getYear());
        } else {
            return "No Year";
        }
    }

    @JsonIgnore
    @Indexable(facet= true, name = "First Received Year")
    public String getFirstPostedYear() {
        if (this.firstReceived!=null) {
            return String.valueOf(TimeUtil.asLocalDate(firstReceived).getYear());
        } else {
            return "No Year";
        }
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Intervention Type")
    public List<String> getInterventionTypeIndexing() {
        if (this.intervention != null) {
            HashMap m = new HashMap<String,Boolean>();
            for (String i: Arrays.asList(this.intervention.split("\\|"))) {
                if(i.startsWith("Drug:")) {
                    m.put("Drug", true);
                } else if(i.startsWith("Combination Product:")) {
                    m.put("Combination Product", true);
                } else if(i.startsWith("Procedure:")) {
                    m.put("Procedure", true);
                } else if(i.startsWith("Biological:")) {
                    m.put("Behavioral", true);
                } else if(i.startsWith("Other:")) {
                    m.put("Other", true);
                } else if(i.startsWith("Device:")) {
                    m.put("Device", true);
                } else if(i.startsWith("Dietary Supplement:")) {
                    m.put("Dietary Supplement", true);
                } else {
                    m.put("Not-classified", true);
                }
            }
            List<String> keys = new ArrayList<String>(m.keySet());
            if(keys!=null) {
                return keys;
            }
        }
        return null;
    }

    @JsonIgnore
    @Indexable(suggest=true, facet=true, name="Conditions")
    public List<String> getConditionsIndexing() {
        if (this.conditions != null) {
            return Arrays.asList(this.conditions.split("\\|"));
        }
        return new ArrayList<String>();
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Interventions")
    public List<String> getInterventionIndexing() {
        if (this.intervention != null) {
            return Arrays.asList(this.intervention.split("\\|"));
        }
        return new ArrayList<String>();
    }

    @JsonIgnore
    @Indexable(suggest=true, facet= true, name="Sponsors")
    public List<String> getSponsorIndexing() {
        if (this.sponsor != null) {
            return Arrays.asList(this.sponsor.split("\\|"));
        }
        return new ArrayList<String>();
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Age Groups")
    public List<String> getAgeGroupIndexing() {
        if (this.ageGroups != null) {
            return Arrays.asList(this.ageGroups.split("\\|"));
        }
        return new ArrayList<String>();
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Outcome Measures")
    public List<String> getOutcomeMeasureIndexing() {
        if (this.outcomeMeasures != null) {
            return Arrays.asList(this.outcomeMeasures.split("\\|"));
        }
        return new ArrayList<String>();
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Study Types")
    public List<String> getStudyTypesIndexing() {
        if (this.studyTypes != null) {
            return Arrays.asList(this.studyTypes.split("\\|"));
        }
        return new ArrayList<String>();
    }
    // ******  end



}
