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

![entwicklungsumgebung](images/entwicklungsumgebung.png)



## JDBC-Übungszettel

Im weiteren Verlauf verlinke ich alle fertiggestellten Aufgaben. Über die Verlinkungen kommt man in den richtigen Ordner und dort befinden sich README Dateien. In diesen README Dateien befinden sich Dokumentationen mit eigenen Erklärungen und die dazugehörigen Versuchsprotokolle zum verfassten Quellcode.

[Der Übungszettel](JDBC_Uebungszettel_V3-3.pdf)

[Aufgabe 1: JDBC Intro Teil 1](jdbcdemo)

[Aufgabe 2: JDBC Intro Teil 2]()

