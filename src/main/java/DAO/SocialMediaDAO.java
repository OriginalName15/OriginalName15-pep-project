package DAO;

import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import Model.Account;

public class SocialMediaDAO {
    

    public Account registerUser(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "insert into Account (username, password) values (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
