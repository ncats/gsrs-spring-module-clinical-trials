package gov.hhs.gsrs.clinicaltrial.europe.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ClinicalTrialEuropeMeddraDTO {

    private int id;
    private String meddraVersion;
    private String meddraClassCode;
    private String meddraTerm;
    private String meddraSystemOrganClass;
}
