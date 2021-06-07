package gov.nih.ncats.clinicaltrial.inheritance;


import gsrs.model.AbstractGsrsEntity;
import javax.persistence.InheritanceType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Shape extends AbstractGsrsEntityAlt {
    @Id
    public String trialNumber;

    public String kind;

    public String name;

    public String title;

    public String getName()  {
        return this.name;
    }
    public String getTrialNumber()  {
        return this.trialNumber;
    }

}