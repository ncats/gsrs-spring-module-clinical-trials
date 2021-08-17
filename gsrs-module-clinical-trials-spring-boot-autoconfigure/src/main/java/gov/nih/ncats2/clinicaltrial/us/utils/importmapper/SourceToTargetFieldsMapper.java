package gov.nih.ncats2.clinicaltrial.us.utils.importmapper;

import gov.nih.ncats2.clinicaltrial.us.models.ClinicalTrialUS;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SourceToTargetFieldsMapper {
    public String LastUpdatedFieldName = "Last Update Posted";


    public static String[] simpleSourceToTargetMap() {
        return new String[]{
                "Rank|_none",
                "NCT Number|trialNumber",
                "Title|title",
                "Acronym|acronym",
                "Status|status",
                "Study Results|studyResults",
                "Conditions|conditions",
                "Interventions|intervention",
                "Outcome Measures|outcomeMeasures",
                "Sponsor/Collaborators|sponsor",
                "Gender|gender",
                "Age|ageGroups",
                "Phases|phases",
                "Enrollment|enrollment",
                "Funded Bys|fundedBys",
                "Study Type|studyTypes",
                "Study Designs|studyDesigns",
                "Other IDs|otherIds",
                "Start Date|startDate",
                "Primary Completion Date|primaryCompletionDate",
                "Completion Date|completionDate",
                "First Posted|firstReceived",
                "Results First Posted|resultsFirstReceived",
                "Last Update Posted|lastUpdated",
                "Locations|locations",
                "Study Documents|_none",
                "Url|url"
        };
    }


    public static String[] sourceFields() {
        List l = new ArrayList<String>();
        String[] arr1 = simpleSourceToTargetMap();
        String[] arr2 = new String[arr1.length];
        int c=0;
        for (String fieldName : simpleSourceToTargetMap()) {
            String[] values = fieldName.split("\\|");
            arr2[c++] = values[0];
        }
        return arr2;
    }


    public static void dumpTargetColumns() {
        List<String> columns =
            Arrays.asList(ClinicalTrialUS.class.getFields())
                .stream()
                .filter(f -> f.getAnnotation(Column.class) != null)
                .map(f -> "\n" + f.getType() + ":" + f.getAnnotation(Column.class).name() + ":" + String.valueOf(f.getAnnotation(Column.class).length()))
                .collect(Collectors.toList());
        System.out.println(columns.toString());
    }

    public static void dumpExtendedSourceToTargetFieldsMap()  {
        LinkedHashMap<String, SourceToTargetField> map  = generateExtendedSourceToTargetFieldsMap();
        for(String key : map.keySet()) {
            SourceToTargetField sourceToTargetField = map.get(key);
            System.out.printf("%s|%s|%s|%s\n", sourceToTargetField.sourceName, sourceToTargetField.targetName, sourceToTargetField.targetType, sourceToTargetField.targetLength);
        }
    }

    public static LinkedHashMap<String, TargetAnnotationField> generateTargetAnnotationsHashMap() {
        LinkedHashMap targetAnnotationFields = new LinkedHashMap<String, TargetAnnotationField>();
        for (Field field : ClinicalTrialUS.class.getFields()) {
            Column c = field.getAnnotation(Column.class);
            TargetAnnotationField taf = new TargetAnnotationField();
            if (c != null) {
                taf.name = field.getName();
                taf.type = String.valueOf(field.getType());
                taf.length = field.getAnnotation(Column.class).length();
                targetAnnotationFields.put(taf.name, taf);
            }
        }
        return targetAnnotationFields;
    }

    public static LinkedHashMap<String, SourceToTargetField> generateExtendedSourceToTargetFieldsMap()  {
        LinkedHashMap<String, TargetAnnotationField> annotations = generateTargetAnnotationsHashMap();
        LinkedHashMap sourceToTargetFieldLHM = new LinkedHashMap<String, SourceToTargetField>();
        for (String fieldName : simpleSourceToTargetMap()) {
            SourceToTargetField sourceToTargetField = new SourceToTargetField();
            String[] values = fieldName.split("\\|");
            sourceToTargetField.sourceName = values[0];
            sourceToTargetField.targetName = values[1];
            if (sourceToTargetField.targetName!="_none") {
                TargetAnnotationField taf = annotations.get(sourceToTargetField.targetName);
                if (taf != null && taf.length!=null) {
                    sourceToTargetField.targetLength = taf.length;
                }
                if (taf != null && taf.type!=null) {
                    sourceToTargetField.targetType = taf.type;
                }
                sourceToTargetFieldLHM.put(sourceToTargetField.sourceName, sourceToTargetField);
            }
        }
        return sourceToTargetFieldLHM;
    }
}