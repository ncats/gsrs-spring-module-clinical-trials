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

import java.util.Date;

public class GeneralTrialSetCreateDateOnCreateIfNullValidator implements ValidatorPlugin<ClinicalTrialUS> {

    @Autowired
    private ClinicalTrialUSRepository repository;

    @Autowired
    private Environment env;

    @Override
    public boolean supports(ClinicalTrialUS newValue, ClinicalTrialUS oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE);
    }
    final String createDateAutomaticallySetOnNull = "The creation date was set to the current date because it was null.";
    @Override
    public void validate(ClinicalTrialUS objnew, ClinicalTrialUS objold, ValidatorCallback callback) {
        System.out.println("Inside TrialSetCreateDateOnCreateIfNullValidator");
        if (objnew != null && objnew.getCreationDate() == null) {
            Date date1 = TimeUtil.getCurrentDate();
            objnew.setCreationDate(date1);
            callback.addMessage(GinasProcessingMessage.INFO_MESSAGE(String.format(createDateAutomaticallySetOnNull)));
        }
    }
}