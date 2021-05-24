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
public class SquareEntityService extends AbstractGsrsEntityService<Square, String> {
    public static final String  CONTEXT = "square";

    public SquareEntityService() {
        super(CONTEXT, Pattern.compile("^square\\d+$"), null, null, null);
    }

    @Autowired
    private SquareRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Class<Square> getEntityClass() {
        return Square.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected Square fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Square.class);

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
    protected Square update(Square square) {
        System.out.println("\n\n ==== Updating ====XX  \n\n");
        return repository.saveAndFlush(square);
    }

    @Override
    protected AbstractEntityUpdatedEvent<Square> newUpdateEvent(Square updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<Square> newCreationEvent(Square createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(Square entity) {
        return entity.getTrialNumber();
    }

    @Override
    protected List<Square> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }


    @Override
    protected Square fromUpdatedJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Square.class);

    }

    @Override
    protected List<Square> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected JsonNode toJson(Square square) throws IOException {
        return objectMapper.valueToTree(square);
    }

    @Override
    public Square create(Square square) {
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
    public Optional<Square> get(String id) {
        if(id==null) {
            
        }
        System.out.println("\n\n ==== Getting ID: " + id + " ====\n\n");
        return repository.findById(id);
    }

    @Override
    public Optional<Square> flexLookup(String someKindOfId) {
        return repository.findByTitle(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<Square> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().getTrialNumber());
        }
        return Optional.empty();
    }


}


