package gov.nih.ncats.clinicaltrial.europe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.model.AbstractGsrsEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;


@Data
@EqualsAndHashCode(exclude="clinicalTrialDrug")
@Entity
@Builder
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
    public ClinicalTrialEurope clinicalTrialEuropeForDrug;

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
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<ClinicalTrialEuropeDrug> clinicalTrialEuropeDrugList = new ArrayList<>();

    public ClinicalTrialEuropeProduct () {}

}