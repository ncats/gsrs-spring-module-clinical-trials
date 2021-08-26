package gov.hhs.gsrs.clinicaltrial.base.repositories;
import gov.hhs.gsrs.clinicaltrial.base.models.ClinicalTrialBase;

import javax.transaction.Transactional;

@Transactional
public interface ClinicalTrialRepository extends ClinicalTrialBaseRepository<ClinicalTrialBase> { }

