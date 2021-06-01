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
@SingleParent
@Table(name="CLINICAL_TRIAL_EU_DRUG")
@Getter
@Setter
public class ClinicalTrialEuropeDrug extends AbstractGsrsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="PRODUCT_ID")
    public ClinicalTrialEuropeProduct owner1;

    public String substanceKey;

    public String substanceKeyType;

    public ClinicalTrialEuropeDrug () {}

}