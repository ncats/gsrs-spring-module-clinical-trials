package gov.nih.ncats.clinicaltrial.validators;

import gov.nih.ncats.clinicaltrial.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.ClinicalTrialRepository;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DuplicateTitleValidator implements ValidatorPlugin<ClinicalTrial> {
    @Autowired
    private ClinicalTrialRepository repository;

    @Override
    public boolean supports(ClinicalTrial newValue, ClinicalTrial oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return methodType != ValidatorConfig.METHOD_TYPE.BATCH;
    }


    // this is just an example, change to something more relevant later.
    @Override
    public void validate(ClinicalTrial objnew, ClinicalTrial objold, ValidatorCallback callback) {
        System.out.println("Skipped, possible validator task with repository access here.");
        /*
        Optional<ClinicalTrial> found = repository.findByTitle(objnew.getTitle());
        if(found.isPresent()){
            if(objold == null || !objnew.getTrialNumber().equals(found.get().getTrialNumber())){
                callback.addMessage(GinasProcessingMessage.ERROR_MESSAGE("Duplicate title '" + objnew.getTitle() +"'"));
            }
        }
        */
    }
}
