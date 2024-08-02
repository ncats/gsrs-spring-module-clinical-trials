package gov.hhs.gsrs.clinicaltrial.us.modelBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import ix.core.controllers.EntityFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

public class ClinicalTrialUSBuilder extends AbstractClinicalTrialUSBuilder<ClinicalTrialUS, ClinicalTrialUSBuilder> {

    private static final ObjectMapper mapper = EntityFactory.EntityMapper.FULL_ENTITY_MAPPER();

    public ClinicalTrialUSBuilder() {
    }

    public ClinicalTrialUSBuilder(ClinicalTrialUS copy) {
        super(copy);
    }

    @Override
    public Supplier<ClinicalTrialUS> getSupplier() {
        return ClinicalTrialUS::new;
    }

    @Override
    protected ClinicalTrialUSBuilder getThis() {
        return this;
    }

    protected <S extends ClinicalTrialUS> ClinicalTrialUSBuilder(AbstractClinicalTrialUSBuilder<S,?> builder){
        this.xandThen = (s)-> (ClinicalTrialUS) builder.xandThen.apply((S) s);
    }

    public static <S extends ClinicalTrialUS, B extends AbstractClinicalTrialUSBuilder<S,B>> B  from(String json) throws IOException {
        return from(mapper.readTree(json));
    }
    public static <S extends ClinicalTrialUS, B extends AbstractClinicalTrialUSBuilder<S,B>> B  from(File json) throws IOException{
        return from(mapper.readTree(json));
    }
    public static <S extends ClinicalTrialUS, B extends AbstractClinicalTrialUSBuilder<S,B>> B  from(InputStream json) throws IOException{
        return from(mapper.readTree(json));
    }
    public static <S extends ClinicalTrialUS, B extends AbstractClinicalTrialUSBuilder<S,B>> B  from(JsonNode json){
        ClinicalTrialUS ctus = null;
        try {
            ctus = mapper.treeToValue(json, ClinicalTrialUS.class);
            return (B) new ClinicalTrialUSBuilder(ctus);

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("JSON parse error:" + e.getMessage(), e);
        }
    }

}