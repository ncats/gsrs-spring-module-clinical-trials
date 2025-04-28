package gov.hhs.gsrs.clinicaltrial.us.exporters;

import com.fasterxml.jackson.databind.ObjectWriter;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import ix.core.controllers.EntityFactory;
import ix.ginas.exporters.Exporter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class JsonExporter implements Exporter<ClinicalTrialUS> {
private final BufferedWriter out;

private static final String LEADING_HEADER= "\t\t";
private final ObjectWriter writer =  EntityFactory.EntityMapper.FULL_ENTITY_MAPPER().writer();

public JsonExporter(OutputStream out) throws IOException{
Objects.requireNonNull(out);
this.out = new BufferedWriter(new OutputStreamWriter(out));
}
@Override
public void export(ClinicalTrialUS obj) throws IOException {
out.write(LEADING_HEADER);
//need to write as a string because the json writer
//will sometimes close the writer depending on which implementation it is...
out.write(writer.writeValueAsString(obj));
out.newLine();
}

@Override
public void close() throws IOException {
out.close();
}
}
