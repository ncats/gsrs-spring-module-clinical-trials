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
/*
    @Transient
    private transient boolean isAllDirty = false;

    @Override
    @JsonIgnore
    public  boolean isAllDirty() {
        return true;
    }
*/

    @Transient
    private transient boolean isAllDirty = false;

    @Override
    @JsonIgnore
    public  boolean isAllDirty() {
        return isAllDirty;
    }

    @Override
    public void setIsAllDirty() {
        isAllDirty=true;
    }

    @Value("${mygsrs.clinicaltrial.us.substance.linking.keyType.value}")
    static String substanceKeyTypeValue;

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)

    @SequenceGenerator(name="ctusdrugSeq", sequenceName="CTRIALUSDRUG_SQ_ID",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctusdrugSeq")

    public Long id;

    @ParentReference
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

}
