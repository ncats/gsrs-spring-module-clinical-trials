package gov.hhs.gsrs.clinicaltrial.us.modelBuilders;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import gov.hhs.gsrs.clinicaltrial.us.models.OutcomeResultNote;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractClinicalTrialUSBuilder <S extends ClinicalTrialUS, T extends AbstractClinicalTrialUSBuilder<S,T>>{
   Function<S, S> xandThen = (f->f);

    public abstract Supplier<S> getSupplier();

    protected abstract T getThis();

    public T setTitle(String title) {
        return andThen(s -> {s.title = title;});
    }

    public T setTrialNumber(String trialNumber) {
        return andThen(s -> {s.trialNumber = trialNumber;});
    }

    public T setKind(String kind) {
        return andThen(s -> {s.kind = kind;});
    }

    public T setUrl(String url) {
        return andThen(s -> {s.url = url;});
    }

    public T setInternalVersion(Long internalVersion) {
        return andThen(s -> {s.internalVersion = internalVersion;});
    }

    public T setClinicalTrialDrug(List<ClinicalTrialUSDrug> ctds) {
        return andThen(s -> {s.setClinicalTrialUSDrug(ctds);});
    }


    public T setRecruitment (String recruitment ) {
        return andThen(s -> {s.recruitment  = recruitment ;});
    }

    public T setResultsFirstReceived(String resultsFirstReceived) {
        return andThen(s -> {s.resultsFirstReceived = resultsFirstReceived;});
    }

    public T setConditions(String conditions) {
        return andThen(s -> {s.conditions = conditions;});
    }

    public T setIntervention(String intervention) {
        return andThen(s -> {s.intervention = intervention;});
    }

    public T setSponsor(String sponsor) {
        return andThen(s -> {s.sponsor = sponsor;});
    }

    public T setPhases(String phases) {
        return andThen(s -> {s.phases = phases;});
    }

    public T setFundedBys(String fundedBys) {
        return andThen(s -> {s.fundedBys = fundedBys;});
    }

    public T setStudyTypes(String studyTypes) {
        return andThen(s -> {s.studyTypes = studyTypes;});
    }

    public T setStudyDesigns(String studyDesigns) {
        return andThen(s -> {s.studyDesigns = studyDesigns;});
    }

    public T setStudyResults(String studyResults) {
        return andThen(s -> {s.studyResults = studyResults;});
    }

    public T setAgeGroups(String ageGroups) {
        return andThen(s -> {s.ageGroups = ageGroups;});
    }

    public T setGender(String gender) {
        return andThen(s -> {s.gender = gender;});
    }

    public T setEnrollment(String enrollment) {
        return andThen(s -> {s.enrollment = enrollment;});
    }

    public T setOtherIds(String otherIds) {
        return andThen(s -> {s.otherIds = otherIds;});
    }

    public T setAcronym(String acronym) {
        return andThen(s -> {s.acronym = acronym;});
    }

    public T setStatus(String status) {
        return andThen(s -> {s.status = status;});
    }

    public T setStartDate(Date startDate) {
        return andThen(s -> {s.startDate = startDate;});
    }

    public T setLastVerified(Date lastVerified) {
        return andThen(s -> {s.lastVerified = lastVerified;});
    }

    public T setCompletionDate(Date completionDate) {
        return andThen(s -> {s.completionDate = completionDate;});
    }

    public T setPrimaryCompletionDate(Date primaryCompletionDate) {
        return andThen(s -> {s.primaryCompletionDate = primaryCompletionDate;});
    }

    public T setFirstReceived(Date firstReceived) {
        return andThen(s -> {s.firstReceived = firstReceived;});
    }

    public T setLastUpdated(Date lastUpdated) {
        return andThen(s -> {s.lastUpdated = lastUpdated;});
    }

    public T setOutcomeMeasures(String outcomeMeasures) {
        return andThen(s -> {s.outcomeMeasures = outcomeMeasures;});
    }

    public T setLocations(String locations) {
        return andThen(s -> {s.locations = locations;});
    }

    public T setGsrsMatchingComplete(boolean complete) {
        return andThen(s -> {s.setGsrsMatchingComplete(complete);});
    }

    public T setClinicalTrialUSDrug(List<ClinicalTrialUSDrug> ctds) {
        return andThen(s -> {s.setClinicalTrialUSDrug(ctds);});
    }

    public T setOutcomeResultNotes(List<OutcomeResultNote> notes) {
        return andThen(s -> {s.setOutcomeResultNotes(notes);});
    }

    public T setCreationDate(Date creationDate) {
        return andThen(s -> {s.creationDate = creationDate;});
    }

    public T setLastModifiedDate(Date lastModifiedDate) {
        return andThen(s -> {s.lastModifiedDate = lastModifiedDate;});
    }

    public T setCreatedBy(String createdBy) {
        return andThen(s -> {s.setCreatedBy(createdBy);});
    }

    public T setModifiedBy(String modifiedBy) {
        return andThen(s -> {s.setModifiedBy(modifiedBy);});
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

        String kind = copy.getKind();
        if (kind != null) {
            setKind(kind);
        }

        String title = copy.getTitle();
        if (title != null) {
            setTitle(title);
        }

        String url = copy.getUrl();
        if (url != null) {
            setUrl(url);
        }

        Long internalVersion = copy.getInternalVersion();
        if (internalVersion != null) {
            setInternalVersion(internalVersion);
        }

        String  recruitment = copy.getRecruitment();
        if (recruitment != null) {
            setRecruitment(recruitment);
        }

        String  resultsFirstReceived = copy.getResultsFirstReceived();
        if (resultsFirstReceived != null) {
            setResultsFirstReceived(resultsFirstReceived);
        }

        String  conditions = copy.getConditions();
        if (conditions != null) {
            setConditions(conditions);
        }

        String  intervention = copy.getIntervention();
        if (intervention != null) {
            setIntervention(intervention);
        }

        String  sponsor = copy.getSponsor();
        if (sponsor != null) {
            setSponsor(sponsor);
        }

        String  phases = copy.getPhases();
        if (phases != null) {
            setPhases(phases);
        }

        String  fundedBys = copy.getFundedBys();
        if (fundedBys != null) {
            setFundedBys(fundedBys);
        }

        String  studyTypes = copy.getStudyTypes();
        if (studyTypes != null) {
            setStudyTypes(studyTypes);
        }

        String  studyDesigns = copy.getStudyDesigns();
        if (studyDesigns != null) {
            setStudyDesigns(studyDesigns);
        }

        String  studyResults = copy.getStudyResults();
        if (studyResults != null) {
            setStudyResults(studyResults);
        }

        String  ageGroups = copy.getAgeGroups();
        if (ageGroups != null) {
            setAgeGroups(ageGroups);
        }

        String  gender = copy.getGender();
        if (gender != null) {
            setGender(gender);
        }

        String  enrollment = copy.getEnrollment();
        if (enrollment != null) {
            setEnrollment(enrollment);
        }

        String  otherIds = copy.getOtherIds();
        if (otherIds != null) {
            setOtherIds(otherIds);
        }

        String  acronym = copy.getAcronym();
        if (acronym != null) {
            setAcronym(acronym);
        }

        String  status = copy.getStatus();
        if (status != null) {
            setStatus(status);
        }

        Date  startDate = copy.getStartDate();
        if (startDate != null) {
            setStartDate(startDate);
        }

        Date  lastVerified = copy.getLastVerified();
        if (lastVerified != null) {
            setLastVerified(lastVerified);
        }

        Date  completionDate = copy.getCompletionDate();
        if (completionDate != null) {
            setCompletionDate(completionDate);
        }

        Date  primaryCompletionDate = copy.getPrimaryCompletionDate();
        if (primaryCompletionDate != null) {
            setPrimaryCompletionDate(primaryCompletionDate);
        }

        Date  firstReceived = copy.getFirstReceived();
        if (firstReceived != null) {
            setFirstReceived(firstReceived);
        }

        Date  lastUpdated = copy.getLastUpdated();
        if (lastUpdated != null) {
            setLastUpdated(lastUpdated);
        }

        String  outcomeMeasures = copy.getOutcomeMeasures();
        if (outcomeMeasures != null) {
            setOutcomeMeasures(outcomeMeasures);
        }

        String  locations = copy.getLocations();
        if (locations != null) {
            setLocations(locations);
        }

        List<ClinicalTrialUSDrug>  clinicalTrialUSDrug = copy.getClinicalTrialUSDrug();
        if (clinicalTrialUSDrug != null) {
            setClinicalTrialUSDrug(clinicalTrialUSDrug);
        }

        List<OutcomeResultNote>  setOutcomeResultNotes = copy.getOutcomeResultNotes();
        if (setOutcomeResultNotes != null) {
            setOutcomeResultNotes(setOutcomeResultNotes);
        }

        boolean gsrsMatchingComplete =copy.isGsrsMatchingComplete();
        setGsrsMatchingComplete(gsrsMatchingComplete);

        Date  lastModifiedDate = copy.getLastModifiedDate();
        if (lastModifiedDate != null) {
            setLastModifiedDate(lastModifiedDate);
        }

        Date  creationDate = copy.getCreationDate();
        if (creationDate != null) {
            setCreationDate(creationDate);
        }

        String  createdBy = copy.getCreatedBy();
        if (createdBy != null) {
            setCreatedBy(createdBy);
        }

        String  modifiedBy = copy.getModifiedBy();
        if (modifiedBy != null) {
            setModifiedBy(modifiedBy);
        }
    }
}