package DAO;

import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
public class SocialMediaDAO {
    

    public boolean isUsernameTaken(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If resultSet has any records, the username is already taken
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
        }

    // Register a new user
    public Account registerUser(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generatedAccountId = pkeyResultSet.getInt(1);
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Login user
    public boolean loginUser(String userName, String password){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Get all messages
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql ="SELECT * from Message";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Get message from ID
    public Message getMessageByID(Integer message_id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = null;
        try{
            String sql = "SELECT * from Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            } 
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    ////get all messages from userID
    public List<Message> getAllMessagesByUserID(Integer user_ID){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_ID);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }  

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }
}
