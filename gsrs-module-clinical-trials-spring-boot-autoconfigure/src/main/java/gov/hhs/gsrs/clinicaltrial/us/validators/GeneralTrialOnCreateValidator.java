package gov.hhs.gsrs.clinicaltrial.us.validators;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.regex.Pattern;

public class GeneralTrialOnCreateValidator implements ValidatorPlugin<ClinicalTrialUS> {

    @Autowired
    private ClinicalTrialUSRepository repository;

    @Override
    public boolean supports(ClinicalTrialUS newValue, ClinicalTrialUS oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE);
    }

    final String trialNumberNullErrorTemplate = "Trial Number is null.";
    final String badlyFormattedTrialNumberTemplate = "Trial Number [%s] had an incorrect format.";
    final String trialNumberAlreadyExistsErrorTemplate = "Trial Number [%s] already exists";
    final Pattern trialNumberPattern = Pattern.compile("^NCT[\\d]+$");

    @Override
    public void validate(ClinicalTrialUS objnew, ClinicalTrialUS objold, ValidatorCallback callback) {
        System.out.println("Inside OnCreateValidator");

        String trialNumber = objnew.getTrialNumber();
        System.out.println("Trial number: " + trialNumber);

        if(trialNumber==null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(trialNumberNullErrorTemplate)));
        }
        boolean formatOK = trialNumberPattern.matcher(trialNumber).matches();
        if(!formatOK) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(badlyFormattedTrialNumberTemplate, trialNumber)));
        }
        System.out.println("Checking that trial doesn't already exists");

        Optional<ClinicalTrialUS> found = repository.findById(objnew.getTrialNumber());
        if(found.isPresent()) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(trialNumberAlreadyExistsErrorTemplate, trialNumber)));
        }
    }
}