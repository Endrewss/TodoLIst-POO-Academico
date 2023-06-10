package web;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.Date;
import java.sql.*;
import model.User;

@WebListener 
public class Listener implements ServletContextListener{
    public static final String CLASS_NAME = "org.sqlite.JDBC";
    public static final String URL = "jdbc:sqlite:parkapp.db";
    public static String InitializeLog = "";
    public static Exception exception = null;
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            Connection c = Listener.getConnection();
            Statement s = c.createStatement();
            InitializeLog += new Date() + ": Initializing database creation; ";
            InitializeLog += ": Creating User table if not exists...; ";
            s.execute(User.getCreateStatement());
            
            if(User.getUsers().isEmpty()) {
                InitializeLog +="Adding default users...";
                User.insertUser("admin", "Administrator", "ADMIN", "1234");
                InitializeLog += "Admin added;";
                User.insertUser("Boris", "Boris Roberto", "USER", "1234");
                InitializeLog += "Boris added;";
            }            
            
            InitializeLog += "done; ";
            
            // para criação de outra tabela utilize o codigo comentado abaixo
            //InitializeLog += ": Create(...); "; essa linha irá realizar uma criação de tabela, isto é, ela precisa estar na classe da linha abaixo
            // colocar s.execute( "nome da classe".getCreateStatement()); 
            // InitializeLog += "done; ";
            s.close();
            c.close();
        }catch(Exception ex) {
            InitializeLog += "Erro:" + ex.getMessage();
            exception = ex;
        }
    }
    
    public static String getMd5Hash(String text) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(text.getBytes(), 0, text.length());
        return new BigInteger(1, m.digest()).toString();
    }
    
    public static Connection getConnection() throws Exception {
        Class.forName(CLASS_NAME);
        return DriverManager.getConnection(URL);
    }
    
}