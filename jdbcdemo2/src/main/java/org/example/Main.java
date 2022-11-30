package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        insertKursDemo();
    }

    public static void insertKursDemo(){
        String connUrl = "jdbc:mysql://localhost:3306/jdbcdemo"; // Verbindungs-URL
        String user = "root";
        String pwd = "";

        try(Connection conn = DriverManager.getConnection(connUrl,user,pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `kurs` (`id`, `name`) VALUES (NULL, ?)");

            try{
                preparedStatement.setString(1,"Deutsch");
                int rowAffected = preparedStatement.executeUpdate(); //wie viele Datensätze wurden verändert
                System.out.println(rowAffected + " Datensätze eingefügt");
            }catch(SQLException ex){
                System.out.println("Fehler im SQL-INSERT Statement" + ex.getMessage());
            }

        }catch (SQLException e){
            System.out.println("Fehler beim Aufbau einer Verbindung.. " + e.getMessage());
        }
    }


}
