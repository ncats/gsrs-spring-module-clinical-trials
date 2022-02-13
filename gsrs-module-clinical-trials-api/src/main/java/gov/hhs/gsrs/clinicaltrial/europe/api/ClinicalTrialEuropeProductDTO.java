package gov.hhs.gsrs.clinicaltrial.europe.api;
import java.util.ArrayList;
import java.util.List;

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
