package gov.hhs.gsrs.clinicaltrial.us.exporters;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import ix.ginas.exporters.*;

import java.io.IOException;
import java.util.*;

enum ClinicalTrialUSDefaultColumns implements Column {
	TRIAL_NUMBER,
	TITLE,
	SUBSTANCE_KEY,
    CONDITIONS,
    SPONSOR_NAME,
    OUTCOME_MEASURES
}

public class ClinicalTrialUSExporter implements Exporter<ClinicalTrialUS> {

    private final Spreadsheet spreadsheet;

    private int row=1;

    private final List<ColumnValueRecipe<ClinicalTrialUS>> recipeMap;

    private ClinicalTrialUSExporter(Builder builder){
        this.spreadsheet = builder.spreadsheet;
        this.recipeMap = builder.columns;
        
        int j=0;
        Spreadsheet.SpreadsheetRow header = spreadsheet.getRow(0);
        for(ColumnValueRecipe<ClinicalTrialUS> col : recipeMap){
            j+= col.writeHeaderValues(header, j);
       }
    } 
    @Override
    public void export(ClinicalTrialUS s) throws IOException {
        Spreadsheet.SpreadsheetRow row = spreadsheet.getRow( this.row++);
        System.out.println("==== Inside export  ====");

        int j=0;
        for(ColumnValueRecipe<ClinicalTrialUS> recipe : recipeMap){
            j+= recipe.writeValuesFor(row, j, s);
        }
    }

    @Override
    public void close() throws IOException {
        spreadsheet.close();
    }

    private static Map<Column, ColumnValueRecipe<ClinicalTrialUS>> DEFAULT_RECIPE_MAP;

    static{
    		
        DEFAULT_RECIPE_MAP = new LinkedHashMap<>();


        DEFAULT_RECIPE_MAP.put(ClinicalTrialUSDefaultColumns.TRIAL_NUMBER, SingleColumnValueRecipe.create( ClinicalTrialUSDefaultColumns.TRIAL_NUMBER ,(s, cell) ->{
            cell.writeString(s.getTrialNumber());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialUSDefaultColumns.TITLE, SingleColumnValueRecipe.create( ClinicalTrialUSDefaultColumns.TITLE ,(s, cell) ->{
            System.out.println("DEFAULT_RECIPE_MAP TITLE: " + s.getTitle());
            cell.writeString(s.getTitle());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialUSDefaultColumns.SUBSTANCE_KEY, SingleColumnValueRecipe.create( ClinicalTrialUSDefaultColumns.SUBSTANCE_KEY ,(s, cell) ->{
            StringBuilder sb = getSubstanceDetails(s, ClinicalTrialUSDefaultColumns.SUBSTANCE_KEY);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialUSDefaultColumns.CONDITIONS, SingleColumnValueRecipe.create( ClinicalTrialUSDefaultColumns.CONDITIONS ,(s, cell) ->{
            cell.writeString(s.getConditions());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialUSDefaultColumns.SPONSOR_NAME, SingleColumnValueRecipe.create( ClinicalTrialUSDefaultColumns.SPONSOR_NAME ,(s, cell) ->{
            cell.writeString(s.getSponsor());
        }));


        DEFAULT_RECIPE_MAP.put(ClinicalTrialUSDefaultColumns.OUTCOME_MEASURES, SingleColumnValueRecipe.create( ClinicalTrialUSDefaultColumns.OUTCOME_MEASURES ,(s, cell) ->{
            cell.writeString(s.getOutcomeMeasures());
        }));

    }

    private static StringBuilder getSubstanceDetails(ClinicalTrialUS s, ClinicalTrialUSDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();

        if(s.getClinicalTrialUSDrug().size() > 0){

            for(ClinicalTrialUSDrug ctd : s.getClinicalTrialUSDrug()){
                if(sb.length()!=0) {
                    sb.append("|");
                }
                switch (fieldName) {
                    case SUBSTANCE_KEY:
                        sb.append((ctd.getSubstanceKey() != null) ? ctd.getSubstanceKey() : "(No SubstanceKey)");
                        break;
                        default: break;
                }
            }
        }
        return sb;
    }


    /**
     * Builder class that makes a SpreadsheetExporter.  By default, the default columns are used
     * but these may be modified using the add/remove column methods.
     *
     */
    public static class Builder{
        private final List<ColumnValueRecipe<ClinicalTrialUS>> columns = new ArrayList<>();
        private final Spreadsheet spreadsheet;

        private boolean publicOnly = false;

        /**
         * Create a new Builder that uses the given Spreadsheet to write to.
         * @param spreadSheet the {@link Spreadsheet} object that will be written to by this exporter. can not be null.
         *
         * @throws NullPointerException if spreadsheet is null.
         */
        public Builder(Spreadsheet spreadSheet){
            Objects.requireNonNull(spreadSheet);
            this.spreadsheet = spreadSheet;
            for(Map.Entry<Column, ColumnValueRecipe<ClinicalTrialUS>> entry : DEFAULT_RECIPE_MAP.entrySet()){
                columns.add(entry.getValue());
            }
        }

        public Builder addColumn(Column column, ColumnValueRecipe<ClinicalTrialUS> recipe){
            return addColumn(column.name(), recipe);
        }

        public Builder addColumn(String columnName, ColumnValueRecipe<ClinicalTrialUS> recipe){
            Objects.requireNonNull(columnName);
            Objects.requireNonNull(recipe);
            columns.add(recipe);

            return this;
        }

        public Builder renameColumn(Column oldColumn, String newName){
            return renameColumn(oldColumn.name(), newName);
        }
        
        public Builder renameColumn(String oldName, String newName){
            //use iterator to preserve order
            ListIterator<ColumnValueRecipe<ClinicalTrialUS>> iter = columns.listIterator();
            while(iter.hasNext()){

                ColumnValueRecipe<ClinicalTrialUS> oldValue = iter.next();
                ColumnValueRecipe<ClinicalTrialUS> newValue = oldValue.replaceColumnName(oldName, newName);
                if(oldValue != newValue){
                   iter.set(newValue);
                }
            }
            return this;
        }

        public ClinicalTrialUSExporter build(){
            return new ClinicalTrialUSExporter(this);
        }

        public Builder includePublicDataOnly(boolean publicOnly){
            this.publicOnly = publicOnly;
            return this;
        }

    }
}