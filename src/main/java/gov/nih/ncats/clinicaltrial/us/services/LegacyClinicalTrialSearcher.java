package gov.nih.ncats.clinicaltrial.us.services;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.repositories.ClinicalTrialRepository;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyClinicalTrialSearcher extends LegacyGsrsSearchService<ClinicalTrial> {

    @Autowired
    public LegacyClinicalTrialSearcher(ClinicalTrialRepository repository) {
        super(ClinicalTrial.class, repository);
    }
}
