package gov.nih.ncats.clinicaltrial.eu.controllers;

import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gov.nih.ncats.clinicaltrial.eu.services.ClinicalTrialEuropeEntityService;
import gov.nih.ncats.clinicaltrial.eu.services.ClinicalTrialEuropeLegacySearchService;
import gov.nih.ncats.clinicaltrial.us.controllers.ClinicalTrialController;
import gsrs.controller.EtagLegacySearchEntityController;
import gsrs.controller.GetGsrsRestApiMapping;
import gsrs.controller.GsrsRestApiController;
import gsrs.controller.IdHelpers;
import gsrs.legacy.LegacyGsrsSearchService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;

import java.util.Hashtable;
import java.util.Map;

/**
 * GSRS Rest API controller for the {@link ClinicalTrialEurope} entity.
 */
@GsrsRestApiController(context = ClinicalTrialEuropeEntityService.CONTEXT,  idHelper = IdHelpers.NUMBER)
@ExposesResourceFor(ClinicalTrialEurope.class)
public class ClinicalTrialEuropeController extends EtagLegacySearchEntityController<ClinicalTrialController, ClinicalTrialEurope, String> {
    @Autowired
    private ClinicalTrialEuropeLegacySearchService clinicalTrialEuropeLegacySearchService;

    // alex trying this
    @Autowired
    private ClinicalTrialEuropeEntityService clinicalTrialEntityService;


    @Autowired
    private EntityLinks entityLinks;


    @Override
    protected LegacyGsrsSearchService<ClinicalTrialEurope> getlegacyGsrsSearchService() {
        return clinicalTrialEuropeLegacySearchService;
    }

    public ClinicalTrialEuropeController() {

    }


}


