package gov.hhs.gsrs.clinicaltrial.us.services;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
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
