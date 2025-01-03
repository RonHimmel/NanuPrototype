/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nanuv01;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.SQLException;

/**
 *
 * @author rajpreetsingh
 */
public class DatabaseConnector {
    
    private static String url;
    private static String user;
    private static String password;
    
    // abstract function to connect to the specific DB
    static {
        try {
            Properties props = new Properties(); 
            InputStream is = DatabaseConnector.class.getClassLoader().getResourceAsStream("db.properties");
            props.load(is);
            
            // specifices to connect to DB by using Java Property File
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            
            // use Driver which acts as a Interface for the Project and Driver
            Class.forName("org.postgresql.Driver");
            
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database connection: ", e);
        }
    }
    
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
