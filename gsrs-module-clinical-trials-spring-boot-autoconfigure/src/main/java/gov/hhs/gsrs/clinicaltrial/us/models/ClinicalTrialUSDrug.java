package gov.hhs.gsrs.clinicaltrial.us.models;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.model.AbstractGsrsEntity;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import ix.core.SingleParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ix.core.models.ParentReference;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Data
@Entity
@Table(name="ctrial_us_drug")
@SingleParent
public class ClinicalTrialUSDrug extends AbstractGsrsEntity implements ForceUpdateDirtyMakerMixin {

    @Value("${mygsrs.clinicaltrial.us.substance.linking.keyType.value}")
    static String substanceKeyTypeValue;

    @Id
    @SequenceGenerator(name="ctusdrugSeq", sequenceName="CTRIALUSDRUG_SQ_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctusdrugSeq")

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

}
