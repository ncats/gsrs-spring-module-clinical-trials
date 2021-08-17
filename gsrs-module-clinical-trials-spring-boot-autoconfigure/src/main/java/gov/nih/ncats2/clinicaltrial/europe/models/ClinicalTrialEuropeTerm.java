package gov.nih.ncats2.clinicaltrial.europe.models;

import gov.nih.ncats2.clinicaltrial.base.models.AbstractGsrsEntityAlt;
import ix.core.SingleParent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Data
@Entity
@Table(name="CTRIAL_EU_TERM")
@SingleParent
@Getter
@Setter
public class ClinicalTrialEuropeTerm extends AbstractGsrsEntityAlt {
    // This class is not used other than for storing data
    // generated for matching. Should probably eliminate
    // to avoid confusion.

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    @Column(name="TRIAL_NUMBER", nullable=false)
    // public ClinicalTrialEurope clinicalTrialEuropeForDrug;
    public String trialNumber;

    @Column(name="PRODUCT_ID")
    public int productId;

    @Column(name="KEY", length=2000)
    public String key;

    @Column(name="VALUE", length=4000)
    public String value;

    public ClinicalTrialEuropeTerm () {}

}