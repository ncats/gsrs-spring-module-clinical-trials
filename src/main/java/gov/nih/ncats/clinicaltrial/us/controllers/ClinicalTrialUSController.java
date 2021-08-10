package gov.nih.ncats.clinicaltrial.us.controllers;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialUS;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialUSEntityService;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialUSMetaUpdaterService;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialUSLegacySearchService;
import gov.nih.ncats.clinicaltrial.us.services.SubstanceAPIService;
import gsrs.controller.*;
import gsrs.legacy.LegacyGsrsSearchService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Stream;

/**
 * GSRS Rest API controller for the {@link ClinicalTrialUS} entity.
 */
@GsrsRestApiController(context = ClinicalTrialUSEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
@ExposesResourceFor(ClinicalTrialUS.class)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Slf4j
public class ClinicalTrialUSController extends EtagLegacySearchEntityController<ClinicalTrialUSController, ClinicalTrialUS, String> {


    @Autowired
    private ClinicalTrialUSLegacySearchService clinicalTrialUSLegacySearchService;

    @Autowired
    private ClinicalTrialUSEntityService clinicalTrialUSEntityService;

    @Autowired
    private ClinicalTrialUSMetaUpdaterService clinicalTrialUSMetaUpdaterService;

    @Autowired
    private SubstanceAPIService substanceAPIService;


    @Autowired
    private EntityLinks entityLinks;

    @Override
    protected LegacyGsrsSearchService<ClinicalTrialUS> getlegacyGsrsSearchService() {
        return clinicalTrialUSLegacySearchService;
    }

    @Autowired
    private Environment env;


    public ClinicalTrialUSController() {

    }

    @Override
    protected ClinicalTrialUSEntityService getEntityService() {
        return clinicalTrialUSEntityService;
    }

    protected Stream<ClinicalTrialUS> filterStream(Stream<ClinicalTrialUS> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }




    // The below will be removed.

    // experimental
    @GetGsrsRestApiMapping("/@exp_updateSmallSampleOfClinicalTrialUSMetaData")
    public JSONObject updateSmallSampleOfClinicalTrialUSMetaData()
    {
        System.out.println("Running Clinical Trials Meta Updater");
        clinicalTrialUSMetaUpdaterService.download();
        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }


    // experimental
    @GetGsrsRestApiMapping("/@exp_updateAllClinicalTrialUSMetaData")
    public JSONObject updateAllClinicalTrialUSMetaData()
    {
        System.out.println("Running Clinical Trials Meta Updater");
        clinicalTrialUSMetaUpdaterService.download2();

        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }

    // experimental
    @GetGsrsRestApiMapping("/@exp_testlog")
    public JSONObject testlog()
    {   log.debug("I am a log entry");
        System.out.println("Running testlog");
        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }

    // experimental
    @GetGsrsRestApiMapping("/@exp_testSubstanceExists/{uuid}")
    public JSONObject testSubstanceExists(@PathVariable String uuid) {
        System.out.println("checking if substance exists");
        Boolean b = substanceAPIService.substanceExists(uuid);
        System.out.println("value returned = "+ b);
        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }

    // experimental

    @GetGsrsRestApiMapping("/@exp_testSubstanceQuickMatches")
    public ResponseEntity<?> testSubstanceQuickMatches()  {
        System.out.println("getting substance substanceQuickMatches");
        return substanceAPIService.getQuickResultMatchesByUuids();
    }
}