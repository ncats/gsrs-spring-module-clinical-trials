package gov.nih.ncats.clinicaltrial.us.models;
import gov.nih.ncats.clinicaltrial.base.models.AbstractGsrsEntityAlt;
import ix.core.SingleParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.model.AbstractGsrsEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Data
@Entity
@Table(name="ct_clinical_trial_drug")
@SingleParent
@Getter
@Setter
public class ClinicalTrialUSDrug extends AbstractGsrsEntityAlt {

    @Value("${mygsrs.clinicaltrial.us.substance.linking.keyType.value}")
    static String substanceKeyTypeValue;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore //ignore in json to avoid infinite recursion
    @JoinColumn(name = "TRIAL_NUMBER")
    public ClinicalTrialUS owner;

    @Column(name="SUBSTANCE_KEY")
    public String substanceKey;
    public void setSubstanceKey(String substanceKey) {
        this.substanceKey = substanceKey;
    }

    @Column(name="SUBSTANCE_KEY_TYPE")
    public String substanceKeyType = substanceKeyTypeValue;
    public void setSubstanceKeyType(String substanceKeyType) {
        this.substanceKeyType = substanceKeyType;
    }

    @Column(name="protected_match")
    public boolean protectedMatch;

    public ClinicalTrialUSDrug() {}

    public Long getId() {
        return this.id;
    }
}
