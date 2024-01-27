/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nanuv01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author rajpreetsingh
 */
public class FormValidation {
   
    public boolean checkUsernameExistence(String username){
    
        String sqlquery = "SELECT username FROM users WHERE username = ?";
        
        try(Connection conn = DatabaseConnector.getConnection()){
            PreparedStatement pst = conn.prepareStatement(sqlquery);
           
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
               return true; // Username Exists
            }else {
                return false; // Username dosen't exist
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean checkEmailCorrectness(String email){
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$";
        
        Pattern pattern = Pattern.compile(emailRegex);
        
        if(email == null){
            return false;
        }
        
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
    }
    
    
    public boolean checkPasswordCorrectness(String password){
         if(password != null && password.length() >= 6){
             String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
             
             Pattern pattern = Pattern.compile(passwordRegex);
             Matcher matcher = pattern.matcher(password);
             
             return matcher.matches();
         }
         
         return false;
    }
    
    
}
