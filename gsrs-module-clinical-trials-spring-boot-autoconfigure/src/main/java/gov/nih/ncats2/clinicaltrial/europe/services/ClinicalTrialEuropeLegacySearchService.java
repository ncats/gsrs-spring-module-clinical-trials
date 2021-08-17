package gov.nih.ncats2.clinicaltrial.europe.services;

import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.nih.ncats2.clinicaltrial.europe.repositories.ClinicalTrialEuropeRepository;
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
