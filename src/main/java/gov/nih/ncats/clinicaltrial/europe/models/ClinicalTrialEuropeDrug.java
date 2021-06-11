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
@SingleParent
@Table(name="CTRIAL_EU_DRUG")

@Getter
@Setter
public class ClinicalTrialEuropeDrug extends AbstractGsrsEntityAlt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="PRODUCT_ID", referencedColumnName="ID", nullable=false)
    public ClinicalTrialEuropeProduct owner;

    @Column(name="SUBSTANCE_KEY", length=255, nullable=false)
    public String substanceKey;

    @Column(name="SUBSTANCE_KEY_TYPE", length=50, nullable=false)
    public String substanceKeyType;

    public ClinicalTrialEuropeDrug () {}

}