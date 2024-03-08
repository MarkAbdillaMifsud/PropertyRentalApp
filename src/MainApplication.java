import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainApplication {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){
        System.out.println("Welcome to your Property Management App!");
        mainMenu();
    }

    private static void mainMenu(){
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
            switch (choice){
                case 1: //Add property
                    break;
                case 2: //Add rental
                    break;
                case 3: //View all properties
                    break;
                case 4: //View rent summary
                    break;
                case 5: //Save
                    break;
                case 6: //Load
                    break;
                case 7: //Exit
                    break;
                default: System.out.println("Invalid option entered");
            }
        } while(choice < 1 || choice > 7);
    }

    private static void addProperty(){
        System.out.println("Enter a property id. It must be at least 10 characters long.");
        String propertyID = sc.nextLine();
        sc.nextLine();
        System.out.println("Enter a short description of your property");
        String propertyDescription = sc.nextLine();
        sc.nextLine();
        System.out.println("Enter the date of registration in the format YYYY-MM-DD. ");
        String date = sc.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate registrationDate = LocalDate.parse(date, dtf);
        sc.nextLine();
        System.out.println("Enter the address of your property");
        String propertyAddress = sc.nextLine();
        sc.nextLine();
        //Check Commercial or Residential, or handle optional variables in Property first?
        //need to store property in variable before setting optionals so start by handling subclass
    }
}
