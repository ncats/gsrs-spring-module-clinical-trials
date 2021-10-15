package gov.hhs.gsrs.clinicaltrial.europe.models;

import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.SingleParent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="CTRIAL_EU_MC")
@SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeMedical extends AbstractGsrsManualDirtyEntity {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="cteumcSeq", sequenceName="CTRIALEUMC_SQ_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cteudmcSeq")

    @Column(name="ID")
    public int id;

    @Column(name="MEDICAL_COND_INVSTGED", length=2000)
    public String medicalCondInvesigated;

    @Column(name="MEDICAL_COND_INVSTGED_EZ", length=2000)
    public String medicalCondInvesigatedEz;

    @Column(name="MEDICAL_COND_THERAP_AREA", length=2000)
    public String medicalCondTherapyArea;

    public ClinicalTrialEuropeMedical () {}

}