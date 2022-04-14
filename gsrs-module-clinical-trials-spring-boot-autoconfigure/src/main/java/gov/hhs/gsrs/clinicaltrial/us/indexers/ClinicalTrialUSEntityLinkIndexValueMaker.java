package gov.hhs.gsrs.clinicaltrial.us.indexers;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class ClinicalTrialUSEntityLinkIndexValueMaker implements IndexValueMaker<ClinicalTrialUS> {
    @Override
    public Class<ClinicalTrialUS> getIndexedEntityClass() {
        return ClinicalTrialUS.class;
    }

    @Override
    public void createIndexableValues(ClinicalTrialUS clinicalTrialUS, Consumer<IndexableValue> consumer) {
        // if(clinicalTrialUS.getTrialNumber()!=null){
        //    consumer.accept(IndexableValue.simpleStringValue("root_trialNumber", clinicalTrialUS.getTrialNumber()));
        // }

        List<ClinicalTrialUSDrug> substances = clinicalTrialUS.getClinicalTrialUSDrug();
        if (substances != null) {
            for (ClinicalTrialUSDrug substance : substances) {
                if (substance.getSubstanceKey() != null) {
                    consumer.accept(IndexableValue.simpleFacetStringValue("entity_link_substances", substance.getSubstanceKey()));
                }
            }
        }

    }
}
