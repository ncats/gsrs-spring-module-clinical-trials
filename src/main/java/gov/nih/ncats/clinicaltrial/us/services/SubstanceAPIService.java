package gov.nih.ncats.clinicaltrial.us.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SubstanceAPIService {


    // experimental
    class QuickResult {
        public boolean found = false;
        public boolean hasResponseError = false;
        public boolean hasInputError = false;
        public String systemCode;
        public String agencyCode;
        public String agencyCodeType = agencyCodeCodeSystemValue;
        public String displayName;
        public String url;
    }


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment env;

    final RestTemplate restTemplate = new RestTemplate();

    @Value("${mygsrs.substanceAPI.baseUrl}")
    private String baseUrl;
    // for testing override see:
    // https://www.baeldung.com/spring-tests-override-properties

    @Value("${mygsrs.clinicaltrial.us.substance.linking.keyType.agencyCodeValue}")
    private String agencyCodeCodeSystemValue;



    public Boolean substanceExists(String uuid) {
        System.out.println("Inside "+ "substanceExists " + uuid);

        // is there a way to make this final and use property?
        String urlTemplate1 = baseUrl +  "api/v1/substances(%s)";
        Boolean exists;
        if (uuid == null) return null;
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(String.format(urlTemplate1, uuid), String.class);
        } catch(HttpClientErrorException e) {
            // this is wierd. 404 will generate exception.
            e.printStackTrace();
            return null;
        }
        if(response == null) return null;

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode == null)  return null;

        if (statusCode.equals(HttpStatus.valueOf(404))) {
            return false;
        }
        if (statusCode.equals(HttpStatus.OK)) {
            JsonNode root = null;
            try {
                root = objectMapper.readTree(response.getBody());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
            // Should not be necessary, but possible to get 200 and valid json
            // on a redirect.
            JsonNode name = root.path("uuid");
            if (name != null &&  name.asText().equals(uuid)) {
                return true;
            }
        }
        return null;
     }



    // this was made experimentally while wait for substance core substanceAPI
    // could evolve into something different.

    public ResponseEntity<List<QuickResult>> getQuickResultMatchesByUuids() {
        List<String> uuidList = new ArrayList<String>();
        uuidList.add("a05ec20c-8fe2-4e02-ba7f-df69e5e30248");
        uuidList.add("ef3fc429-75a3-4691-9ccb-66715060dce8");

        String urlTemplate1 = baseUrl + "ginas/app/api/v1/substances(%s)?view=internal";
        List<QuickResult> matches = new ArrayList<QuickResult>();
        for (String uuid : uuidList) {
            QuickResult qr = new QuickResult();
            matches.add(qr);
            if (uuid == null) {
                qr.hasInputError = true;
                continue;
            }
            ResponseEntity<String> response = null;
            HttpStatus statusCode = null;
            try {
                response = restTemplate.getForEntity(String.format(urlTemplate1, uuid), String.class);
            } catch (HttpClientErrorException e) {
                qr.hasResponseError = true;
                continue;
            }
            if (response == null) {
                continue;
            }
            statusCode = response.getStatusCode();
            if (statusCode.equals(HttpStatus.valueOf(404))) {
                qr.hasResponseError = true;
            }
            if (statusCode.equals(HttpStatus.OK)) {
                JsonNode root = null;
                try {
                    root = objectMapper.readTree(response.getBody());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    qr.hasResponseError = true;
                }

                JsonNode systemCodeNode = root.path("uuid");
                if(root.path("codes").isArray()) {
                    ArrayNode codes =  (ArrayNode) root.path("codes");
                    for (JsonNode code : codes) {
                        // fix for possible multiple bdnum situation.
                        // note this doesn't give anything ncats public version because they don't have a bdnum
                        if (
                            !code.path("codeSystem").isMissingNode()
                            && code.path("codeSystem").asText().equals(agencyCodeCodeSystemValue)
                            && !code.path("type").isMissingNode()
                            && code.path("type").asText().equals("PRIMARY")
                            && !code.path("code").isMissingNode()
                        ) {
                            qr.agencyCode = code.path("code").asText();
                            break;
                        }
                    }
                }
                if (!systemCodeNode.isMissingNode() && systemCodeNode.asText().equals(uuid)) {
                    qr.found = true;
                    qr.systemCode = systemCodeNode.asText();
                    JsonNode displayNameNode = root.path("_name");
                    JsonNode urlNode = root.path("_self");
                    if (!displayNameNode.isMissingNode()) {
                        qr.displayName = displayNameNode.asText();
                    }
                    if (!urlNode.isMissingNode()) {
                        qr.url = urlNode.asText();
                    }
                }
            }
        }
        return ResponseEntity.ok(matches);
    }



    // using this temporarily to get around auth/CORS issues.
    // actually not needed; at least GSRS public version is not giving me CORS problem

    public ResponseEntity<String> getSubstanceDetailsFromUUID(String uuid) {
        System.out.println("Inside "+ "getSubstanceDetailsFromUUID " + uuid);
        // is there a way to make this final and use property?
        String urlTemplate1 = baseUrl +  "ginas/app/api/v1/substances(%s)";
        Boolean exists;
        if (uuid == null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{'errors': 'Substance UUID is required.'}");
        }
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(String.format(urlTemplate1, uuid), String.class);
            return response;
        } catch(HttpClientErrorException e) {
            // this is weird. 404 will generate exception.
            e.printStackTrace();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'errors': 'There were errors.'}");
    }

    // using this temporarily to get around auth/CORS issues.
    // actually not needed; at least GSRS public version is not giving me CORS problem

    public ResponseEntity<String> getSubstanceDetailsFromName(String name) {
        System.out.println("Inside "+ "getSubstanceDetailsFromName " + name);
        // is there a way to make this final and use property?
        String urlTemplate1 = baseUrl +  "ginas/app/api/v1/substances/search?q=root_names_name:\"^%s$\"&fdim=1";
        Boolean exists;
        if (name == null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{'errors': 'Name is required.'}");
        }
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(String.format(urlTemplate1, encodeParameterValue(name)), String.class);
            return response;
        } catch(HttpClientErrorException e) {
            // this is weird. 404 will generate exception.
            e.printStackTrace();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'errors': 'There were errors.'}");
    }

    private String encodeParameterValue(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}