package gov.nih.ncats.clinicaltrial.base.repositories;
import gov.nih.ncats.clinicaltrial.base.models.ClinicalTrialBase;

import javax.transaction.Transactional;

@Transactional
public interface ClinicalTrialRepository
        extends ClinicalTrialBaseRepository<ClinicalTrialBase> { }

