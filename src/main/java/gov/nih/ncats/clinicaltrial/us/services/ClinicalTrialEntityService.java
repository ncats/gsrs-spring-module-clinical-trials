package gov.nih.ncats.clinicaltrial.us.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nih.ncats.clinicaltrial.us.repositories.ClinicalTrialRepository;
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

import gov.nih.ncats.clinicaltrial.us.utils.importmapper.SourceToTargetFieldsMapper;

@Service
public class ClinicalTrialEntityService extends AbstractGsrsEntityService<ClinicalTrial, String> {
    public static final String  CONTEXT = "clinicaltrial";

    // @Value("${mygsrs.clinicaltrial.eu.ClinicalTrial.trialNumberPattern}")
    // private String trialNumberPattern;

    public ClinicalTrialEntityService() {
        super("clinicaltrial", Pattern.compile("^NCT\\d+$"), null, null, null);
    }

    @Autowired
    private ClinicalTrialRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Class<ClinicalTrial> getEntityClass() {
        return ClinicalTrial.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected ClinicalTrial fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, ClinicalTrial.class);

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
    protected ClinicalTrial update(ClinicalTrial clinicalTrial) {
        System.out.println("\n\n ==== Updating ====XX  \n\n");
        return repository.saveAndFlush(clinicalTrial);
    }

    @Override
    protected AbstractEntityUpdatedEvent<ClinicalTrial> newUpdateEvent(ClinicalTrial updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<ClinicalTrial> newCreationEvent(ClinicalTrial createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(ClinicalTrial entity) {
        return entity.getTrialNumber();
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
    public ClinicalTrial create(ClinicalTrial clinicalTrial) {
        System.out.println("\n\n ==== Creating  ==== XX  \n\n");
        try {
            // System.out.println(clinicalTrial.getTrialNumber());
            return repository.saveAndFlush(clinicalTrial);
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
    public Optional<ClinicalTrial> get(String id) {
        if(id==null) {
            
        }
        System.out.println("\n\n ==== Getting ID: " + id + " ====\n\n");
        return repository.findById(id);
    }

    @Override
    public Optional<ClinicalTrial> flexLookup(String someKindOfId) {
        return repository.findByTitle(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<ClinicalTrial> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().getTrialNumber());
        }
        return Optional.empty();
    }


}


