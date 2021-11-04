package gov.hhs.gsrs.clinicaltrial.us.validators;

// import fda.gsrs.substance.exporters.FDACodeExporter;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import gov.hhs.gsrs.clinicaltrial.us.services.SubstanceAPIService;
// import gsrs.module.substance.substanceapi.services.SubstanceAPIService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Set;

public class SubstancesExistValidator implements ValidatorPlugin<ClinicalTrialUS> {

    @Autowired
    private SubstanceAPIService substanceAPIService;

    @Autowired
    private Environment env;


    @Override
    public boolean supports(ClinicalTrialUS newValue, ClinicalTrialUS oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE
        || methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }

    @Override
    public void validate(ClinicalTrialUS objnew, ClinicalTrialUS objold, ValidatorCallback callback) {
        System.out.println("Validating substances");
        List<ClinicalTrialUSDrug> ctds = objnew.getClinicalTrialUSDrug();
        String skip = env.getProperty("mygsrs.clinicaltrial.us.skipSubstanceValidation");
        Boolean s = false;
        if (skip != null && skip == "true") s = true;
        System.out.println("Validating substances, boolean skip value is: " + s);
        if (!s) {
            for (ClinicalTrialUSDrug ctd : ctds) {
                Boolean b = substanceAPIService.substanceExists(ctd.getSubstanceKey());
                if (b == null || b != true)
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("Substance UUID not found"));
            }
        }
    }

}