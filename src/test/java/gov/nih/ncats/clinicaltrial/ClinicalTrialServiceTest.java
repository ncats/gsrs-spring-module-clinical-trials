package gov.nih.ncats.clinicaltrial;

import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.repositories.ClinicalTrialRepository;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialEntityService;
import gsrs.startertests.GsrsEntityTestConfiguration;
import gsrs.startertests.GsrsJpaTest;
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

import java.io.File;
import java.time.LocalDate;
// import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static gsrs.assertions.GsrsMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@GsrsJpaTest(classes = { GsrsSpringApplication.class, GsrsControllerConfiguration.class, GsrsEntityTestConfiguration.class, ClinicalTrialRepository.class})
@Import({ClinicalTrial.class, ClinicalTrialEntityService.class})
public class ClinicalTrialServiceTest extends AbstractGsrsJpaEntityJunit5Test {
    @TempDir
    static File tempDir;

    @Autowired
    private ClinicalTrialEntityService clinicalTrialService;

    @RegisterExtension
    TimeTraveller timeTraveller = new TimeTraveller(LocalDate.of(1955, 11, 5));

    private JacksonTester<ClinicalTrial> json;
    ObjectMapper objectMapper = new ObjectMapper();

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
        ClinicalTrial clinicalTrial = new ClinicalTrial();
        clinicalTrial.setTitle("myFoo");
        clinicalTrial.setTrialNumber("NCT101");


        AbstractGsrsEntityService.CreationResult<ClinicalTrial> result = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrial));
        assertTrue(result.isCreated());
        ClinicalTrial savedClinicalTrial = result.getCreatedEntity();

        assertNotNull(savedClinicalTrial.getTrialNumber());
        assertThat(savedClinicalTrial, matchesExample(ClinicalTrial.builder()
                .title("myFoo")
                .creationDate(timeTraveller.getWhereWeAre().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(0L)
                .build()));
    }

    @Test

    public void callingGetManyTimesDoesNotIncrementVersion() throws Exception {
        ClinicalTrial clinicalTrial = new ClinicalTrial();
        clinicalTrial.setTitle("myFoo");
        clinicalTrial.setTrialNumber("NCT101");

        AbstractGsrsEntityService.CreationResult<ClinicalTrial> result = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrial));
        assertTrue(result.isCreated());
        ClinicalTrial savedClinicalTrial = result.getCreatedEntity();

        assertNotNull(savedClinicalTrial.getTrialNumber());
        for(int i=0 ;i< 10; i++) {
            assertThat(clinicalTrialService.getEntityBySomeIdentifier(savedClinicalTrial.getTrialNumber().toString()).get(), matchesExample(ClinicalTrial.builder()
                    .title("myFoo")
                    .creationDate(timeTraveller.getWhereWeAre().asDate())
                    .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                    .internalVersion(0L)
                    .build()));
        }
    }

    @Test
    public void loadTwoDifferentRecords() throws Exception {
        ClinicalTrial clinicalTrial = new ClinicalTrial();
        clinicalTrial.setTitle("myFoo");
        clinicalTrial.setTrialNumber("NCT101");

        AbstractGsrsEntityService.CreationResult<ClinicalTrial> result = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrial));
        assertTrue(result.isCreated());
        ClinicalTrial savedClinicalTrial = result.getCreatedEntity();
        assertEquals("myFoo", savedClinicalTrial.getTitle());
        assertNotNull(savedClinicalTrial.getTrialNumber());


        assertThat(savedClinicalTrial, matchesExample(ClinicalTrial.builder()
                .title("myFoo")
                .creationDate(timeTraveller.getWhereWeAre().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(0L)
                .build()));


        ClinicalTrial clinicalTrial2 = new ClinicalTrial();
        clinicalTrial2.setTitle("myFoo2");
        clinicalTrial2.setTrialNumber("NCT102");

        AbstractGsrsEntityService.CreationResult<ClinicalTrial> result2 = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrial2));
        assertTrue(result2.isCreated());
        ClinicalTrial savedClinicalTrial2 = result2.getCreatedEntity();
        assertNotNull(savedClinicalTrial2.getTrialNumber());


        assertThat(savedClinicalTrial2, matchesExample(ClinicalTrial.builder()
                .title("myFoo2")
                .creationDate(timeTraveller.getWhereWeAre().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(0L)
                .build()));

    }

    @Test
    public void updateSingleRecord() throws Exception {
        ClinicalTrial clinicalTrial = new ClinicalTrial();
        clinicalTrial.setTitle("myFoo");
        clinicalTrial.setTrialNumber("NCT101");

        AbstractGsrsEntityService.CreationResult<ClinicalTrial> result = clinicalTrialService.createEntity(objectMapper.valueToTree(clinicalTrial));
        assertTrue(result.isCreated());
        ClinicalTrial savedClinicalTrial = result.getCreatedEntity();

        String trialNumber = savedClinicalTrial.getTrialNumber();
        assertNotNull(trialNumber);
        assertThat(savedClinicalTrial, matchesExample(ClinicalTrial.builder()
                .title("myFoo")
                .creationDate(timeTraveller.getWhereWeAre().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(0L)
                .build()));

        savedClinicalTrial.setTitle("updatedFoo");
        timeTraveller.jumpAhead(1, TimeUnit.DAYS);

        AbstractGsrsEntityService.UpdateResult<ClinicalTrial> updateResult = clinicalTrialService.updateEntity(objectMapper.valueToTree(savedClinicalTrial));

        assertEquals(AbstractGsrsEntityService.UpdateResult.STATUS.UPDATED, updateResult.getStatus());
        assertThat(updateResult.getUpdatedEntity(), matchesExample(ClinicalTrial.builder()
                .title("updatedFoo")
                .trialNumber(trialNumber)
                .creationDate(timeTraveller.getWhereWeWere().get().asDate())
                .lastModifiedDate(timeTraveller.getWhereWeAre().asDate())
                .internalVersion(1L)
                .build()));
    }
}
