package gov.hhs.gsrs.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.SingleParent;
import ix.core.models.ParentReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@Entity
@SingleParent
@Table(name="ctrial_eu_drug")
public class ClinicalTrialEuropeDrug extends AbstractGsrsEntity implements ForceUpdateDirtyMakerMixin {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="cteudrugseq", sequenceName="ctrialeudrug_sq_id",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cteudrugseq")

    @Column(name="id")
    public int id;

    @ParentReference
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name="product_id", referencedColumnName="id", nullable=false)
    public ClinicalTrialEuropeProduct owner;

    @Column(name="substance_key", length=255, nullable=false)
    public String substanceKey;

    @Column(name="substance_key_type", length=50, nullable=false)
    public String substanceKeyType;

}