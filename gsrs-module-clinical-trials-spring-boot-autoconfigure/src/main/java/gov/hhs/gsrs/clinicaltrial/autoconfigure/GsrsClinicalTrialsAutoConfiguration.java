package gov.hhs.gsrs.clinicaltrial.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import gsrs.EnableGsrsApi;
import gsrs.EnableGsrsJpaEntities;
import gsrs.api.substances.SubstanceRestApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableGsrsJpaEntities
@EnableGsrsApi
@Configuration
// @Import({})
public class GsrsClinicalTrialsAutoConfiguration {


    private ObjectMapper mapper = new ObjectMapper();

    @Bean
//    public SubstanceRestApi substanceRestApi(RestTemplateBuilder builder, SubstancesApiConfiguration substancesApiConfiguration){
//        substancesApiConfiguration.configure(builder);
//        SubstanceRestApi api = new SubstanceRestApi(builder, substancesApiConfiguration.getBaseURL(), mapper);
//        System.out.println("\n\n====\n\n API IS:" + api + "\n\n====\n\n");
//        return api;
//    }

    public SubstanceRestApi substanceRestApi( SubstancesApiConfiguration substancesApiConfiguration){
        return new SubstanceRestApi(substancesApiConfiguration.createNewRestTemplateBuilder(), substancesApiConfiguration.getBaseURL(), mapper);
    }

}
