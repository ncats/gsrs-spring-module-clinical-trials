package gov.nih.ncats.clinicaltrial.eu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.nih.ncats.clinicaltrial.base.models.ClinicalTrialBase;
import gov.nih.ncats.clinicaltrial.us.models.ClinicalTrial;
import gsrs.model.AbstractGsrsEntity;
import ix.core.SingleParent;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;


@Data
@EqualsAndHashCode(exclude="clinicalTrialDrug")
@Entity
@Builder
@SingleParent
@AllArgsConstructor
// @NoArgsConstructor
@Table(name="clinical_trial_eu_prod")
// @ToString
public class ClinicalTrialEuropeProduct extends AbstractGsrsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ID")
    public int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="TRIAL_NUMBER")
    public ClinicalTrialEurope owner;

    @Column(name="IMP_SECTION")
    public String impSection;

    @Column(name="PRODUCT_NAME")
    public String productName;

    @Column(name="TRADE_NAME")
    public String tradeName;

    @Column(name="IMP_ROLE")
    public String impRole;

    @Column(name="IMP_ROUTES_ADMIN")
    public String impRoutesAdmin;

    @Column(name="PHARMACEUTICAL_FORM")
    public String pharmaceuticalForm;

    @Transient
    public String prodIngredName;

    // @JsonIgnore
    @ToString.Exclude
    // @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    // this owner in child class
    @OneToMany(mappedBy = "owner1", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<ClinicalTrialEuropeDrug> clinicalTrialEuropeDrugList = new ArrayList<>();

    public void setClinicalTrialEuropeDrugList(List<ClinicalTrialEuropeDrug>  clinicalTrialEuropeDrugList) {
        // System.out.println("HERE0");
        // System.out.println("HERE1");
        this.clinicalTrialEuropeDrugList = clinicalTrialEuropeDrugList;
        // System.out.println("HERE2");
        if(clinicalTrialEuropeDrugList != null) {
            // System.out.println("HERE3");
            for ( ClinicalTrialEuropeDrug ctd : clinicalTrialEuropeDrugList )
            {
                // System.out.println("HERE4" + ctd.getSubstanceKeyType());
                ctd.setOwner1(this);
                // System.out.println("HERE5");
            }
        }
        // setIsDirty("clinicalTrialDrug");
    }

    public ClinicalTrialEuropeProduct () {}

}