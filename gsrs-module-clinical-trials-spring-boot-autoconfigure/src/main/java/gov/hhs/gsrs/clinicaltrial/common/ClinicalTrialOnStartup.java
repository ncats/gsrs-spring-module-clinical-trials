package gov.hhs.gsrs.clinicaltrial.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import gsrs.cv.api.ControlledVocabularyApi;
import gsrs.cv.api.ControlledVocabularyRestApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class ClinicalTrialOnStartup implements WebApplicationInitializer {

    @Value("${mygsrs.clinicaltrial.cvUrl}")
    private String cvUrl;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // System.out.println("ClinicalTrialOnStartup.ClinicalTrialOnStartup is running.");
        // servletContext.setInitParameter(spring.profiles.active", "devh2");
    }

    @Bean
    public ControlledVocabularyApi controlledVocabularyApi(RestTemplateBuilder restTemplateBuilder) {
        return new ControlledVocabularyRestApi(restTemplateBuilder, cvUrl, new ObjectMapper());
    }
}