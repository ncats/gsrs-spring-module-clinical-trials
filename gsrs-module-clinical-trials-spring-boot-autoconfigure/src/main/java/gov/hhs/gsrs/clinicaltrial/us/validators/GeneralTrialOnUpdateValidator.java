package gov.hhs.gsrs.clinicaltrial.us.validators;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gov.nih.ncats.common.util.TimeUtil;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

public class GeneralTrialOnUpdateValidator implements ValidatorPlugin<ClinicalTrialUS> {

    @Autowired
    private ClinicalTrialUSRepository repository;

    @Autowired
    private Environment env;

    @Override
    public boolean supports(ClinicalTrialUS newValue, ClinicalTrialUS oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }

    final String objoldIsNullErrorTemplate = "The old trial object is null.";
    final String objnewIsNullErrorTemplate = "The new trial object is null.";
    final String trialNumberNullErrorTemplate = "Trial Number is null.";
    final String badlyFormattedTrialNumberTemplate = "Trial Number [%s] had an incorrect format.";
    final String trialNumberShouldAlreadyExistErrorTemplate = "Trial Number [%s] SHOULD already exist.";
    final String newCreationDateNullErrorTemplate = "New Creation Date is null.";
    final String newLastModifiedDateNullErrorTemplate = "New Last Modified Date is null.";
    final String oldCreationDateNullErrorTemplate = "Old Creation Date is null.";
    final String oldLastModifiedDateNullErrorTemplate = "Old Last Modified Date is null.";
    final String newOldCreationDatesDifferentErrorTemplate = "New and old Creation Dates must be equal in record to be updated.";
    final String newOldLastModifiedDatesDifferentErrorTemplate = "New and old Last Modified Dates must be equal in record to be updated.";

    // TO DO: makes this come from a configuration.
    final Pattern trialNumberPattern = Pattern.compile("^NCT[\\d]+$");

    @Override
    public void validate(ClinicalTrialUS objnew, ClinicalTrialUS objold, ValidatorCallback callback) {
        if (objold == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorNullError1", String.format(objoldIsNullErrorTemplate)));
            return;
        }

        if (objnew == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorNullError2", String.format(objnewIsNullErrorTemplate)));
            return;
        }
        String trialNumber = objnew.getTrialNumber();
        if (trialNumber == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorNullError3", String.format(trialNumberNullErrorTemplate)));
            return;
        }
        boolean formatOK = trialNumberPattern.matcher(trialNumber).matches();

        if (!formatOK) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorFormatError", String.format(badlyFormattedTrialNumberTemplate, trialNumber)));
            return;
        }

        Optional<ClinicalTrialUS> found = repository.findById(objnew.getTrialNumber());
        if(!found.isPresent()) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorExistError", String.format(trialNumberShouldAlreadyExistErrorTemplate, trialNumber)));
            return;
        }

        Date newCreationDate = objnew.getCreationDate();
        Date newLastModifiedDate = objnew.getLastModifiedDate();
        Date oldCreationDate = objold.getCreationDate();
        Date oldLastModifiedDate = objold.getLastModifiedDate();

       if (newCreationDate == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorNullError4", String.format(newCreationDateNullErrorTemplate)));
            return;
        }
        if (newLastModifiedDate == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorNullError5", String.format(newLastModifiedDateNullErrorTemplate)));
            return;
        }
        if (oldCreationDate == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorNullError6", String.format(oldCreationDateNullErrorTemplate)));
            return;
        }
        if (oldLastModifiedDate == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorNullError7",String.format(oldLastModifiedDateNullErrorTemplate)));
            return;
        }

        LocalDateTime ldtc1 = TimeUtil.asLocalDateTime(newCreationDate);
        LocalDateTime ldtc2 = TimeUtil.asLocalDateTime(oldCreationDate);
        LocalDateTime ldtm1 = TimeUtil.asLocalDateTime(newLastModifiedDate);
        LocalDateTime ldtm2 = TimeUtil.asLocalDateTime(oldLastModifiedDate);
        boolean cmpc = ldtc1.isEqual(ldtc2);
        boolean cmpm = ldtm1.isEqual(ldtm2);
        if (cmpc != true) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorDifferentError1", String.format(newOldCreationDatesDifferentErrorTemplate)));
        }
        if (cmpm != true) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("GeneralTrialOnUpdateValidatorDifferentError2", String.format(newOldLastModifiedDatesDifferentErrorTemplate)));
        }
    }
}