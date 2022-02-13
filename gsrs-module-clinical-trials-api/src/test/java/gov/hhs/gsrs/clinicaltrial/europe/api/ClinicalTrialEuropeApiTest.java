package gov.hhs.gsrs.clinicaltrial.europe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.hhs.gsrs.clinicaltrial.europe.api.ClinicalTrialEuropeApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
//@SpringBootTest
@RestClientTest(ClinicalTrialEuropeApi.class)
public class ClinicalTrialEuropeApiTest {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private ClinicalTrialEuropeApi api;

    @TestConfiguration
    static class Testconfig{
        @Bean
        public ClinicalTrialEuropeApi clinicalTrialEuropeApi(RestTemplateBuilder restTemplateBuilder){
            return new ClinicalTrialEuropeApi(restTemplateBuilder, "http://example.com", new ObjectMapper());
        }
    }

    @BeforeEach
    public void setup(){
        this.mockRestServiceServer.reset();
    }

    @AfterEach
    public void verify(){
        this.mockRestServiceServer.verify();
    }

    @Test
    public void count() throws IOException {
        this.mockRestServiceServer
                .expect(requestTo("/api/v1/clinicaltrialseurope/@count"))
                .andRespond(withSuccess("5", MediaType.APPLICATION_JSON));

        assertEquals(5L, api.count());
    }
    @Test
    public void count0() throws IOException {
        this.mockRestServiceServer
                .expect(requestTo("/api/v1/clinicaltrialseurope/@count"))
                .andRespond(withSuccess("0", MediaType.APPLICATION_JSON));

        assertEquals(0L, api.count());
    }
    @Test
    public void countError(){
        this.mockRestServiceServer
                .expect(requestTo("/api/v1/clinicaltrialseurope/@count"))
                .andRespond(withServerError());
        boolean exThrown = false;
        assertThrows(IOException.class,()-> api.count());
    }

    // see SubstanceApiTest for more test examples
}
