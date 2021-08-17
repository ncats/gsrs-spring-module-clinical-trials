package gov.nih.ncats2.clinicaltrial.europe.validators;

// import fda.gsrs.substance.exporters.FDACodeExporter;

import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEuropeDrug;
import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEuropeProduct;

import gov.nih.ncats2.clinicaltrial.us.services.SubstanceAPIService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.List;

public class SubstancesExistValidator implements ValidatorPlugin<ClinicalTrialEurope> {

    @Autowired
    private SubstanceAPIService substanceAPIService;

    @Autowired
    private Environment env;


    @Override
    public boolean supports(ClinicalTrialEurope newValue, ClinicalTrialEurope oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE
                || methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }

    @Override
    public void validate(ClinicalTrialEurope objnew, ClinicalTrialEurope objold, ValidatorCallback callback) {
        System.out.println("Validating substances");
        String skip = env.getProperty("mygsrs.clinicaltrial.eu.skipSubstanceValidation");
        Boolean s = false;
        if (skip != null && skip == "true") s = true;
        System.out.println("Validating substances, boolean skip value is: " + s);
        if (!s) {
            List<ClinicalTrialEuropeProduct> products = objnew.getClinicalTrialEuropeProductList();
            for (ClinicalTrialEuropeProduct product : products) {
                List<ClinicalTrialEuropeDrug> ctds = product.getClinicalTrialEuropeDrugList();
                HashMap<String, Boolean> map = new HashMap<>();
                for (ClinicalTrialEuropeDrug ctd : ctds) {
                    Boolean b = substanceAPIService.substanceExists(ctd.getSubstanceKey());
                    if (b != true)
                        callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("Substance UUID not found"));
                }
            }
        }
    }
}