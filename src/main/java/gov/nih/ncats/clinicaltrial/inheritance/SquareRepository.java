package gov.nih.ncats.clinicaltrial.inheritance;

import java.util.List;

import javax.transaction.Transactional;


@Transactional
public interface SquareRepository extends BaseShapeRepository<Square> { }

