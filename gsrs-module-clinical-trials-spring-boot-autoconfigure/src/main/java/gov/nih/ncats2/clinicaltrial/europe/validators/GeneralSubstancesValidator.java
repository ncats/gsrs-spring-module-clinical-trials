package gov.nih.ncats2.clinicaltrial.europe.validators;

import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEuropeProduct;
import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEuropeDrug;
import gov.nih.ncats2.clinicaltrial.us.services.SubstanceAPIService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class GeneralSubstancesValidator implements ValidatorPlugin<ClinicalTrialEurope> {

    // 404 message with incorrectly formatted flex id.
    // takes a long time to come back.
    // How can we abort before we get to that?

    @Autowired
    private SubstanceAPIService substanceAPIService;

    @Autowired
    private Environment env;

    // @Value("${mygsrs.clinicaltrial.us.substanceKeyPatternRegex}")
    // private String substanceKeyPatternRegex;

    // @Value("${mygsrs.clinicaltrial.us.substance.linking.keyType.value}")
    // private String substanceKeyTypeValue;

    // @Value("${mygsrs.clinicaltrial.eu.substanceKeyPatternRegex}")
    // private String substanceKeyPatternRegex;

    @Override
    public boolean supports(ClinicalTrialEurope newValue, ClinicalTrialEurope oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE
               || methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }

    final String substanceKeyNullErrorTemplate = "Substance Key is null.";
    final String duplicateSubstanceErrorTemplate = "Substance Key [%s] is a duplicate.";
    final String badlyFormattedSubstanceKeyTemplate = "Substance Key [%s] had an incorrect format.";
    // final Pattern substanceKeyPattern = Pattern.compile(substanceKeyPatternRegex, Pattern.CASE_INSENSITIVE);
    final String substanceKeyTypeNullErrorTemplate = "Substance Key Type is null.";
    // final String substanceKeyTypeBadValueErrorTemplate = "Substance Key Type should be " + substanceLinkingKeyTypeAgencyCode + ".";

    @Override
    public void validate(ClinicalTrialEurope objnew, ClinicalTrialEurope objold, ValidatorCallback callback) {
        System.out.println("Inside GeneralSubstancesValidator");
        String substanceKeyTypeValue = "UUID";

        String substanceKeyPatternRegex = "^[-0-9a-f]{36}$";
        String substanceKeyTypeBadValueErrorTemplate = "Substance Key Type should be " + substanceKeyTypeValue  + ".";

        Pattern substanceKeyPattern = Pattern.compile(substanceKeyPatternRegex, Pattern.CASE_INSENSITIVE);
        List<ClinicalTrialEuropeProduct> products = objnew.getClinicalTrialEuropeProductList();
        for(ClinicalTrialEuropeProduct product: products) {
            List<ClinicalTrialEuropeDrug> ctds = product.getClinicalTrialEuropeDrugList();
            HashMap<String, Boolean> map = new HashMap<>();
            for(ClinicalTrialEuropeDrug ctd : ctds) {
                // System.out.println("Inside GeneralSubstancesValidator Loop");
                String substanceKeyType = ctd.getSubstanceKeyType();
                if(substanceKeyType == null) {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(substanceKeyTypeNullErrorTemplate)));
                    continue;
                }
                if(!substanceKeyType.equals(substanceKeyTypeValue) ) {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(substanceKeyTypeBadValueErrorTemplate)));
                    continue;
                }
                String substanceKey = ctd.getSubstanceKey();
                if(substanceKey == null) {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(substanceKeyNullErrorTemplate)));
                    continue;
                }
                // boolean formatOK = isUuid(uuid);
                boolean formatOK = substanceKeyPattern.matcher(substanceKey).matches();
                // System.out.println("formatOK value:" + formatOK);
                if(!formatOK) {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(badlyFormattedSubstanceKeyTemplate, substanceKey)));
                    continue;
                }
                System.out.println("\n\n===== Substance  key: =====\n\n" + substanceKey);
                if(map.get(substanceKey)==null) {
                    map.put(substanceKey, true);
                } else {
                    callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE(String.format(duplicateSubstanceErrorTemplate,ctd.getSubstanceKey())));
                }
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