package gov.nih.ncats2.clinicaltrial.us.services;

import gov.nih.ncats2.clinicaltrial.us.models.ClinicalTrialUS;
import gov.nih.ncats2.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gsrs.legacy.LegacyGsrsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicalTrialUSLegacySearchService extends LegacyGsrsSearchService<ClinicalTrialUS> {
    @Autowired
    public ClinicalTrialUSLegacySearchService(ClinicalTrialUSRepository repository) {
        super(ClinicalTrialUS.class, repository);
    }

}
