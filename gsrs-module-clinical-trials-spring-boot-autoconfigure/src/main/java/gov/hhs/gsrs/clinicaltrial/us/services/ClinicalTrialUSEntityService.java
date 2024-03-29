package gov.hhs.gsrs.clinicaltrial.us.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.hhs.gsrs.clinicaltrial.us.events.ClinicalTrialCreatedEvent;
import gov.hhs.gsrs.clinicaltrial.us.events.ClinicalTrialUSUpdateEvent;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.service.AbstractGsrsEntityService;
import gsrs.springUtils.StaticContextAccessor;
import ix.core.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ClinicalTrialUSEntityService extends AbstractGsrsEntityService<ClinicalTrialUS, String> {
    public static final String  CONTEXT = "clinicaltrialsus";

    // @Value("${mygsrs.clinicaltrial.eu.ClinicalTrial.trialNumberPattern}")
    // private String trialNumberPattern;

    public ClinicalTrialUSEntityService() {
        super(CONTEXT, Pattern.compile("^NCT\\d+$"), null, null, null);
    }

    @Autowired
    private ClinicalTrialUSRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Class<ClinicalTrialUS> getEntityClass() {
        return ClinicalTrialUS.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected ClinicalTrialUS fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, ClinicalTrialUS.class);

    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    // @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    // TODO reinstate transactional nature.
    // @Transactional()
    protected ClinicalTrialUS update(ClinicalTrialUS clinicalTrialUS) {
        log.debug("\n\n ==== Updating ==== \n\n");
        // substance service merges the entity to the entity manager before saving and flushing. Perhaps this should do the same?
        return repository.saveAndFlush(clinicalTrialUS);
    }


    @Override
    protected AbstractEntityUpdatedEvent<ClinicalTrialUS> newUpdateEvent(ClinicalTrialUS updatedEntity) {
        return new ClinicalTrialUSUpdateEvent(updatedEntity);
    }

    @Override
    protected AbstractEntityCreatedEvent<ClinicalTrialUS> newCreationEvent(ClinicalTrialUS createdEntity) {
        return new ClinicalTrialCreatedEvent(createdEntity);
    }

    @Override
    public String getIdFrom(ClinicalTrialUS entity) {
        return entity.getTrialNumber();
    }

    @Override
    protected List<ClinicalTrialUS> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }


    @Override
    protected ClinicalTrialUS fromUpdatedJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, ClinicalTrialUS.class);

    }

    @Override
    protected List<ClinicalTrialUS> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected JsonNode toJson(ClinicalTrialUS clinicalTrialUS) throws IOException {
        return objectMapper.valueToTree(clinicalTrialUS);
    }

    @Override
    public ClinicalTrialUS create(ClinicalTrialUS clinicalTrialUS) {
        try {
            ClinicalTrialUS ctus = repository.saveAndFlush(clinicalTrialUS);
            return ctus;
        } catch(Throwable t){
            t.printStackTrace();
            throw t;
        }

    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<ClinicalTrialUS> get(String id) {
        if(id==null) {
            
        }
        return repository.findById(id);
    }

    @Override
    public Optional<ClinicalTrialUS> flexLookup(String someKindOfId) {
        return repository.findByTitle(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<ClinicalTrialUS> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().getTrialNumber());
        }
        return Optional.empty();
    }


}


