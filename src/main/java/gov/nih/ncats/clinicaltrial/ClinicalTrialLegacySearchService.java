package gov.nih.ncats.clinicaltrial;

import gov.nih.ncats.clinicaltrial.models.ClinicalTrial;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// ClinicalTrialRepository
// ClinicalTrial

@Service
public class ClinicalTrialLegacySearchService extends LegacyGsrsSearchService<ClinicalTrial> {
    @Autowired
    public ClinicalTrialLegacySearchService(ClinicalTrialRepository repository) {
        super(ClinicalTrial.class, repository);
    }

}
