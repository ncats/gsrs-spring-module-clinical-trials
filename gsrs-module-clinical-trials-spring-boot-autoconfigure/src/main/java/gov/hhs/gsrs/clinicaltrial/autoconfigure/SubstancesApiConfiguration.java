package gov.hhs.gsrs.clinicaltrial.autoconfigure;

import gsrs.api.AbstractGsrsRestApiConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@ConfigurationProperties("gsrs.microservice.substances.api")
public class SubstancesApiConfiguration extends AbstractGsrsRestApiConfiguration {
}


