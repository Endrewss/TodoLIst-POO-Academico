package model;

import java.util.ArrayList;
import java.sql.*;

public class Task {
    public static final String CLASS_NAME = "org.sqlite.JDBC";
     public static final String URL = "jdbc:sqlite:listadeafazeres.db";
     
     public static Exception excessao = null;
     
     public static void createTable() {
         try{
             Connection conexao = getConnection();
             Statement status = conexao.createStatement();
             status.execute("create table if not exists tasks(title varchar not null)");
             status.close();
             conexao.close();
         }catch(Exception ex) {
             excessao = ex;
         }
     }
             
     public static Connection getConnection() throws Exception{
         return DriverManager.getConnection(URL);
     }
    
    public static ArrayList<Task> getList() throws Exception{
            ArrayList<Task> list = new ArrayList<>();
             Connection conexao = getConnection();
             Statement status = conexao.createStatement();
             ResultSet resultaset = status.executeQuery("select * from tasks");
             while (resultaset.next()){
                 list.add(new Task(resultaset.getString("title")));
             }
             resultaset.close();
             status.close();
             conexao.close();
             return list;
    }
    
    public static void addTask(String title) throws Exception{
        Connection conexao = getConnection();
        PreparedStatement status = conexao.prepareStatement("insert into tasks values(?)");
        status.setString(1, title);
        status.close();
        conexao.close();
    }
    
    public static void removeTask(String title) throws Exception{
        Connection conexao = getConnection();
        PreparedStatement status = conexao.prepareStatement("delete from tasks where title = ?");
        status.setString(1, title);
        status.close();
        conexao.close();
    }
        
    public static ArrayList<Task> list = new ArrayList<>();
    
    private String title;
    
    public Task() {
        this.setTitle("[NEW]");
    }

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}