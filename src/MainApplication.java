import java.io.IOException;
import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainApplication implements Serializable {

    private static Scanner sc = new Scanner(System.in);
    private static PropertyManager propertyManager = new PropertyManager();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("Welcome to your Property Management App!");
        mainMenu();
    }

    private static void mainMenu() {
        int choice;
        while (true) { // Loop continues until valid option is entered
            System.out.println("Please choose an option by entering the corresponding number:");
            System.out.println("1. Add commercial or residential properties");
            System.out.println("2. Add rental agreements");
            System.out.println("3. Edit existing property");
            System.out.println("4. View all properties");
            System.out.println("5. View rent summary");
            System.out.println("6. Save file");
            System.out.println("7. Load file");
            System.out.println("8. Exit Application");

            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1: addProperty();
                        break;
                    case 2: addRental();
                        break;
                    case 3: editProperty();
                        break;
                    case 4: viewAllProperties();
                        break;
                    case 5: viewRentalSummary();
                        break;
                    case 6: saveListOfProperties();
                        break;
                    case 7: loadListOfProperties();
                        break;
                    case 8: System.exit(0); // Exit application
                        break;
                    default: System.out.println("Invalid option entered. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("You must enter a number to continue. Please try again.");
                sc.nextLine();
            }
        }
    }

    private static void addProperty(){
        /*
        Required variables
         */
        System.out.println("Enter a property id. It must be at least 10 characters long.");
        String propertyID = sc.nextLine();
        while(!propertyManager.isPropertyIDValid(propertyID)){
            propertyID = sc.nextLine();
        }
        System.out.println("Enter a short description of your property");
        String propertyDescription = sc.nextLine();
        LocalDate registrationDate = null; //value to check in case of parse exception
        while(registrationDate == null){
            System.out.println("Enter the date of registration in the format YYYY-MM-DD. ");
            String date = sc.nextLine();
            try{
                registrationDate = LocalDate.parse(date, dtf);
            } catch (DateTimeParseException e){
                System.out.println("Error: The date was entered in an incorrect format.");
            }
        }
        System.out.println("Enter the address of your property");
        String propertyAddress = sc.nextLine();
        /*
        Commercial or Residential check
         */
        System.out.println("Is this a commercial or a residential property? Enter 'C' or 'R' to continue" );
        String propertyType = sc.nextLine();
        Property newProperty = null; //required since subclass will be set in if-else block and will result in scope issue if declared in that block
        if(propertyType.equalsIgnoreCase("C") || propertyType.equalsIgnoreCase("Commercial")){
            System.out.println("What is the license class of the property? Please enter a value between 1 and 8");
            int propertyLicenceClass;
            do{
                propertyLicenceClass = sc.nextInt();
                if(propertyLicenceClass < 1 || propertyLicenceClass > 8){
                    System.out.println("Invalid property licence class number. Enter a value between 1 and 8");
                }
            } while (propertyLicenceClass < 1 || propertyLicenceClass > 8);
            sc.nextLine();
            System.out.println("Does the property have accessibility measures? Enter Y or N");
            String accessibility = sc.nextLine();
            boolean isAccessible = false;
            if(accessibility.equalsIgnoreCase("Y") || accessibility.equalsIgnoreCase("Yes")){
                isAccessible = true;
            }
            newProperty = new CommercialProperty(propertyID, propertyDescription, registrationDate, propertyAddress, propertyLicenceClass, isAccessible);
        } else if(propertyType.equalsIgnoreCase("R") || propertyType.equalsIgnoreCase("Residential")){
            System.out.println("How many bedrooms does the property have?");
            int numOfBedrooms = sc.nextInt();
            sc.nextLine();
            System.out.println("How many bathrooms does the property have?");
            int numOfBathrooms = sc.nextInt();
            sc.nextLine();
            System.out.println("Does the property have views? Enter Y or N");
            String views = sc.nextLine();
            boolean hasViews = false;
            if(views.equalsIgnoreCase("Y") || views.equalsIgnoreCase("Yes")){
                hasViews = true;
            }
            System.out.println("What year was the property built?");
            int year = sc.nextInt();
            sc.nextLine();
            newProperty = new ResidentialProperty(propertyID, propertyDescription, registrationDate, propertyAddress, numOfBedrooms, numOfBathrooms, hasViews, year);
        }
        /*
        Optional variables
         */
        System.out.println("What is the total area of the property in square metres? Leave blank to skip");
        String areaInput = sc.nextLine();
        if(!areaInput.isEmpty()){ //allow no input since variable is optional
            try{
                double totalArea = Double.parseDouble(areaInput);
                newProperty.setArea(totalArea);
            } catch (NumberFormatException e){
                System.out.println("Invalid value entered");
            }
        }
        System.out.println("What is the monthly rental price of this property?");
        String priceInput = sc.nextLine();
        if(!priceInput.isEmpty()){
            try{
                float monthlyPrice = Float.parseFloat(priceInput);
                newProperty.setMonthlyRentalPrice(monthlyPrice);
            } catch (NumberFormatException e){
                System.out.println("Invalid value entered");
            }
        }
        /*
        Insertion sort to place the property object into the Vector
         */
        propertyManager.addProperty(newProperty, propertyManager.getProperties(), propertyManager.getProperties().size());
        mainMenu();
    }

    private static void addRental(){
        /*
        Loop through Property Vector to find the property ID we inserted, then store that property object into a variable to go into constructor
         */
        System.out.println("Insert the propertyID of the property you would like to rent out");
        String propertyID = sc.nextLine();
        if(!propertyManager.doesPropertyIDExist(propertyID)){
            System.out.println(propertyID + " does not exist in the property list. You can only rent out an existing property");
            mainMenu();
        }
        Property propertyToRent = propertyManager.findPropertyByPropertyID(propertyID);
        LocalDate rentalStartDate = null;
        LocalDate rentalEndDate = null;
        while(rentalStartDate == null){
            try {
                System.out.println("Please enter the date of the start of the rental");
                String startDate = sc.nextLine();
                rentalStartDate = LocalDate.parse(startDate, dtf);
                sc.nextLine();
            } catch(DateTimeParseException e){
                System.out.println("Incorrect date format entered. Please enter it correctly.");
            }
        }

        boolean endDateIsValid = false;
        while(!endDateIsValid){
            try{
                System.out.println("Please enter the end date of the rental");
                String endDate = sc.nextLine();
                rentalEndDate = LocalDate.parse(endDate, dtf);

                if(rentalEndDate.isAfter(rentalStartDate)){
                    endDateIsValid = true;
                } else {
                    System.out.println("The end date date must be after the start date.");
                }
                sc.nextLine();
            } catch (DateTimeParseException e){
                System.out.println("Incorrect date format entered. Please enter it correctly.");
            }
        }

        Rental newRental = new Rental(propertyToRent, rentalStartDate, rentalEndDate);

        propertyManager.addRental(newRental);
        mainMenu();
    }

    private static void editProperty(){
        System.out.println("Enter a property id to find an existing property");
        String propertyID = sc.nextLine();
        sc.nextLine();
        if(!propertyManager.doesPropertyIDExist(propertyID)){
            System.out.println(propertyID + " does not exist in the property list. You can only rent out an existing property");
            mainMenu();
        }
        Property property = propertyManager.findPropertyByPropertyID(propertyID);
        int choice = 0;

        while(choice < 1 || choice > 10){
            System.out.println("What would you like to change?");
            System.out.println("1. Change PropertyID");
            System.out.println("2. Change Description");
            System.out.println("3. Change Registration Date");
            System.out.println("4. Change Property Address");
            System.out.println("5. Change Area of Property");
            System.out.println("6. Change Monthly Rental Price");
            System.out.println("7. Delete Property");
            if(property instanceof ResidentialProperty){
                System.out.println("8. Edit Residential Property attributes");
            } else if(property instanceof CommercialProperty){
                System.out.println("8. Edit Commercial Property attributes");
            }
            System.out.println("9. Return to Main Menu");
            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1: System.out.println("Enter a new property id");
                        String newPropertyID = sc.nextLine();
                        sc.nextLine();
                        property.setPropertyID(newPropertyID);
                        //TODO: Include Validation
                        break;
                    case 2: System.out.println("Enter a new description");
                        String newDescription = sc.nextLine();
                        sc.nextLine();
                        property.setPropertyDescription(newDescription);
                        break;
                    case 3: System.out.println("Enter a new registration date");
                        String newDate = sc.nextLine();
                        LocalDate newRegistrationDate = LocalDate.parse(newDate, dtf);
                        property.setRegistrationDate(newRegistrationDate);
                        sc.nextLine();
                        break;
                    case 4: System.out.println("Enter a new address");
                        String newAddress = sc.nextLine();
                        sc.nextLine();
                        property.setPropertyAddress(newAddress);
                        break;
                    case 5: System.out.println("Enter a new area");
                        double newArea = sc.nextDouble();
                        property.setArea(newArea);
                        break;
                    case 6: System.out.println("Enter a new rental price");
                        float newPrice = sc.nextFloat();
                        property.setMonthlyRentalPrice(newPrice);
                        break;
                    case 7: propertyManager.removeProperty(propertyID);
                        System.out.println("Property removed. Please press Enter to return to the Main Menu");
                        sc.nextLine();
                        mainMenu();
                    case 8: if(property instanceof ResidentialProperty residentialProperty) {
                        editResidentialProperties(residentialProperty);
                    } else if(property instanceof CommercialProperty commercialProperty){
                        editCommercialProperties(commercialProperty);
                    }
                        break;
                    case 9: mainMenu();
                    default: System.out.println("Invalid option entered. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("You must enter a number to continue. Please try again.");
                sc.nextLine();
            }
        }
    }

    private static void editResidentialProperties(ResidentialProperty property){
        int choice = 0;
        while(choice < 1 || choice > 5){
            System.out.println("1. Edit Number of Bedrooms");
            System.out.println("2. Edit Number of Bathrooms");
            System.out.println("3. Edit Views");
            System.out.println("4. Edit Year of Build");
            System.out.println("5. Return to Main Menu");
            try{
                choice = sc.nextInt();
                switch (choice){
                    case 1: System.out.println("Enter the number of bedrooms in the property");
                        int newNumBedrooms = sc.nextInt();
                        property.setNumOfBedrooms(newNumBedrooms);
                        break;
                    case 2: System.out.println("Enter the number of bathrooms in the property");
                        int numNewBathrooms = sc.nextInt();
                        property.setNumOfBathrooms(numNewBathrooms);
                        break;
                    case 3: System.out.println("Does the property have views?");
                        String newViews = sc.nextLine();
                        if(newViews.equalsIgnoreCase("Y") || newViews.equalsIgnoreCase("Yes")){
                            property.setHasViews(true);
                        } else {
                            property.setHasViews(false);
                        }
                        break;
                    case 4: System.out.println("What year was the property built?");
                        int newBuildYear = sc.nextInt();
                        property.setBuildYear(newBuildYear);
                        break;
                    case 5: mainMenu();
                    default: System.out.println("Invalid option entered. Please try again.");
                }
            } catch (InputMismatchException e){
                System.out.println("You must enter a number to continue. Please try again.");
                sc.nextLine();
            }
        }
    }

    private static void editCommercialProperties(CommercialProperty property){
        int choice = 0;
        while(choice < 1 || choice > 3) {
            System.out.println("1. Edit Property Licence Class");
            System.out.println("2. Edit Property Accessibility");
            System.out.println("3. Return to Main Menu");
            try {
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter the property licence class number");
                        int newClassNum = sc.nextInt();
                        property.setPropertyLicenseClass(newClassNum);
                        break;
                    case 2:
                        System.out.println("Is the property accessible?");
                        String newAccessible = sc.nextLine();
                        if (newAccessible.equalsIgnoreCase("Y") || newAccessible.equalsIgnoreCase("Yes")) {
                            property.setAccessible(true);
                        } else {
                            property.setAccessible(false);
                        }
                        break;
                    case 3:
                        mainMenu();
                    default:
                        System.out.println("Invalid option entered. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("You must enter a number to continue. Please try again.");
                sc.nextLine();
            }
        }
    }

    private static void viewAllProperties(){
        propertyManager.viewProperties();
        mainMenu();
    }

    private static void viewRentalSummary(){
        propertyManager.calculateTotalRents();
        mainMenu();
    }

    private static void saveListOfProperties() {
        try{
            propertyManager.saveFile();
            sc.nextLine();
        } catch (IOException e){
            System.out.println("Failed to save properties to file");
        }
        mainMenu();
    }

    private static void loadListOfProperties() {
        try{
            propertyManager.loadFile();
            sc.nextLine();
        } catch (IOException e){
            System.out.println("Failed to load properties from file. Please make sure the file exists and try again.");
        }
        mainMenu();
    }
}
