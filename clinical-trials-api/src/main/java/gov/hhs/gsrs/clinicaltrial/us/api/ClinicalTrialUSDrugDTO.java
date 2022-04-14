package gov.hhs.gsrs.clinicaltrial.us.api;

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

public class ClinicalTrialUSDrugDTO {
    private Long id;
    private String substanceKey;
    private String substanceKeyType;
    private boolean protectedMatch;
}