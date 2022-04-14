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

public class ClinicalTrialEuropeMedicalDTO {
    private int id;
    private String medicalCondInvesigated;
    private String medicalCondInvesigatedEz;
    private String medicalCondTherapyArea;
}
