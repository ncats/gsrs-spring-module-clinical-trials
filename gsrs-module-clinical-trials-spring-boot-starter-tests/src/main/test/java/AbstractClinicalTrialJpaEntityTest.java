package gsrs.substances.tests;

import gsrs.legacy.structureIndexer.StructureIndexerService;
import gsrs.startertests.GsrsJpaTest;
import ix.seqaln.service.SequenceIndexerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

@GsrsJpaTest
public abstract class AbstractClinicalTrialJpaEntityTest extends AbstractClinicalTrialJpaEntityTestSuperClass {

    @Autowired
    protected TestEntityManager entityManager;

    @Override
    protected EntityManagerFacade getEntityManagerFacade() {
        return EntityManagerFacade.wrap(entityManager);
    }
}
