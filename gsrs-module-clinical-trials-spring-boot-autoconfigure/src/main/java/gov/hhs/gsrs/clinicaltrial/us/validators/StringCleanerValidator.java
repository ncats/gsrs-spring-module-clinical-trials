package gov.hhs.gsrs.clinicaltrial.us.validators;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gov.hhs.gsrs.clinicaltrial.us.services.ClinicalTrialUSEntityService;
import gsrs.controller.GsrsControllerConfiguration;
import gsrs.validator.ValidatorConfig;
import ix.core.util.EntityUtils;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Optional;

public class StringCleanerValidator implements ValidatorPlugin<ClinicalTrialUS> {

    @Autowired
    private Environment env;

    @Override
    public boolean supports(ClinicalTrialUS newValue, ClinicalTrialUS oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return (methodType == ValidatorConfig.METHOD_TYPE.CREATE
        || methodType == ValidatorConfig.METHOD_TYPE.UPDATE);
    }

    final String WHITESPACEFOUNDTEMPLATE = "StringCleaner has removed whitespace.";

    @Override
    public void validate(ClinicalTrialUS objnew, ClinicalTrialUS objold, ValidatorCallback callback) {
        boolean hasWhitespace = false;
        if(hasWhitespace) {
            callback.addMessage(GinasProcessingMessage.WARNING_MESSAGE(String.format(WHITESPACEFOUNDTEMPLATE)));
        }


        EntityUtils.EntityWrapper  ew = EntityUtils.EntityWrapper.of(objnew);
        ew.traverse().execute((p, child) -> {
            EntityUtils.EntityWrapper<EntityUtils.EntityWrapper> wrapped = EntityUtils.EntityWrapper.of(child);
            boolean isEntity = wrapped.isEntity();
            boolean isRootIndexed = wrapped.isRootIndex();

            if (isEntity && isRootIndexed) {
                try {
                    System.out.println("wrapped: " + wrapped);
                    Optional<EntityUtils.FieldMeta> fieldMeta=wrapped.getFieldInfo()
                    .stream()
                    .findFirst();
                    if(fieldMeta.isPresent()){
                        System.out.println(fieldMeta.get().getClass());
                    }
                } catch (Throwable t) {
                    System.out.println("error handling:" + wrapped);
                }
            }
        });

     }





}