package gov.hhs.gsrs.clinicaltrial;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gov.hhs.gsrs.clinicaltrial.us.services.ClinicalTrialUSEntityService;
import gsrs.startertests.GsrsEntityTestConfiguration;
import gsrs.startertests.GsrsJpaTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.databind.ObjectMapper;
import gsrs.controller.GsrsControllerConfiguration;
import gsrs.junit.TimeTraveller;
import gsrs.service.AbstractGsrsEntityService;
// import gsrs.startertests.*;
import gsrs.startertests.jupiter.AbstractGsrsJpaEntityJunit5Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
// import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.io.File;
import java.time.LocalDate;
// import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static gsrs.assertions.GsrsMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ActiveProfiles("test")
@GsrsJpaTest(classes = { GsrsSpringApplication.class, GsrsControllerConfiguration.class, GsrsEntityTestConfiguration.class, ClinicalTrialUSRepository.class})
@Import({ClinicalTrialUS.class, ClinicalTrialUSEntityService.class})
public class ClinicalTrialUSServiceTest extends AbstractGsrsJpaEntityJunit5Test {
    @TempDir
    static File tempDir;

    @Autowired
    private ClinicalTrialUSEntityService clinicalTrialService;

    @RegisterExtension
    TimeTraveller timeTraveller = new TimeTraveller(LocalDate.of(1955, 11, 5));

    private JacksonTester<ClinicalTrialUS> json;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setup() {

        JacksonTester.initFields(this, objectMapper);
    }
    @Test
    public void noDataLoadedShouldHave0Results() throws Exception {
        assertEquals(0, clinicalTrialService.count());
    }

    @Test
    public void loadSingleRecord() throws Exception {

        ClinicalTrialUS clinicalTrialUS = new ClinicalTrialUS();
        clinicalTrialUS.setTitle("myFoo");
        clinicalTrialUS.setTrialNumber("NCT101");


        AbstractGsrsEntityService.CreationResult<ClinicalTrialUS> result = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrialUS));
        assertTrue(result.isCreated());
        ClinicalTrialUS savedClinicalTrialUS = result.getCreatedEntity();

        assertNotNull(savedClinicalTrialUS.getTrialNumber());
        assertThat(savedClinicalTrialUS, matchesExample(ClinicalTrialUS.builder()
                .title("myFoo")
                .creationDate(timeTraveller.getWhereWeAre().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(0L)
                .build()));
    }

    @Test

    public void callingGetManyTimesDoesNotIncrementVersion() throws Exception {
        ClinicalTrialUS clinicalTrialUS = new ClinicalTrialUS();
        clinicalTrialUS.setTitle("myFoo");
        clinicalTrialUS.setTrialNumber("NCT101");

        AbstractGsrsEntityService.CreationResult<ClinicalTrialUS> result = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrialUS));
        assertTrue(result.isCreated());
        ClinicalTrialUS savedClinicalTrialUS = result.getCreatedEntity();

        assertNotNull(savedClinicalTrialUS.getTrialNumber());
        for(int i=0 ;i< 10; i++) {
            assertThat(clinicalTrialService.getEntityBySomeIdentifier(savedClinicalTrialUS.getTrialNumber().toString()).get(), matchesExample(ClinicalTrialUS.builder()
                    .title("myFoo")
                    .creationDate(timeTraveller.getWhereWeAre().asDate())
                    .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                    .internalVersion(0L)
                    .build()));
        }
    }

    @Test
    public void loadTwoDifferentRecords() throws Exception {
        ClinicalTrialUS clinicalTrialUS = new ClinicalTrialUS();
        clinicalTrialUS.setTitle("myFoo");
        clinicalTrialUS.setTrialNumber("NCT101");

        AbstractGsrsEntityService.CreationResult<ClinicalTrialUS> result = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrialUS));
        assertTrue(result.isCreated());
        ClinicalTrialUS savedClinicalTrialUS = result.getCreatedEntity();
        assertEquals("myFoo", savedClinicalTrialUS.getTitle());
        assertNotNull(savedClinicalTrialUS.getTrialNumber());


        assertThat(savedClinicalTrialUS, matchesExample(ClinicalTrialUS.builder()
                .title("myFoo")
                .creationDate(timeTraveller.getWhereWeAre().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(0L)
                .build()));


        ClinicalTrialUS clinicalTrialUS2 = new ClinicalTrialUS();
        clinicalTrialUS2.setTitle("myFoo2");
        clinicalTrialUS2.setTrialNumber("NCT102");

        AbstractGsrsEntityService.CreationResult<ClinicalTrialUS> result2 = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrialUS2));
        assertTrue(result2.isCreated());
        ClinicalTrialUS savedClinicalTrialUS2 = result2.getCreatedEntity();
        assertNotNull(savedClinicalTrialUS2.getTrialNumber());


        assertThat(savedClinicalTrialUS2, matchesExample(ClinicalTrialUS.builder()
                .title("myFoo2")
                .creationDate(timeTraveller.getWhereWeAre().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(0L)
                .build()));

    }

    @Test
    public void updateSingleRecord() throws Exception {
        ClinicalTrialUS clinicalTrialUS = new ClinicalTrialUS();
        clinicalTrialUS.setTitle("myFoo");
        clinicalTrialUS.setTrialNumber("NCT101");

        AbstractGsrsEntityService.CreationResult<ClinicalTrialUS> result = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrialUS));
        assertTrue(result.isCreated());
        ClinicalTrialUS savedClinicalTrialUS = result.getCreatedEntity();

        String trialNumber = savedClinicalTrialUS.getTrialNumber();
        assertNotNull(trialNumber);
        assertThat(savedClinicalTrialUS, matchesExample(ClinicalTrialUS.builder()
                .title("myFoo")
                .creationDate(timeTraveller.getWhereWeAre().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(0L)
                .build()));

        savedClinicalTrialUS.setTitle("updatedFoo");
        timeTraveller.jumpAhead(1, TimeUnit.DAYS);

        AbstractGsrsEntityService.UpdateResult<ClinicalTrialUS> updateResult = clinicalTrialService.updateEntity(objectMapper.valueToTree(savedClinicalTrialUS));

        assertEquals(AbstractGsrsEntityService.UpdateResult.STATUS.UPDATED, updateResult.getStatus());
        assertThat(updateResult.getUpdatedEntity(), matchesExample(ClinicalTrialUS.builder()
                .title("updatedFoo")
                .trialNumber(trialNumber)
                .creationDate(timeTraveller.getWhereWeWere().get().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(1L)
                .build()));
    }
}
