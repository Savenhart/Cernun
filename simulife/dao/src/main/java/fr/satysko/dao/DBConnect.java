package fr.satysko.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {
    private static final String jdbc = "org.mariadb.jdbc.Driver";
    private static Properties props =  new Properties();
    private static Connection conn = null;

    public static Connection getInstance() throws ClassNotFoundException{

        if (conn == null) {
            try{
                props.load(new FileInputStream(new File("app.properties")));
                String url = props.getProperty("url");
                String user = props.getProperty("user");
                String password = props.getProperty("pass");
                // Au moment de son utilisation, on va charger le driver
                Class.forName(jdbc);
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

}
