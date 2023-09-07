package gov.hhs.gsrs.clinicaltrial.us.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsrs.ForceUpdateDirtyMakerMixin;
import gsrs.model.AbstractGsrsEntity;
import ix.core.SingleParent;
import ix.core.models.ParentReference;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

@Data
@Entity
@Table(name="ctrial_us_outcome_result")
@SingleParent
public class OutcomeResultNote extends AbstractGsrsEntity implements ForceUpdateDirtyMakerMixin {

    @Id
    @SequenceGenerator(name = "ctusornotesseq", sequenceName = "ctrialus_orn_sq_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ctusornotesseq")

    public Long id;

    @ParentReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore //ignore in json to avoid infinite recursion
    @JoinColumn(name = "trial_number", referencedColumnName = "trial_number")

    public ClinicalTrialUS owner;

    @Lob
    public String outcome;

    @Lob
    public String result;

    @Lob
    public String narrative;

    @Override
    public String toString() {
        ReflectionToStringBuilder rtsb = new ReflectionToStringBuilder(this);
        rtsb.setExcludeNullValues(true);
        return rtsb.toString();
    }

}
