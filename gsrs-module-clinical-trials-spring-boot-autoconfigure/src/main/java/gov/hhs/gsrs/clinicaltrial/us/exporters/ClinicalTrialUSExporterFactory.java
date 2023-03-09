package gov.hhs.gsrs.clinicaltrial.us.exporters;

import gsrs.api.substances.SubstanceRestApi;
import ix.ginas.exporters.ExporterFactory;
import ix.ginas.exporters.OutputFormat;
import ix.ginas.exporters.Spreadsheet;
import ix.ginas.exporters.SpreadsheetFormat;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Set;

public class ClinicalTrialUSExporterFactory implements ExporterFactory {
    @Autowired
    public SubstanceRestApi substanceRestApi;


    OutputFormat format = new OutputFormat("ctus.xlsx", "Spreadsheet File");

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
        Spreadsheet spreadsheet = format.createSpreadsheet(out);

        ClinicalTrialUSExporter.Builder builder = new ClinicalTrialUSExporter.Builder(spreadsheet);

        configure(builder, params);

        return builder.build(substanceRestApi);
    }
    
    protected void configure(ClinicalTrialUSExporter.Builder builder, Parameters params){
        builder.includePublicDataOnly(params.publicOnly());
    }

}