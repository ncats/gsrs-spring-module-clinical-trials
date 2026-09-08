package gov.hhs.gsrs.clinicaltrial.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
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
//    public SubstanceRestApi substanceRestApi(RestTemplateBuilder builder, SubstancesApiConfiguration substancesApiConfiguration){
//        substancesApiConfiguration.configure(builder);
//        SubstanceRestApi api = new SubstanceRestApi(builder, substancesApiConfiguration.getBaseURL(), mapper);
//        return api;
//    }

    public SubstanceRestApi substanceRestApi( SubstancesApiConfiguration substancesApiConfiguration){
        return new SubstanceRestApi(substancesApiConfiguration.createNewRestTemplateBuilder(), substancesApiConfiguration.getBaseURL(), new ObjectMapper());
    }

}
