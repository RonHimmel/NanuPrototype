/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nanuv01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author rajpr
 */
public class Authentication {
    
    public boolean loginUser(String username, String password){
        String sqlquery = "SELECT password FROM users WHERE username = ?";
        try(Connection conn = DatabaseConnector.getConnection()){
            PreparedStatement pst = conn.prepareStatement(sqlquery);
         
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                String storedHash = rs.getString("password");
                if(BCrypt.checkpw(password, storedHash)){
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean registerUser(String username, String email, String password){
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());        
        String sqlquery = "INSERT INTO users (username, password, email, score) VALUES (?, ?, ?, 0)";
        
        try(Connection conn = DatabaseConnector.getConnection()){
            PreparedStatement pst = conn.prepareStatement(sqlquery);
            
            pst.setString(1, username);
            pst.setString(2, hashedPassword);
            pst.setString(3, email);
            
            int affectedRows = pst.executeUpdate();
            
            if(affectedRows > 0){
                return true;
            }else {
                return false;
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    public boolean resetPassword(String username, String newPassword){
        
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        
        String sqlquery = "UPDATE users SET password = ? WHERE username = ?";
        try(Connection conn = DatabaseConnector.getConnection()){
          PreparedStatement pst = conn.prepareStatement(sqlquery);
            
          pst.setString(1, username);
          pst.setString(2, hashedPassword); 
          
          int affectedRows = pst.executeUpdate();
          
          if(affectedRows > 0){
              return true;
          }else{
              return false;
          }
          
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    
}
