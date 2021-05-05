package gov.nih.ncats.clinicaltrial.us.validators;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialDrug;
import gov.nih.ncats.clinicaltrial.us.services.SubstanceAPIService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class GeneralSubstancesValidator implements ValidatorPlugin<ClinicalTrial> {

    // 404 message with incorrectly formatted flex id.
    // takes a long time to come back.
    // How can we abort before we get to that?


    @Autowired
    private SubstanceAPIService substanceAPIService;

    @Autowired
    private Environment env;

    @Override
    public boolean supports(ClinicalTrial newValue, ClinicalTrial oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE
               || methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }

    final String substanceUuidNullErrorTemplate = "Substance UUID is null.";
    final String duplicateSubstanceErrorTemplate = "Substance UUID [%s] is a duplicate.";
    final String badlyFormattedSubstanceUuidTemplate = "Substance UUID [%s] had an incorrect format.";
    final Pattern uuidPattern = Pattern.compile("^[-0-9a-f]{36}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(ClinicalTrial objnew, ClinicalTrial objold, ValidatorCallback callback) {
        System.out.println("Inside GeneralSubstancesValidator");
        Set<ClinicalTrialDrug> ctds = objnew.getClinicalTrialDrug();
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
            for (ClinicalTrialDrug ctd : ctds) {
                System.out.println("Inside GeneralSubstancesValidator Loop");

                String uuid = ctd.getSubstanceUuid();
                if(uuid == null) {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(substanceUuidNullErrorTemplate)));
                    continue;
                }
                // boolean formatOK = isUuid(uuid);
                boolean formatOK = uuidPattern.matcher(uuid).matches();

                System.out.println("formatOK value:" + formatOK);

                if(!formatOK) {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(badlyFormattedSubstanceUuidTemplate, uuid)));
                    continue;
                }
                if(map.get(uuid)==null) {
                    map.put(uuid, true);
                } else {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(duplicateSubstanceErrorTemplate,ctd.getSubstanceUuid())));
                }
            }
    }

    static boolean isUuid(String s) {
        // not working right in java 8
        try {
            UUID.fromString(s);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}