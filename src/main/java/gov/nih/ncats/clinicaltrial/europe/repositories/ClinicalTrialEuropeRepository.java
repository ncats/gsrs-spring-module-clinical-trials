package gov.nih.ncats.clinicaltrial.europe.repositories;

import gov.nih.ncats.clinicaltrial.base.repositories.ClinicalTrialBaseRepository;
import gov.nih.ncats.clinicaltrial.europe.models.ClinicalTrialEurope;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrialUS;
import gsrs.repository.GsrsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ClinicalTrialEuropeRepository extends ClinicalTrialBaseRepository<ClinicalTrialEurope> { }
