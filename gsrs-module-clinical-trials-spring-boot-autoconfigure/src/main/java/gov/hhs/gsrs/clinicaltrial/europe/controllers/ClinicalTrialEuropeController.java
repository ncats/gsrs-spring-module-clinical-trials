package gov.hhs.gsrs.clinicaltrial.europe.controllers;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.hhs.gsrs.clinicaltrial.europe.services.ClinicalTrialEuropeEntityService;
// import gov.hhs.gsrs.clinicaltrial.europe.services.ClinicalTrialEuropeExportService;
import gov.hhs.gsrs.clinicaltrial.europe.services.ClinicalTrialEuropeLegacySearchService;
import gsrs.controller.EtagLegacySearchEntityController;
import gsrs.controller.GetGsrsRestApiMapping;
import gsrs.controller.GsrsRestApiController;
import gsrs.controller.IdHelpers;
import gsrs.legacy.LegacyGsrsSearchService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Stream;

/**
 * GSRS Rest API controller for the {@link ClinicalTrialEurope} entity.
 */
@GsrsRestApiController(context = ClinicalTrialEuropeEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
@ExposesResourceFor(ClinicalTrialEurope.class)
public class ClinicalTrialEuropeController extends EtagLegacySearchEntityController<ClinicalTrialEuropeController, ClinicalTrialEurope, String> {
    @Autowired
    private ClinicalTrialEuropeLegacySearchService clinicalTrialEuropeLegacySearchService;

    // alex trying this
    @Autowired
    private ClinicalTrialEuropeEntityService clinicalTrialEuropeEntityService;


    @Autowired
    private EntityLinks entityLinks;


    @Override
    protected LegacyGsrsSearchService<ClinicalTrialEurope> getlegacyGsrsSearchService() {
        return clinicalTrialEuropeLegacySearchService;
    }
    @Override
    protected ClinicalTrialEuropeEntityService getEntityService() {
        return clinicalTrialEuropeEntityService;
    }

    protected Stream<ClinicalTrialEurope> filterStream(Stream<ClinicalTrialEurope> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }

    public ClinicalTrialEuropeController() {}

//    @Autowired
//    ClinicalTrialEuropeExportService clinicalTrialEuropeExportService;

    // experimental
    /*
    @GetGsrsRestApiMapping("/@exp_exportToAllToJsonFile")
    public JSONObject test_exportToAllToJsonFile() {
        Map<String, String> hm = new Hashtable<String, String>();
        try {
            clinicalTrialEuropeExportService.exportToAllToJsonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }
    */
}

