package gov.hhs.gsrs.clinicaltrial.us.exporters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import ix.ginas.exporters.Exporter;
import ix.ginas.exporters.ExporterFactory;
import ix.ginas.exporters.OutputFormat;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

public class JsonExporterFactory implements ExporterFactory {

OutputFormat format = new OutputFormat("gsrs", "GSRS bulk, gzipped JSON (.gsrs)");

@Override
public boolean supports(Parameters params) {
return params.getFormat().equals(format);
}

@Override
public Set<OutputFormat> getSupportedFormats() {
return Collections.singleton(format);
}

@Override
public Exporter<ClinicalTrialUS> createNewExporter(OutputStream out, Parameters params) throws IOException {
// if(params.shouldCompress()) {
return new JsonExporter(new GZIPOutputStream(out));
//        }
//        return new JsonExporter(out);
}

@Override
public JsonNode getSchema() {
ObjectNode parameters = JsonNodeFactory.instance.objectNode();
parameters.put("excludedCollections", "String[]");
parameters.put("recordLimit", Integer.class.getName());
return generateSchemaNode("JSON Exporter Parameters", parameters);
}


}

