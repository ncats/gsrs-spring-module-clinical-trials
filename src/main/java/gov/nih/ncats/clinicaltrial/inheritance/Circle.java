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
@Table(name="shape_circle")
public class Circle extends Shape {
    @Id
    @Column(name="trialNumber")
    public String trialNumber;

    @Column(name="kind")
    public String kind = "circle";

    @Column(name="name")
    public String name;

    @Column(name="circumference")
    public String circumference;


    public Circle() {
    }

    public Circle(String trialNumber, String name) {
        this.trialNumber = trialNumber;
        this.name = name;
    }
   public void setName(String name) {
        this.name = name;
   }
   public void setTrialNumber(String trialNumber) {
        this.trialNumber = trialNumber;
   }
    public String getName()  {
        return this.name;
    }
    public String getTrialNumber()  {
        return this.trialNumber;
    }
    public String getCircumference()  {
        return this.circumference;
    }


}