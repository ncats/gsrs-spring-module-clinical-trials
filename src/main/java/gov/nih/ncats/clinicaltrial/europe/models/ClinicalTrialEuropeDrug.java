package gov.nih.ncats.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.nih.ncats.clinicaltrial.base.models.AbstractGsrsEntityAlt;
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
public class ClinicalTrialEuropeDrug extends AbstractGsrsEntityAlt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="PRODUCT_ID")
    public ClinicalTrialEuropeProduct owner;

    public String substanceKey;

    public String substanceKeyType;

    public ClinicalTrialEuropeDrug () {}

}