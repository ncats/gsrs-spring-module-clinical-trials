package gov.hhs.gsrs.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.SingleParent;
import ix.core.models.Indexable;
import ix.core.models.ParentReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="ctrial_eu_medd")
@SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeMeddra extends AbstractGsrsEntity implements ForceUpdateDirtyMakerMixin {
    @Id

    @SequenceGenerator(name="cteumeddseq", sequenceName="ctrialeumedd_sq_id",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cteudmeddseq")
    @Column(name="id")
    public int id;

    @ParentReference
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name="trial_number", nullable=false)
    public ClinicalTrialEurope owner;

    @Column(name="meddra_version", length=2000)
    public String meddraVersion;

    @Column(name="meddra_class_code", length=2000)
    public String meddraClassCode;

    @Indexable(sortable = true)
    @Column(name="meddra_term", length=2000)
    public String meddraTerm;

    @Column(name="meddra_system_organ_class", length=2000)
    public String meddraSystemOrganClass;

    public ClinicalTrialEuropeMeddra () {}

}