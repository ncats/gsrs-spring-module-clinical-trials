package gov.hhs.gsrs.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.model.AbstractGsrsEntity;
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
public class ClinicalTrialEuropeProduct extends AbstractGsrsEntity implements ForceUpdateDirtyMakerMixin {
    public ClinicalTrialEuropeProduct () {}

    @Id
    @SequenceGenerator(name="cteuprodSeq", sequenceName="CTRIALEUPROD_SQ_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cteudprodSeq")

    @Column(name="ID")
    public int id;

    @ParentReference
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
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
    @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<ClinicalTrialEuropeDrug> clinicalTrialEuropeDrugList = new ArrayList<>();

    public void setClinicalTrialEuropeDrugList(List<ClinicalTrialEuropeDrug>  clinicalTrialEuropeDrugList) {
        this.clinicalTrialEuropeDrugList = clinicalTrialEuropeDrugList;
        if(clinicalTrialEuropeDrugList != null) {
            for ( ClinicalTrialEuropeDrug ctd : clinicalTrialEuropeDrugList )
            {
                ctd.setOwner(this);
            }
        }
    }


}