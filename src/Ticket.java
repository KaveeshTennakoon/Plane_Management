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
        System.out.println("\033[1mTicket Information\033[0m \n\tRow: " + getRow() + "\tSeat: " + getSeat() +
                "\n\tPrice: \u00A3" + getPrice() +
                "\n\033[1mPerson Information\033[0m");
        person.PersonInfo();
    }
}
