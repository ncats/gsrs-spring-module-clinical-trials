package gov.hhs.gsrs.clinicaltrial.us.scrubbers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.Predicate;

import gov.hhs.gsrs.clinicaltrial.ClinicalTrialDataSourceConfig;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.nih.ncats.common.stream.StreamUtil;
import ix.core.EntityFetcher;
import ix.core.models.Group;
import ix.core.util.EntityUtils;
import ix.ginas.exporters.RecordScrubber;
// import ix.ginas.modelBuilders.SubstanceBuilder;
import ix.ginas.models.GinasAccessControlled;
// import ix.ginas.models.GinasAccessReferenceControlled;
import ix.ginas.models.GinasCommonData;
;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

/*
Note: as of 22 September, most of this class is commented out and a quick and dirty implementation is in place.
This will change in the ensuing weeks
 */
@Slf4j
public class BasicCtusScrubber implements RecordScrubber<ClinicalTrialUS>{


private static final String TO_DELETE = "TO_DELETE";
private static final String TO_FORCE_KEEP = "TO_FORCE_KEEP";
private BasicCtusScrubberParameters scrubberSettings;
private static Set<Group> toDelete = new HashSet<>();
private static Set<Group> toKeep = new HashSet<>();



public BasicCtusScrubber(BasicCtusScrubberParameters scrubberSettings){
this.scrubberSettings = scrubberSettings;
}

//public void setResolver( ClinicalTrialUSReferenceResolver newResolver) {
//this.resolver= newResolver;
//}


    @SneakyThrows
    @Override
    public Optional<ClinicalTrialUS> scrub(ClinicalTrialUS ctus) {
        log.trace("starting in scrub");
        String ctusJson;
        try {
            ctusJson = ctus.toInternalJsonNode().toString();
        } catch (Exception ex){
            log.error("Error retrieving substance; using alternative method");
            EntityUtils.Key trialNumber = EntityUtils.Key.of(ClinicalTrialUS.class, ctus.trialNumber);
            Optional<ClinicalTrialUS> ctusRefetch = EntityFetcher.of(trialNumber).getIfPossible().map(o->(ClinicalTrialUS) o);
            ctusJson = ctusRefetch.get().toInternalJsonNode().toString();
        }

//        try {
//            //TODO: confirm if this forces as a concept. It should not,
//            // but we need to check.
////            ClinicalTrialUS ctusNew = ClinicalTrialUSBuilder.from(ctusJson).build();
//  //          return Optional.of(ctusNew);
//        }
//        catch (Exception ex) {
//            log.warn("error processing record; Will return empty", ex);
//        }
        return Optional.empty();
    }










}
