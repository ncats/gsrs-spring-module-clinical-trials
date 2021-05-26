package gov.nih.ncats.clinicaltrial.us.controllers;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialEntityService;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialMetaUpdaterService;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialLegacySearchService;
import gov.nih.ncats.clinicaltrial.us.services.SubstanceAPIService;
import gsrs.controller.*;
import gsrs.legacy.LegacyGsrsSearchService;
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
 * GSRS Rest API controller for the {@link ClinicalTrial} entity.
 */
@GsrsRestApiController(context = ClinicalTrialEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
@ExposesResourceFor(ClinicalTrial.class)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ClinicalTrialController extends EtagLegacySearchEntityController<ClinicalTrialController, ClinicalTrial, String> {


    @Autowired
    private ClinicalTrialLegacySearchService clinicalTrialLegacySearchService;

    @Autowired
    private ClinicalTrialEntityService clinicalTrialEntityService;

    @Autowired
    private ClinicalTrialMetaUpdaterService clinicalTrialMetaUpdaterService;

    @Autowired
    private SubstanceAPIService substanceAPIService;


    @Autowired
    private EntityLinks entityLinks;

    @Override
    protected LegacyGsrsSearchService<ClinicalTrial> getlegacyGsrsSearchService() {
        return clinicalTrialLegacySearchService;
    }

    @Autowired
    private Environment env;


    public ClinicalTrialController() {

    }

    @Override
    protected  ClinicalTrialEntityService getEntityService() {
        return clinicalTrialEntityService;
    }

    protected Stream<ClinicalTrial> filterStream(Stream<ClinicalTrial> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }




    // The below will be removed.

    // experimental
    @GetGsrsRestApiMapping("/@exp_updateSmallSampleOfClinicalTrialMetaData")
    public JSONObject updateSmallSampleOfClinicalTrialMetaData()
    {
        System.out.println("Running Clinical Trials Meta Updater");
        clinicalTrialMetaUpdaterService.download();
        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }


    // experimental
    @GetGsrsRestApiMapping("/@exp_updateAllClinicalTrialMetaData")
  public JSONObject updateAllClinicalTrialMetaData()
  {
      System.out.println("Running Clinical Trials Meta Updater");
      clinicalTrialMetaUpdaterService.download2();

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


