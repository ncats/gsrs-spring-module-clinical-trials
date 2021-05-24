package gov.nih.ncats.clinicaltrial.us.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nih.ncats.clinicaltrial.inheritance.*;
import gov.nih.ncats.clinicaltrial.inheritance.CircleEntityService;
import gov.nih.ncats.clinicaltrial.inheritance.SquareEntityService;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Stream;

/**
 * GSRS Rest API controller for the {@link ClinicalTrial} entity.
 */
@GsrsRestApiController(context = ClinicalTrialEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
@ExposesResourceFor(ClinicalTrial.class)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ClinicalTrialController extends EtagLegacySearchEntityController<ClinicalTrialController, ClinicalTrial, String> {
/*
    @Autowired
    private ShapeRepository shapeRepository;
    @Autowired
    private CircleRepository circleRepository;
    @Autowired
    private SquareRepository squareRepository;
    @Autowired
    private gov.nih.ncats.clinicaltrial.inheritance.CircleEntityService circleEntityService;
    @Autowired
    private gov.nih.ncats.clinicaltrial.inheritance.SquareEntityService squareEntityService;
*/



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

/*

    @GetGsrsRestApiMapping("/@tryshape")
    public JSONObject tryShape() {

        List messages = new ArrayList<String>();
        List errors = new ArrayList<String>();
        HttpStatus status = HttpStatus.OK;
        Circle circle1 = new Circle();
        circle1.trialNumber = "circle1";
        circle1.name = "pizza";
        circleRepository.save(circle1);

        Square square2 = new Square();
        square2.trialNumber = "square2";
        square2.name = "boxing ring";
        squareRepository.save(square2);

        Square square3 = new Square();
        square3.trialNumber = "square3";
        square3.name = "window";
        squareRepository.save(square3);

        // Access by subclass
        System.out.println("Count circles: "+ circleRepository.count());
        System.out.println("Count squares: "+ circleRepository.count());

        // Access in aggregate by baseclass

        System.out.println("Count shapes: "+ shapeRepository.count());

        List<Shape> shapes = shapeRepository.findAll();
        for(Shape s: shapes) {
            System.out.println("Shape id|name " + s.getTrialNumber() +"|"+ s.getName());
        }
*/
/*

        Circle circle1 = new Circle();
        circle1.trialNumber = "circle1";
        circle1.name = "pizza";
        circleEntityService.create(circle1);

        Square square2 = new Square();
        square2.trialNumber = "square2";
        square2.name = "boxing ring";
        squareEntityService.create(square2);

        Square square3 = new Square();
        square3.trialNumber = "square3";
        square3.name = "window";
        squareEntityService.create(square3);

        // Access by subclass
        System.out.println("Count circles: "+ circleRepository.count());
        System.out.println("Count squares: "+ circleRepository.count());

        // Access in aggregate by baseclass

        System.out.println("Count shapes: "+ shapeRepository.count());

        List<Shape> shapes = shapeRepository.findAll();
        for(Shape s: shapes) {
            System.out.println("Shape id|name " + s.getTrialNumber() +"|"+ s.getName());
        }


        Map<String, String> hm = new Hashtable<String, String>();
        hm.put("one", "a");
        hm.put("two", "b");
        return new JSONObject(hm);
    }

 */


}







