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
@Table(name="CLINICAL_TRIAL_EU_TERM")
@SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeTerm extends AbstractGsrsEntityAlt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    @ManyToOne
    @JoinColumn(name="TRIAL_NUMBER")
    public ClinicalTrialEurope clinicalTrialEuropeForDrug;

    @Column(name="PRODUCT_ID")
    public int productId;

    @Column(name="KEY")
    public String key;

    @Column(name="VALUE")
    public String value;

    public ClinicalTrialEuropeTerm () {}

}