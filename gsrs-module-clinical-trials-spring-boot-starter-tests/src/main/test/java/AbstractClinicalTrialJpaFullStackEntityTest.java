package gsrs.substances.tests;

//import example.GsrsModuleSubstanceApplication;

import gsrs.startertests.GsrsFullStackTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;

//@SpringBootTest(classes = {GsrsModuleSubstanceApplication.class})
//@SpringBootTest
@GsrsFullStackTest(dirtyMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractCLinicalTrialJpaFullStackEntityTest extends AbstractClincialTrialJpaEntityTestSuperClass {
    @Autowired
    protected EntityManager entityManager;

    @Override
    protected EntityManagerFacade getEntityManagerFacade() {
        return EntityManagerFacade.wrap(entityManager);
    }

    public AbstractCLinicalTrialJpaFullStackEntityTest(){
        super();
    }
    public AbstractCLinicalTrialJpaFullStackEntityTest(boolean createAdminUserOnInit){
        super(createAdminUserOnInit);
    }
}
