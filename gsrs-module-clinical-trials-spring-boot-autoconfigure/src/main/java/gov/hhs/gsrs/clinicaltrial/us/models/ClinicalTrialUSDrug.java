package gov.hhs.gsrs.clinicaltrial.us.models;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.model.AbstractGsrsEntity;
import ix.core.SingleParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ix.core.models.ParentReference;
import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="ctrial_us_drug")
@SingleParent
public class ClinicalTrialUSDrug extends AbstractGsrsEntity implements ForceUpdateDirtyMakerMixin {

    @Value("${mygsrs.clinicaltrial.us.substance.linking.keyType.value}")
    static String substanceKeyTypeValue;

    @Id
    @SequenceGenerator(name="ctusdrugseq", sequenceName="ctrialusdrug_sq_id",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctusdrugseq")

    public Long id;

    @ParentReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore //ignore in json to avoid infinite recursion
    @JoinColumn(name = "trial_number", referencedColumnName = "trial_number")

    public ClinicalTrialUS owner;

    @Column(name="substance_key")
    public String substanceKey;
    public void setSubstanceKey(String substanceKey) {
        this.substanceKey = substanceKey;
    }

    @Column(name="substance_key_type")
    public String substanceKeyType = substanceKeyTypeValue;
    public void setSubstanceKeyType(String substanceKeyType) {
        this.substanceKeyType = substanceKeyType;
    }

    @Column(name="protected_match")
    public boolean protectedMatch;

    // @JsonIgnore
    // @ToString.Exclude
    @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<SubstanceRole> substanceRoles = new ArrayList<>();

    public void setSubstanceRoles (List<SubstanceRole>  substanceRoles) {
        this.substanceRoles = substanceRoles;
        if(substanceRoles != null) {
            for ( SubstanceRole sr : substanceRoles )
            {
                sr.setOwner(this);
            }
        }
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this);
        rtsb.setExcludeNullValues(true);
        return rtsb.toString();
    }

}
