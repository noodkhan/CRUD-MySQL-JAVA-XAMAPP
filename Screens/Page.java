package Screens;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import Services.DataBaseQuery;
import java.util.ArrayList;


public class Page {
    
    // Env Setting Screens Width | Height
    JFrame frame;
    GraphicsDevice graphics = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice() ; 
    int width = graphics.getDisplayMode().getWidth() ; 
    int height = graphics.getDisplayMode().getHeight() ;     

    public void createBuffer(String[][] data){
        // Create JTable
        String[] heading = {"id" , "name"} ; 
        JTable table = new JTable(data , heading);
        JScrollPane panel = new JScrollPane(table);
        frame.add(panel , BorderLayout.WEST) ; 
    }

    public void clearBuffer(JScrollPane buffer){
        frame.remove(buffer);
    }

    // Create Data for -> table ---> Data
    public String[][] get(){
        ArrayList<String[]> response = new DataBaseQuery().display();
        int size = response.size();
        String[][] data = new String[size][];
        for(int i = 0 ; i < size ; ++i){
            String[] array = new String[2];
            array[0] = response.get(i)[0] ; // id
            array[1] = response.get(i)[1] ; // name
            data[i] = array ; 
        }
        return data;
    }

    public Page(){
        // Create JFrame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CRUD System MySQL");

        // Create JTable
        String[][] data = get() ; 
        String[] heading = {"id" , "name"} ; 
        JTable table = new JTable(data , heading);
        JScrollPane buffer = new JScrollPane(table);
        frame.add(buffer , BorderLayout.WEST) ; 

        // GetData Function + Buffer -> Container
        JTabbedPane container = new JTabbedPane();
        JPanel getDataContainer = new JPanel();
        JLabel textData = new JLabel("Data");
        JButton buttonData = new JButton("GetData");
        buttonData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data = get() ; 
                clearBuffer(buffer) ; 
                createBuffer(data) ; 
            } 
        });
        getDataContainer.add(textData) ;  
        getDataContainer.add(buttonData); 

        // Read Function to -> Container
        JPanel readContainer = new JPanel();
        JLabel textRead = new JLabel("Enter ID");
        JTextField input_id_read = new JTextField(10);
        input_id_read.setText("Insert ID \t\t");
        JButton buttonRead = new JButton("Search") ; 
        JLabel caution = new JLabel("Refresh APP FOR DATA");
        caution.setForeground(Color.RED);
        buttonRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getRowCount() ; 
                int target = Integer.parseInt(input_id_read.getText()) ; 
                for(int i = 0 ; i < row ; ++i){
                    Object cellID = table.getValueAt(i, 0);
                    String strID = ""+cellID; 
                    int id = Integer.parseInt(strID); 
                    Object cellName = table.getValueAt(i, 1);
                    String name = ""+cellName;
                    if(id == target){
                        JPanel subContainer = new JPanel() ; 
                        JLabel nameText = new JLabel("Name");
                        JLabel idText = new JLabel("id");
                        JTextField readTextField = new JTextField(id + "\t\t");
                        JTextField idTextField = new JTextField(name + "\t\t") ;  
                        readTextField.setEditable(false);
                        idTextField.setEditable(false);
                       
                        subContainer.add(nameText) ; 
                        subContainer.add(readTextField) ; 
                        subContainer.add(idText) ; 
                        subContainer.add(idTextField); 
                        readContainer.add(subContainer) ; 
                    }
                }
            }
        });
        readContainer.add(textRead) ; 
        readContainer.add(input_id_read) ; 
        readContainer.add(buttonRead) ; 
        readContainer.add(caution);

        // Create Function to -> Container
        JPanel createContainer = new JPanel(); 
        JLabel textCreate = new JLabel("Enter Name");
        JTextField input_name_create = new JTextField(10);
        input_name_create.setText("Insert Name \t\t");
        JButton buttonCreate = new JButton("Create") ; 
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = input_name_create.getText() ;                 
                new DataBaseQuery().create(input);
            }
        });
        createContainer.add(textCreate) ; 
        createContainer.add(input_name_create) ;  
        createContainer.add(buttonCreate) ; 

        // Delete Function to -> Container
        JPanel deleteContainer = new JPanel();
        JLabel textDelete = new JLabel("Enter ID");

        JTextField input_id_delete = new JTextField(10);
        input_id_delete.setText("Insert Name \t\t");

        JButton buttonDelete = new JButton("Delete") ; 
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = input_id_delete.getText();
                int id = Integer.parseInt(input) ; 
                new DataBaseQuery().delete(id); 
            }
        });
        deleteContainer.add(textDelete);  
        deleteContainer.add(input_id_delete) ; 
        deleteContainer.add(buttonDelete)  ; 

        // Update Function to -> Container
        JPanel updateContainer = new JPanel();
        JLabel textUpdate = new JLabel("Enter ID & Name");

        JTextField input_id_update = new JTextField(10);
        input_id_update.setText("Insert ID\t");

        JTextField input_name_update = new JTextField(10);
        input_name_update.setText("Insert name\t");

        JButton buttonUpdate = new JButton("Update") ; 
        buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputID = input_id_update.getText();
                String name = input_name_update.getText(); 
                int id = Integer.parseInt(inputID) ; 
                new DataBaseQuery().update(id, name);
            }
        });
        updateContainer.add(textUpdate) ; 
        updateContainer.add(input_id_update) ;
        updateContainer.add(input_name_update) ;  
        updateContainer.add(buttonUpdate) ; 

        // build Container For Multiple Pages
        container.add("DATA" ,  getDataContainer);
        container.add("READ" ,     readContainer); 
        container.add("CREATE" , createContainer); 
        container.add("DELETE" , deleteContainer); 
        container.add("UPDATE" , updateContainer); 

        // add Container to Frame
        frame.add(container , BorderLayout.CENTER) ; 
 
        // Rest of the frame Setting 
        frame.setSize(width / 2, height / 2);
        frame.setVisible(true); 
   }
}
