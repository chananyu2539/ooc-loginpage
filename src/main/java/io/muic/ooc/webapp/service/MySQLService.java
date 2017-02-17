package io.muic.ooc.webapp.service;

import com.ja.security.PasswordHash;

import java.sql.*;
import java.util.HashMap;

public class MySQLService {

    enum TestTableColumns {
        id,username,password,name;
    }

    private final String jdbcDriverStr;
    private final String jdbcURL;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public MySQLService(String jdbcDriverStr, String jdbcURL) {
        this.jdbcDriverStr = jdbcDriverStr;
        this.jdbcURL = jdbcURL;
    }

    public void deleteData(String id) throws Exception{
        connection = DriverManager.getConnection(jdbcURL + "?useSSL=false", "root", "12345");
        preparedStatement = connection.prepareStatement("DELETE FROM test.test_table where id = '"+id+"'");

        preparedStatement.executeUpdate();
    }

    public void updateData(String id,String username,String name)throws Exception{
        connection = DriverManager.getConnection(jdbcURL + "?useSSL=false", "root", "12345");
        preparedStatement = connection.prepareStatement("update test.test_table SET username = ?, name = ? where id = '"+id+"'");
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,name);
        preparedStatement.executeUpdate();
    }


    public void writeData(String username, String password, String name) throws Exception{
//        System.out.println(password.length());
        connection = DriverManager.getConnection(jdbcURL + "?useSSL=false", "root", "12345");
        preparedStatement = connection.prepareStatement("insert into test.test_table values (default,?,?,?)");
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,name);
        preparedStatement.executeUpdate();

    }

    public String getFirstName(String username){
        String result = "";
        try {
            Class.forName(jdbcDriverStr);
//            connection = DriverManager.getConnection(jdbcURL);
            connection = DriverManager.getConnection(jdbcURL + "?useSSL=false", "root", "12345");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from test.test_table;");
            while (resultSet.next()) {
                String usr = resultSet.getString(TestTableColumns.username.toString());
                if(usr.equals(username)){
                    result = resultSet.getString(TestTableColumns.name.toString());
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
            return result;
        }
    }

    public HashMap<String,String> getIdUsr()throws Exception{
        HashMap<String,String> result = new HashMap<>();
        try {
            Class.forName(jdbcDriverStr);
//            connection = DriverManager.getConnection(jdbcURL);
            connection = DriverManager.getConnection(jdbcURL + "?useSSL=false", "root", "12345");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from test.test_table;");
            while (resultSet.next()) {
                String id = resultSet.getString(TestTableColumns.id.toString());
                String usr = resultSet.getString(TestTableColumns.username.toString());
                result.put(id,usr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
            return result;
        }
    }

    public HashMap<String,String> getUsrPass() throws Exception {
        HashMap<String,String> result = new HashMap<>();
        try {
            Class.forName(jdbcDriverStr);
//            connection = DriverManager.getConnection(jdbcURL);
            connection = DriverManager.getConnection(jdbcURL + "?useSSL=false", "root", "12345");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from test.test_table;");
            result = getResultSet(resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            close();
            return result;
        }
    }

    private HashMap<String,String> getResultSet(ResultSet resultSet) throws Exception {
        HashMap<String,String> result = new HashMap<>();
        while (resultSet.next()) {
            String usr = resultSet.getString(TestTableColumns.username.toString());
            String pass = resultSet.getString(TestTableColumns.password.toString());
            result.put(usr,pass);
        }
        return result;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
        }
    }

    public boolean isValidInput(String[] input)throws Exception{
        HashMap<String,String> currentDB = getUsrPass();
        if(currentDB.keySet().contains(input[0])){
            return new PasswordHash().validatePassword(input[1],currentDB.get(input[0]));
        }
        return false;

    }
}
