package gov.hhs.gsrs.clinicaltrial.europe.api;

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
public class ClinicalTrialEuropeDTO {
    private String trialNumber;
    private String kind;
    private String title;
    private String url;
    public String sponsorName;
    public String trialStatus;
    public Date dateFirstEnteredDb;
    public String trialResults;
    public String nationalCompetentAuthority;
    public String competentAuthorityDecision;
    public Date competentAuthorityDecisionDate;
    public String ethicsComOpinionApp;
    public String ethicsComOpinionReason;
    public Date ethicsComOpinionDate;
    public String country;
    public List<ClinicalTrialEuropeProductDTO> clinicalTrialEuropeProductList = new ArrayList<>();
    public List<ClinicalTrialEuropeMedicalDTO> clinicalTrialEuropeMedicalList = new ArrayList<>();
    public List<ClinicalTrialEuropeMeddraDTO> clinicalTrialEuropeMeddraList = new ArrayList<>();
    public Date lastModifiedDate;
    public Date creationDate;

    // deprecated?
}

