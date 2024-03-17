import java.io.*;
import java.util.Vector;

public class PropertyManager implements Serializable {
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

    public void addRental(Rental rental){
        rentals.add(rental);
    }

    public void viewProperties(){
        for(Property property : properties){
            property.printDetailedInfo();
        }
    }

    public void viewRentals(){
        for(Rental rental : rentals){
            System.out.println(rental.getProperty());
            System.out.println(rental.getRentalStartDate());
            System.out.println(rental.getRentalEndDate());
            System.out.println();
        }
    }

    public boolean isPropertyIDUnique(String propertyID){
        for(Property property : properties){
            if(property.getPropertyID().equals(propertyID)){
                System.out.println(propertyID + " is already being used. Please enter another ID");
                return false;
            }
        }
        return true;
    }

    public boolean doesPropertyIDExist(String propertyID){
        for(Property property : properties){
            if(property.getPropertyID().equals(propertyID)){
                return true;
            }
        }
        return false;
    }

    public Property findPropertyByPropertyID(String propertyID){
        for(Property property : properties){
            if(property.getPropertyID().equals(propertyID)){
                return property;
            }
        }
        return null;
    }

    public void saveFile() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("propertyList.dat"));
        os.writeObject(properties);
        os.writeObject(rentals);
        os.close();
    }

    public void loadFile() throws IOException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream("propertyList.dat"));
        //TODO: Finish this method to load the list back into the program
    }
}
