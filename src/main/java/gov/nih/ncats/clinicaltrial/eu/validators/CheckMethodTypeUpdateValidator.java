package gov.nih.ncats.clinicaltrial.eu.validators;

import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;

public class CheckMethodTypeUpdateValidator implements ValidatorPlugin<ClinicalTrialEurope> {

    @Override
    public boolean supports(ClinicalTrialEurope newValue, ClinicalTrialEurope oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return methodType == ValidatorConfig.METHOD_TYPE.CREATE;
    }

    @Override
    public void validate(ClinicalTrialEurope objnew, ClinicalTrialEurope objold, ValidatorCallback callback) {
        System.out.println("AM I A CREATE VALIDATOR?");
    }
}