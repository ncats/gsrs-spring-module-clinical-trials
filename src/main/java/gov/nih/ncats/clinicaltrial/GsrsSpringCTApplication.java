package gov.nih.ncats.clinicaltrial;
import gsrs.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableConfigurationProperties
@EnableGsrsApi(indexerType = EnableGsrsApi.IndexerType.LEGACY,
                entityProcessorDetector = EnableGsrsApi.EntityProcessorDetector.CONF)
@EnableGsrsJpaEntities
@SpringBootApplication
@EntityScan(basePackages ={"ix","gsrs", "gov.nih.ncats", "fda.gsrs.substance"} )
@EnableJpaRepositories(basePackages ={"ix","gsrs", "gov.nih.ncats"} )
// @EnableGsrsLegacyAuthentication
@EnableGsrsLegacyCache
@EnableGsrsLegacyPayload
@EnableGsrsLegacySequenceSearch
@EnableGsrsScheduler
@EnableGsrsBackup
@EnableGsrsLegacyStructureSearch
public class GsrsSpringCTApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsrsSpringCTApplication.class, args);
    }

}
