package gov.nih.ncats2.clinicaltrial.europe.repositories;

import gov.nih.ncats2.clinicaltrial.base.repositories.ClinicalTrialBaseRepository;
import gov.nih.ncats2.clinicaltrial.europe.models.ClinicalTrialEurope;

import org.springframework.stereotype.Service;

@Service
public interface ClinicalTrialEuropeRepository extends ClinicalTrialBaseRepository<ClinicalTrialEurope> { }