package gov.nih.ncats.clinicaltrial.us.services;

import gov.nih.ncats.clinicaltrial.us.repositories.ClinicalTrialRepository;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
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
