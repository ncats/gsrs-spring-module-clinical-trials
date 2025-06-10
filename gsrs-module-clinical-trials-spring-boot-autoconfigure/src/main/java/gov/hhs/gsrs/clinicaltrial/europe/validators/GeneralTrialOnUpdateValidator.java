package gov.hhs.gsrs.clinicaltrial.europe.validators;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.hhs.gsrs.clinicaltrial.europe.repositories.ClinicalTrialEuropeRepository;
import gov.nih.ncats.common.util.TimeUtil;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

public class GeneralTrialOnUpdateValidator implements ValidatorPlugin<ClinicalTrialEurope> {

    @Autowired
    private ClinicalTrialEuropeRepository repository;

    @Override
    public boolean supports(ClinicalTrialEurope newValue, ClinicalTrialEurope oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }

    // @Value("${mygsrs.clinicaltrial.eu.ClinicalTrialEurope.trialNumberPattern}")
    // private String trialNumberPatternRegex;

    final String trialNumberNullErrorTemplate = "Trial Number is null.";
    final String badlyFormattedTrialNumberTemplate = "Trial Number [%s] had an incorrect format.";
    final String trialNumberShouldAlreadyExistErrorTemplate = "Trial Number [%s] SHOULD already exist.";
    final String newCreationDateNullErrorTemplate = "New Creation Date is null.";
    final String newLastModifiedDateNullErrorTemplate = "New Last Modified Date is null.";
    final String oldCreationDateNullErrorTemplate = "Old Creation Date is null.";
    final String oldLastModifiedDateNullErrorTemplate = "Old Last Modified Date is null.";
    final String newOldLastModifiedDatesDifferentErrorTemplate = "New and old Last Modified Dates must be equal.";


    // final String newCreationDateDifferentFromOldErrorTemplate = "New Creation Date different that record being replaced.";
    // final String newLastModifiedDateDifferentFromOldErrorTemplate = "New Last Modified Date different that record being replaced.";


    // final Pattern trialNumberPattern = Pattern.compile(trialNumberPatternRegex);
    final Pattern trialNumberPattern = Pattern.compile("^\\d{4}-\\d{6}-\\d{2}-[A-Z]{2}$");

    @Override
    public void validate(ClinicalTrialEurope objnew, ClinicalTrialEurope objold, ValidatorCallback callback) {

        String trialNumber = objnew.getTrialNumber();
        if(trialNumber==null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(trialNumberNullErrorTemplate)));
        }
        boolean formatOK = trialNumberPattern.matcher(trialNumber).matches();
        if(!formatOK) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(badlyFormattedTrialNumberTemplate, trialNumber)));
        }
        // ask danny about this? how is objold populated.
        if(objold != null) {
        }
        Optional<ClinicalTrialEurope> found = repository.findById(objnew.getTrialNumber());
        if(!found.isPresent()) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(trialNumberShouldAlreadyExistErrorTemplate, trialNumber)));
        } else {
            ClinicalTrialEurope ct = found.get();
            Date newCreationDate = objnew.getCreationDate();
            Date newLastModifiedDate = objnew.getLastModifiedDate();
            Date oldCreationDate = ct.getCreationDate();
            Date oldLastModifiedDate = ct.getLastModifiedDate();
            if (newCreationDate == null) {
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(newCreationDateNullErrorTemplate)));
            }
            if (newLastModifiedDate == null) {
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(newLastModifiedDateNullErrorTemplate)));
            }
            if (oldCreationDate == null) {
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(oldCreationDateNullErrorTemplate)));
            }
            if (oldLastModifiedDate == null) {
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(oldLastModifiedDateNullErrorTemplate)));
            }
/*
            LocalDateTime ldt1 = TimeUtil.asLocalDateTime(newLastModifiedDate);
            LocalDateTime ldt2 = TimeUtil.asLocalDateTime(oldLastModifiedDate);
            boolean cmp = ldt1.isEqual(ldt2);

            if (cmp != true) {
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(newOldLastModifiedDatesDifferentErrorTemplate)));
            }
*/
        }
    }
}