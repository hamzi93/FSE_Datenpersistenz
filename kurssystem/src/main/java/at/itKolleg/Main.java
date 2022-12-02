package at.itKolleg;

import at.itKolleg.dataaccess.MySqlDatabaseConnection;
import at.itKolleg.ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // CLI/UI
        Cli myCli = new Cli();
        myCli.start();

        // Datenbankverbindung über das Singleton
        try {
            Connection myConnection =
                    MySqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem","root","");
            System.out.println("Verbindung wurde hergestellt!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}