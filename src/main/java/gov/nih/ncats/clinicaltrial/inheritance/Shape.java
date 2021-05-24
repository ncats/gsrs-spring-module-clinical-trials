package gov.nih.ncats.clinicaltrial.inheritance;

import gsrs.model.AbstractGsrsEntity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Inheritance
public abstract class Shape extends AbstractGsrsEntity {
    @Id
    public String trialNumber;
    // @NotNull
    public String name;

    public String title;

    public String getName()  {
        return this.name;
    }
    public String getTrialNumber()  {
        return this.trialNumber;
    }

}