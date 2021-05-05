package gov.nih.ncats.clinicaltrial.eu.services;

import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gov.nih.ncats.clinicaltrial.eu.repositories.ClinicalTrialEuropeRepository;
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
