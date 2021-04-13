package gov.nih.ncats.clinicaltrial.eu.validators;

import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gov.nih.ncats.clinicaltrial.eu.repositories.ClinicalTrialEuropeRepository;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.ValidatorCallback;
import ix.ginas.utils.validation.ValidatorPlugin;
import org.springframework.beans.factory.annotation.Autowired;

public class DuplicateTitleValidator implements ValidatorPlugin<ClinicalTrialEurope> {
    @Autowired
    private ClinicalTrialEuropeRepository repository;

    @Override
    public boolean supports(ClinicalTrialEurope newValue, ClinicalTrialEurope oldValue, ValidatorConfig.METHOD_TYPE methodType) {
        return methodType != ValidatorConfig.METHOD_TYPE.BATCH;
    }


    // this is just an example, change to something more relevant later.
    @Override
    public void validate(ClinicalTrialEurope objnew, ClinicalTrialEurope objold, ValidatorCallback callback) {
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
