package at.itKolleg;

import java.sql.*;

public class JdbcDemo {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        //INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, 'Armin Hamzic', 'armin.ha@tsn.at'), (NULL, 'Hans Wurst', 'hans.99@tsn.at');
        selectAllDemo();
        insertStudentDemo();
        selectAllDemo();
    }

    public static void insertStudentDemo(){
        System.out.println("Insert DEMO mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo"; //Verbindungs-Url
        String user = "root";
        String pwd = "";

        // Verbindung zur DB aufbauen
        try(Connection conn = DriverManager.getConnection(connectionUrl,user,pwd)){
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, ?, ?)");

            try{
                preparedStatement.setString(1,"Pap Azt");
                preparedStatement.setString(2,"pap@hotmail.com");
                int rowAffected = preparedStatement.executeUpdate(); //wie viele Datensätze wurden verändert
                System.out.println(rowAffected + " Datensätze eingefügt");
            }catch(SQLException ex){
                System.out.println("Fehler im SQL-INSERT Statement" + ex.getMessage());
            }

        }catch(SQLException e){
            System.out.println("Fehler beim Aufbau einer Verbindung.. " + e.getMessage());
        }
    }

    public static void selectAllDemo(){
        System.out.println("Select DEMO mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo"; //Verbindungs-Url
        String user = "root";
        String pwd = "";
        // Verbindung zur DB aufbauen
        try(Connection conn = DriverManager.getConnection(connectionUrl,user,pwd)){
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `student`"); //SQL-Statement
            ResultSet rs = preparedStatement.executeQuery(); //rs = Ergebnismenge; executeQuery()-> Ausführung der Abfrage

            while (rs.next()){ //wenn es einen nächsten Datensatz gibt befinden wir uns in der Schleife
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [ID] " + id + " [NAME] " + name + " [EMAIL] " + email);
            }

        }catch(SQLException e){
            System.out.println("Fehler beim Aufbau einer Verbindung.. " + e.getMessage());
        }
    }
}