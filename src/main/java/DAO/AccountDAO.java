package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "select * from Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                accounts.add(account);
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return accounts;
    }

    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        if(account.getUsername() == "" || account.getPassword().length() < 4  || usernameExists(account)) {
            return null;
        }
        try {
            String sql = "insert into Account (username, password) VALUES ( ? , ? )";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                return new Account(rs.getInt(1),account.getUsername(),account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean usernameExists(Account account){
        Connection connection = ConnectionUtil.getConnection();
        if (account == null) {
            return false;
        }
        try {
            String sql = "select username from Account where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){return true;}            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public Account getAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from Account where username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account foundAccount = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                return foundAccount;                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    public Account getAccount(int ID){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from Account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account foundAccount = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                return getAccount(foundAccount);                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }
}
