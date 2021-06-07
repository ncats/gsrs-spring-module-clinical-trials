package gov.nih.ncats.clinicaltrial.us.repositories;

import gov.nih.ncats.clinicaltrial.base.repositories.ClinicalTrialBaseRepository;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialUS;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public interface ClinicalTrialUSRepository extends ClinicalTrialBaseRepository<ClinicalTrialUS> { }


