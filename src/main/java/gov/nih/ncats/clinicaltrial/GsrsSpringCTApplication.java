package gov.nih.ncats.clinicaltrial;
import gsrs.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableConfigurationProperties
@EnableGsrsApi(indexerType = EnableGsrsApi.IndexerType.LEGACY,
                entityProcessorDetector = EnableGsrsApi.EntityProcessorDetector.CONF)
@EnableGsrsJpaEntities
@SpringBootApplication
@EntityScan(basePackages ={"ix","gsrs", "gov.nih.ncats"} )
@EnableJpaRepositories(basePackages ={"ix","gsrs", "gov.nih.ncats"} )
@ComponentScan({"gov.nih.ncats.clinicaltrial.inheritance"})
// @EnableGsrsLegacyAuthentication
@EnableGsrsLegacyCache
@EnableGsrsLegacyPayload
@EnableGsrsLegacySequenceSearch
@EnableGsrsScheduler
@EnableGsrsBackup

public class GsrsSpringCTApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsrsSpringCTApplication.class, args);
    }

}
