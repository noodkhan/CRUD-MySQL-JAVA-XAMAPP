package Services;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DataBaseQuery implements DatabaseInterface{
    Connection connection = new DataBaseConnection().getConnection();
    public DataBaseQuery(){


    }

    @Override
    public ArrayList<String[]> display() {
        ArrayList<String[]> data = new ArrayList<>() ; 
        try { 
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from employee") ; 
            while(res.next()){
                String[] array = new String[2] ; 
                array[0] = res.getString(1) ; 
                array[1] = res.getString(2) ; 
                data.add(array) ; 
            }
            connection.close() ;  
            System.out.println("\t Display from Database Sucessful . . ");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;  
    }

    @Override
    public void create(String name) {
        try { 
            String sqlQuery = "INSERT INTO employee (name) VALUES (?)" ;
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, name);
            statement.executeUpdate() ; 
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Create Failed . . . ");
        }
    }

    @Override
    public String read(int id) {
        String strs = "" ; 
        try { 
            String sqlQuery = "SELECT * FROM employee WHERE id = ?"; 
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery() ; 
            while(res.next()){
                strs += res.getString(1) + "\f";
                strs += res.getString(2) + "\f" ; 
            }
            connection.close();
            System.out.println("\t Read From Database Sucessful . . ");
        } catch (SQLException e) {
            System.out.println("Read Failed . . . ");
            System.out.println(e);
        }
        return strs; 
    }

    @Override
    public void update(int id, String name) { 
        try { 
            String sqlQuery = "UPDATE employee SET name = ? WHERE id = ?"; 
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate() ; 
            connection.close();
            System.out.println("\t Update to Database Sucessful . . ");
        } catch (SQLException e) {
            System.out.println("Update Failed . . . ");
            System.out.println(e);
        }       
    }

    @Override
    public void delete(int id) {
        try { 
            String sqlQuery = "DELETE FROM employee WHERE id = ?"; 
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            statement.executeUpdate() ; 
            connection.close();
            System.out.println("\t Delete to Database Sucessful . . ");
        } catch (SQLException e) {
            System.out.println("Delete Failed . . . ");
            System.out.println(e);
        }
    }
}