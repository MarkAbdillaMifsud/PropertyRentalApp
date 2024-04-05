import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Vector;

public class PropertyManager implements Serializable {
    private Vector<Property> properties = new Vector<>();
    private Vector<Rental> rentals = new Vector<>();
    private final String PROPERTY_FILE = "propertyList.dat";

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

    /*
    Add property methods using Insertion Sort
     */
    public void addProperty(Property newProperty, Vector<Property> properties, int numbersSoFar){
        int pos = findPosition(newProperty.getPropertyID(), properties, numbersSoFar);
        shift(pos, properties, numbersSoFar);
        properties.set(pos, newProperty);
    }

    private void shift(int pos, Vector<Property> properties, int numbersSoFar){
        properties.add(null); //adds element at end of Vector to have space for shift
        for(int i = numbersSoFar; i > pos; i--){
            properties.set(i, properties.get(i - 1));
        }
    }

    private int findPosition(String propertyID, Vector<Property> properties, int numbersSoFar){
        for(int i = 0; i < numbersSoFar; i++){
            if(propertyID.compareTo(properties.get(i).getPropertyID()) < 0){
                return i;
            }
        }
        return numbersSoFar;
    }

    /*
    Add rental property
     */
    public void addRental(Rental rental){
        rentals.add(rental);
    }

    /*
    View all properties
     */

    public void viewProperties(){
        for(Property property : properties){
            property.printDetailedInfo();
        }
    }

    /*
    View all Rentals
     */

    public void viewRentals(){
        for(Rental rental : rentals){
            System.out.println(rental.getProperty());
            System.out.println(rental.getRentalStartDate());
            System.out.println(rental.getRentalEndDate());
            System.out.println();
        }
    }

    /*
    Check whether property ID already exists and if it is less than 10 characters.
    Intended to be used to validate inputs for new properties as well as when changing existing property ids
     */

    public boolean isPropertyIDValid(String propertyID){
        for(Property property : properties){
            if(property.getPropertyID().equals(propertyID)){
                System.out.println(propertyID + " is already being used. Please enter another ID");
                return false;
            }
        }
        if(propertyID.length() < 10){
            System.out.println(propertyID + " has less than 10 characters. Please enter another ID");
            return false;
        }
        return true;
    }

    /*
    Delete a property. Will also check rentals for the same property.
    Since a rental needs a property, deleting a property should also delete the rental
     */
    public void removeProperty(String propertyID){
        for(int i = 0; i < properties.size(); i++){
            Property property = properties.get(i);
            if(property.getPropertyID().equals(propertyID)){
                //iterate backwards since size is changing and could lead to some elements being skipped
                for(int j = rentals.size() - 1; j >= 0; j--){
                    Rental rental = rentals.get(j);
                    if(rental.getProperty().getPropertyID().equals(propertyID)){
                        rentals.remove(j);
                    }
                }
                properties.remove(i);
                return;
            }
        }
    }

    /*
    Check whether a propertyID already exists.
    This can be used to check whether a property can be added as a rental since the propertyID is shared
     */
    public boolean doesPropertyIDExist(String propertyID){
        for(Property property : properties){
            if(property.getPropertyID().equals(propertyID)){
                return true;
            }
        }
        return false;
    }

    /*
    Go through the property Vector to get a specific property. Useful for editing existing elements
     */
    public Property findPropertyByPropertyID(String propertyID){
        for(Property property : properties){
            if(property.getPropertyID().equals(propertyID)){
                return property;
            }
        }
        return null;
    }

    /*
    Calculate and print the total collected rents from all properties in the rentals vector.
    If rental stops partway through a full month, then the full month's rent is considered
     */
    public void calculateTotalRents(){
        HashMap<String, Float> totalRents = new HashMap<>();

        for(Rental rental : rentals){
            Property property = rental.getProperty();
            //calculate number of months of the rental
            long monthsBetween = ChronoUnit.MONTHS.between(rental.getRentalStartDate().withDayOfMonth(1), rental.getRentalEndDate().withDayOfMonth(1));
            if(rental.getRentalEndDate().getDayOfMonth() > rental.getRentalStartDate().getDayOfMonth()){
                monthsBetween++; //adds a month in case a rental ends before the end of a particular month
            }
            float totalRentForThisRental = monthsBetween * property.getMonthlyRentalPrice();

            //allows for a final rent amount generated by each property
            totalRents.merge(property.getPropertyID(), totalRentForThisRental, Float::sum);
        }

        //print total rents collected for each property
        for(String propertyID : totalRents.keySet()){
            System.out.println("Property ID: " + propertyID + ", Total Rent Collected: €" + totalRents.get(propertyID));
        }
    }

    /*
    Save properties and rentals to propertyList.dat
     */
    public void saveFile() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("propertyList.dat"));
        os.writeObject(properties);
        os.writeObject(rentals);
        os.close();
        System.out.println("Status successfully saved to " + PROPERTY_FILE);
    }

    /*
    Load contents of propertyList.dat
     */

    public void loadFile() throws IOException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(PROPERTY_FILE));
        try{
            properties = (Vector<Property>) is.readObject();
            rentals = (Vector<Rental>) is.readObject();
            System.out.println(PROPERTY_FILE + " successfully loaded");
        } catch(FileNotFoundException e){
            System.out.println("File does not exist");
        } catch(ClassNotFoundException e){
            System.out.println("A class in the serialized object cannot be found");
        }
        is.close();
    }
}
