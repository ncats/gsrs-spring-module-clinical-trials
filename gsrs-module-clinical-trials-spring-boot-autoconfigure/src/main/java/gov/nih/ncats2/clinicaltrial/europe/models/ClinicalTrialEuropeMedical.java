package gov.nih.ncats2.clinicaltrial.europe.models;

import gov.nih.ncats2.clinicaltrial.base.models.AbstractGsrsEntityAlt;
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
public class ClinicalTrialEuropeMedical extends AbstractGsrsEntityAlt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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