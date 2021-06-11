package gov.nih.ncats.clinicaltrial.europe.services;

import gov.nih.ncats.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.nih.ncats.clinicaltrial.europe.repositories.ClinicalTrialEuropeRepository;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyClinicalEuropeTrialSearcher extends LegacyGsrsSearchService<ClinicalTrialEurope> {

    @Autowired
    public LegacyClinicalEuropeTrialSearcher(ClinicalTrialEuropeRepository repository) {
        super(ClinicalTrialEurope.class, repository);
    }
}
