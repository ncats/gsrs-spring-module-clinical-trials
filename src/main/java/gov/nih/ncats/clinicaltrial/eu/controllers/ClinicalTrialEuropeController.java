package gov.nih.ncats.clinicaltrial.eu.controllers;

import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gov.nih.ncats.clinicaltrial.eu.services.ClinicalTrialEuropeEntityService;
import gov.nih.ncats.clinicaltrial.eu.services.ClinicalTrialEuropeLegacySearchService;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gsrs.controller.EtagLegacySearchEntityController;
import gsrs.controller.GsrsRestApiController;
import gsrs.controller.IdHelpers;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;

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

    public ClinicalTrialEuropeController() {

    }


}


