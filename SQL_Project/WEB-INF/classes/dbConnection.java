/*
* Name: Cadilnis Chau
* Course: CNT4714
* Project 4 - Servlets
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Establish connection 
public class dbConnection
{
  private Connection myConnection = null;

  public dbConnection() {}

  public boolean isConnected()
  {
    if (myConnection == null)
    {
      return false;
    }
    try
    {
      return !myConnection.isClosed();
    }
    catch (SQLException localSQLException) {
    //left blank 
    }
    return false;
  }

  public void connect(String driver, String url, String user, String password) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
  {
      
    //Attempt Connection
    Class.forName(driver).newInstance();
    myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project4", "root", "root");
  }

  public ResultSet executeQuery(String command) throws SQLException
  {
    Statement statement = myConnection.createStatement(1004, 1007);
    return statement.executeQuery(command);
  }

  public int executeUpdate(String command) throws SQLException
  {
    Statement statement = myConnection.createStatement();
    int result = statement.executeUpdate(command);
    statement.close();

    return result;
  }
}