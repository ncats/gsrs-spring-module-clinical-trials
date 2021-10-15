package gov.hhs.gsrs.clinicaltrial.europe.models;

import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.SingleParent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="CTRIAL_EU_MEDD")
@SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeMeddra extends AbstractGsrsManualDirtyEntity {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="cteumeddSeq", sequenceName="CTRIALEUMEDD_SQ_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cteudmeddSeq")
    @Column(name="ID")
    public int id;

    @Column(name="MEDDRA_VERSION", length=2000)
    public String meddraVersion;

    @Column(name="MEDDRA_CLASS_CODE", length=2000)
    public String meddraClassCode;

    @Column(name="MEDDRA_TERM", length=2000)
    public String meddraTerm;

    @Column(name="MEDDRA_SYSTEM_ORGAN_CLASS", length=2000)
    public String meddraSystemOrganClass;

    public ClinicalTrialEuropeMeddra () {}

}