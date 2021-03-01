package gov.nih.ncats.clinicaltrial;

import gov.nih.ncats.clinicaltrial.models.ClinicalTrial;
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
