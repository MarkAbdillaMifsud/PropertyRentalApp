import java.util.Vector;

public class PropertyManager {
    private Vector<Property> properties = new Vector<>();
    private Vector<Rental> rentals = new Vector<>();

    public Vector<Property> getProperties() {
        return properties;
    }

    public void setProperties(Vector<Property> properties) {
        this.properties = properties;
    }

    public Vector<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Vector<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addProperty(Property property){
        properties.add(property);
    }

    public void addRental(Rental rental, Vector<Rental> rentals){
        rentals.add(rental);
    }

    public void viewProperties(Vector<Property> properties){
        for(Property property : properties){
            System.out.println(property.getPropertyID());
            System.out.println(property.getPropertyDescription());
            System.out.println(property.getRegistrationDate());
            System.out.println(property.getPropertyAddress());
            System.out.println(property.getArea());
            System.out.println(property.getMonthlyRentalPrice());
            System.out.println();
        }
    }

    public void viewRentals(Vector<Rental> rentals){
        for(Rental rental : rentals){
            System.out.println(rental.getProperty());
            System.out.println(rental.getRentalStartDate());
            System.out.println(rental.getRentalEndDate());
            System.out.println();
        }
    }

    public boolean isPropertyIDUnique(String propertyID, Vector<Property> properties){
        for(Property property : properties){
            if(property.getPropertyID().equals(propertyID)){
                System.out.println(propertyID + " is already being used. Please enter another ID");
                return false;
            }
        }
        return true;
    }
}
