package gov.nih.ncats.clinicaltrial.inheritance;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Column;

import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="shape_square")
public class Square extends Shape {
    @Id
    @Column(name="trial_number")
    public String trialNumber;

    @Column(name="name")
    public String name;

    public Square() {
    }

    public Square(String trialNumber, String name) {
        this.trialNumber = trialNumber;
        this.name = trialNumber;
    }
    public String getName()  {
        return this.name;
    }
    public String getTrialNumber()  {
        return this.trialNumber;
    }

}