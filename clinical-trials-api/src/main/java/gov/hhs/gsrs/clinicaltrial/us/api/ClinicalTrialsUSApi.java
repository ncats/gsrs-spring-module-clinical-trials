package gov.hhs.gsrs.clinicaltrial.us.api;

import tools.jackson.databind.JsonNode;
import gsrs.api.AbstractLegacySearchGsrsEntityRestTemplate;
import org.springframework.boot.restclient.RestTemplateBuilder;
import tools.jackson.databind.json.JsonMapper;

//
public class ClinicalTrialsUSApi extends AbstractLegacySearchGsrsEntityRestTemplate<ClinicalTrialUSDTO, String> {
    public ClinicalTrialsUSApi(RestTemplateBuilder restTemplateBuilder, String baseUrl, JsonMapper mapper) {
        super(restTemplateBuilder, baseUrl, "clinicaltrialsus", mapper);
    }

    @Override
    protected ClinicalTrialUSDTO parseFromJson(JsonNode node) {
        return getMapper().convertValue(node, ClinicalTrialUSDTO.class);
    }

    @Override
    protected String getIdFrom(ClinicalTrialUSDTO dto) {
        return dto.getTrialNumber();
    }
}