    package gov.hhs.gsrs.clinicaltrial.us.indexers;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.nih.ncats.common.util.TimeUtil;
import ix.core.search.text.IndexValueMaker;
import ix.core.search.text.IndexableValue;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
public class ClinicalTrialUSCommonIndexValueMaker implements IndexValueMaker<ClinicalTrialUS> {
    @Override
    public Class<ClinicalTrialUS> getIndexedEntityClass() {
        return ClinicalTrialUS.class;
    }

    @Override
    public void createIndexableValues(ClinicalTrialUS clinicalTrialUS, Consumer<IndexableValue> consumer) {

        /*
        getCTMatchingComplete ==> CT Matching Complete
        getHasSubstances ==> Has Substances
        getLastUpdatedYear ==> Last Updated Year
        getPrimaryCompletionYear ==> Primary Completion Year
        getFirstPostedYear ==> First Received Year
        getInterventionTypeIndexing ==> Intervention Type
        getConditionsIndexing ==> Conditions
        getInterventionIndexing ==> Interventions
        getSponsorIndexing ==> Sponsors
        getAgeGroupIndexing ==> Age Groups
        getOutcomeMeasureIndexing ==> Outcome Measures
        getStudyTypesIndexing ==> Study Types
         */
        String value = null;
        if (clinicalTrialUS.gsrsMatchingComplete == true) {
            consumer.accept(IndexableValue.simpleFacetStringValue("CT Matching Complete", "Complete"));
        } else {
            consumer.accept(IndexableValue.simpleFacetStringValue("CT Matching Complete", "Not Complete"));
        }
        if (clinicalTrialUS.clinicalTrialUSDrug != null && !clinicalTrialUS.clinicalTrialUSDrug.isEmpty()) {
            consumer.accept(IndexableValue.simpleFacetStringValue("Has Substances", "Has Substances"));
        } else  {
            consumer.accept(IndexableValue.simpleFacetStringValue("Has Substances", "No Substances"));
        }

        if (clinicalTrialUS.lastUpdated!=null) {
                consumer.accept(IndexableValue.simpleFacetStringValue("Last Updated Year", String.valueOf(TimeUtil.asLocalDate(clinicalTrialUS.lastUpdated).getYear())));
        } else {
            consumer.accept(IndexableValue.simpleFacetStringValue("Last Updated Year", "No Year"));
        }

        if (clinicalTrialUS.primaryCompletionDate!=null) {
            consumer.accept(IndexableValue.simpleFacetStringValue("Primary Completion Year",
                    String.valueOf(TimeUtil.asLocalDate(clinicalTrialUS.primaryCompletionDate).getYear())));
        } else {
            consumer.accept(IndexableValue.simpleFacetStringValue("Primary Completion Year", "No Year" ));
        }

        if (clinicalTrialUS.firstReceived!=null) {
            consumer.accept(IndexableValue.simpleFacetStringValue("First Received Year",
                    String.valueOf(TimeUtil.asLocalDate(clinicalTrialUS.firstReceived).getYear())));
        } else {
            consumer.accept(IndexableValue.simpleFacetStringValue("First Received Year", "No Year" ));
        }

        Set<String> interventionTypeValues = getInterventionTypeIndexing(clinicalTrialUS);
        if (interventionTypeValues!=null) {
            for(String interventionTypeValue: interventionTypeValues) {
                consumer.accept(IndexableValue.simpleFacetStringValue("Intervention Type", interventionTypeValue));
            }
        }

        if (clinicalTrialUS.conditions != null) {
            for(String conditionValue: Arrays.asList(clinicalTrialUS.conditions.split("\\|"))) {
                consumer.accept(IndexableValue.simpleFacetStringValue("Conditions", conditionValue).suggestable());
            }
        }

        if (clinicalTrialUS.intervention != null) {
            for(String interventionValue: Arrays.asList(clinicalTrialUS.intervention.split("\\|"))) {
                consumer.accept(IndexableValue.simpleFacetStringValue("Interventions", interventionValue));
            }
        }

        if (clinicalTrialUS.sponsor != null) {
            for(String sponsorValue: Arrays.asList(clinicalTrialUS.sponsor.split("\\|"))) {
                consumer.accept(IndexableValue.simpleFacetStringValue("Sponsors", sponsorValue).suggestable());
            }
        }

        if (clinicalTrialUS.ageGroups != null) {
            for(String ageGroupValue: Arrays.asList(clinicalTrialUS.ageGroups.split("\\|"))) {
                consumer.accept(IndexableValue.simpleFacetStringValue("Age Groups", ageGroupValue));
            }
        }

        if (clinicalTrialUS.outcomeMeasures != null) {
            for(String outcomeMeasureValue: Arrays.asList(clinicalTrialUS.outcomeMeasures.split("\\|"))) {
                consumer.accept(IndexableValue.simpleFacetStringValue("Outcome Measures", outcomeMeasureValue));
            }
        }

        if (clinicalTrialUS.studyTypes != null) {
            for(String studyTypeValue: Arrays.asList(clinicalTrialUS.studyTypes.split("\\|"))) {
                consumer.accept(IndexableValue.simpleFacetStringValue("Study Types", studyTypeValue));
            }
        }

    }

    public Set<String> getInterventionTypeIndexing(ClinicalTrialUS c) {
        if (c.intervention != null) {
            Set<String> types = new HashSet<String>();
            for (String i: Arrays.asList(c.intervention.split("\\|"))) {
                String type= i.split("[:]")[0];
                switch(type){
                    case "Drug":
                        types.add("Drug");
                    break;
                    case "Combination Product":
                        types.add("Combination Product");
                        break;
                    case "Procedure":
                        types.add("Procedure");
                        break;
                    case "Behavioral":
                        types.add("Behavioral");
                        break;
                    case "Biological":
                        types.add("Biological");
                        break;
                    case "Other":
                        types.add("Other");
                        break;
                    case "Device":
                        types.add("Device");
                        break;
                    case "Dietary Supplement":
                        types.add("Dietary Supplement");
                        break;
                    default:
                        types.add("Not-classified");
                        break;
                }
            }
            return types;
        }
        return null;
    }
}
