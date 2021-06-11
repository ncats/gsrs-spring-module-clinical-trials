package gov.nih.ncats.clinicaltrial.europe.models;

import gov.nih.ncats.clinicaltrial.base.models.AbstractGsrsEntityAlt;
import gsrs.model.AbstractGsrsEntity;
import ix.core.SingleParent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="SRSCID_CLINICAL_TRIAL_EU_MEDD")
@SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeMeddra extends AbstractGsrsEntityAlt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    @Column(name="MEDDRA_VERSION")
    public String meddraVersion;

    @Column(name="MEDDRA_CLASS_CODE")
    public String meddraClassCode;

    @Column(name="MEDDRA_TERM")
    public String meddraTerm;

    @Column(name="MEDDRA_SYSTEM_ORGAN_CLASS")
    public String meddraSystemOrganClass;

    public ClinicalTrialEuropeMeddra () {}

}