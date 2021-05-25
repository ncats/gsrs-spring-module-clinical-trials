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
public class ShapeEntityService extends AbstractGsrsEntityService<Shape, String> {
    public static final String  CONTEXT = "shape";

    public ShapeEntityService() {
        super(CONTEXT, Pattern.compile("^shape\\d+$"), null, null, null);
    }

    @Autowired
    private ShapeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Class<Shape> getEntityClass() {
        return Shape.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected Shape fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Shape.class);

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
    protected Shape update(Shape square) {
        System.out.println("\n\n ==== Updating ====XX  \n\n");
        return repository.saveAndFlush(square);
    }

    @Override
    protected AbstractEntityUpdatedEvent<Shape> newUpdateEvent(Shape updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<Shape> newCreationEvent(Shape createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(Shape entity) {
        return entity.getTrialNumber();
    }

    @Override
    protected List<Shape> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }


    @Override
    protected Shape fromUpdatedJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Shape.class);

    }

    @Override
    protected List<Shape> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected JsonNode toJson(Shape square) throws IOException {
        return objectMapper.valueToTree(square);
    }

    @Override
    public Shape create(Shape square) {
        System.out.println("\n\n ==== Creating  ==== XX  \n\n");
        try {
            // System.out.println(square.getTrialNumber());
            return repository.saveAndFlush(square);
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
    public Optional<Shape> get(String id) {
        if(id==null) {

        }
        System.out.println("\n\n ==== Getting ID: " + id + " ====\n\n");
        return repository.findById(id);
    }

    @Override
    public Optional<Shape> flexLookup(String someKindOfId) {
        return repository.findByTitle(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<Shape> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().getTrialNumber());
        }
        return Optional.empty();
    }


}


