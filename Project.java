import static java.lang.System.in;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Project {

    static boolean login = false;
    static boolean book = false;
    static String username = "";


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(in);
        System.out.println("==================================================");
        System.out.println("||\t\tSardar Patel International Airport\t\t||");
        System.out.println("==================================================");

        String input = "0";

        while (!input.equals("8")) {
            System.out.println("\n\n\n");
            System.out.println("==================================================");
            System.out.println("\t\t\t\t\tMenus");
            System.out.println("==================================================");

            System.out.println("""
                
                 1. Login / Sign up
                 2. View today's Schedule
                 3. Book a ticket
                 4. View your ticket
                 5. View latency of trains
                 6. Order food on your seat
                 7. Give Feedback
                 8. Exit
                """);

            System.out.print("Please select one of these: ");

            input = scanner.nextLine();

            switch (input) {
                case "1":
                    handleLoginOrSignup(scanner);
                    break;
                case "2":
                    viewSchedule();
                    break;
                case "3":
                    bookTicket(scanner);
                    break;
                case "4":
                    viewTicket();
                    break;
                case "5":
                    viewLatency();
                    break;
                case "6":
                    orderFood(scanner);
                    break;
                case "7":
                    giveFeedback(scanner);
                    break;
                case "8":
                    System.out.println("\n\nThank you!");
                    break;
                default:
                    System.out.println("\nSelect one from the serial numbers.");
                    System.out.println("Press Enter to continue! ");
                    scanner.nextLine();
            }
        }
        scanner.close();
    }



    private static void handleLoginOrSignup(Scanner sc) {
        System.out.println("\n1) Login");
        System.out.println("2) Sign up");
        System.out.print("\nPlease select one of these: ");

        String choice = sc.nextLine();

        if (choice.equals("1")) {
            System.out.print("Enter your Username: ");
            String inputUsername = sc.next();
            System.out.print("Enter your Password: ");
            String inputPassword = sc.next();
            sc.nextLine();

            if (validateLogin(inputUsername, inputPassword)) {

                username = inputUsername;
                login = true;

                System.out.println("\nYou are now logged in");

            }
            else {

                System.out.println("\nIncorrect username or password");

            }
        }
        else if (choice.equals("2")) {

            System.out.println("Please fill these details");
            System.out.print("Username: ");
            String newUsername = sc.next();
            System.out.print("Email: ");
            String email = sc.next();
            System.out.print("Create a password: ");
            String password = sc.next();

            if (!(newUsername.isEmpty() || email.isEmpty() || password.isEmpty())) {

                addUserToCSV(newUsername, email, password);

                username = newUsername;
                login = true;

                System.out.println("\nSign up and Login success\n");

            }
            else {
                System.out.println("\nThe details must not be empty..\n");
            }

        }
        else {
            System.out.println("Enter value from the given list..");
        }

        System.out.println("Press Enter to continue! ");
        sc.nextLine();

    }





    static void viewSchedule() {
        System.out.println("\n\nHere's the schedule for today:\n\n");

        String loginsDataPath = "Data/schedule.csv";

        try {

            String schedule = new String(Files.readAllBytes(Paths.get(loginsDataPath)));

            int linesCount = count(schedule, '\n') + 1;


            String[][] flights = new String[linesCount][12];

            String[] lines = schedule.split("\n");

            for (int i = 0; i < lines.length; i++) {

                flights[i] = lines[i].split(",");

            }


            printArr(flights);


        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }


    static void printArr(String[][] table) {

        int[] columnWidths = new int[table[0].length];


        for (String[] row : table) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].trim().length());
            }
        }


        // Print the formatted table
        for (int i = 0; i < table.length; i++) {

            for (int j = 0; j < table[i].length; j++) {

                if (!(j == 3 || j == 4 || j == 7))
                    System.out.printf("%-" + columnWidths[j] + "s ", table[i][j].trim());

            }
            System.out.println();
        }

    }






    static void bookTicket(Scanner scanner) {
        if (login) {
            System.out.println("Booking ticket functionality is under development.");
        } else {
            System.out.println("\nYou first have to login..\n");
        }
    }

    static void viewTicket() {
        if (book) {
            System.out.println("Viewing ticket functionality is under development.");
        } else {
            System.out.println("\nFirst you have to book a ticket.");
        }
    }

    static void viewLatency() {
        System.out.println("Latency graph functionality is under development.");
    }

    static void orderFood(Scanner scanner) {
        if (book) {
            System.out.println("Food ordering functionality is under development.");
        } else {
            System.out.println("\nFirst you have to book a ticket.");
        }
    }

    static void giveFeedback(Scanner scanner) {
        if (login) {
            System.out.print("\nEnter your Feedback here: \n\n");
            String feedback = scanner.nextLine();
            addFeedbackToCSV(username, feedback);
            System.out.println("Thank you for your valuable feedback.\n\n");
        } else {
            System.out.println("First, you have to login.");
        }
    }



    static boolean validateLogin(String username, String password) {

        String loginsDataPath = "Data/logins.csv";

        try {

            String fileContent = new String(Files.readAllBytes(Paths.get(loginsDataPath)));

            fileContent = fileContent.substring(
                    fileContent.indexOf("\n") + 1
            );

            int linesCount = count(fileContent, '\n') + 1;

            String[][] logins = new String[linesCount][3];

            String[] lines = fileContent.split("\n");

            for(int i = 0; i < lines.length; i++){

                logins[i] = lines[i].split(",");

            }



            for(int i = 0; i < logins.length; i++){

                if(username.equals(logins[i][0]) && password.equals(logins[i][2]))
                    return true;

            }

        }
        catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return false;

    }





    // Add logic to append user data to a CSV file
    static void addUserToCSV(String username, String email, String password) {

        try (
                FileWriter fw = new FileWriter("Data/logins.csv", true);

                BufferedWriter bw = new BufferedWriter(fw);

                PrintWriter out = new PrintWriter(bw)
        ) {
            out.print("\n" + username + "," + email + "," + password);

        }

        catch (IOException e) {
            System.out.println("Error writing to logins.csv: " + e.getMessage());
        }
    }

    // Add logic to append feedback data to a CSV file
    static void addFeedbackToCSV(String username, String feedback) {
        try (FileWriter fw = new FileWriter("Data/feedback.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(username + "=" + feedback);
            System.out.println("Feedback submitted successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to feedback.csv: " + e.getMessage());
        }
    }


    static int count(String str, char ch){

        int count = 0;
        for (int i = 0; i < str.length(); i++){

            if(ch == str.charAt(i))
                count++;

        }

        return count;

    }

}