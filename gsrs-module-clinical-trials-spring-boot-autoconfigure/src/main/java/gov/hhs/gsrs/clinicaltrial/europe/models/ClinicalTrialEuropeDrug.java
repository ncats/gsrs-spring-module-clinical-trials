package gov.hhs.gsrs.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="CTRIAL_EU_DRUG")
public class ClinicalTrialEuropeDrug extends AbstractGsrsManualDirtyEntity {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="cteudrugSeq", sequenceName="CTRIALEUDRUG_SQ_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cteudrugSeq")

    @Column(name="ID")
    public int id;

    @ParentReference
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="PRODUCT_ID", referencedColumnName="ID", nullable=false)
    public ClinicalTrialEuropeProduct owner;

    @Column(name="SUBSTANCE_KEY", length=255, nullable=false)
    public String substanceKey;

    @Column(name="SUBSTANCE_KEY_TYPE", length=50, nullable=false)
    public String substanceKeyType;

}