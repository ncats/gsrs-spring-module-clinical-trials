package gov.nih.ncats2.clinicaltrial.us.repositories;

import gov.nih.ncats2.clinicaltrial.base.repositories.ClinicalTrialBaseRepository;
import gov.nih.ncats2.clinicaltrial.us.models.ClinicalTrialUS;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public interface ClinicalTrialUSRepository extends ClinicalTrialBaseRepository<ClinicalTrialUS> { }


