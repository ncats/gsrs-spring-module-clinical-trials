package gov.hhs.gsrs.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import gsrs.model.AbstractGsrsTablePerClassEntity;
import ix.core.SingleParent;
import ix.core.models.ParentReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;


@Data
// @EqualsAndHashCode(exclude="clinicalTrialEuropeDrug")
@Entity
// @SuperBuilder
@SingleParent
@AllArgsConstructor
// @NoArgsConstructor
@Table(name="CTRIAL_EU_PROD")
// @ToString
public class ClinicalTrialEuropeProduct extends AbstractGsrsManualDirtyEntity {
    public ClinicalTrialEuropeProduct () {}

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="cteuprodSeq", sequenceName="CTRIALEUPROD_SQ_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cteudprodSeq")

    @Column(name="ID")
    public int id;

    @ParentReference
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="TRIAL_NUMBER", nullable=false)
    public ClinicalTrialEurope owner;

    @Column(name="IMP_SECTION", length=255)
    public String impSection;

    @Column(name="PRODUCT_NAME", length=2000)
    public String productName;

    @Column(name="TRADE_NAME", length=2000)
    public String tradeName;

    @Column(name="IMP_ROLE", length=255)
    public String impRole;

    @Column(name="IMP_ROUTES_ADMIN", length=2000)
    public String impRoutesAdmin;

    @Column(name="PHARMACEUTICAL_FORM", length=2000)
    public String pharmaceuticalForm;

    @Transient
    public String prodIngredName;

    // @JsonIgnore
    @ToString.Exclude
    // had to add orphan removal or would not delete.
    // long term should find a better solution.
    @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
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
                ctd.setOwner(this);
                System.out.println("HERE5 XX");
            }
        }
        // setIsDirty("clinicalTrialDrug");
    }


}