import java.io.Serializable;
import java.time.LocalDate;

public class Rental implements Serializable {
    private Property property;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    public Rental(Property property, LocalDate rentalStartDate, LocalDate rentalEndDate){
        this.property = property;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }

    /*
    Getters and Setters
     */

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        if(rentalStartDate.isAfter(this.rentalEndDate) || rentalStartDate.equals(this.rentalEndDate)){
            System.out.println("Date of rental start must be before the rental end date");
        } else {
            this.rentalStartDate = rentalStartDate;
        }
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDate rentalEndDate) {
        if(rentalEndDate.isBefore(this.rentalStartDate) || rentalEndDate.equals(this.rentalStartDate)){
            System.out.println("Date of rental end must be after the rental start date");
        } else {
            this.rentalEndDate = rentalEndDate;
        }
    }
}
