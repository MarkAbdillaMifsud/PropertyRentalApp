import java.time.*;

public class ResidentialProperty extends Property {
    private int numOfBedrooms;
    private int numOfBathrooms;
    private boolean hasViews;
    private int buildYear;

    public ResidentialProperty(String propertyID, String propertyDescription, LocalDate registrationDate, String propertyAddress, int numOfBedrooms, int numofBathrooms, boolean hasViews, int buildYear){
        super(propertyID, propertyDescription, registrationDate, propertyAddress);
        this.numOfBedrooms = numOfBedrooms;
        this.numOfBathrooms = numofBathrooms;
        this.hasViews = hasViews;
        this.buildYear = buildYear;
    }

    public int getNumOfBedrooms() {
        return numOfBedrooms;
    }

    public void setNumOfBedrooms(int numOfBedrooms) {
        if(numOfBedrooms < 1){
            System.out.println("Property must have at least one bedroom. Defaulting to 1");
            this.numOfBedrooms = 1;
        } else {
            this.numOfBedrooms = numOfBedrooms;
        }
    }

    public int getNumOfBathrooms() {
        return numOfBathrooms;
    }

    public void setNumOfBathrooms(int numOfBathrooms) {
        if(numOfBathrooms < 1){
            System.out.println("Property must have at least one bathroom. Defaulting to 1");
            this.numOfBathrooms = 1;
        } else {
            this.numOfBathrooms = numOfBathrooms;
        }
    }

    public boolean isHasViews() {
        return hasViews;
    }

    public void setHasViews(boolean hasViews) {
        this.hasViews = hasViews;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }
}
