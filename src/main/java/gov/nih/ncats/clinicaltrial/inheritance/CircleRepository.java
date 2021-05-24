package gov.nih.ncats.clinicaltrial.inheritance;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import javax.transaction.Transactional;


@Transactional
public interface CircleRepository extends BaseShapeRepository<Circle> { }