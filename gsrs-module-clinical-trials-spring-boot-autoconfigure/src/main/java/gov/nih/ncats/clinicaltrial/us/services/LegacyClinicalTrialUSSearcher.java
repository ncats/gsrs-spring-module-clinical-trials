package gov.nih.ncats.clinicaltrial.us.services;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialUS;
import gov.nih.ncats.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacyClinicalTrialUSSearcher extends LegacyGsrsSearchService<ClinicalTrialUS> {

    @Autowired
    public LegacyClinicalTrialUSSearcher(ClinicalTrialUSRepository repository) {
        super(ClinicalTrialUS.class, repository);
    }
}
