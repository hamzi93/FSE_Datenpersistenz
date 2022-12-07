package at.itKolleg.domain;

import java.sql.Date;
import java.time.LocalDate;

public class Student extends BaseEntity {

    private String firstName;
    private String lastName;
    private Date birthDate;

    public Student(Long id, String firstName, String lastName, Date birthDate) {
        super(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setBirthDate(birthDate);
    }

    public Student(String firstName, String lastName, Date birthDate) {
        super(null);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setBirthDate(birthDate);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws InvalidValueExeption {
        if (firstName != null && firstName.length() > 2) { //Abfrage ob Wert null ist und ob länge > 2
            this.firstName = firstName;
        } else {
            throw new InvalidValueExeption("Vorname muss mindestens 3 Zeichen lang sein!");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidValueExeption {
        if (lastName != null && lastName.length() > 2) { //Abfrage ob Wert null ist und ob länge > 2
            this.lastName = lastName;
        } else {
            throw new InvalidValueExeption("Nachname muss mindestens 3 Zeichen lang sein!");
        }
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) throws InvalidValueExeption {
        Date localDate = Date.valueOf(LocalDate.now());
        if (birthDate != null){ // null Abfrage
            if (birthDate.before(localDate)){ // geburtstag darf nicht in der Zukunft liegt.
                this.birthDate = birthDate;
            }else {
                throw new InvalidValueExeption("Geburtsdatum darf nicht in der Zukunft liegen!");
            }
        }else {
            throw new InvalidValueExeption("Geburtsdatum darf nicht leer sein!");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + this.getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
