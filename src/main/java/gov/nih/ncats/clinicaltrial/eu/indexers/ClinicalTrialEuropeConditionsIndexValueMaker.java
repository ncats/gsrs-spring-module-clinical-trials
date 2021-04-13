package gov.nih.ncats.clinicaltrial.eu.indexers;

import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClinicalTrialEuropeConditionsIndexValueMaker implements IndexValueMaker<ClinicalTrialEurope> {
    private static final Pattern covid19Pattern = Pattern.compile("covid19|SARSCoV2", Pattern.CASE_INSENSITIVE);
    @Override

    public Class<ClinicalTrialEurope> getIndexedEntityClass() {
        return ClinicalTrialEurope.class;
    }

    @Override
    public void createIndexableValues(ClinicalTrialEurope clinicalTrialEurope, Consumer<IndexableValue> consumer) {
        System.out.println("Develop value maker later.");
/*
        String conditions = clinicalTrialEurope.getConditions();
        if(conditions ==null){
            return;
        }
        Matcher matcher = covid19Pattern.matcher(conditions);
        if(matcher.find()){
            conditions = conditions.replace("-","");
            consumer.accept(IndexableValue.simpleFacetStringValue("conditions.covid19Pattern", conditions));
        }

 */
    }
}
