package at.itKolleg;

import at.itKolleg.dataaccess.MySqlCourseRepository;
import at.itKolleg.dataaccess.MySqlDatabaseConnection;
import at.itKolleg.dataaccess.MySqlStudentRepository;
import at.itKolleg.ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        // CLI/UI

        try {
            Cli myCli = new Cli(new MySqlCourseRepository(), new MySqlStudentRepository());
            myCli.studentStart();
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage() + " SQL State: " + e.getSQLState());
        } catch (ClassNotFoundException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }


        // Datenbankverbindung über das Singleton
        try {
            Connection myConnection =
                    MySqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "");
            System.out.println("Verbindung wurde hergestellt!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}