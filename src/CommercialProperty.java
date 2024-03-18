import java.io.Serializable;
import java.time.LocalDate;

public class CommercialProperty extends Property implements Serializable {
    private int propertyLicenseClass;
    private boolean isAccessible;

    public CommercialProperty(String propertyID, String propertyDescription, LocalDate registrationDate, String propertyAddress, int propertyLicenseClass, boolean isAccessible){
        super(propertyID, propertyDescription, registrationDate, propertyAddress);
        this.propertyLicenseClass = propertyLicenseClass;
        this.isAccessible = isAccessible;
    }

    public int getPropertyLicenseClass() {
        return propertyLicenseClass;
    }

    public void setPropertyLicenseClass(int propertyLicenseClass) {
        if(propertyLicenseClass < 1 || propertyLicenseClass > 8){
            System.out.println("Property License must be between 1 and 8");
        } else {
            this.propertyLicenseClass = propertyLicenseClass;
        }
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    @Override
    public void printDetailedInfo() {
        super.printDetailedInfo(); // Call the parent method to print common details
        System.out.println("Property License Class: " + getPropertyLicenseClass());
        System.out.println("Is Accessible: " + isAccessible());
        System.out.println(); // Blank line for separation
    }
}
