package gov.hhs.gsrs.clinicaltrial.us.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicalTrialUSDTO {
    private String trialNumber;
    private String kind;
    private String title;
    private String url;
    private String recruitment;
    private String resultsFirstReceived;
    private String conditions;
    private String intervention;
    private String sponsor;
    private String phases;
    private String fundedBys;
    private String studyTypes;
    private String studyDesigns;
    private String studyResults;
    private String ageGroups;
    private String gender;
    private String enrollment;
    private String otherIds;
    private String acronym;
    private String status;
    private Date startDate;
    private Date lastVerified;
    private Date completionDate;
    private Date primaryCompletionDate;
    private Date firstReceived;
    private Date lastUpdated;
    private String outcomeMeasures;
    private String locations;
    private List<ClinicalTrialUSDrugDTO> clinicalTrialUSDrug = new ArrayList();
    private boolean gsrsMatchingComplete;
    private Date lastModifiedDate;
    private Date creationDate;
    // deprecated?
}

