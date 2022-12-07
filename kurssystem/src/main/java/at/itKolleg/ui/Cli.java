package at.itKolleg.ui;

import at.itKolleg.dataaccess.DatabaseException;
import at.itKolleg.dataaccess.MyCourseRepository;
import at.itKolleg.dataaccess.MySqlStudentRepository;
import at.itKolleg.domain.Course;
import at.itKolleg.domain.CourseTyp;
import at.itKolleg.domain.InvalidValueExeption;
import at.itKolleg.domain.Student;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.Date;

public class Cli {

    Scanner scan;
    MyCourseRepository repo;
    MySqlStudentRepository studentRepo;

    public Cli(MyCourseRepository repo, MySqlStudentRepository studentRepo) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
        this.studentRepo = studentRepo;
    }

    public void studentStart() {
        String input = "-";
        while (!input.equals("x") && !input.equals("k")) {
            showStudentMenu();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    //Alle Studenten anzeigen
                    showAllStudents();
                    break;
                case "2":
                    //Studenten hinzufügen
                    addStudent();
                    break;
                case "3":
                    // Studentendetails ausgeben
                    showStudentDetails();
                    break;
                case "4":
                    // Studentendetails ändern
                    updateStudentDetails();
                    break;
                case "5":
                    // Student löschen
                    deleteStudent();
                    break;
                case "6":
                    // Student suchen (name)
                    studentSearchByName();
                    break;
                case "7":
                    // Student suchen (Geburtsjahr)

                    break;
                case "k":
                    // Student suchen (Geburtsjahr)
                    kursStart();
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

    public void kursStart() {
        String input = "-";
        while (!input.equals("x") && !input.equals("s")) {
            showCourseMenu();
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
                case "6":
                    // Kurs suche
                    courseSearch();
                    break;
                case "7":
                    // laufende Kurse
                    runningCourses();
                    break;
                case "s":
                    // auf studentmanagment switchen
                    studentStart();
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

    private void studentSearchByName(){
        System.out.println("Geben SIe einen Suchbegriff (Vor- oder Nachname) an: ");
        String searchString = scan.nextLine();
        List<Student> studentsList;
        try {
            studentsList = studentRepo.findAllStudentsByFirstOrLastName(searchString);
            for (Student student : studentsList) {
                System.out.println(student);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Studentensuch: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Studentensuche: " + exception.getMessage());
        }
    }

    private void deleteStudent() {
        System.out.println("Welchen Studenten möchten Sie löschen? Bitte ID eingeben: ");
        Long studentIdToDelete = Long.parseLong(scan.nextLine());

        try {
            studentRepo.deleteById(studentIdToDelete);
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Löschen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Löschen: " + exception.getMessage());
        }
    }

    private void updateStudentDetails() {
        System.out.println("Für welche Studenten-ID möchten Sie die Studentendetails ändern?");
        Long studentId = Long.parseLong(scan.nextLine());

        try {
            Optional<Student> studentOptional = studentRepo.getById(studentId);
            if (studentOptional.isEmpty()) {
                System.out.println("Student mit der gegebenen ID nicht vorhanden!");
            } else {
                Student student = studentOptional.get();
                System.out.println("Änderung für folgenden Kurs: ");
                System.out.println(student);

                String firstname, lastname, birthdate;
                //hier kommt userinput Validierung

                System.out.println("Bitte neue Studentendaten angeben (Enter, falls keine Änderungen gewünscht ist): ");
                System.out.println("Vorname: ");
                firstname = scan.nextLine();
                System.out.println("Nachname: ");
                lastname = scan.nextLine();
                System.out.println("Geburtstag (YYYY-MM-DD): ");
                birthdate = scan.nextLine();

                Optional<Student> optionalStudentUpdated = studentRepo.update(
                        new Student(
                                student.getId(),
                                firstname.equals("") ? student.getFirstName() : firstname,
                                lastname.equals("") ? student.getLastName() : lastname,
                                birthdate.equals("") ? student.getBirthDate() : Date.valueOf(birthdate)
                        )
                );

                optionalStudentUpdated.ifPresentOrElse(
                        (c) -> System.out.println("Student aktualisiert: " + c),
                        () -> System.out.println("Student konnte nicht aktualisiert werden!")
                );
            }

        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler:  " + illegalArgumentException.getMessage());
        } catch (InvalidValueExeption invalidValueExeption) {
            System.out.println("Studentendaten nicht korrekt angegeben: " + invalidValueExeption.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void showStudentDetails() {
        System.out.println("Für welchen Studenten möchten Sie die Studentendetails anzeigen?");
        Long courseId = Long.parseLong(scan.nextLine());

        try {
            Optional<Student> studentOptional = studentRepo.getById(courseId);
            if (studentOptional.isPresent()) {
                System.out.println(studentOptional.get());

            } else {
                System.out.println("Student mit der ID " + courseId + " nicht gefunden");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Student-Detailanzeige: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Student-Detailanzeige: " + exception.getMessage());
        }
    }

    private void addStudent() {
        String firstName, lastName;
        Date birthDate;

        try {
            System.out.println("Bitte alle Studentendaten angeben: ");
            System.out.println("Vorname: ");
            firstName = scan.nextLine();
            if (firstName.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Nachname: ");
            lastName = scan.nextLine();
            if (lastName.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Geburtstag (YYYY-MM-DD): ");
            birthDate = Date.valueOf(scan.nextLine());


            Optional<Student> optionalStudent = studentRepo.insert(
                    new Student(firstName, lastName, birthDate)
            );

            if (optionalStudent.isPresent()) {
                System.out.println("Student angelegt: " + optionalStudent.get());
            } else {
                System.out.println("Student konnte nicht angelegt werden!");
            }

        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler:  " + illegalArgumentException.getMessage());
        } catch (InvalidValueExeption invalidValueExeption) {
            System.out.println("Studentendaten nicht korrekt angegeben: " + invalidValueExeption.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void showAllStudents() {
        List<Student> list;

        try {
            list = studentRepo.getAll();
            if (list.size() > 0) {
                for (Student student : list) {
                    System.out.println(student); // toString-Methode wird aufgerufen
                }
            } else {
                System.out.println("Studentenliste ist leer!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Anzeige aller Studenten: " + databaseException.getMessage());
        } catch (Exception exception) { // alle anderen Exceptions
            System.out.println("Unbekannter Fehler bei Anzeigen aller Studenten " + exception.getMessage());
        }
    }

    private void runningCourses() {
        System.out.println("Aktuell laufende Kurse: ");
        List<Course> list;
        try {
            list = repo.findAllRunningCourses();
            for (Course course : list) {
                System.out.println(course);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Kurs-Anzeige für laufende Kurse: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Kurs-Anzeige bei laiufende Kurse " + exception.getMessage());
        }
    }

    private void courseSearch() {
        System.out.println("Geben SIe einen Suchbegriff an: ");
        String searchString = scan.nextLine();
        List<Course> courseList;
        try {
            courseList = repo.findAllCoursesByNameOrDescription(searchString);
            for (Course course : courseList) {
                System.out.println(course);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Kurssuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Kurssuche: " + exception.getMessage());
        }
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

    private void showCourseMenu() {
        System.out.println("-------------- KURSMANAGEMENT ---------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t (3) Kursdetails anzeigen");
        System.out.println("(4) Kursdetails ändern \t (5) Kurs löschen\t (6) Kurssuche");
        System.out.println("(7) Laufende Kurse \t (s) Studentenmanagement  \t (-) xxx");
        System.out.println("(x) ENDE");
    }

    private void showStudentMenu() {
        System.out.println("-------------- STUDENTENMANAGEMENT ---------------");
        System.out.println("(1) Alle Studenten anzeigen \t (2) Student einfügen \t (3) Studentendetails anzeigen");
        System.out.println("(4) Studentendetails ändern \t (5) Student löschen \t (6) Studentensuche (Name)");
        System.out.println("(7) Studentensuche (Geburtsjahr) \t (k) Kursmanagement \t (-) xxx");
        System.out.println("(x) ENDE");
    }
}
