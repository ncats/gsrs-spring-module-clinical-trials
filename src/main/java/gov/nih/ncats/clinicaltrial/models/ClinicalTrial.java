package gov.nih.ncats.clinicaltrial.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gsrs.model.AbstractGsrsEntity;
import ix.core.models.Indexable;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
// import javax.persistence.Entity;
// import javax.persistence.Column;
// import javax.persistence.Transient;
// import javax.persistence.GeneratedValue;
// import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import gov.nih.ncats.common.util.TimeUtil;
import java.util.LinkedHashSet;
import java.util.Set;


@Data
@EqualsAndHashCode(exclude="clinicalTrialDrug")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ct_clinical_trial")
@ToString
public class ClinicalTrial extends AbstractGsrsEntity {
    @Id
    // @GeneratedValue
    public String trialNumber;

    @Indexable
    @Column(name = "TITLE", length=4000)
    public String title;

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

    @Column(name = "URL", length=4000)
    public String url;

    @Column(name = "LOCATIONS", length=4000)
    public String locations;

    @Transient
    public List<String> locationList = new ArrayList<String>();

    @Transient
    public List<String> sponsorList = new ArrayList<String>();

    //Added on Oct 8, AN
    // @JsonIgnore
    // @Indexable(indexed=false)
    // @JoinColumn(name = "NCT_NUMBER", referencedColumnName = "NCTN")
    // @OneToMany(mappedBy="clinicalTrialApp", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    // public List<ClinicalTrialApplication> clinicalTrialApplicationList = new ArrayList<ClinicalTrialApplication>();

    // @OneToMany(mappedBy = "owner", fetch= FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Basic(fetch= FetchType.EAGER)
    // had to add this or I got circular references when string building.
    @ToString.Exclude
    public Set<ClinicalTrialDrug> clinicalTrialDrug = new LinkedHashSet<ClinicalTrialDrug>();

    public void setClinicalTrialDrug(LinkedHashSet<ClinicalTrialDrug> clinicalTrialDrugs) {
        // System.out.println("HERE0");
        // System.out.println("HERE1");
        this.clinicalTrialDrug = clinicalTrialDrugs;
        // System.out.println("HERE2");
        if(clinicalTrialDrugs !=null){
            // System.out.println("HERE3");
            for ( ClinicalTrialDrug ctd : clinicalTrialDrugs )
            {
                // System.out.println("HERE4");
                ctd.setOwner(this);
                // System.out.println("HERE5");
            }
        }
        // setIsDirty("ctds");
    }



    @JsonIgnore
    public String ctRegion() {
        return "US";
    }

    @JsonIgnore
    public String ctId() {
        return this.trialNumber;
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
        if (this.clinicalTrialDrug!= null && !this.clinicalTrialDrug.isEmpty()) {
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
        if (this.lastUpdated!=null) {
            return String.valueOf(TimeUtil.asLocalDate(primaryCompletionDate).getYear());
        } else {
            return "No Year";
        }
    }

    @JsonIgnore
    @Indexable(facet= true, name = "Primary Completion Year")
    public String getFirstPostedYear() {
        if (this.lastUpdated!=null) {
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
    @Indexable(facet= true, name = "Conditions")
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
    @Indexable(facet= true, name = "Sponsors")
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

    @Version
    @Column(name = "INTERNAL_VERSION", nullable = false)
    public Long internalVersion = 0L;

    @Column(name = "GSRS_MATCHING_COMPLETE")
    public boolean gsrsMatchingComplete;

    @Column(name = "GSRS_CREATED")
    public long gsrsCreated;

    @Column(name = "GSRS_UPDATED")
    public long gsrsUpdated;


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