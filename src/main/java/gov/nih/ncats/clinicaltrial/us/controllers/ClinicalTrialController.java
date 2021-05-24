package gov.nih.ncats.clinicaltrial.us.controllers;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialEntityService;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialMetaUpdaterService;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialLegacySearchService;
import gov.nih.ncats.clinicaltrial.us.services.SubstanceAPIService;
import gsrs.controller.*;
import gsrs.legacy.LegacyGsrsSearchService;
//import org.hibernate.search.backend.lucene.LuceneExtension;
//import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
//import org.hibernate.search.engine.search.predicate.dsl.MatchPredicateFieldStep;
//import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
//import org.hibernate.search.engine.search.query.SearchResult;
//import org.hibernate.search.mapper.orm.search.query.dsl.HibernateOrmSearchQuerySelectStep;
//import org.hibernate.search.mapper.orm.session.SearchSession;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetGsrsRestApiMapping("/@lf0")
    public JSONObject sayHello0()
    {
        System.out.println("Running Clinical Trials Meta Updater");
        clinicalTrialMetaUpdaterService.download();

        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }

    @GetGsrsRestApiMapping("/@lf1")
  public JSONObject sayHello1()
  {
      System.out.println("Running Clinical Trials Meta Updater");
      clinicalTrialMetaUpdaterService.download2();

      Map<String, String> hm = new Hashtable<String, String>();
      hm.put("one", "a");
      hm.put("two", "b");
      return new JSONObject(hm);
  }

    @GetGsrsRestApiMapping("/@lf2/{uuid}")
    public JSONObject sayHello2(@PathVariable String uuid) {
        System.out.println("checking if substance exists");
        Boolean b = substanceAPIService.substanceExists(uuid);
        System.out.println("value returned = "+ b);
        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }

    @GetGsrsRestApiMapping("/@substancepassthru/{uuid}")
    public ResponseEntity<String> sayHello3(@PathVariable String uuid) {
        System.out.println("getting substance json");
        return substanceAPIService.getSubstanceDetailsFromUUID(uuid);
    }

    @GetGsrsRestApiMapping("/@substancenamepassthru/search")
    public ResponseEntity<String> sayHello4( @RequestParam("name") String name)  {
        System.out.println("getting substance from name json");
        return substanceAPIService.getSubstanceDetailsFromName(name);
    }

    @GetGsrsRestApiMapping("/@substanceQuickMatches")
    public ResponseEntity<?> sayHello5()  {
        System.out.println("getting substance substanceQuickMatches");
        return substanceAPIService.getQuickResultMatchesByUuids();
    }




}


