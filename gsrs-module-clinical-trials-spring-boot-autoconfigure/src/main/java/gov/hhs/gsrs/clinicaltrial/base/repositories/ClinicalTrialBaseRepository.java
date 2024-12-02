package gov.hhs.gsrs.clinicaltrial.base.repositories;

import java.util.Optional;

import gov.hhs.gsrs.clinicaltrial.base.models.ClinicalTrialBase;
import gsrs.repository.GsrsRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
@NoRepositoryBean
public interface ClinicalTrialBaseRepository<T extends ClinicalTrialBase> extends GsrsRepository<T, String> {
    Optional<T> findByTitle(String title);
}

