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

@Table(name="shape_circle")
public class Circle extends Shape {
    @Id
    @Column(name="trialNumber")
    public String trialNumber;

    @Column(name="name")
    public String name;

    public Circle() {
    }

    public Circle(String trialNumber, String name) {
        this.trialNumber = trialNumber;
        this.name = name;
    }

    public String getName()  {
        return this.name;
    }
    public String getTrialNumber()  {
        return this.trialNumber;
    }


}