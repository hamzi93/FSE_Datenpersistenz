package at.itKolleg.ui;

import at.itKolleg.dataaccess.DatabaseException;
import at.itKolleg.dataaccess.MyCourseRepository;
import at.itKolleg.domain.Course;
import at.itKolleg.domain.CourseTyp;
import at.itKolleg.domain.InvalidValueExeption;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.Date;

public class Cli {

    Scanner scan;
    MyCourseRepository repo;

    public Cli(MyCourseRepository repo) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start() {
        String input = "-";
        while (!input.equals("x")) {
            showMenu();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    //System.out.println("Kurseingabe");
                    addCourse();
                    break;
                case "2":
                    //Alle Kurse anzigen
                    showAllCourses();
                    break;
                case "3":
                    // Kursdetails ausgeben
                    showCourseDetails();
                    break;
                case "4":
                    // Kursdetails ändern
                    updateCourseDetails();
                    break;
                case "5":
                    // Kurs löschen
                    deleteCourse();
                    break;
                case "x":
                    System.out.println("Aufwiedersehen!");
                    break;
                default:
                    inputError();
                    break;
            }
        }
        scan.close();
    }

    private void deleteCourse() {
        System.out.println("Welchen Kurs möchten Sie löschen? Bitte ID eingeben: ");
        Long courseIdToDelete = Long.parseLong(scan.nextLine());

        try {
            repo.deleteById(courseIdToDelete);
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Löschen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Löschen: " + exception.getMessage());
        }
    }

    private void updateCourseDetails() {
        System.out.println("Für welche Kurs-ID möchten Sie die Kursdetails ändern?");
        Long courseId = Long.parseLong(scan.nextLine());

        try {
            Optional<Course> courseOptional = repo.getById(courseId);
            if (courseOptional.isEmpty()) {
                System.out.println("Kurs mit der gegebenen ID nicht vorhanden!");
            } else {
                Course course = courseOptional.get();
                System.out.println("Änderung für folgenden Kurs: ");
                System.out.println(course);

                String name, description, hours, dateFrom, dateTo, courseTyp;
                //hier kommt userinput Validierung

                System.out.println("Bitte neue Kursdaten angeben (Enter, falls keine Änderungen gewünscht ist): ");
                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("Beschreibung: ");
                description = scan.nextLine();
                System.out.println("Stundenanzahl: ");
                hours = scan.nextLine();
                System.out.println("Startdatum (YYYY-MM-DD): ");
                dateFrom = scan.nextLine();
                System.out.println("Enddatum (YYYY-MM-DD): ");
                dateTo = scan.nextLine();
                System.out.println("Kurstyp (OE/BF/ZA/FF): ");
                courseTyp = scan.nextLine();

                Optional<Course> optionalCourseUpdated = repo.update(
                        new Course(
                                course.getId(),
                                name.equals("") ? course.getName() : name,
                                description.equals("") ? course.getDescription() : description,
                                hours.equals("") ? course.getHours() : Integer.parseInt(hours),
                                dateFrom.equals("") ? course.getBeginDate() : Date.valueOf(dateFrom),
                                dateTo.equals("") ? course.getEndDate() : Date.valueOf(dateTo),
                                courseTyp.equals("") ? course.getCourseTyp() : CourseTyp.valueOf(courseTyp)
                        )
                );

                optionalCourseUpdated.ifPresentOrElse(
                        (c) -> System.out.println("Kurs aktualisiert: " + c),
                        () -> System.out.println("Kurs konnte nicht aktualisiert werden!")
                );
            }

        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler:  " + illegalArgumentException.getMessage());
        } catch (InvalidValueExeption invalidValueExeption) {
            System.out.println("Kursdaten nicht korrekt angegeben: " + invalidValueExeption.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void addCourse() {
        String name, description;
        int hours;
        Date dateFrom, dateTo;
        CourseTyp courseTyp;

        try {
            System.out.println("Bitte alle Kursdaten angeben: ");
            System.out.println("Name: ");
            name = scan.nextLine();
            if (name.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Beschreibung: ");
            description = scan.nextLine();
            if (description.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Stundenanzahl: ");
            hours = Integer.parseInt(scan.nextLine());
            System.out.println("Startdatum (YYYY-MM-DD): ");
            dateFrom = Date.valueOf(scan.nextLine());
            System.out.println("Enddatum (YYYY-MM-DD): ");
            dateTo = Date.valueOf(scan.nextLine());
            System.out.println("Kurstyp (OE/BF/ZA/FF): ");
            courseTyp = CourseTyp.valueOf(scan.nextLine());

            Optional<Course> optionalCourse = repo.insert(
                    new Course(name, description, hours, dateFrom, dateTo, courseTyp)
            );

            if (optionalCourse.isPresent()) {
                System.out.println("Kurs angelegt: " + optionalCourse.get());
            } else {
                System.out.println("Kurs konnte nicht angelegt werden!");
            }

        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler:  " + illegalArgumentException.getMessage());
        } catch (InvalidValueExeption invalidValueExeption) {
            System.out.println("Kursdaten nicht korrekt angegeben: " + invalidValueExeption.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void showCourseDetails() {
        System.out.println("Für welchen Kurs möchten Sie die Kursdetails anzeigen?");
        Long courseId = Long.parseLong(scan.nextLine());

        try {
            Optional<Course> courseOptional = repo.getById(courseId);
            if (courseOptional.isPresent()) {
                System.out.println(courseOptional.get());

            } else {
                System.out.println("Kurs mit der ID " + courseId + " nicht gefunden");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Kurs-Detailanzeige: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Kurs-Detailanzeige: " + exception.getMessage());
        }
    }

    private void showAllCourses() {
        List<Course> list = null;

        try {
            list = repo.getAll();
            if (list.size() > 0) {
                for (Course course : list) {
                    System.out.println(course); // toString-Methode wird aufgerufen
                }
            } else {
                System.out.println("Kursliste ist leer!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Anzeige aller Kurse: " + databaseException.getMessage());
        } catch (Exception exception) { // alle anderen Exceptions
            System.out.println("Unbekannter Fehler bei Anzeigen aller Kurse " + exception.getMessage());
        }
    }

    private void inputError() {
        System.out.println("Bitte dem Menü folgen und richtige Zahlen eingeben");
    }

    private void showMenu() {
        System.out.println("-------------- KURSMANAGEMENT ---------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t (3) Kursdetails anzeigen");
        System.out.println("(4) Kursdetails ändern \t (5) Kurs löschen\t () xxx");
        System.out.println("(x) ENDE");
    }
}
