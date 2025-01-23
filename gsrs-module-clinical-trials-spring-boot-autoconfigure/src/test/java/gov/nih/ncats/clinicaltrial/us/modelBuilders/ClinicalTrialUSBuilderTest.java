package gov.nih.ncats.clinicaltrial.us.modelBuilders;

import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import gov.hhs.gsrs.clinicaltrial.us.models.SubstanceRole;
import gov.hhs.gsrs.clinicaltrial.us.repositories.ClinicalTrialUSRepository;
import gsrs.services.PrincipalService;
import gsrs.springUtils.AutowireHelper;
import ix.core.models.Principal;
import oracle.ons.Cli;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import gov.hhs.gsrs.clinicaltrial.us.modelBuilders.ClinicalTrialUSBuilder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

// import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringRunner.class)
//@Import(AutowireHelper.class)
public class ClinicalTrialUSBuilderTest {

//    @MockBean
//    ClinicalTrialUSRepository clinicalTrialUSRepository;

//    @Before
//    public void setup(){
//        ClinicalTrialUS setupCtus = new ClinicalTrialUS();
//        setupCtus.trialNumber="NCT099";
//        setupCtus.title="setupCtus title";
//        Mockito.when(clinicalTrialUSRepository.save(setupCtus)).thenReturn(setupCtus);
//    }


    @Test
public void xyzTest(){
    Function<String, String> appendA = s -> s+"A";
    Function<String, String> appendB = s -> s+"B";
    Function<String, String> appendC = s -> s+"C";
    System.out.println(appendA.apply("boat"));
    System.out.println(appendA.andThen(appendB).andThen(appendC).apply("boat"));
    System.out.println(appendA.compose(appendB).compose(appendC).apply("boat"));
}


    @Test
    public void setTitleTest(){
        ClinicalTrialUS ctus = new ClinicalTrialUSBuilder()
        .setTitle("foo")
        .build();
        assertEquals("foo", ctus.getTitle());
    }

    @Test
    public void setTrialNumberTest(){
        ClinicalTrialUS ctus = new ClinicalTrialUSBuilder()
        .setTrialNumber("NCT001")
        .build();
        assertEquals("NCT001", ctus.getTrialNumber());
    }

    @Test
    public void builderTest(){
        ClinicalTrialUS ctus = new ClinicalTrialUS();
        ctus.setTrialNumber("NCT001");
        ctus.setTitle("Trial 1");
        ctus.setKind("US");
        ctus.setUrl("Url 1");

        ClinicalTrialUSDrug s = new ClinicalTrialUSDrug();
        s.setOwner(ctus);
        s.setId(0L);
        ctus.getClinicalTrialUSDrug().add(s);
        UUID u = UUID.randomUUID();
        s.setSubstanceKey(u.toString());
        s.setSubstanceKeyType("UUID");
        List<SubstanceRole> srl = new ArrayList<SubstanceRole>();
        SubstanceRole sr1 = new SubstanceRole();
        sr1.setOwner(s);
        sr1.setSubstanceRole("Role 1");
        srl.add(sr1);
        SubstanceRole sr2 = new SubstanceRole();
        sr2.setOwner(s);
        sr2.setSubstanceRole("Role 2");
        srl.add(sr2);



        ClinicalTrialUSBuilder testBuilder = new ClinicalTrialUSBuilder(ctus);




        ClinicalTrialUS newCtus = testBuilder.build();
        assertEquals(ctus.getTrialNumber(), newCtus.getTrialNumber());
        assertEquals(ctus.getTitle(), newCtus.getTitle());
        assertEquals(ctus.getKind(), newCtus.getKind());
        assertEquals(ctus.getUrl(), newCtus.getUrl());

    }







//
//        @org.junit.Test
//        public void ChemicalSubstanceFromJsonInputStream() throws Exception{
//            try(InputStream in = getClass().getResourceAsStream("/testJSON/pass/2moities.json")){
//                assertNotNull(in);
//                ChemicalSubstanceBuilder builder = SubstanceBuilder.from(in);
//
//                assert2MoietiesBuiltCorrectly(builder);
//            }
//        }
//
//        @org.junit.Test
//        public void ChemicalSubstanceFromJsonFile() throws Exception{
//            ChemicalSubstanceBuilder builder = SubstanceBuilder.from(new File(getClass().getResource("/testJSON/pass/2moities.json").getFile()));
//
//            assert2MoietiesBuiltCorrectly(builder);
//
//        }
//
//        private void assert2MoietiesBuiltCorrectly(ChemicalSubstanceBuilder builder) {
//            ChemicalSubstance substance = builder.build();
//
//            Assertions.assertEquals("1db30542-0cc4-4098-9d89-8340926026e9", substance.getUuid().toString());
//            Assertions.assertEquals(2, substance.moieties.size());
//        }
//
//        @org.junit.Test
//        public void modifyChemicalSubstanceFromJson() throws Exception{
//            ///home/katzelda/GIT/inxight3/modules/ginas/test/testJSON/pass/2moities.json
//            String path = "test/testJSON/pass/2moities.json";
//
//            try(InputStream in = getClass().getResourceAsStream("/testJSON/pass/2moities.json")){
//                assertNotNull(in);
//                ChemicalSubstanceBuilder builder = SubstanceBuilder.from(in);
//
//                UUID uuid = UUID.randomUUID();
//                builder.setUUID( uuid);
//                ChemicalSubstance substance = builder.build();
//
//                Assertions.assertEquals(uuid, substance.getUuid());
//                Assertions.assertEquals(2, substance.moieties.size());
//            }
//        }
//    }
//
//

}

