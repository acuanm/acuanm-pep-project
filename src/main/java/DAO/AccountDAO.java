package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO Account(username, password) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
            

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null; 

    }

    public boolean checkUserExists(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
            

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false; 

    }

    public boolean checkUserExists(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE account_id= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false; 

    }

    public Account checkUserVerified(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next() == false){
                return null;
            } else {
                do {
                    return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                } while(rs.next());
              }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    
}
