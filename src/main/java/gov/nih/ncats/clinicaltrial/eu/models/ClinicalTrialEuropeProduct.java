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
    // this owner1 is in child class
    @OneToMany(mappedBy = "owner1", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<ClinicalTrialEuropeDrug> clinicalTrialEuropeDrugList = new ArrayList<>();

    // this setter "runs" but but even so the product_id in the datatable
    // is null
    // so I put the so I "added logic" to ClinicalTrialEuropeProduct
    public void setClinicalTrialEuropeDrugList(List<ClinicalTrialEuropeDrug>  clinicalTrialEuropeDrugList) {
        System.out.println("HERE0 XX setClinicalTrialEuropeDrugList ");
        System.out.println("HERE1 XX");
        this.clinicalTrialEuropeDrugList = clinicalTrialEuropeDrugList;
        System.out.println("HERE2 XX");
        if(clinicalTrialEuropeDrugList != null) {
            System.out.println("HERE3 XX");
            for ( ClinicalTrialEuropeDrug ctd : clinicalTrialEuropeDrugList )
            {
                System.out.println("HERE4 XX" + ctd.getSubstanceKeyType());
                System.out.println("HERE4 class" + this.getClass());

                // produt_id does not get set correctly
                // something is not working as epected.
                // so modified parent class setClinicalTrialEuropeProductList
                // setter.
                ctd.setOwner1(this);
                System.out.println("HERE5 XX");
            }
        }
        // setIsDirty("clinicalTrialDrug");
    }

    public ClinicalTrialEuropeProduct () {}

}