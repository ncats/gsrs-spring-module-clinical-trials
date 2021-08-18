package gov.nih.ncats2.clinicaltrial.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class ClinicalTrialOnStartup implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // System.out.println("ClinicalTrialOnStartup.ClinicalTrialOnStartup is running.");
        // servletContext.setInitParameter(spring.profiles.active", "devh2");
    }
}