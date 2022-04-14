package gov.hhs.gsrs.clinicaltrial.us.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gsrs.api.AbstractLegacySearchGsrsEntityRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

public class ClinicalTrialsUSApi extends AbstractLegacySearchGsrsEntityRestTemplate<ClinicalTrialUSDTO, String> {
    public ClinicalTrialsUSApi(RestTemplateBuilder restTemplateBuilder, String baseUrl, ObjectMapper mapper) {
        super(restTemplateBuilder, baseUrl, "clinicaltrialsus", mapper);
    }

    @Override
    protected ClinicalTrialUSDTO parseFromJson(JsonNode node) {
        return getObjectMapper().convertValue(node, ClinicalTrialUSDTO.class);
    }

    @Override
    protected String getIdFrom(ClinicalTrialUSDTO dto) {
        return dto.getTrialNumber();
    }
}