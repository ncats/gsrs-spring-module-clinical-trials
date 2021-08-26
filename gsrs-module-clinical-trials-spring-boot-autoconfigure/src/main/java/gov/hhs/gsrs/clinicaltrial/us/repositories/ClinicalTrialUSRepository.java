package gov.hhs.gsrs.clinicaltrial.us.repositories;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.base.repositories.ClinicalTrialBaseRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public interface ClinicalTrialUSRepository extends ClinicalTrialBaseRepository<ClinicalTrialUS> { }


