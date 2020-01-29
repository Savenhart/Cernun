package fr.satysko.dao;

import java.sql.Connection;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Connection conn;

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        try {
            conn = DBConnect.getInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
