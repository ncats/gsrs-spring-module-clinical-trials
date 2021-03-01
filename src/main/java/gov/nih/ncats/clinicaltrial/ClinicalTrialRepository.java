package gov.nih.ncats.clinicaltrial;

import gov.nih.ncats.clinicaltrial.models.ClinicalTrial;
import gsrs.repository.GsrsRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public interface ClinicalTrialRepository extends GsrsRepository<ClinicalTrial, String> {
    Optional<ClinicalTrial> findByTitle(String title);
}
