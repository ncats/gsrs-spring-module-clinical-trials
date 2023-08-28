package gov.nih.ncats.clinicaltrial.us.model;

import com.hp.hpl.jena.shared.uuid.UUID_V1_Gen;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUS;
import gov.hhs.gsrs.clinicaltrial.us.models.ClinicalTrialUSDrug;
import gov.hhs.gsrs.clinicaltrial.us.models.OutcomeResultNote;
import gov.hhs.gsrs.clinicaltrial.us.models.SubstanceRole;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClinicalTrialUSTest {

    @Test
    public void substanceRolesTest(){
        ClinicalTrialUS ctus = new ClinicalTrialUS();
        ctus.setTrialNumber("NCT001");
        ctus.setTitle("Trial 1");
        ClinicalTrialUSDrug s = new ClinicalTrialUSDrug();
        s.setOwner(ctus);
        s.setId(0L);
        ctus.getClinicalTrialUSDrug().add(s);
        UUID_V1_Gen u = new UUID_V1_Gen();
        s.setSubstanceKey(u.generate().toString());
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
        ctus.getClinicalTrialUSDrug().get(0).setSubstanceRoles(srl);
        assertEquals(2,ctus.getClinicalTrialUSDrug().get(0).getSubstanceRoles().size());
    }

    @Test
    public void OutcomeResultNotesTest(){
        ClinicalTrialUS ctus = new ClinicalTrialUS();
        ctus.setTrialNumber("NCT001");
        ctus.setTitle("Trial 1");
        List<OutcomeResultNote> notes = new ArrayList<OutcomeResultNote>();
        ctus.setOutcomeResultNotes(notes);

        notes.add(new OutcomeResultNote());
        notes.get(0).setResult("Result 1");
        notes.get(0).setNarrative("Narrative 1");
        notes.get(0).setOwner(ctus);

        notes.add(new OutcomeResultNote());
        notes.get(1).setResult("Result 2");
        notes.get(1).setNarrative("Narrative 2");
        notes.get(1).setOwner(ctus);

        assertEquals(2,ctus.getOutcomeResultNotes().size());
    }

}
