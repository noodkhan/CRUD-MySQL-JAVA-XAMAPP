package Services;

import java.util.ArrayList;

/**
 * DatabaseInterface
 */
public interface DatabaseInterface {
    public ArrayList<String[]>display(); 
    public void create(String name) ; 
    public String read(int id) ; 
    public void update(int id , String updated_Name) ; 
    public void delete(int id) ; 
}