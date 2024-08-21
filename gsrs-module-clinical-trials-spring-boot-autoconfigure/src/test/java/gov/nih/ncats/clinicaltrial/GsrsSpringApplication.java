package gov.nih.ncats.clinicaltrial;

import gov.hhs.gsrs.clinicaltrial.ClinicalTrialDataSourceConfig;
import gov.hhs.gsrs.clinicaltrial.europe.EnableClinicalTrialEurope;
import gov.hhs.gsrs.clinicaltrial.us.EnableClinicalTrialUS;
import gsrs.*;
import gsrs.repository.UserProfileRepository;
import ix.core.search.bulk.EnableBulkSearch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ConditionalOnMissingBean(UserProfileRepository.class)
@EnableClinicalTrialUS
@EnableClinicalTrialEurope
@EnableConfigurationProperties

// @EnableGsrsApi(indexerType = EnableGsrsApi.IndexerType.LEGACY,
//entityProcessorDetector = EnableGsrsApi.EntityProcessorDetector.CUSTOM,
// indexValueMakerDetector = EnableGsrsApi.IndexValueMakerDetector.CUSTOM)

@EnableGsrsApi(
indexValueMakerDetector = EnableGsrsApi.IndexValueMakerDetector.CONF,
additionalDatabaseSourceConfigs = {ClinicalTrialDataSourceConfig.class}
)


// @EnableGsrsJpaEntities
@EnableGsrsLegacyAuthentication
@SpringBootApplication
@EnableBulkSearch
@EntityScan(basePackages ={"ix","gsrs", "gov.nih.ncats", "gov.hhs.gsrs.clinicaltrial"} )
// @EnableJpaRepositories(basePackages ={"ix","gsrs", "gov.nih.ncats"} )
//@Import(GsrsEntityTestConfiguration.class)
// @EnableGsrsLegacySequenceSearch
// @EnableGsrsLegacyStructureSearch
@EnableGsrsLegacyCache
@EnableGsrsLegacyPayload


public class GsrsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsrsSpringApplication.class, args);
    }
}