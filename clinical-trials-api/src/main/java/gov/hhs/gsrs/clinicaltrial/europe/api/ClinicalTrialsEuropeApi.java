package gov.hhs.gsrs.clinicaltrial.europe.api;

import tools.jackson.databind.JsonNode;
import gsrs.api.AbstractLegacySearchGsrsEntityRestTemplate;
import org.springframework.boot.restclient.RestTemplateBuilder;
import tools.jackson.databind.json.JsonMapper;

public class ClinicalTrialsEuropeApi extends AbstractLegacySearchGsrsEntityRestTemplate<ClinicalTrialEuropeDTO, String> {
    public ClinicalTrialsEuropeApi(RestTemplateBuilder restTemplateBuilder, String baseUrl, JsonMapper mapper) {
        super(restTemplateBuilder, baseUrl, "clinicaltrialseurope", mapper);
    }

    @Override
    protected ClinicalTrialEuropeDTO parseFromJson(JsonNode node) {
        return getMapper().convertValue(node, ClinicalTrialEuropeDTO.class);
    }

    @Override
    protected String getIdFrom(ClinicalTrialEuropeDTO dto) {
        return dto.getTrialNumber();
    }
}