public class Person {

    //attributes
     private String name;
     private String surname;
     private  String email;

     //constructor
    public Person(String name, String surname, String email) {

        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    //getters and setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void PersonInfo() {
        System.out.printf("%-11s %-14s %-20s\n", getName(), getSurname(), getEmail());
    }
}
