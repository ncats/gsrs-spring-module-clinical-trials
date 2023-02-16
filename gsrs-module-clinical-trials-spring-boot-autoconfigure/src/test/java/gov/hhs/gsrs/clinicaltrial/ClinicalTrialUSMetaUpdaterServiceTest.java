package gov.hhs.gsrs.clinicaltrial;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gov.hhs.gsrs.clinicaltrial.us.services.ClinicalTrialUSEntityService;
import gov.hhs.gsrs.clinicaltrial.us.services.ClinicalTrialUSMetaUpdaterService;
import gov.nih.ncats.common.util.TimeUtil;
import gsrs.controller.GsrsControllerConfiguration;
import gsrs.junit.TimeTraveller;
import gsrs.startertests.GsrsEntityTestConfiguration;
import gsrs.startertests.GsrsJpaTest;
import gsrs.startertests.jupiter.AbstractGsrsJpaEntityJunit5Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ActiveProfiles("test")
@GsrsJpaTest(classes = { GsrsSpringApplication.class, GsrsControllerConfiguration.class, GsrsEntityTestConfiguration.class, ClinicalTrialUSRepository.class})
@Import({ClinicalTrialUS.class, ClinicalTrialUSEntityService.class, ClinicalTrialUSMetaUpdaterService.class})
public class ClinicalTrialUSMetaUpdaterServiceTest extends AbstractGsrsJpaEntityJunit5Test {
    @TempDir
    static File tempDir;

    @Autowired
    private ClinicalTrialUSEntityService clinicalTrialService;

    @Autowired
    private ClinicalTrialUSMetaUpdaterService clinicalTrialMetaUpdaterService;

    @RegisterExtension
    TimeTraveller timeTraveller = new TimeTraveller(LocalDate.of(1955, 11, 5));

    private JacksonTester<ClinicalTrialUS> json;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {

        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void compareLastUpdatedDatesWhenOneIsNull() throws Exception {
        Date date1 = null;
        Date date2 = TimeUtil.toDate(2014, 02, 11);
        assertEquals("DO_UPDATE_ON_DATE1_NULL", clinicalTrialMetaUpdaterService.compareLastUpdated(date1, date2));
    }
    @Test
    public void compareLastUpdatedDatesWhenEqual() throws Exception {
        Date date1 = TimeUtil.toDate(2014, 02, 11);
        Date date2 = TimeUtil.toDate(2014, 02, 11);
        assertEquals("SKIP_UPDATE", clinicalTrialMetaUpdaterService.compareLastUpdated(date1, date2));
    }

    @Test
    public void compareLastUpdatedDate1LessThanDate2() throws Exception {
        Date date1 = TimeUtil.toDate(2014, 2, 10);
        Date date2 = TimeUtil.toDate(2014, 2, 11);
        assertEquals("DO_UPDATE_DATE1_LT_DATE2", clinicalTrialMetaUpdaterService.compareLastUpdated(date1, date2));
    }
    @Test
    public void convertDateString1() throws Exception {
        Date date1 = TimeUtil.toDate(2020, 9, 19);
        assertEquals(date1.toInstant(), clinicalTrialMetaUpdaterService.convertDateString("September 19, 2020").toInstant());
    }
    @Test
    public void convertDateString2() throws Exception {
        Date date1 = TimeUtil.toDate(2020, 9, 1);
        assertEquals(date1.toInstant(), clinicalTrialMetaUpdaterService.convertDateString("September 2020").toInstant());
    }








}
