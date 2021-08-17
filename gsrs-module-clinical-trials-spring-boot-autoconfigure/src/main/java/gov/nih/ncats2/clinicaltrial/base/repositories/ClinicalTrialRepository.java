package gov.nih.ncats2.clinicaltrial.base.repositories;
import gov.nih.ncats2.clinicaltrial.base.models.ClinicalTrialBase;

import javax.transaction.Transactional;

@Transactional
public interface ClinicalTrialRepository extends ClinicalTrialBaseRepository<ClinicalTrialBase> { }

