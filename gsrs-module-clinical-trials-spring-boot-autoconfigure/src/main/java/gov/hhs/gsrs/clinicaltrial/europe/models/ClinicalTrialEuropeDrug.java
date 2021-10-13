package gov.hhs.gsrs.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.hhs.gsrs.clinicaltrial.base.models.AbstractGsrsEntityAlt;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import gsrs.model.AbstractGsrsTablePerClassEntity;
import ix.core.SingleParent;
import ix.core.models.ParentReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@SingleParent
@Table(name="CTRIAL_EU_DRUG")
public class ClinicalTrialEuropeDrug extends AbstractGsrsManualDirtyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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