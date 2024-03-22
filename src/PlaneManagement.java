import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

public class PlaneManagement {

    private static final Scanner input = new Scanner(System.in); // to take inputs
    private static int[][] Plane_Seats; // implementing the 2D array for seats
    private static final char[] Seat_Rows = {'A', 'B', 'C', 'D'}; //seat rows are labelled according to their index

    public static Ticket[][] Tickets; // 2D array of type Ticket to store ticket information

    public static void main(String[] args) {

        System.out.println("\033[1m" +  "Welcome to the Plane Management application" + "\033[0m\n");

        Init_Seats();
        Init_Tickets();
        Menu();

        mainloop:
        while (true) {
            try {
                System.out.print("Please select an option: ");
                int option = input.nextInt();
                switch (option) {
                    case 1:
                        buy_seat();
                        break;

                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        ticket_price_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("\nExiting program...")
                        break mainloop;
                    default:
                        System.out.println("Please choose a valid option from 1 to 6");
                        continue mainloop;
                }
                Menu();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number (1-6).");
                input.nextLine(); //clears the invalid input from scanner
            }
        }
    }

    private static void Init_Seats() {
        Plane_Seats = new int[][]{
                new int[14], new int[12], new int[12], new int[14]  // creating seat numbers according each row
        };
        // making all the seats as available by marking as 0
        for (int i = 0; i < Plane_Seats.length; i++) {
            for (int j = 0; j < Plane_Seats[i].length; j++) {
                Plane_Seats[i][j] = 0;
            }
        }

    }

    private static void Init_Tickets() {
        Tickets = new Ticket[][] {
                new Ticket[14], new Ticket[12], new Ticket[12], new Ticket[14]
        };
    }

    private static void Menu() {
        System.out.println("*".repeat(50) + "\n" +
                "*" + " ".repeat(18) + "MENU OPTIONS" + " ".repeat(18) + "*\n" +
                "*".repeat(50) + "\n" +
                "\t1) Buy a seat\n" +
                "\t2) Cancel a seat\n" +
                "\t3) Find first available seat\n" +
                "\t4) Show seating plan\n" +
                "\t5) Print tickets information and total sales\n" +
                "\t6) Search ticket\n" +
                "\t0) Quit\n" +
                "*".repeat(50));
    }

    private static int[] Validate_Seat(){
        int row_index = -1; //initialize to invalid seat row
        int seat_number = -1; //initialize to invalid seat number

        do {

            System.out.print("Please enter the seat row: ");
            char row_letter = input.next().toUpperCase().charAt(0);
            input.nextLine();
            for (int i=0; i<Seat_Rows.length; i++){
                if (Seat_Rows[i] == row_letter){
                    row_index = i; //valid row index is set to row_index
                    break;
                }
            }
            if (row_index == -1){
                System.out.println("Invalid seat letter. Please choose from A, B, C, or D");
            }

        } while (row_index == -1);

        do {
            try {
                System.out.print("Please enter the seat number: ");
                seat_number = input.nextInt();

                if (seat_number > Plane_Seats[row_index].length || seat_number < 1) {
                    System.out.println("Invalid seat number. Please choose a number between 1 and " +
                            Plane_Seats[row_index].length); //prompt error message if an invalid seat number is entered
                    input.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer value for the seat number.");
                input.nextLine(); //clears buffer
            }
        } while (seat_number > Plane_Seats[row_index].length || seat_number < 1);

        int[] Seats = {row_index, seat_number};
        return Seats;

    }
    private static void buy_seat() {

        //while (true) {
            int[] SeatsDetails = Validate_Seat();
            if (Plane_Seats[SeatsDetails[0]][SeatsDetails[1] - 1] == 0) { //checks if the seat is available
                Plane_Seats[SeatsDetails[0]][SeatsDetails[1] - 1] = 1; //marks the seat as unavailable
                input.nextLine();
                System.out.print("Please enter your first name: ");
                String name = input.nextLine();
                System.out.print("Please enter your surname: ");
                String surname = input.nextLine();
                System.out.print("Please enter your email: ");
                String email = input.nextLine();

                Person new_person = new Person(name, surname, email);

                double price; //sets price according to their seat
                if (SeatsDetails[1]<= 5){
                    price = 200d;
                } else if (SeatsDetails[1] <= 9) {
                    price = 150d;
                } else {
                    price = 180d;
                }

                Ticket new_ticket = new Ticket(Seat_Rows[SeatsDetails[0]], SeatsDetails[1], price, new_person);
                Tickets[SeatsDetails[0]][SeatsDetails[1]-1] = new_ticket;

                new_ticket.save();

                System.out.println("Congratulations! You have successfully booked seat " + Seat_Rows[SeatsDetails[0]] +
                        SeatsDetails[1] + "\n");
                return; //exits the method
            } else {
                System.out.println("We're sorry, seat " + Seat_Rows[SeatsDetails[0]] + SeatsDetails[1] + " is unavailable. " +
                        "Please try another seat\n");
            }
        //}
    }

    private static void cancel_seat() {
        int[] SeatsDetails = Validate_Seat();

        if (Plane_Seats[SeatsDetails[0]][SeatsDetails[1] - 1] == 1) { //checks if the seat is already booked
            Plane_Seats[SeatsDetails[0]][SeatsDetails[1] - 1] = 0; //mark the seat as available

            String path = "Ticket Information" + File.separator +Seat_Rows[SeatsDetails[0]] + "" + SeatsDetails[1] + ".txt";

            File file = new File(path);
            file.delete();

            Tickets[SeatsDetails[0]][SeatsDetails[1]-1] = null; //removing the saved ticket information
            System.out.println("Seat successfully canceled\n");
        } else {
            System.out.println("This seat is not currently booked\n");
        }
    }

    private static void find_first_available() {
        // liner search is done as one by one seat has to checked to determine the first available seat
        for (int row=0; row< Plane_Seats.length; row++){
            for (int number=0; number<Plane_Seats[row].length; number++){
                if (Plane_Seats[row][number] == 0){
                    System.out.println("First available seat: " + Seat_Rows[row] + (number+1) + "\n");
                    return;
                }
            }
        }
        System.out.println("All the seats are booked\n");
    }

    private static void show_seating_plan() {
        System.out.println("Seating Plan (O: available, X: unavailable)");
        for (int[] row : Plane_Seats) {
            for (int seat : row) {
                if (seat == 0){
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void ticket_price_info() {
        System.out.println("\033[1mTickets sold during this session:\033[0m");
        System.out.printf("%-10s %-15s %-11s %-14s %-20s\n", "Seat", "Price(£)", "Name", "Surname", "Email");
        System.out.println("-".repeat(60));
        double total = 0d;
        for (Ticket[] row : Tickets) {
            for (Ticket seat : row) {
                if (seat != null) { //prints the information if array index is not null
                    seat.TicketInfo();
                    total += seat.getPrice();
                }
            }
        }
        System.out.println("-".repeat(60));
        System.out.println("Total ticket sells : £" + total + "\n");
    }

    private static void search_ticket() {
        int[] SeatsDetails = Validate_Seat();
        System.out.println();
        if (Plane_Seats[SeatsDetails[0]][SeatsDetails[1]-1] == 1) {
            Tickets[SeatsDetails[0]][SeatsDetails[1] - 1].PrintTicket(); //prints ticket information if seat is booked
        } else {
            System.out.println("The seat is available\n");
        }
    }
}
