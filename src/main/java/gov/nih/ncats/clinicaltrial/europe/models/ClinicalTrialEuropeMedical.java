package gov.nih.ncats.clinicaltrial.europe.models;

import ix.core.SingleParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.model.AbstractGsrsEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="SRSCID_CLINICAL_TRIAL_EU_MC")
@SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeMedical extends AbstractGsrsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    @Column(name="MEDICAL_COND_INVSTGED")
    public String medicalCondInvesigated;

    @Column(name="MEDICAL_COND_INVSTGED_EZ")
    public String medicalCondInvesigatedEz;

    @Column(name="MEDICAL_COND_THERAP_AREA")
    public String medicalCondTherapyArea;

    // public ClinicalTrialEuropeMedical () {}

}