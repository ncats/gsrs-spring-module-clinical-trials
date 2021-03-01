package gov.nih.ncats.clinicaltrial;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nih.ncats.clinicaltrial.models.ClinicalTrial;
import gsrs.service.AbstractGsrsEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;



@Service
public class ClinicalTrialEntityService extends AbstractGsrsEntityService<ClinicalTrial, String> {
    public static final String  CONTEXT = "clinicaltrial";

    public ClinicalTrialEntityService() {
        super("clinicaltrial", Pattern.compile("^NCT\\d+$"));
    }

    @Autowired
    private ClinicalTrialRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    // @Autowired
    // private ClinicalTrialSearchService searchService;

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
        // return CTUtils.adaptSingleRecord(json, objectMapper, true);
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
    public String getIdFrom(ClinicalTrial entity) {
        return entity.getTrialNumber();
    }

    @Override
    protected List<ClinicalTrial> fromNewJsonList(JsonNode list) throws IOException {
        // return CTUtils.adaptList(list, objectMapper, true);
        return null;
    }


    @Override
    protected ClinicalTrial fromUpdatedJson(JsonNode json) throws IOException {
        // return CTUtils.adaptSingleRecord(json, objectMapper, false);
        return objectMapper.convertValue(json, ClinicalTrial.class);

    }

    @Override
    protected List<ClinicalTrial> fromUpdatedJsonList(JsonNode list) throws IOException {
        // return CTUtils.adaptList(list, objectMapper, false);
        return null;
    }

    @Override
    protected JsonNode toJson(ClinicalTrial clinicalTrial) throws IOException {
        return objectMapper.valueToTree(clinicalTrial);
    }

    @Override
    protected ClinicalTrial create(ClinicalTrial clinicalTrial) {
        System.out.println("\n\n ==== Creating  ==== XX  \n\n");
        try {
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
