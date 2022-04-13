package gov.hhs.gsrs.clinicaltrial.us.indexers;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClinicalTrialUSIndexValueMaker implements IndexValueMaker<ClinicalTrialUS> {
    @Override
    public Class<ClinicalTrialUS> getIndexedEntityClass() {
        return ClinicalTrialUS.class;
    }

    @Override
    public void createIndexableValues(ClinicalTrialUS clinicalTrialUS, Consumer<IndexableValue> consumer) {
        String conditions = clinicalTrialUS.getConditions();
        if(conditions ==null){
            return;
        }
        Matcher matcher = covid19Pattern.matcher(conditions);
        if(matcher.find()){
            conditions = conditions.replace("-","");
            consumer.accept(IndexableValue.simpleFacetStringValue("conditions.covid19Pattern", conditions));
        }
    }
}
