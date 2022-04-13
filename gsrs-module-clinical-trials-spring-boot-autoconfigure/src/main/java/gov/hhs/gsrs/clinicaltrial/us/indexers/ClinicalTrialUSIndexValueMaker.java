package gov.hhs.gsrs.clinicaltrial.us.indexers;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ClinicalTrialUSIndexValueMaker implements IndexValueMaker<ClinicalTrialUS> {
    @Override
    public Class<ClinicalTrialUS> getIndexedEntityClass() {
        return ClinicalTrialUS.class;
    }

    @Override
    public void createIndexableValues(ClinicalTrialUS clinicalTrialUS, Consumer<IndexableValue> consumer) {
        if(clinicalTrialUS.getTrialNumber()!=null){
            consumer.accept(IndexableValue.simpleFacetStringValue("root_trialNumber", clinicalTrialUS.getTrialNumber()));
        }
    }
}