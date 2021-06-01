package gov.nih.ncats.clinicaltrial.us.validators;

import fda.gsrs.substance.exporters.FDACodeExporter;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialDrug;
import gov.nih.ncats.clinicaltrial.us.services.SubstanceAPIService;
// import gsrs.module.substance.substanceapi.services.SubstanceAPIService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Set;

public class SubstancesExistValidator implements ValidatorPlugin<ClinicalTrial> {

    @Autowired
    private SubstanceAPIService substanceAPIService;

    @Autowired
    private Environment env;


    @Override
    public boolean supports(ClinicalTrial newValue, ClinicalTrial oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE
        || methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }

    @Override
    public void validate(ClinicalTrial objnew, ClinicalTrial objold, ValidatorCallback callback) {
        System.out.println("Validating substances");
        Set<ClinicalTrialDrug> ctds = objnew.getClinicalTrialDrug();
        String skip = env.getProperty("mygsrs.clinicaltrial.us.skipSubstanceValidation");
        Boolean s = false;
        if (skip != null && skip == "true") s = true;
        System.out.println("Validating substances, boolean skip value is: " + s);
        if (!s) {
            for (ClinicalTrialDrug ctd : ctds) {
                Boolean b = substanceAPIService.substanceExists(ctd.getSubstanceKey());
                if (b != true)
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("Substance UUID not found"));
            }
        }
    }

}