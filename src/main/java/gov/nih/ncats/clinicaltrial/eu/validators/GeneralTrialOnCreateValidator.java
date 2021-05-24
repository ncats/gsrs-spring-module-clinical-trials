package gov.nih.ncats.clinicaltrial.eu.validators;

import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gov.nih.ncats.clinicaltrial.eu.repositories.ClinicalTrialEuropeRepository;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.regex.Pattern;

public class GeneralTrialOnCreateValidator implements ValidatorPlugin<ClinicalTrialEurope> {

    @Autowired
    private ClinicalTrialEuropeRepository repository;

    @Override
    public boolean supports(ClinicalTrialEurope newValue, ClinicalTrialEurope oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE);
    }

    // @Value("${mygsrs.clinicaltrial.eu.ClinicalTrialEurope.trialNumberPatternRegex}")
    // private String trialNumberPatternRegex;

    final String trialNumberNullErrorTemplate = "Trial Number is null.";
    final String badlyFormattedTrialNumberTemplate = "Trial Number [%s] had an incorrect format.";
    final String trialNumberAlreadyExistsErrorTemplate = "Trial Number [%s] already exists";
    final Pattern trialNumberPattern = Pattern.compile("^NCT[\\d]+$");


    @Override
    public void validate(ClinicalTrialEurope objnew, ClinicalTrialEurope objold, ValidatorCallback callback) {
        System.out.println("Inside GeneralTrialValidator");

        String trialNumber = objnew.getTrialNumber();
        System.out.println("Trial nubmer: " + trialNumber);

        if(trialNumber==null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(trialNumberNullErrorTemplate)));
        }
        boolean formatOK = trialNumberPattern.matcher(trialNumber).matches();
        if(!formatOK) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(badlyFormattedTrialNumberTemplate, trialNumber)));
        }
        System.out.println("Checking that trial doesn't already exists");

        Optional<ClinicalTrialEurope> found = repository.findById(objnew.getTrialNumber());
        if(found.isPresent()) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(trialNumberAlreadyExistsErrorTemplate, trialNumber)));
        }
    }
}