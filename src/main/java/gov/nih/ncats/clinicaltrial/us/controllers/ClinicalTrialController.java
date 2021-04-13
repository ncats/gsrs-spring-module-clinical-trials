package gov.nih.ncats.clinicaltrial.us.controllers;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialEntityService;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialLegacySearchService;
import gsrs.controller.EtagLegacySearchEntityController;
import gsrs.controller.GetGsrsRestApiMapping;
import gsrs.controller.GsrsRestApiController;
import gsrs.controller.IdHelpers;
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
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;

import java.util.Hashtable;
import java.util.Map;

/**
 * GSRS Rest API controller for the {@link ClinicalTrial} entity.
 */
@GsrsRestApiController(context = ClinicalTrialEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
@ExposesResourceFor(ClinicalTrial.class)
public class ClinicalTrialController extends EtagLegacySearchEntityController<ClinicalTrialController, ClinicalTrial, String> {
    @Autowired
    private ClinicalTrialLegacySearchService clinicalTrialLegacySearchService;

    // alex trying this
    @Autowired
    private ClinicalTrialEntityService clinicalTrialEntityService;


    @Autowired
    private EntityLinks entityLinks;


    @Override
    protected LegacyGsrsSearchService<ClinicalTrial> getlegacyGsrsSearchService() {
        return clinicalTrialLegacySearchService;
    }

    public ClinicalTrialController() {

    }

  @GetGsrsRestApiMapping("/@lf")
  public JSONObject sayHello()
  {
      System.out.println("Running Clinical Trials Meta Updater");
//      ClinicalTrialSourceDownloader dl = new ClinicalTrialSourceDownloader();
//      dl.download();
      clinicalTrialEntityService.download();

      Map<String, String> hm = new Hashtable<String, String>();
      hm.put("one", "a");
      hm.put("two", "b");
      return new JSONObject(hm);
  }
}


