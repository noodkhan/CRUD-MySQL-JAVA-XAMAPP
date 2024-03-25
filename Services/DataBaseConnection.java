package Services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database
 */

public class DataBaseConnection{
   public Connection getConnection(){
        Connection connection = null ; 
        try { 
            // database Name , username , password
            Class.forName("com.mysql.jdbc.Driver"); 
            connection = DriverManager.getConnection("jdbc:mysql://localhost/db", "root", "");
            System.out.println(" DataBase Connection Successful Login to Server . . .");
        } 
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
            System.out.println(" DataBase Connection Failed Expected Handshake From Server . . .");
        } 
        return connection ; 
   } 
}