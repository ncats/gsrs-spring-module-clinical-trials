package gov.hhs.gsrs.clinicaltrial.europe.repositories;

import gov.hhs.gsrs.clinicaltrial.base.repositories.ClinicalTrialBaseRepository;
import gov.hhs.gsrs.clinicaltrial.europe.models.ClinicalTrialEurope;

import org.springframework.stereotype.Service;

@Service
public interface ClinicalTrialEuropeRepository extends ClinicalTrialBaseRepository<ClinicalTrialEurope> { }