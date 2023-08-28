package gov.hhs.gsrs.clinicaltrial.us.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.model.AbstractGsrsEntity;
import ix.core.SingleParent;
import ix.core.models.ParentReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import javax.persistence.*;


@Data
@Entity
@Table(name="ctrial_us_substance_role")
@SingleParent
public class SubstanceRole extends AbstractGsrsEntity implements ForceUpdateDirtyMakerMixin {

    @Id
    @SequenceGenerator(name="ctussrseq", sequenceName="ctrialus_sr_sq_id",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctussrseq")
    public Long id;

    @ParentReference
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name="substance_id", referencedColumnName="id", nullable=false)
    public ClinicalTrialUSDrug owner;


    @Column(length=500)
    public String substanceRole;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this);
        rtsb.setExcludeNullValues(true);
        return rtsb.toString();
    }

}
