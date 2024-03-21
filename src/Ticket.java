import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {

    //attributes
    private char row;
    private int seat;
    private double price;
    private Person person;

    //constructor
    public Ticket(char row, int seat, double price, Person person) {

        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    //getters and setters
    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void TicketInfo() {
        System.out.printf("%-10s %-16s", getRow()+""+getSeat(), getPrice());
        person.PersonInfo();
    }

    public void save() {
        String path = "Ticket Information" + File.separator + String.valueOf(getRow()) + getSeat() + ".txt";
        try {

            File folder = new File("Ticket Information");
            if (!folder.exists()) {
                folder.mkdirs(); //makes a new folder if it doesn't exist
            }

            FileWriter file = new FileWriter(path); // writing the file with seat as the file name

            file.write("Ticket Information \n\tSeat: " +
                    getRow() + "" + getSeat() + "\n\tPrice: " + getPrice() +
                    "\nPerson Information \n\tName: " +
                    person.getName() + "\n\tSurname: " +
                    person.getSurname() + "\n\tEmail: " +
                    person.getEmail());
            file.close();

        } catch (IOException e) {
            System.out.println("An error occurred");
        }
    }

    public void PrintTicket() { //printing when the option 6 is called
        System.out.println("\033[1mTicket Information\033[0m \n\tRow: " + getRow() + "\tSeat: " + getSeat() +
                "\n\tPrice: £" + getPrice() +
                "\n\033[1mPerson Information\033[0m" +
                "\n\tName: " + person.getName() +
                "\n\tSurname: " + person.getSurname() +
                "\n\tEmail: " + person.getEmail() + "\n");
    }
}
