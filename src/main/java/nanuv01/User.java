package nanuv01;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rajpreetsingh
 */
public class User {
    
    
    // Update Users Score
    public boolean updateScore(int score, String username){
        String sqlquery = "UPDATE users SET score=score + ? WHERE username=?";
        try(Connection conn = DatabaseConnector.getConnection()){
            PreparedStatement pst = conn.prepareStatement(sqlquery);
            
            pst.setInt(1, score);
            pst.setString(2, username);
            
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
    
}
