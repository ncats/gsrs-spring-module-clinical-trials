package gov.hhs.gsrs.clinicaltrial.europe.validators;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gov.nih.ncats.common.util.TimeUtil;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Date;

public class GeneralTrialSetCreateUpdateDateOnUpdateIfNullValidator implements ValidatorPlugin<ClinicalTrialEurope> {

    @Autowired
    private ClinicalTrialUSRepository repository;

    @Autowired
    private Environment env;

    @Override
    public boolean supports(ClinicalTrialEurope newValue, ClinicalTrialEurope oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }
    final String createDateAutomaticallySetOnNull = "The creation date was to the current date because it was null.";
    final String lastModifiedDateAutomaticallySetOnNull = "The update date was to the current date because it was null.";

    @Override
    public void validate(ClinicalTrialEurope objnew, ClinicalTrialEurope objold, ValidatorCallback callback) {
        if (objnew != null && objnew.getCreationDate() == null) {
            Date date1 = TimeUtil.getCurrentDate();
            objnew.setCreationDate(date1);
            callback.addMessage(GinasProcessingMessage.INFO_MESSAGE(String.format(createDateAutomaticallySetOnNull)));
        }
        if (objnew != null && objnew.getLastModifiedDate() == null) {
            Date date2 = TimeUtil.getCurrentDate();
            objnew.setLastModifiedDate(date2);
            callback.addMessage(GinasProcessingMessage.INFO_MESSAGE(String.format(lastModifiedDateAutomaticallySetOnNull)));
        }

    }
}