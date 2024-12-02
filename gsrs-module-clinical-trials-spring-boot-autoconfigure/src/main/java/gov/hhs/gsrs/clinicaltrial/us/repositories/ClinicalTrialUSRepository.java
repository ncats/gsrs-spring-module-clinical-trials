package gov.hhs.gsrs.clinicaltrial.us.repositories;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.base.repositories.ClinicalTrialBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Repository
@Transactional
public interface ClinicalTrialUSRepository extends ClinicalTrialBaseRepository<ClinicalTrialUS> {

    @Query("select t from ClinicalTrialUS t")
    public Stream<ClinicalTrialUS> streamAll();

    @Query("select t.trialNumber from ClinicalTrialUS t")
    List<String> getAllIds();



}


