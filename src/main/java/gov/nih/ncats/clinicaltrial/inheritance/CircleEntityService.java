package gov.nih.ncats.clinicaltrial.inheritance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
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
public  class CircleEntityService extends AbstractGsrsEntityService<Circle, String> {
    public static final String  CONTEXT = "circle";

    public CircleEntityService() {
        super(CONTEXT, Pattern.compile("^circle\\d+$"), null, null, null);
    }

    @Autowired
    private CircleRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Class<Circle> getEntityClass() {
        return Circle.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected Circle fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Circle.class);

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
    protected Circle update(Circle circle) {
        System.out.println("\n\n ==== Updating ====XX  \n\n");
        return repository.saveAndFlush(circle);
    }

    @Override
    protected AbstractEntityUpdatedEvent<Circle> newUpdateEvent(Circle updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<Circle> newCreationEvent(Circle createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(Circle entity) {
        return entity.getTrialNumber();
    }

    @Override
    protected List<Circle> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }


    @Override
    protected Circle fromUpdatedJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Circle.class);

    }

    @Override
    protected List<Circle> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected JsonNode toJson(Circle circle) throws IOException {
        return objectMapper.valueToTree(circle);
    }

    @Override
    public Circle create(Circle circle) {
        System.out.println("\n\n ==== Creating  ==== XX  \n\n");
        try {
            // System.out.println(circle.getTrialNumber());
            return repository.saveAndFlush(circle);
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
    public Optional<Circle> get(String id) {
        if(id==null) {
            
        }
        System.out.println("\n\n ==== Getting ID: " + id + " ====\n\n");
        return repository.findById(id);
    }

    @Override
    public Optional<Circle> flexLookup(String someKindOfId) {
        return repository.findByTitle(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<Circle> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().getTrialNumber());
        }
        return Optional.empty();
    }


}


