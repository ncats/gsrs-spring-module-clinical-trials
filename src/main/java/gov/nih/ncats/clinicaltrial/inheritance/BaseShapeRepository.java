package gov.nih.ncats.clinicaltrial.inheritance;

import java.util.List;
import java.util.Optional;

import gsrs.repository.GsrsRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;


@Service
@NoRepositoryBean
public interface BaseShapeRepository<T extends Shape> extends GsrsRepository<T, String> {
    Optional<T> findById(String trialNumber);
    List<T> findAll();
    long count();
    // T save(T persisted);
    void deleteById(String id);
    Optional<T> findByTitle(String title);
}

