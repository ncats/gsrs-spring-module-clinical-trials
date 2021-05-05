package gov.nih.ncats.clinicaltrial.eu.repositories;

import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gsrs.repository.GsrsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ClinicalTrialEuropeRepository extends GsrsRepository<ClinicalTrialEurope, String> {
    Optional<ClinicalTrialEurope> findByTitle(String title);
}
