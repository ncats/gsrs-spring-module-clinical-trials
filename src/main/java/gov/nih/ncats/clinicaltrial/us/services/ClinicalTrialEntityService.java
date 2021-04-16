package gov.nih.ncats.clinicaltrial.us.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.nih.ncats.clinicaltrial.us.repositories.ClinicalTrialRepository;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.utils.importmapper.SourceToTargetField;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.service.AbstractGsrsEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import gov.nih.ncats.clinicaltrial.us.utils.importmapper.SourceToTargetFieldsMapper;

@Service
public class ClinicalTrialEntityService extends AbstractGsrsEntityService<ClinicalTrial, String> {
    public static final String  CONTEXT = "clinicaltrial";

    public ClinicalTrialEntityService() {
        super("clinicaltrial", Pattern.compile("^NCT\\d+$"), null, null, null);
    }

    @Autowired
    private ClinicalTrialRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Class<ClinicalTrial> getEntityClass() {
        return ClinicalTrial.class;
    }

    @Override
    public String parseIdFromString(String idAsString) {
        return idAsString;
    }

    @Override
    protected ClinicalTrial fromNewJson(JsonNode json) throws IOException {
        // return CTUtils.adaptSingleRecord(json, objectMapper, true);
        return objectMapper.convertValue(json, ClinicalTrial.class);

    }

    @Override
    public Page page(Pageable pageable) {

        return repository.findAll(pageable);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    protected ClinicalTrial update(ClinicalTrial clinicalTrial) {
        System.out.println("\n\n ==== Updating ====XX  \n\n");
        return repository.saveAndFlush(clinicalTrial);
    }

    @Override
    protected AbstractEntityUpdatedEvent<ClinicalTrial> newUpdateEvent(ClinicalTrial updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<ClinicalTrial> newCreationEvent(ClinicalTrial createdEntity) {
        return null;
    }

    @Override
    public String getIdFrom(ClinicalTrial entity) {
        return entity.getTrialNumber();
    }

    @Override
    protected List<ClinicalTrial> fromNewJsonList(JsonNode list) throws IOException {
        // return CTUtils.adaptList(list, objectMapper, true);
        return null;
    }


    @Override
    protected ClinicalTrial fromUpdatedJson(JsonNode json) throws IOException {
        // return CTUtils.adaptSingleRecord(json, objectMapper, false);
        return objectMapper.convertValue(json, ClinicalTrial.class);

    }

    @Override
    protected List<ClinicalTrial> fromUpdatedJsonList(JsonNode list) throws IOException {
        // return CTUtils.adaptList(list, objectMapper, false);
        return null;
    }

    @Override
    protected JsonNode toJson(ClinicalTrial clinicalTrial) throws IOException {
        return objectMapper.valueToTree(clinicalTrial);
    }

    @Override
    public ClinicalTrial create(ClinicalTrial clinicalTrial) {
        System.out.println("\n\n ==== Creating  ==== XX  \n\n");
        try {
            // System.out.println(clinicalTrial.getTrialNumber());
            return repository.saveAndFlush(clinicalTrial);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<ClinicalTrial> get(String id) {
        if(id==null) {
            
        }
        System.out.println("\n\n ==== Getting ID: " + id + " ====\n\n");
        return repository.findById(id);
    }

    @Override
    public Optional<ClinicalTrial> flexLookup(String someKindOfId) {
        return repository.findByTitle(someKindOfId);
    }

    @Override
    protected Optional<String> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<ClinicalTrial> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().getTrialNumber());
        }
        return Optional.empty();
    }

    // create chunk stream either from string, file, or url-web
    // applyMetaData to existing trials or create when then don't exist
    // provide feedback to task manager or UI.

    // ui example.
        // I am editing clinical trial and want to update meta data.

    // task manager
        // weekly updates are made from clinical trials.
    final String urlTemplate= "https://clinicaltrials.gov/ct2/results/download_fields?down_flds=all&down_count=%s&down_fmt=tsv&down_chunk=%s&term=%s";
    // final String nctNumber = "NCT04818177";
    final String nctNumber = null;
    final int downCount= 100;
    final int downChunk= 1;
    final DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    final DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("MMMM yyyy");
    final SourceToTargetFieldsMapper sourceToTargetFieldsMapper = new SourceToTargetFieldsMapper();
    final LinkedHashMap<String, SourceToTargetField> sourceToTargetFieldsLHM = sourceToTargetFieldsMapper.generateExtendedSourceToTargetFieldsMap();


    public void download() {
        /* move to a test
        Date date1 = null;
        Date date2 = new Date(2014, 02, 11);
        System.out.println("date1:" + date1);
        System.out.println("date2:" + date2);
        System.out.print("Doing compare " + compareLastUpdated(date1, date2));
        */
    sourceToTargetFieldsMapper.dumpExtendedSourceToTargetFieldsMap();
    ObjectMapper objectMapper = new ObjectMapper();
    BufferedReader in = prepareCTApiV1StreamFromWeb(downCount, downChunk, nctNumber);
    List<LinkedHashMap<String, Object>> list = processCTApiV1StreamChunk(in);
    for (LinkedHashMap lhm : list) {
        // AbstractGsrsEntityService.CreationResult<ClinicalTrial> result = null;
        createOrUpdateEntityFromMetaData(lhm);
    }
    }

    public void createOrUpdateEntityFromMetaData(LinkedHashMap lhm) {
        // takes linked hashmap derived from clinicaltrials.gov api v1 tsv format.
        // Either creates or  updates the metadata for a trial.
        // But only updates if the lastUpdated values are different.
        // There should later be on option to force update even if lastUpdated
        // dates are the same.
        String trialNumber = String.valueOf(lhm.get("NCT Number"));
        Optional<ClinicalTrial> ctOld =  get(trialNumber);
        ClinicalTrial ctNew = new ClinicalTrial();
        if(!ctOld.isPresent()) {
            try {
                ctNew = applyCTApiV1TsvHashMapToClinicalTrial(lhm, ctNew);
                createEntity(objectMapper.valueToTree(ctNew));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String compare = compareLastUpdated(
                        ctOld.get().getLastUpdated(),
                        convertDateString((String) lhm.get("Last Update Posted"))
                );
                System.out.println("Comparison result = " + compare);
                if (compare.equals("DO_UPDATE") || compare.equals("DO_UPDATE_ON_DATE1_NULL")) {
                    ctNew = applyCTApiV1TsvHashMapToClinicalTrial(lhm, ctOld.orElse(null));
                    updateEntity(objectMapper.valueToTree(ctNew));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String compareLastUpdated(Date oldLastUpdated, Date newLastUpdated) {
        String result = null;
        if (newLastUpdated == null) {
            return "SKIP_UPDATE_ON_DATE2_NULL";
        }
        if (oldLastUpdated == null) {
            return "DO_UPDATE_ON_DATE1_NULL";
        }
        if ((int) oldLastUpdated.compareTo(newLastUpdated) < 0) {
            return "DO_UPDATE_DATE1_LT_DATE2)";
        }
        return "SKIP_UPDATE";
    }



    public Date convertDateString(String s) {
        if (s==null || s.trim().equals("")) return null;
        LocalDate localDate = null;
        Date date;
        try {
            localDate = LocalDate.parse(s, dateFormatter1);
            date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return date;
        } catch (Exception ex) {
            System.out.println("Date conversion error 1 occurred. Will try strategy 2 on:" + s);
        }
        try {
            YearMonth ym = YearMonth.parse(s, dateFormatter2);
            localDate = ym.atDay(1);
            date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return date;
        } catch (Exception ex) {
            System.out.println("Date conversion error 2 occurred on:" + s);
        }
        return null;
    }

    public String truncateUtf8(String value, int maxLen) {
        if (value==null) return null;
        byte[] bytes = new byte[0];
        try {
            bytes = value.getBytes("UTF-8");
            if (bytes.length > maxLen) {
                return new String(bytes, 0, maxLen, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public ClinicalTrial applyCTApiV1TsvHashMapToClinicalTrial(LinkedHashMap lhm, ClinicalTrial ct) {
        // ct should either be an empty trial Pojo or an existing one for update
        ct.setTrialNumber(String.valueOf(lhm.get("NCT Number")));
        ct.setTitle(truncateUtf8(String.valueOf(lhm.get("Title")), sourceToTargetFieldsLHM.get("Title").targetLength));
        ct.setAcronym(truncateUtf8(String.valueOf(lhm.get("Acronym")),sourceToTargetFieldsLHM.get("Acronym").targetLength));
        ct.setStatus(truncateUtf8(String.valueOf(lhm.get("Status")),sourceToTargetFieldsLHM.get("Status").targetLength));
        ct.setStudyResults(truncateUtf8(String.valueOf(lhm.get("Study Results")),sourceToTargetFieldsLHM.get("Study Results").targetLength));
        ct.setConditions(truncateUtf8(String.valueOf(lhm.get("Conditions")),sourceToTargetFieldsLHM.get("Conditions").targetLength));
        ct.setIntervention(truncateUtf8(String.valueOf(lhm.get("Interventions")),sourceToTargetFieldsLHM.get("Interventions").targetLength));
        ct.setOutcomeMeasures(truncateUtf8(String.valueOf(lhm.get("Outcome Measures")),sourceToTargetFieldsLHM.get("Outcome Measures").targetLength));
        ct.setSponsor(truncateUtf8(String.valueOf(lhm.get("Sponsor/Collaborators")),sourceToTargetFieldsLHM.get("Sponsor/Collaborators").targetLength));
        ct.setGender(truncateUtf8(String.valueOf(lhm.get("Gender")),sourceToTargetFieldsLHM.get("Gender").targetLength));
        ct.setAgeGroups(truncateUtf8(String.valueOf(lhm.get("Age")),sourceToTargetFieldsLHM.get("Age").targetLength));
        ct.setPhases(truncateUtf8(String.valueOf(lhm.get("Phases")),sourceToTargetFieldsLHM.get("Phases").targetLength));
        ct.setEnrollment(truncateUtf8(String.valueOf(lhm.get("Enrollment")),sourceToTargetFieldsLHM.get("Enrollment").targetLength));
        ct.setFundedBys(truncateUtf8(String.valueOf(lhm.get("Funded Bys")),sourceToTargetFieldsLHM.get("Funded Bys").targetLength));
        ct.setStudyTypes(truncateUtf8(String.valueOf(lhm.get("Study Type")),sourceToTargetFieldsLHM.get("Study Type").targetLength));
        ct.setOtherIds(truncateUtf8(String.valueOf(lhm.get("Other IDs")),sourceToTargetFieldsLHM.get("Other IDs").targetLength));
        ct.setStudyDesigns(truncateUtf8(String.valueOf(lhm.get("Study Designs")),sourceToTargetFieldsLHM.get("Study Designs").targetLength));
        ct.setLocations(truncateUtf8(String.valueOf(lhm.get("Locations")),sourceToTargetFieldsLHM.get("Locations").targetLength));
        ct.setUrl(truncateUtf8(String.valueOf(lhm.get("Url")),sourceToTargetFieldsLHM.get("Url").targetLength));
        // dates
        ct.setStartDate(convertDateString(String.valueOf(lhm.get("Start Date"))));
        ct.setPrimaryCompletionDate(convertDateString(String.valueOf(lhm.get("Primary Completion Date"))));
        ct.setCompletionDate(convertDateString(String.valueOf(lhm.get("Completion Date"))));
        ct.setFirstReceived(convertDateString(String.valueOf(lhm.get("First Posted"))));
        ct.setLastUpdated(convertDateString(String.valueOf(lhm.get("Last Update Posted"))));
        return ct;
    }


    public BufferedReader prepareCTApiV1StreamFromString(String input) {
        Reader inputString = new StringReader(input);
        BufferedReader br = new BufferedReader(inputString);
        return br;
    }

    public BufferedReader prepareCTApiV1StreamFromFile(String inputFile) throws FileNotFoundException {
        FileReader fr = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fr);
        return br;
    }

    public BufferedReader prepareCTApiV1StreamFromWeb(int downCount, int downChunk, String trialNumber) {
        if (trialNumber == null) {
            trialNumber = "";
        }
        String urlString = String.format(urlTemplate, downCount, downChunk, trialNumber);

        URL link = null;
        try {
            link = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(link.openStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return br;
    }


    public List<LinkedHashMap<String, Object>> processCTApiV1StreamChunk(BufferedReader br) {
        // Note: in stream will be closed after processing chunk.
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
        String inputLine;
        String[] rawFields = null;
        String [] fieldNames = sourceToTargetFieldsMapper.sourceFields();

        System.out.println("fieldNames.length: " + fieldNames.length);
        int c=0;
        while (true) {
            try {
                if (!((inputLine = br.readLine()) != null)) break;
                inputLine = inputLine.replace("[\n\r]$", "");
                rawFields = inputLine.split("\t");
                c++;
                if (c == 1) { continue; }
                try {
                    LinkedHashMap<String,Object> lhm = new LinkedHashMap<String, Object>();
                    int f = 0;
                    for (String field : rawFields) {
                        byte[] bytes = field.getBytes("UTF-8");
                        lhm.put(fieldNames[f], field);
                        f++;
                    }
                    list.add(lhm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch(Exception e) {
                System.out.println(e.toString());
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}


