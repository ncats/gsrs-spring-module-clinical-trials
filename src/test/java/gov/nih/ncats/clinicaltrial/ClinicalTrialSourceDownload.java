package gov.nih.ncats.clinicaltrial;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gov.nih.ncats.clinicaltrial.us.services.ClinicalTrialEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.String;
import java.time.format.DateTimeFormatter;


@SpringBootTest
class ClinicalTrialSourceDownload {
    final String urlTemplate= "https://clinicaltrials.gov/ct2/results/download_fields?down_flds=all&down_count=%s&down_fmt=tsv&down_chunk=%s";
    final int downCount= 100;
    final int downChunk= 1;
    @Test
    void test() {
        List<LinkedHashMap<String, Object>> list = loadChunk();
        ClinicalTrialEntityService cts = new ClinicalTrialEntityService();
        String s = null;
        for (LinkedHashMap hm: list) {
            s = String.valueOf(hm.get("NCT Number"));
            System.out.println(s +" ... ");

            Optional<ClinicalTrial> t = cts.get(s);
            if(t.isPresent()) {
                System.out.println(s +" ... Present");
            } else {
                System.out.println(s + "... not present");
            }

//            for (String s: _fieldNames()) {
  //              System.out.print(String.valueOf(hm.get(s)));
    //        }
            System.out.println("");
        }
    }


    @Test
    public List<LinkedHashMap<String, Object>> loadChunk() {
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
        String urlString = String.format(urlTemplate,downCount,downChunk);
        DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        URL link = null;
        try {
            link = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(link.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine;
        int maxLen= 4000-2;
        String[] rawFields = null;
        String [] fieldNames = _fieldNames();
        String [] dateFields= _dateFields();

        System.out.println("fieldNames.length: " + fieldNames.length);
        int c=0;
        while (true) {
            try {
                if (!((inputLine = in.readLine()) != null)) break;
                inputLine = inputLine.replace("[\n\r]$", "");
                rawFields = inputLine.split("\t");
                // System.out.println("rawFields.length: " + rawFields.length);
                c++;
                // if (c==1) { System.out.println(String.join("\n", rawFields)); }
                if (c == 1) { continue; }
                try {
                    LinkedHashMap<String,Object> cleanedMap = new LinkedHashMap<String, Object>();
                    int f = 0;
                    for (String field : rawFields) {
                        byte[] bytes = field.getBytes("UTF-8");
                        if (bytes.length > maxLen) {
                            cleanedMap.put(fieldNames[f], new String(bytes, 0, maxLen, "UTF-8"));
                        } else {
                            cleanedMap.put(fieldNames[f], field);
                        }
                        f++;
                    }
                    list.add(cleanedMap);
                } catch (IOException e) {
                e.printStackTrace();
            }

//                System.out.println(cleanedMap.toString());
//                String test = String.valueOf(cleanedMap.get("Last Update Posted"));
//                LocalDate date = LocalDate.parse(test, dateFormatter1);
//                System.out.println(date.getDayOfMonth());
            } catch(Exception e) {
                System.out.println(e.toString());
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        LocalDate date = LocalDate.parse("2018-05-05");
//        int dom = date.getDayOfMonth();
//        System.out.println("dom: "+dom);
            return list;
    }

    public static String[] _dateFields() {
        return new String[]{
                "Start Date",
//         "Primary Completion Date",
//         "Completion Date",
//         "First Posted"
                // ,
//         "Results First Posted",
//                "Last Update Posted"
        };
    }



    public static String[] _fieldNames() {
        return new String[]{
                "Rank",
                "NCT Number",
                "Title",
                "Acronym",
                "Status",
                "Study Results",
                "Conditions",
                "Interventions",
                "Outcome Measures",
                "Sponsor/Collaborators",
                "Gender",
                "Age",
                "Phases",
                "Enrollment",
                "Funded Bys",
                "Study Type",
                "Study Designs",
                "Other IDs",
                "Start Date",
                "Primary Completion Date",
                "Completion Date",
                "First Posted",
                "Results First Posted",
                "Last Update Posted",
                "Locations",
                "Study Documents",
                "URL"
        };
    }



    /*
    if exists
        checkup update date
            if different update
    else
        create
     */
}