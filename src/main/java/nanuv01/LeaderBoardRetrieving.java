/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nanuv01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rajpr
 */
public class LeaderBoardRetrieving {
   
    public List<Map<String, Object>> retrieveUsersScore() {
        String sqlquery = "SELECT username, score FROM users ORDER BY score DESC";
        List<Map<String, Object>> userScores = new ArrayList<>();
        
        try(Connection conn = DatabaseConnector.getConnection()) {
           PreparedStatement pst = conn.prepareStatement(sqlquery);
           
           ResultSet rs = pst.executeQuery();
           
           while(rs.next()) {
               Map<String,Object> userScore = new HashMap<>();
               userScore.put("username", rs.getString("username"));
               userScore.put("score", rs.getInt("score"));
               userScores.add(userScore);
           }
           
        }catch(SQLException e) {
            e.printStackTrace();
        }
       
       return userScores;
    }
    
}
