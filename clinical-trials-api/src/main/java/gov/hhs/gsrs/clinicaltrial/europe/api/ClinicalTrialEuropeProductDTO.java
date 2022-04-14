package gov.hhs.gsrs.clinicaltrial.europe.api;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ClinicalTrialEuropeProductDTO {
    public int id;
    public String impSection;
    public String productName;
    public String tradeName;
    public String impRole;
    public String impRoutesAdmin;
    public String pharmaceuticalForm;
    public String prodIngredName;
    public List<ClinicalTrialEuropeDrugDTO> clinicalTrialEuropeDrugList = new ArrayList<>();
}
