import java.io.IOException;
import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainApplication implements Serializable {

    private static Scanner sc = new Scanner(System.in);
    private static PropertyManager propertyManager = new PropertyManager();
    public static void main(String[] args) {
        System.out.println("Welcome to your Property Management App!");
        mainMenu();
    }

    private static void mainMenu() {
        int choice;
        System.out.println("Please choose an option by entering the corresponding number:");
        System.out.println("1. Add commercial or residential properties");
        System.out.println("2. Add rental agreements");
        System.out.println("3. View all properties");
        System.out.println("4. View rent summary");
        System.out.println("5. Save file");
        System.out.println("6. Load file");
        System.out.println("7. Exit Application");
        do{
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1: addProperty();
                    break;
                case 2: addRental();
                    break;
                case 3: viewAllProperties();
                    break;
                case 4: viewRentalSummary();
                    break;
                case 5: saveListOfProperties();
                    break;
                case 6: loadListOfProperties();
                    break;
                case 7: System.exit(0);
                    break;
                default: System.out.println("Invalid option entered");
            }
        } while(choice < 1 || choice > 7);
    }

    private static void addProperty(){
        System.out.println("Enter a property id. It must be at least 10 characters long.");
        String propertyID = sc.nextLine();
        System.out.println("Enter a short description of your property");
        String propertyDescription = sc.nextLine();
        System.out.println("Enter the date of registration in the format YYYY-MM-DD. ");
        String date = sc.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate registrationDate = LocalDate.parse(date, dtf);
        System.out.println("Enter the address of your property");
        String propertyAddress = sc.nextLine();
        System.out.println("Is this a commercial or a residential property? Enter 'C' or 'R' to continue" );
        String propertyType = sc.nextLine();
        Property newProperty = null; //required since subclass will be set in if-else block and will result in scope issue if declared in that block
        if(propertyType.equalsIgnoreCase("C") || propertyType.equalsIgnoreCase("Commercial")){
            System.out.println("What is the license class of the property? Please enter a value between 1 and 8");
            int propertyLicenceClass = sc.nextInt();
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
        System.out.println("What is the total area of the property in square metres? Leave blank to skip");
        String areaInput = sc.nextLine();
        if(!areaInput.isEmpty()){
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
        propertyManager.addProperty(newProperty);
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
        System.out.println("Please enter the date of the start of the rental");
        String startDate = sc.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rentalStartDate = LocalDate.parse(startDate, dtf);
        sc.nextLine();
        System.out.println("Please enter the end date of the rental");
        String endDate = sc.nextLine();
        LocalDate rentalEndDate = LocalDate.parse(endDate, dtf);
        sc.nextLine();

        Rental newRental = new Rental(propertyToRent, rentalStartDate, rentalEndDate);

        propertyManager.addRental(newRental);
        mainMenu();
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
        } catch (IOException e){
            System.out.println("Failed to save properties to file");
        }
        mainMenu();
    }

    private static void loadListOfProperties() {
        try{
            propertyManager.loadFile();
        } catch (IOException e){
            System.out.println("Failed to load properties from file. Please make sure the file exists and try again.");
        }
        mainMenu();
    }
}
