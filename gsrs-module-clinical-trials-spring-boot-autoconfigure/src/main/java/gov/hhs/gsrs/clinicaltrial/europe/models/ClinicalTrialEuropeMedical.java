package gov.hhs.gsrs.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.SingleParent;
import ix.core.models.ParentReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="ctrial_eu_mc")
@SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeMedical extends AbstractGsrsEntity implements ForceUpdateDirtyMakerMixin {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="cteumcSeq", sequenceName="CTRIALEUMC_SQ_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cteudmcSeq")

    @Column(name="id")
    public int id;

    @ParentReference
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name="trial_number", nullable=false)
    public ClinicalTrialEurope owner;

    @Column(name="medical_cond_invstged", length=2000)
    public String medicalCondInvesigated;

    @Column(name="medical_cond_invstged_ez", length=2000)
    public String medicalCondInvesigatedEz;

    @Column(name="medical_cond_therap_area", length=2000)
    public String medicalCondTherapyArea;

    public ClinicalTrialEuropeMedical () {}

}