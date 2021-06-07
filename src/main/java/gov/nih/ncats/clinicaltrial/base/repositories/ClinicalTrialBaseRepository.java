package gov.nih.ncats.clinicaltrial.base.repositories;

import java.util.List;
import java.util.Optional;

import gov.nih.ncats.clinicaltrial.base.models.ClinicalTrialBase;
import gov.nih.ncats.clinicaltrial.eu.models.ClinicalTrialEurope;
import gsrs.repository.GsrsRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;


@Service
@NoRepositoryBean
public interface ClinicalTrialBaseRepository<T extends ClinicalTrialBase> extends GsrsRepository<T, String> {
    Optional<T> findByTitle(String title);
}

