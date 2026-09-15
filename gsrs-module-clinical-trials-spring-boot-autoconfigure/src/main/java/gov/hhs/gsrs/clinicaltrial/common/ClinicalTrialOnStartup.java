package gov.hhs.gsrs.clinicaltrial.common;

import tools.jackson.databind.json.JsonMapper;
import gsrs.cv.api.ControlledVocabularyApi;
import gsrs.cv.api.ControlledVocabularyRestApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@Configuration
public class ClinicalTrialOnStartup implements WebApplicationInitializer {


    @Value("${mygsrs.clinicaltrial.cvUrl}")
    private String cvUrl;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // servletContext.setInitParameter(spring.profiles.active", "devh2");
    }

    @Bean
    public ControlledVocabularyApi controlledVocabularyApi(RestTemplateBuilder restTemplateBuilder) {
        return new ControlledVocabularyRestApi(restTemplateBuilder, cvUrl, JsonMapper.builderWithJackson2Defaults().build());
    }

}