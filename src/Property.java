import java.io.Serializable;
import java.time.*;
public class Property implements Serializable {
    private String propertyID;
    private String propertyDescription;
    private LocalDate registrationDate;
    private String propertyAddress;
    private double area;
    private float monthlyRentalPrice;

    public Property(String propertyID, String propertyDescription, LocalDate registrationDate, String propertyAddress){
        this.propertyID = propertyID;
        this.propertyDescription = propertyDescription;
        this.registrationDate = registrationDate;
        this.propertyAddress = propertyAddress;
    }

    /*
    Getters and setters
     */
    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        if(propertyID.length() < 10){
            throw new IllegalArgumentException("Property ID must have at least 10 characters");
        }
        else {
            this.propertyID = propertyID;
        }
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        if(registrationDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Registration date cannot be in the future");
        } else {
            this.registrationDate = registrationDate;
        }
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        if(area < 0){
            System.out.println("Area of property cannot be a negative number. Defaulting area to 0.");
            this.area = 0 ;
        } else {
            this.area = area;
        }
    }

    public float getMonthlyRentalPrice() {
        return monthlyRentalPrice;
    }

    public void setMonthlyRentalPrice(float monthlyRentalPrice) {
        if(monthlyRentalPrice < 0.0f){
            System.out.println("Rental Price of property cannot be a less than 0. Defaulting price to 0.");
            this.monthlyRentalPrice = 0 ;
        } else {
            this.monthlyRentalPrice = monthlyRentalPrice;
        }
    }

    /*
    Method for users to view property details
     */
    public void printDetailedInfo(){
        System.out.println("Property ID: " + getPropertyID());
        System.out.println("Description: " + getPropertyDescription());
        System.out.println("Registration Date: " + getRegistrationDate());
        System.out.println("Address: " + getPropertyAddress());
        System.out.println("Area: " + getArea());
        System.out.println("Monthly Rental Price: €" + getMonthlyRentalPrice());
    }
}
