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
    final String newCreationDateNullErrorTemplate = "New Creation Date is null.";
    final String newLastModifiedDateNullErrorTemplate = "New Last Modified Date is null.";
    final String oldCreationDateNullErrorTemplate = "Old Creation Date is null.";
    final String oldLastModifiedDateNullErrorTemplate = "Old Last Modified Date is null.";
    final String newOldCreationDatesDifferentErrorTemplate = "New and old Creation Dates must be equal in record to be updated.";
    final String newOldLastModifiedDatesDifferentErrorTemplate = "New and old Last Modified Dates must be equal in record to be updated.";

    final Pattern trialNumberPattern = Pattern.compile("^NCT[\\d]+$");

    @Override
    public void validate(ClinicalTrialUS objnew, ClinicalTrialUS objold, ValidatorCallback callback) {
        if (objold == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(objoldIsNullErrorTemplate)));
        } else if (objnew == null) {
            callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(objnewIsNullErrorTemplate)));
        } else {
            String trialNumber = objnew.getTrialNumber();
            if (trialNumber == null) {
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(trialNumberNullErrorTemplate)));
            }
            boolean formatOK = trialNumberPattern.matcher(trialNumber).matches();

            if (!formatOK) {
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(badlyFormattedTrialNumberTemplate, trialNumber)));
            }

            Date newCreationDate = objnew.getCreationDate();
            Date newLastModifiedDate = objnew.getLastModifiedDate();
            Date oldCreationDate = objold.getCreationDate();
            Date oldLastModifiedDate = objold.getLastModifiedDate();

            boolean abort1 = false;

            if (newCreationDate == null) {
                abort1 = true;
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(newCreationDateNullErrorTemplate)));
            }
            if (newLastModifiedDate == null) {
                abort1 = true;
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(newLastModifiedDateNullErrorTemplate)));
            }
            if (oldCreationDate == null) {
                abort1 = true;
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(oldCreationDateNullErrorTemplate)));
            }
            if (oldLastModifiedDate == null) {
                abort1 = true;
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(oldLastModifiedDateNullErrorTemplate)));
            }

            if (!abort1) {
                LocalDateTime ldtc1 = TimeUtil.asLocalDateTime(newCreationDate);
                LocalDateTime ldtc2 = TimeUtil.asLocalDateTime(oldCreationDate);

                LocalDateTime ldtm1 = TimeUtil.asLocalDateTime(newLastModifiedDate);
                LocalDateTime ldtm2 = TimeUtil.asLocalDateTime(oldLastModifiedDate);
                boolean cmpc = ldtc1.isEqual(ldtc2);
                boolean cmpm = ldtm1.isEqual(ldtm2);
                if (cmpc != true) {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(newOldCreationDatesDifferentErrorTemplate)));
                }
                if (cmpm != true) {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(newOldLastModifiedDatesDifferentErrorTemplate)));
                }
            }
        }
    }
}