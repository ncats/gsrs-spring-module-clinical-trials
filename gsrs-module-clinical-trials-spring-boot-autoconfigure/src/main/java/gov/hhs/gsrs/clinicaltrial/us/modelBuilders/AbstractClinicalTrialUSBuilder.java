package gov.hhs.gsrs.clinicaltrial.us.modelBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import ix.core.controllers.EntityFactory;
import ix.core.models.Group;
import ix.core.models.Keyword;
import ix.core.models.Principal;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractClinicalTrialUSBuilder <S extends ClinicalTrialUS, T extends AbstractClinicalTrialUSBuilder<S,T>>{
   Function<S, S> xandThen = (f->f);

    public abstract Supplier<S> getSupplier();

    protected abstract T getThis();

    public T setTitle(String title) {
        return andThen(s -> {
            s.trialNumber = title;
        });
    }
    public T setTrialNumber(String trialNumber) {
        return andThen(s -> {s.trialNumber=trialNumber;});
    }

    public T andThen(Consumer<S> fun){
        xandThen = xandThen.andThen(s ->{ fun.accept(s); return s;});
        return getThis();
    }

    public S build(){
        S s = getSupplier().get();
        return additionalSetup().apply(afterCreate().apply(s));
    }

    public final Function<S, S> afterCreate(){
        return xandThen;
    }

    public  Function<S, S> additionalSetup(){
        return Function.identity();
    }
    public AbstractClinicalTrialUSBuilder(){
    }

    public AbstractClinicalTrialUSBuilder(ClinicalTrialUS copy) {

        String trialNumber = copy.getTrialNumber();
        if (trialNumber != null) {
            setTrialNumber(trialNumber);
        }

    }


}