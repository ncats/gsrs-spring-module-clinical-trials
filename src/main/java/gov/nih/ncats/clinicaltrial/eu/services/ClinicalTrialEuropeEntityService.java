package gov.nih.ncats.clinicaltrial.eu.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEuropeProduct;
import gov.nih.ncats.clinicaltrial.eu.repositories.ClinicalTrialEuropeRepository;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.service.AbstractGsrsEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ClinicalTrialEuropeEntityService extends AbstractGsrsEntityService<ClinicalTrialEurope, String> {
    public static final String  CONTEXT = "clinicaltrialeurope";

    public ClinicalTrialEuropeEntityService() {
        super("clinicaltrialeurope", Pattern.compile("^NCT\\d+$"), null, null, null);
    }

    @Autowired
    private ClinicalTrialEuropeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Class<ClinicalTrialEurope> getEntityClass() {
        return ClinicalTrialEurope.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected ClinicalTrialEurope fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, ClinicalTrialEurope.class);

    }

    @Override
    public Page page(Pageable pageable) {

        return repository.findAll(pageable);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    protected ClinicalTrialEurope update(ClinicalTrialEurope clinicalTrialEurope) {
        System.out.println("\n\n ==== Updating ====XX  \n\n");
        return repository.saveAndFlush(clinicalTrialEurope);
    }
    @Override
    protected AbstractEntityUpdatedEvent<ClinicalTrialEurope> newUpdateEvent(ClinicalTrialEurope updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<ClinicalTrialEurope> newCreationEvent(ClinicalTrialEurope createdEntity) {
        return null;
    }


    @Override
    public String getIdFrom(ClinicalTrialEurope entity) {
        return entity.getTrialNumber();
    }

    @Override
    protected List<ClinicalTrialEurope> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }


    @Override
    protected ClinicalTrialEurope fromUpdatedJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, ClinicalTrialEurope.class);

    }

    @Override
    protected List<ClinicalTrialEurope> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected JsonNode toJson(ClinicalTrialEurope clinicalTrialEurope) throws IOException {
        return objectMapper.valueToTree(clinicalTrialEurope);
    }

    @Override
    public ClinicalTrialEurope create(ClinicalTrialEurope clinicalTrialEurope) {
        System.out.println("\n\n ==== Creating  ==== XX  \n\n");
        try {
            // System.out.println(clinicalTrialEurope.getTrialNumber());
            return repository.saveAndFlush(clinicalTrialEurope);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<ClinicalTrialEurope> get(String id) {
        if(id==null) {
            
        }
        System.out.println("\n\n ==== Getting ID: " + id + " ====\n\n");
        return repository.findById(id);
    }

    @Override
    public Optional<ClinicalTrialEurope> flexLookup(String someKindOfId) {
        return repository.findByTitle(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<ClinicalTrialEurope> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().getTrialNumber());
        }
        return Optional.empty();
    }

}


