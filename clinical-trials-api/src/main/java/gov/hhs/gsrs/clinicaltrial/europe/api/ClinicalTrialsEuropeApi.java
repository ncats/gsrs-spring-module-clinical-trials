package gov.hhs.gsrs.clinicaltrial.europe.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gsrs.api.AbstractLegacySearchGsrsEntityRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

public class ClinicalTrialsEuropeApi extends AbstractLegacySearchGsrsEntityRestTemplate<ClinicalTrialEuropeDTO, String> {
    public ClinicalTrialsEuropeApi(RestTemplateBuilder restTemplateBuilder, String baseUrl, ObjectMapper mapper) {
        super(restTemplateBuilder, baseUrl, "clinicaltrialseurope", mapper);
    }

    @Override
    protected ClinicalTrialEuropeDTO parseFromJson(JsonNode node) {
        return getObjectMapper().convertValue(node, ClinicalTrialEuropeDTO.class);
    }

    @Override
    protected String getIdFrom(ClinicalTrialEuropeDTO dto) {
        return dto.getTrialNumber();
    }
}