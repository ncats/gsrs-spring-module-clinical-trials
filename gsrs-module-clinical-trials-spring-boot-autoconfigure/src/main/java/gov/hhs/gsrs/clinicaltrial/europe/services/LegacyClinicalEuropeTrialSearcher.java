package gov.hhs.gsrs.clinicaltrial.europe.services;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.hhs.gsrs.clinicaltrial.europe.repositories.ClinicalTrialEuropeRepository;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyClinicalEuropeTrialSearcher extends LegacyGsrsSearchService<ClinicalTrialEurope> {
    // this appears to be deprecated
    // check if it can be deleted
    @Autowired
    public LegacyClinicalEuropeTrialSearcher(ClinicalTrialEuropeRepository repository) {
        super(ClinicalTrialEurope.class, repository);
    }
}
