# Datenpersistenz

In der Programmierung bezeichnet Persistenz die Lebensdauer einer Variable. Die Datenpersistenz ist die dauerhafte Aufbewahrung von Daten in einem Datenbank-Managementsystem (DBMS). Die Lebensdauer von Daten muss von den Benutzern direkt oder indirekt bestimmbar sein und darf nicht von irgendwelchen Systemgegebenheiten abhängen. Weiters sind Änderungen, die eine Transaktion in einer Datenbank vornimmt, dauerhaft. [Quelle: gitta.info](http://www.gitta.info/IntroToDBS/de/html/DBApproaChar_datapersis.html)

## JDBC

Java Database Connectivity erlaubt es über Java auf Datenbanken zuzugreifen.

Die Entwicklungsumgebung beinhaltet einen Webserver und einen Datenbankserver mit Admin-Konsole. Dies ist mit XAMPP oder Docker möglich. Schritte zum einrichten der Umgebung mittels XAMPP:

1. Download von XAMPP

2. Apache Webserver und die Mysql-Datenbank starten

3. Neues Maven Projekt anlegen

4. MySql-Connector-Java Dependency in die pom.xml Datei hinzufügen, diese findet man unter [Maven Repository: mysql-connector-java](https://mvnrepository.com/artifact/mysql/mysql-connector-java)

5. Eine Datenbank inklusive Tabelle und Spalten über phpmyadmin (localhost/phpmyadmin) erstellen
     * 5.1 Gegebenenfalls erstellt man noch eine Main Klasse mit einer Main-Methode
     * 5.2 Man kann weiters Metadaten in die gerade erstellte Tabelle einfügen (name, email), um Versuchsdaten zu erstellen

## JDBC Demo-Projekt

Aufgrund der durchgeführten Einrichtung einer Entwicklungsumgebung ist es nun möglich über das Java Programm, mittels SQL-Statements, mit einer Datenbank zu kommunizieren. 

```java
public static void selectAllDemo(){
        String sqlSelectAllPersons = "SELECT * FROM `student`"; //select-statement
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo"; //Verbindungs-Url
    
        // Verbindung zur DB aufbauen
        try(Connection conn = DriverManager.getConnection(connectionUrl,"root","")){
            System.out.println("Verbindung zur DB hergestellt!");
        }catch(SQLException e){
            System.out.println("Fehler beim Aufbau einer Verbindung.. " +e.getMessage());
        }
    }
```

Man sollte wissen welche Datensätze man braucht und wo diese zu finden sind. Um bestimmte Datensätze anzusprechen, verwendet man SQL-Statements. Um weiter mit bestimmten Datensätzen zu arbeiten sollte man diese in Variablen speichern.

```java
PreparedStatement preparedStatement = conn.preparedStatement("SELECT * FROM `student`"); //SQL-Statement
ResultSet rs = preparedStatement.executeQuery(); //rs = Ergebnismenge; executeQuery()-> Ausführung der Abfrage

while (rs.next()){ //wenn es einen nächsten Datensatz gibt befinden wir uns in der Schleife
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [ID] " + id + " [NAME] " + name + " [EMAIL] " + email);
            }
```

PreparedStatement: 

* Speichert das SQL-Statement in einer Variable
* Sendet anschließend dieses Statement an die Datenbank mit der Methode *executeQuery()*

ResultSet: 

* Von der Abfrage ausgehend kommt ein ResultSet zurück
* Das ResultSet hält nun alle Datensätze 

