package gov.hhs.gsrs.clinicaltrial.europe.services;

import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.hhs.gsrs.clinicaltrial.europe.repositories.ClinicalTrialEuropeRepository;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// ClinicalTrialRepository
// ClinicalTrial

@Service
public class ClinicalTrialEuropeLegacySearchService extends LegacyGsrsSearchService<ClinicalTrialEurope> {
    @Autowired
    public ClinicalTrialEuropeLegacySearchService(ClinicalTrialEuropeRepository repository) {
        super(ClinicalTrialEurope.class, repository);
    }

}
