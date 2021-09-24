package gov.hhs.gsrs.clinicaltrial.autoconfigure;

import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;
import org.springframework.context.annotation.Configuration;

@EnableGsrsJpaEntities
@EnableGsrsApi
@Configuration
// @Import({})
public class GsrsClinicalTrialsAutoConfiguration {
}