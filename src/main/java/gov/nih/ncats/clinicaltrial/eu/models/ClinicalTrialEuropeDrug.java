package gov.nih.ncats.clinicaltrial.eu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.model.AbstractGsrsEntity;
import ix.core.SingleParent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="CLINICAL_TRIAL_EU_DRUG")
// @SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeDrug extends AbstractGsrsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    // commented out in 2.0
    //@ManyToOne
    //@JoinColumn(name="EUDRACT_NUMBER")
    //public ClinicalTrialEurope clinicalTrialEuropeForDrug;

    // skipping this because I will have substance_uuid
    // @ManyToOne
    // @JoinColumn(name="BDNUM")
    // public BdnumName bdnumName;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="PRODUCT_ID")
    public ClinicalTrialEuropeProduct owner;

    public String substanceUuid;

    public ClinicalTrialEuropeDrug () {}

}