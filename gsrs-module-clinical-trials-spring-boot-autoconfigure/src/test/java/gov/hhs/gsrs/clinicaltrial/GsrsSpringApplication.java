package gov.hhs.gsrs.clinicaltrial;

import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;
import gsrs.EnableGsrsLegacyAuthentication;
import gsrs.EnableGsrsLegacyCache;
import ix.core.search.bulk.EnableBulkSearch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableConfigurationProperties
@EnableGsrsApi(indexerType = EnableGsrsApi.IndexerType.LEGACY,
        entityProcessorDetector = EnableGsrsApi.EntityProcessorDetector.CUSTOM,
        indexValueMakerDetector = EnableGsrsApi.IndexValueMakerDetector.CUSTOM)
@EnableGsrsLegacyCache
@EnableGsrsLegacyAuthentication
@EnableGsrsJpaEntities
@SpringBootApplication
@EnableBulkSearch
@EntityScan(basePackages ={"ix","gsrs", "gov.nih.ncats", "gov.hhs.gsrs.clinicaltrial"} )

// @EnableJpaRepositories(basePackages ={"ix","gsrs", "gov.nih.ncats", "gov.hhs.gsrs.clinicaltrial"} )
//@Import(GsrsEntityTestConfiguration.class)
public class GsrsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsrsSpringApplication.class, args);
    }
}