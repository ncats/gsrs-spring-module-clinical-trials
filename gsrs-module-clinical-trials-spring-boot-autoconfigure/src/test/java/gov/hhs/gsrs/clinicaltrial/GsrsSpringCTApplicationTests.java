package gov.hhs.gsrs.clinicaltrial;

import gov.hhs.gsrs.clinicaltrial.us.controllers.ClinicalTrialUSController;
import gov.hhs.gsrs.clinicaltrial.us.services.ClinicalTrialUSLegacySearchService;
import gsrs.startertests.GsrsEntityTestConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// @Disabled
@ActiveProfiles("test")
@SpringBootTest(classes = {GsrsSpringApplication.class})
@TestPropertySource(properties = {
"gsrs.microservice.substances.api.baseURL=http://localhost:8081/",
"mygsrs.clinicaltrial.cvUrl=http://localhost:8080",
"mygsrs.substanceAPI.baseUrl=http://localhost:8080/",
"mygsrs.clinicaltrial.us.substance.linking.keyType.value=UUID",
"mygsrs.clinicaltrial.us.substance.linking.keyType.agencyCodeValue=BDNUM",
"mygsrs.clinicaltrial.us.ClinicalTrial.trialNumberPattern=^NCT\\d+$",
"mygsrs.clinicaltrial.us.substanceKeyPatternRegex=^[-0-9a-f]{36}$",
"mygsrs.clinicaltrial.us.agencySubstanceKeyTypeValue=BDNUM",
"mygsrs.clinicaltrial.us.skipSubstanceValidation=false",
"mygsrs.clinicaltrial.eu.ClinicalTrialEurope.trialNumberPattern=^\\d{4}-\\d{6}-\\d{2}-[A-Z]{2}$",
"mygsrs.clinicaltrial.eu.substanceKeyPatternRegex=^[-0-9a-f]{36}$",
"mygsrs.clinicaltrial.eu.agencySubstanceKeyTypeValue=BDNUM",
"mygsrs.clinicaltrial.eu.skipSubstanceValidation=false"
})

class GsrsSpringCTApplicationTests {
    @Autowired
    private ClinicalTrialUSController controller;

    @Autowired
    private ClinicalTrialUSLegacySearchService searchService;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
