# Datenpersistenz

JDBC -> Java Database Connectivity, über Java auf Datenbanken zugreifen

Entwicklungsumgebung -> Webserver, Datenbankserver mit Admin-Konsole.

Mit XAMPP oder mit Docker möglich. 

Download von XAMPP

apache webserver starten und die mysql-Datenbank. 

Neues Maven Projekt auswählen in Intellij. mysql-connector-java Dependency in die pom.xml Datei einfügen. Eine JdbcDemo Klasse mit main Methode anlegen (Fertig).

Über phpmyadmin (localhost/phpmyadmin) eine DB erstellen mit einer Tabelle (student). Spalten der Tabelle definieren mit id, name, email. Danach hinzufügen von Metadaten über phpmyadmin.

Können nun über unser Java Programm insert statements ausführen. 



