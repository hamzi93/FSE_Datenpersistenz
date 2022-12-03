package at.itKolleg.ui;

import at.itKolleg.dataaccess.DatabaseException;
import at.itKolleg.dataaccess.MyCourseRepository;
import at.itKolleg.domain.Course;

import java.util.List;
import java.util.Scanner;

public class Cli {

    Scanner scan;
    MyCourseRepository repo;

    public Cli(MyCourseRepository repo){
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start(){
        String input = "-";
        while(!input.equals("x")){
            showMenu();
            input = scan.nextLine();
            switch (input){
                case "1":
                    System.out.println("Kurseingabe");
                    break;
                case "2":
                    //Alle Kurse anzigen
                    showAllCourses();
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
        } catch (DatabaseException databaseException){
            System.out.println("Datenbankfehler bei Anzeige aller Kurse: " + databaseException.getMessage());
        } catch (Exception exception){ // alle anderen Exceptions
            System.out.println("Unbekannter Fehler bei Anzeigen aller Kurse " + exception.getMessage());
        }
    }

    private void inputError() {
        System.out.println("Bitte dem Menü folgen und richtige Zahlen eingeben");
    }

    private void showMenu() {
        System.out.println("-------------- KURSMANAGEMENT ---------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t");
        System.out.println("(x) ENDE");
    }
}
