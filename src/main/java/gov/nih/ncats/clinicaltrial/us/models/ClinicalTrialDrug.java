package gov.nih.ncats.clinicaltrial.us.models;
import ix.core.SingleParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.model.AbstractGsrsEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="ct_clinical_trial_drug")
@SingleParent
@Getter
@Setter
public class ClinicalTrialDrug extends AbstractGsrsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;

    // @ManyToOne(cascade = CascadeType.PERSIST)
    // @JoinColumn(name = "trialNumber")
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "trial_number")
    // @Basic(fetch= FetchType.EAGER)

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore //ignore in json to avoid infinite recursion
    @JoinColumn(name = "trial_number")
    public ClinicalTrial owner;


    @Column(name="SUBSTANCE_UUID")
    public String substanceUuid;

    public void setSubstanceUuid(String substanceUuid) {
        this.substanceUuid = substanceUuid;
    }


    /*
    @JsonIgnore
    @Transient
    private CachedSupplier<String> substanceDisplayName = CachedSupplier.of(new Supplier<String>(){
        public String get(){
            Substance s = null;
            try {
                s = EntityUtils
                        .getEntityInfoFor(Substance.class)
                        .findById(substanceUuid);
            } catch (java.lang.IllegalArgumentException e){
                System.out.println("Bad substanceUuid passed to finder.");
            }
            if (s == null) {
                return null;
            }
            Optional<Name> aName = s.getDisplayName();
            if(aName.isPresent()){
                return aName.get().name;
            }
            return null;
        }
    });

    @JsonProperty("substanceDisplayName")
    public String getSubstanceDisplayName(){
        return substanceDisplayName.get();
    }
    */

    @Column(name="protected_match")
    public boolean protectedMatch;

    //Added on Oct 10, AN
    // @JsonIgnore
    // @Transient
    // public BdnumName clinicalTrialIngredientDetail = null;

    public ClinicalTrialDrug () {}

    public Long getId() {
        return this.id;
    }
}
