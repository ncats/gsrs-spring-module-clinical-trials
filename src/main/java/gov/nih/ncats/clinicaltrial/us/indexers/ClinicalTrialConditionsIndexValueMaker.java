package gov.nih.ncats.clinicaltrial.us.indexers;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClinicalTrialConditionsIndexValueMaker implements IndexValueMaker<ClinicalTrial> {
    private static final Pattern covid19Pattern = Pattern.compile("covid19|SARSCoV2", Pattern.CASE_INSENSITIVE);
    @Override
    public Class<ClinicalTrial> getIndexedEntityClass() {
        return ClinicalTrial.class;
    }

    @Override
    public void createIndexableValues(ClinicalTrial clinicalTrial, Consumer<IndexableValue> consumer) {
        String conditions = clinicalTrial.getConditions();
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
