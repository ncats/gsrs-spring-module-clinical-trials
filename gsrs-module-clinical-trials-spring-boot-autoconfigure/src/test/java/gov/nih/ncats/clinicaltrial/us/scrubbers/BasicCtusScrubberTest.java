package gov.nih.ncats.clinicaltrial.us.scrubbers;

import gov.hhs.gsrs.clinicaltrial.autoconfigure.SubstancesApiConfiguration;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import gov.nih.ncats.clinicaltrial.GsrsSpringApplication;
import gov.hhs.gsrs.clinicaltrial.us.scrubbers.BasicCtusScrubber;
import gov.hhs.gsrs.clinicaltrial.us.scrubbers.BasicCtusScrubberParameters;

// import gsrs.substances.tests.SubstanceTestUtil;
import gsrs.api.substances.SubstanceRestApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

@SpringBootTest(classes = {GsrsSpringApplication.class, SubstancesApiConfiguration.class})
// @WithMockUser(username = "admin", roles = "Admin")
@Slf4j
// @ActiveProfiles("test")
// @Profile("test")
@TestPropertySource(properties = {
    "mygsrs.substanceAPI.baseUrl='http://localhost:9081/'",
    "gsrs.microservice.substances.api.baseUrl='http://localhost:9081/'",
    "gsrs.microservice.substances.api.headers.auth-username='admin'",
    "gsrs.microservice.substances.api.headers.auth-key='myKey'"
})
public class BasicCtusScrubberTest {


    @Autowired
    private SubstanceRestApi substanceRestApi;
    @Test
    public void testScrubSubstanceStatus2() {
        ClinicalTrialUS ctus = new ClinicalTrialUS();
        ctus.setTrialNumber("NCT001");
        ctus.setTitle("Title 1");
        ClinicalTrialUSDrug ctd1 = new ClinicalTrialUSDrug();
        ctd1.setId(1L);
        ctd1.setSubstanceKey(UUID.randomUUID().toString());
        ctd1.setSubstanceKeyType("UUID");
        ctd1.setProtectedMatch(false);

        ClinicalTrialUSDrug ctd2 = new ClinicalTrialUSDrug();
        ctd2.setSubstanceKey(UUID.randomUUID().toString());
        ctd2.setSubstanceKeyType("UUID");
        ctd2.setProtectedMatch(true);

        BasicCtusScrubberParameters scrubberSettings = new BasicCtusScrubberParameters();
        scrubberSettings.setSubstanceReferenceCleanup(false);
        scrubberSettings.setRegenerateUUIDs(false);
        scrubberSettings.setChangeAllStatuses(false);
        scrubberSettings.setChangeAllStatusesNewStatusValue("pending");
        BasicCtusScrubber scrubber = new BasicCtusScrubber(scrubberSettings);
        Optional<ClinicalTrialUS> cleaned = scrubber.scrub(ctus);
        ClinicalTrialUS cleanedTrial = (ClinicalTrialUS) cleaned.get();
        Assertions.assertEquals("NCT001", cleanedTrial.getTrialNumber());
    }

    @Test
    public void testProtectedMatch() {
        ClinicalTrialUS ctus = new ClinicalTrialUS();
        ctus.setTrialNumber("NCT001");
        ctus.setTitle("Title 1");
        ClinicalTrialUSDrug ctd1 = new ClinicalTrialUSDrug();
        ctd1.setId(1L);
        ctd1.setSubstanceKey(UUID.randomUUID().toString());
        ctd1.setSubstanceKeyType("UUID");
        ctd1.setProtectedMatch(false);
        ctus.getClinicalTrialUSDrug().add(ctd1);

        ClinicalTrialUSDrug ctd2 = new ClinicalTrialUSDrug();
        ctd2.setId(2L);
        ctd2.setSubstanceKey(UUID.randomUUID().toString());
        ctd2.setSubstanceKeyType("UUID");
        ctd2.setProtectedMatch(true);
        ctus.getClinicalTrialUSDrug().add(ctd2);


        BasicCtusScrubberParameters scrubberSettings = new BasicCtusScrubberParameters();
        scrubberSettings.setSubstanceReferenceCleanup(false);
        scrubberSettings.setRegenerateUUIDs(false);
        scrubberSettings.setChangeAllStatuses(false);
        scrubberSettings.setChangeAllStatusesNewStatusValue("pending");
        BasicCtusScrubber scrubber = new BasicCtusScrubber(scrubberSettings);
        Optional<ClinicalTrialUS> cleaned = scrubber.scrub(ctus);
        ClinicalTrialUS cleanedTrial = (ClinicalTrialUS) cleaned.get();
        Assertions.assertEquals(1, cleanedTrial.getClinicalTrialUSDrug().size());
    }


    private void predicateUsageAssertionHelper(List<?> predicate) {
        System.out.println(predicate.toString());
    }
}
