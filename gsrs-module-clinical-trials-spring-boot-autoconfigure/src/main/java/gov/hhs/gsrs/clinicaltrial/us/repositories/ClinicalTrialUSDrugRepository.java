package gov.hhs.gsrs.clinicaltrial.us.repositories;

import gov.hhs.gsrs.clinicaltrial.base.models.ClinicalTrialBase;
import gov.hhs.gsrs.clinicaltrial.base.repositories.ClinicalTrialBaseRepository;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import gsrs.repository.GsrsRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public interface ClinicalTrialUSDrugRepository extends  GsrsRepository<ClinicalTrialUSDrug, Long> { }


