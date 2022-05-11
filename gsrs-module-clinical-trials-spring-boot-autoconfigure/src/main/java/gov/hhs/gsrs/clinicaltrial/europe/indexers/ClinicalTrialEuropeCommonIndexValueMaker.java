package gov.hhs.gsrs.clinicaltrial.europe.indexers;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
public class ClinicalTrialEuropeCommonIndexValueMaker implements IndexValueMaker<ClinicalTrialEurope> {
    @Override
    public Class<ClinicalTrialEurope> getIndexedEntityClass() {
        return ClinicalTrialEurope.class;
    }

    @Override
    public void createIndexableValues(ClinicalTrialEurope clinicalTrialEurope, Consumer<IndexableValue> consumer) {
    }


}
