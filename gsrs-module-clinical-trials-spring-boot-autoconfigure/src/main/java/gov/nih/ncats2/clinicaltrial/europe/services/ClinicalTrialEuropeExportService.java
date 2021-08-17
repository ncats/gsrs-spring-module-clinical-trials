package gov.nih.ncats2.clinicaltrial.europe.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.nih.ncats2.clinicaltrial.europe.repositories.ClinicalTrialEuropeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.ServletContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ClinicalTrialEuropeExportService {

    public static final String  CONTEXT = "clinicaltrialeurope";

    @Autowired
    private ClinicalTrialEuropeEntityService clinicalTrialEuropeEntityService;

    @Autowired
    private ClinicalTrialEuropeRepository clinicalTrialEuropeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ServletContext servletContext;

    public ClinicalTrialEuropeExportService() {}

    public void exportToAllToJsonFile() throws IOException {
        Path path = Paths.get(servletContext.getRealPath("/data/exports") + "cteu-json-dump.txt");
        List<ClinicalTrialEurope> list = clinicalTrialEuropeRepository.findAll();
        try {
            String filePathString = "C:/Users/Alexander.Welsch/Documents/d/gsrs3/gsbmct/1/gsbmct/data/exports/" + "cteu-json-dump.txt";

            FileOutputStream fos = new FileOutputStream(filePathString);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);
            list.forEach(cte -> {
                try {
                    writer.write(clinicalTrialEuropeEntityService.toJson(cte).toString());
                    writer.newLine();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            });
            writer.close();
        } catch (IllegalStateException e) {
            throw new IOException(e.getCause());
        }
    }
}

