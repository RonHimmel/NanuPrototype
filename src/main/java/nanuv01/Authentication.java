/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nanuv01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author rajpr
 */
public class Authentication {
    
    private FormValidation formValidator = new FormValidation();
    private boolean checkUsername; 
    private boolean checkEmail;
    private boolean checkPassword;
    
    // login user retreiving from DB
    public boolean loginUser(String username, String password) {
        String sqlquery = "SELECT password FROM users WHERE username = ?";
        
        // checking password if in right format
        checkPassword = formValidator.checkPasswordCorrectness(password);
        
        if(checkPassword) {
            try(Connection conn = DatabaseConnector.getConnection()) {
                PreparedStatement pst = conn.prepareStatement(sqlquery);
         
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
            
                if(rs.next()) {
                    String storedHash = rs.getString("password");
                    // Hash Algorithm to check password correctness
                    if(BCrypt.checkpw(password, storedHash)){
                        return true;
                    }
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Password should have at least 6 characters and should consist of a Number and Text.\n");
        }
        
        return false;
    }
    
    // Register a new User by inserting it into DB
    public boolean registerUser(String username, String email, String password, Date birthday) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());        
        String sqlquery = "INSERT INTO users (username, password, email, birthday, score) VALUES (?, ?, ?, ?, 0)";
        
        // formvalidation for each input
        checkUsername = formValidator.checkUsernameExistence(username);
        checkEmail = formValidator.checkEmailCorrectness(email);
        checkPassword = formValidator.checkPasswordCorrectness(password);
       
        if(!checkUsername && checkEmail && checkPassword) {
            try(Connection conn = DatabaseConnector.getConnection()) {
                PreparedStatement pst = conn.prepareStatement(sqlquery);
            
                pst.setString(1, username);
                pst.setString(2, hashedPassword);
                pst.setString(3, email);
                pst.setDate(4, birthday);
            
                int affectedRows = pst.executeUpdate();
            
                if(affectedRows > 0) {
                    return true;
                }else {
                    return false;
                }
            
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }else {
            StringBuilder message = new StringBuilder();
            
            //Error Handeling
            if(checkUsername) {
                message.append("Username exists.\n");
            }
            
            if(!checkEmail) {
                message.append("The Email is not valid.\n");
            }
            
            if(!checkPassword) {
                message.append("Password should have at least 6 characters and should consist of a Number and Text.\n");
            }

            JOptionPane.showMessageDialog(null, message.toString());
        }
        
        return false;
    }
    
    // reset password of User and update in DB
    public boolean resetPassword(String username, String newPassword) {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        String sqlquery = "UPDATE users SET password = ? WHERE username = ?";
        
        checkPassword = formValidator.checkPasswordCorrectness(newPassword);
 
        if(checkPassword) {
        try(Connection conn = DatabaseConnector.getConnection()){
          PreparedStatement pst = conn.prepareStatement(sqlquery);
            
          pst.setString(1, hashedPassword);
          pst.setString(2, username); 
          
          int affectedRows = pst.executeUpdate();
          
          if(affectedRows > 0){
              return true;
          }else{
              return false;
          }
          
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        }else {
            JOptionPane.showMessageDialog(null, "Password should have at least 6 characters and should consist of a Number and Text.\n");
        }
        
        return false;
    }
    
    
}
