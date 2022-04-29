package gov.hhs.gsrs.clinicaltrial.us.exporters;

import ix.ginas.exporters.ExporterFactory;
import ix.ginas.exporters.OutputFormat;
import ix.ginas.exporters.Spreadsheet;
import ix.ginas.exporters.SpreadsheetFormat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

;

public class ClinicalTrialUSExporterFactory implements ExporterFactory {

/*

    private static final Set<OutputFormat> FORMATS;
    static{
        Set<OutputFormat> set = new LinkedHashSet<>();
        set.add(SpreadsheetFormat.TSV);
        set.add(SpreadsheetFormat.CSV);
        set.add(SpreadsheetFormat.XLSX);

        FORMATS = Collections.unmodifiableSet(set);
    }

    @Override
    public Set<OutputFormat> getSupportedFormats() {
        return FORMATS;
    }

    @Override
    public boolean supports(Parameters params) {
        return params.getFormat() instanceof SpreadsheetFormat;
    }
*/
    OutputFormat format = new OutputFormat("ctxlsx", "Spreadsheet File");

    @Override
    public boolean supports(Parameters params) {
        return params.getFormat().equals(format);
    }

    @Override
    public Set<OutputFormat> getSupportedFormats() {
        return Collections.singleton(format);
    }


    @Override
    public ClinicalTrialUSExporter createNewExporter(OutputStream out, Parameters params) throws IOException {

        SpreadsheetFormat format = SpreadsheetFormat.XLSX;
        Spreadsheet spreadsheet = format.createSpeadsheet(out);

        ClinicalTrialUSExporter.Builder builder = new ClinicalTrialUSExporter.Builder(spreadsheet);

        configure(builder, params);

        return builder.build();
    }
    
    protected void configure(ClinicalTrialUSExporter.Builder builder, Parameters params){
        builder.includePublicDataOnly(params.publicOnly());
    }

}