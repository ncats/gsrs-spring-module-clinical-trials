package gov.nih.ncats.clinicaltrial;

import gov.nih.ncats.clinicaltrial.models.ClinicalTrial;
import gsrs.controller.EtagLegacySearchEntityController;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * GSRS Rest API controller for the {@link ClinicalTrial} entity.
 */
@GsrsRestApiController(context = ClinicalTrialEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
@ExposesResourceFor(ClinicalTrial.class)
public class ClinicalTrialController extends EtagLegacySearchEntityController<ClinicalTrialController, ClinicalTrial, String> {
    @Autowired
    private ClinicalTrialLegacySearchService clinicalTrialLegacySearchService;


    @Autowired
    private EntityLinks entityLinks;


    @Override
    protected LegacyGsrsSearchService<ClinicalTrial> getlegacyGsrsSearchService() {
        return clinicalTrialLegacySearchService;
    }

    public ClinicalTrialController() {
    }

    @RequestMapping("/lf") /// doesn't work
    public String lf() {
        System.out.println("\n\n ======== \n\n");
        return "lf";
    }
}
/*

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gsrs.controller.EtagLegacySearchEntityController;
import gsrs.controller.GsrsRestApiController;
import gsrs.controller.IdHelpers;
import gsrs.controller.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashSet;
import java.util.regex.Pattern;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@GsrsRestApiController(context = "clinicaltrial", idHelper = IdHelpers.STRING_NO_WHITESPACE)
public class ClinicalTrialController extends EtagLegacySearchEntityController<ClinicalTrial, String> {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClinicalTrialRepository clinicalTrialRepository;

    @Autowired
    private LegacyClinicalTrialSearcher legacyClinicalTrialSearcher;

    public ClinicalTrialController() {
        super("clinicaltrial", Pattern.compile("^NCT\\d+$"));
    }

    @Override
    protected ClinicalTrial fromNewJson(JsonNode json) throws IOException {
        //remove id?

        return objectMapper.convertValue(json, ClinicalTrial.class);
    }

    @Override
    protected List<ClinicalTrial> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected ClinicalTrial fromUpdatedJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, ClinicalTrial.class);
    }

    @Override
    protected List<ClinicalTrial> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected JsonNode toJson(ClinicalTrial clinicalTrial) throws IOException {
        return objectMapper.valueToTree(clinicalTrial);
    }

    @Override
    protected ClinicalTrial create(ClinicalTrial clinicalTrial) {
        return clinicalTrialRepository.saveAndFlush(clinicalTrial);
    }

    @Override
    protected long count() {
        return clinicalTrialRepository.count();
    }

    @Override
    protected Optional<ClinicalTrial> get(String id) {
        Optional<ClinicalTrial> cto = clinicalTrialRepository.findById(id);

        System.out.println("\n\n\n XXXXXXXXXXXXXXXXXXXX\n\n\n");

        cto.ifPresent(ct -> System.out.println(ct.getClinicalTrialDrug().toString()));
        System.out.println("\n\n\n XXXXXXXXXXXXXXXXXXXX\n\n\n");

        return clinicalTrialRepository.findById(id);

        // return clinicalTrialRepository.findById(id);
    }

    @Override
    protected String parseIdFromString(String idAsString) {
         // return Long.parseLong(idAsString);
        return idAsString;
    }

    @Override
    protected Optional<ClinicalTrial> flexLookup(String someKindOfId) {

        return clinicalTrialRepository.findByTitle(someKindOfId);
    }

    @Override
    protected Class<ClinicalTrial> getEntityClass() {
        return ClinicalTrial.class;
    }

    @Override
    protected Page page(long offset, long numOfRecords, Sort sort) {
        return clinicalTrialRepository.findAll(new OffsetBasedPageRequest(offset, numOfRecords, sort));
    }

    @Override
    protected void delete(String id) {
        clinicalTrialRepository.deleteById(id);
    }

    @Override
    protected String getIdFrom(ClinicalTrial entity) {
        return entity.getId();
    }

    @Override
    protected ClinicalTrial update(ClinicalTrial clinicalTrial) {
        return clinicalTrialRepository.saveAndFlush(clinicalTrial);
    }

    @Override
    protected LegacyClinicalTrialSearcher getlegacyGsrsSearchService() {
        return legacyClinicalTrialSearcher;
    }

}

*/
