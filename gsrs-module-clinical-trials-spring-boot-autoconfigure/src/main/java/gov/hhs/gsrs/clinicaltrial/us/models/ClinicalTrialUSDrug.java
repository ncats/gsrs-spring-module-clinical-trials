package gov.hhs.gsrs.clinicaltrial.us.models;
import gov.hhs.gsrs.clinicaltrial.base.models.AbstractGsrsEntityAlt;
import gsrs.model.AbstractGsrsManualDirtyEntity;
import gsrs.model.AbstractGsrsTablePerClassEntity;
import ix.core.SingleParent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ix.core.models.Backup;
import ix.core.models.IndexableRoot;
import ix.core.models.ParentReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Data
@Entity
@Table(name="ctrial_us_drug")
@SingleParent

public class ClinicalTrialUSDrug extends AbstractGsrsManualDirtyEntity {

    @Value("${mygsrs.clinicaltrial.us.substance.linking.keyType.value}")
    static String substanceKeyTypeValue;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
