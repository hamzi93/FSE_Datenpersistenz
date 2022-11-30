package at.itKolleg.dataaccess;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabaseConnection {

    private static Connection connection = null;

    private MySqlDatabaseConnection(){ //ist nicht mehr mit new Aufrufbar

    }

    /**
     * @param url der Pfad zur MySql-Datenbank
     * @param user der username (zur Anmeldung)
     * @param pwd das Passwort des users
     * @return Connection gibt entweder eine bereits vorhandene oder
     * bei keiner vorhandenen eine neu gestzte Connetion zurück.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException {
        if(connection!=null){
            return connection;
        } else {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,pwd);
            return connection;
        }
    }
}
