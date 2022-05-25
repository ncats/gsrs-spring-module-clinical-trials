package gov.hhs.gsrs.clinicaltrial.europe.exporters;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEuropeDrug;
import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEuropeMeddra;
import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEuropeProduct;
import gsrs.api.substances.SubstanceRestApi;
import gsrs.substances.dto.NameDTO;
import ix.ginas.exporters.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

@Slf4j
public class ClinicalTrialEuropeExporter implements Exporter<ClinicalTrialEurope> {

    enum ClinicalTrialEuropeDefaultColumns implements Column {
        TRIAL_NUMBER,
        TITLE,
        SUBSTANCE_NAME,
        SUBSTANCE_KEY,
        CONDITIONS,
        SPONSOR_NAME,
        RESULTS
    }

    private static SubstanceRestApi substanceRestApi;

    private final Spreadsheet spreadsheet;

    private int row=1;

    private final List<ColumnValueRecipe<ClinicalTrialEurope>> recipeMap;

    private ClinicalTrialEuropeExporter(Builder builder, SubstanceRestApi substanceRestApi){
        this.substanceRestApi = substanceRestApi;
        this.spreadsheet = builder.spreadsheet;
        this.recipeMap = builder.columns;
        
        int j=0;
        Spreadsheet.SpreadsheetRow header = spreadsheet.getRow(0);
        for(ColumnValueRecipe<ClinicalTrialEurope> col : recipeMap){
            j+= col.writeHeaderValues(header, j);
       }
    } 
    @Override
    public void export(ClinicalTrialEurope s) throws IOException {
        Spreadsheet.SpreadsheetRow row = spreadsheet.getRow( this.row++);

        int j=0;
        for(ColumnValueRecipe<ClinicalTrialEurope> recipe : recipeMap){
            j+= recipe.writeValuesFor(row, j, s);
        }
    }

    @Override
    public void close() throws IOException {
        spreadsheet.close();
    }

    private static Map<Column, ColumnValueRecipe<ClinicalTrialEurope>> DEFAULT_RECIPE_MAP;

    static{
    		
        DEFAULT_RECIPE_MAP = new LinkedHashMap<>();


        DEFAULT_RECIPE_MAP.put(ClinicalTrialEuropeDefaultColumns.TRIAL_NUMBER, SingleColumnValueRecipe.create( ClinicalTrialEuropeDefaultColumns.TRIAL_NUMBER ,(s, cell) ->{
            cell.writeString(s.getTrialNumber());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialEuropeDefaultColumns.TITLE, SingleColumnValueRecipe.create( ClinicalTrialEuropeDefaultColumns.TITLE ,(s, cell) ->{
            cell.writeString(s.getTitle());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialEuropeDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create( ClinicalTrialEuropeDefaultColumns.SUBSTANCE_NAME ,(s, cell) ->{
            StringBuilder sb = getSubstanceDetails(s, ClinicalTrialEuropeDefaultColumns.SUBSTANCE_NAME);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialEuropeDefaultColumns.SUBSTANCE_KEY, SingleColumnValueRecipe.create( ClinicalTrialEuropeDefaultColumns.SUBSTANCE_KEY ,(s, cell) ->{
            StringBuilder sb = getSubstanceDetails(s, ClinicalTrialEuropeDefaultColumns.SUBSTANCE_KEY);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialEuropeDefaultColumns.CONDITIONS, SingleColumnValueRecipe.create( ClinicalTrialEuropeDefaultColumns.CONDITIONS ,(s, cell) ->{
            StringBuilder sb = getMeddraTermDetails(s, ClinicalTrialEuropeDefaultColumns.CONDITIONS);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialEuropeDefaultColumns.SPONSOR_NAME, SingleColumnValueRecipe.create( ClinicalTrialEuropeDefaultColumns.SPONSOR_NAME ,(s, cell) ->{
            cell.writeString(s.getSponsorName());
        }));

        DEFAULT_RECIPE_MAP.put(ClinicalTrialEuropeDefaultColumns.RESULTS, SingleColumnValueRecipe.create( ClinicalTrialEuropeDefaultColumns.RESULTS ,(s, cell) ->{
            cell.writeString(s.getTrialResults());
        }));
    }
        private static StringBuilder getMeddraTermDetails(ClinicalTrialEurope s, ClinicalTrialEuropeDefaultColumns fieldName) {
            StringBuilder sb = new StringBuilder();
            List<ClinicalTrialEuropeMeddra> list = s.getClinicalTrialEuropeMeddraList();
            if(list !=null && !list.isEmpty()){
                for(ClinicalTrialEuropeMeddra item : list){
                    if(sb.length()!=0) {
                        sb.append("|");
                    }
                    switch (fieldName) {
                        case CONDITIONS:
                            String value = (item != null && item.getMeddraTerm()!=null) ? item.getMeddraTerm(): "(Null Meddra Term)";
                            sb.append(value);
                            break;
                        default:
                            break;
                    }
                }
            }
            return sb;
        }

    private static StringBuilder getSubstanceDetails(ClinicalTrialEurope s, ClinicalTrialEuropeDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();
        List<ClinicalTrialEuropeProduct> pList = s.getClinicalTrialEuropeProductList();
        if(pList !=null && !pList.isEmpty()) {
            for (ClinicalTrialEuropeProduct p : pList) {
                List<ClinicalTrialEuropeDrug> dList = p.getClinicalTrialEuropeDrugList();
                for (ClinicalTrialEuropeDrug ctd : dList) {
                    if (sb.length() != 0) {
                        sb.append("|");
                    }
                    switch (fieldName) {
                        case SUBSTANCE_NAME:
                            try {
                                Optional<List<NameDTO>> namesDTO = substanceRestApi.getNamesOfSubstance(ctd.getSubstanceKey());
                                if (namesDTO.isPresent()) {
                                    String value = namesDTO.get().stream().filter(n -> {
                                            return (n.isDisplayName());
                                    })
                                    .map(n -> n.getName()).findAny()
                                    .orElse("(No Display Name)");
                                    sb.append(value);
                                } else {
                                    sb.append("(Names Object Not Present)");
                                }
                            } catch (Exception e) {
                                sb.append("(Exception Getting Name)");
                                log.warn("Exception Getting Name in Clinical Trial Europe export.", e);
                            }
                            break;
                        case SUBSTANCE_KEY:
                            sb.append((ctd.getSubstanceKey() != null) ? ctd.getSubstanceKey() : "(No SubstanceKey)");
                            break;
                        default:
                            break;
                    }
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
        private final List<ColumnValueRecipe<ClinicalTrialEurope>> columns = new ArrayList<>();
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
            for(Map.Entry<Column, ColumnValueRecipe<ClinicalTrialEurope>> entry : DEFAULT_RECIPE_MAP.entrySet()){
                columns.add(entry.getValue());
            }
        }

        public Builder addColumn(Column column, ColumnValueRecipe<ClinicalTrialEurope> recipe){
            return addColumn(column.name(), recipe);
        }

        public Builder addColumn(String columnName, ColumnValueRecipe<ClinicalTrialEurope> recipe){
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
            ListIterator<ColumnValueRecipe<ClinicalTrialEurope>> iter = columns.listIterator();
            while(iter.hasNext()){

                ColumnValueRecipe<ClinicalTrialEurope> oldValue = iter.next();
                ColumnValueRecipe<ClinicalTrialEurope> newValue = oldValue.replaceColumnName(oldName, newName);
                if(oldValue != newValue){
                   iter.set(newValue);
                }
            }
            return this;
        }

        public ClinicalTrialEuropeExporter build(SubstanceRestApi substanceRestApi){

            return new ClinicalTrialEuropeExporter(this, substanceRestApi);
        }

        public Builder includePublicDataOnly(boolean publicOnly){
            this.publicOnly = publicOnly;
            return this;
        }

    }
}
