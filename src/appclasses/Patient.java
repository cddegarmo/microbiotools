package appclasses;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Patient {

    public enum Sex {
        MALE, FEMALE
    }

    private final String firstName;
    private final String lastName;
    private final Sex gender;
    private final LocalDate birthday;

    private Patient(String lastName) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter patient's first name: ");
        firstName = s.nextLine();
        System.out.print("Enter patient's last name: ");
        this.lastName = lastName;
        System.out.print("Enter patient's gender (1 for male, 2 for female): ");
        gender = Sex.values()[s.nextInt() - 1];
        s.nextLine();
        System.out.print("Enter patient's birthday (ex. Jun 4, 1991): ");
        String input = s.nextLine();
        birthday = dateInput(input);
    }

    public static Patient addRecord(String lastName) {
        if(lastName.equals(""))
            throw new IllegalArgumentException("Patient name required to create record.");
        else
            return new Patient(lastName);
    }

    private static LocalDate dateInput(String input) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM d, yyyy");
            LocalDate ld = LocalDate.parse(input, df);
            return ld;
        } catch (DateTimeParseException exc) {
            System.out.printf("%s is not parsable!%n", input);
            throw exc;
        }
    }

    private String birthdayFormatter() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM d, yyyy");
            String out = birthday.format(dtf);
            return out;
        } catch (DateTimeException exc) {
            System.out.printf("%s cannot be formatted", birthday);
            throw exc;
        }
    }

    public int getAge() {
        return birthday
                .until(LocalDate.now())
                .getYears();
    }

    public int compareByAge(Patient a, Patient b) {
        return a.birthday.compareTo(b.birthday);
    }

    public String getFirstName()    { return firstName; }
    public String getLastName()     { return lastName;  }
    public Sex getGender()          { return gender;    }
    public LocalDate getBirthday()  { return birthday;  }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Patient))
            return false;
        Patient pt = (Patient) o;
        return pt.lastName.equals(lastName) && pt.firstName.equals(firstName);
    }

    @Override
    public int hashCode() {
        int result = lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s, %s%nBorn: %s", lastName, firstName, birthdayFormatter());
    }

    public static void main(String[] args) {
        Patient test = Patient.addRecord("Roman");
        System.out.println(test.birthday.toString());
        System.out.println(test.birthdayFormatter());
        System.out.println(test.getAge());
    }
}
