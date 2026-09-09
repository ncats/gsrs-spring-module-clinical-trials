package gov.hhs.gsrs.clinicaltrial.autoconfigure;

import tools.jackson.databind.json.JsonMapper;
import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;
import gsrs.api.substances.SubstanceRestApi;
import ix.core.search.bulk.EnableBulkSearch;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@EnableGsrsJpaEntities
@EnableGsrsApi
@EnableBulkSearch
@AutoConfiguration
public class GsrsClinicalTrialsAutoConfiguration {

    @Bean
    @Primary
    public JsonMapper objectMapper() {
        return JsonMapper.builderWithJackson2Defaults().build();
    }

    @Bean
    public SubstanceRestApi substanceRestApi( SubstancesApiConfiguration substancesApiConfiguration){
        return new SubstanceRestApi(substancesApiConfiguration.createNewRestTemplateBuilder(), substancesApiConfiguration.getBaseURL(),
                JsonMapper.builderWithJackson2Defaults().build());
    }

}
