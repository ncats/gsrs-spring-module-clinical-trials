package gov.nih.ncats2.clinicaltrial.base.repositories;

import java.util.Optional;

import gov.nih.ncats2.clinicaltrial.base.models.ClinicalTrialBase;
import gsrs.repository.GsrsRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;


@Service
@NoRepositoryBean
public interface ClinicalTrialBaseRepository<T extends ClinicalTrialBase> extends GsrsRepository<T, String> {
    Optional<T> findByTitle(String title);
}
