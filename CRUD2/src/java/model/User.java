/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.sql.*;
import web.Listener;

/**
 *
 * @author Lucas Silva
 */
public class User {
    private long rowId;
    private String name;
    private String login;
    private String role; // verificar se é um atributo necessário
    private String passwordHash;
    
    // implementar mais requisitos na tabela caso o banco não se baseia no nosso modelo de negocio
    public static String getCreateStatement() { 
        return "CREATE TABLE IF NOT EXISTS users ("
                + "login VARCHAR(50) UNIQUE NOT NULL,"
                + "name VARCHAR(200) NOT NULL,"
                + "role VARCHAR(20) NOT NULL," // acho que esse atributo não será necessário no nosso projeto, favor verificar em seguida
                + "password_hash VARCHAR NOT NULL"
                + ")";
    }

    public static ArrayList<User> getUsers() throws Exception{
        ArrayList<User> list = new ArrayList<>();
        Connection con = Listener.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery ("SELECT rowid, * from users ");
        
        while(rs.next()) {
            long rowId = rs.getLong("rowid");
            String login = rs.getString("login");
            String name = rs.getString("name");
            String role = rs.getString("role"); // verificar se esse atributo é necessário
            String passwordHash = rs.getString("password_hash");
            list.add(new User(rowId, login, name, role, passwordHash)); // adiciona na lista o novo usuário com todos os parametros.
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }
    
    public static User getUser(String login, String password) throws Exception {
        User user = null;
        Connection con = Listener.getConnection();
        String sql = "SELECT rowid, * from users WHERE login=? AND password_hash=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, login);
        stmt.setString(2, Listener.getMd5Hash(password));
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()) {
            long rowId = rs.getLong("rowid");
            String name = rs.getString("name");
            String role = rs.getString("role");  // verificar se há necessidade de parametro
            String passwordHash = rs.getString("password_hash");
            user = new User(rowId, login, name, role, passwordHash);
        }
        rs.close();
        stmt.close();
        con.close();
        return user;
    }
    
    public static void insertUser(String login, String name, String role, String password) throws Exception {
        Connection con = Listener.getConnection();
        String sql = "INSERT INTO users(login, name, role, password_hash)"
                       +"VALUES(?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, login);
        stmt.setString(2, name);
        stmt.setString(3, role);
        stmt.setString(4, Listener.getMd5Hash(password));
        stmt.execute();
        stmt.close();
        con.close(); 
    }
    
    public static void updateUser(String login, String name, String role, String password) throws Exception {
        Connection con = Listener.getConnection();
        String sql = "UPDATE users SET name=?, role=?, password_hash=? WHERE login=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, role);
        stmt.setString(3, Listener.getMd5Hash(password));
        stmt.setString(4, login);
        stmt.execute();
        stmt.close();
        con.close(); 
    }
    
    public static void deleteUser(long rowId) throws Exception{
        Connection con = Listener.getConnection();
        String sql = "DELETE FROM users WHERE rowid = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setLong(1, rowId);
        stmt.execute();
        stmt.close();
        con.close();
    }
    
    public User(long rowId, String name, String login, String role, String passwordHash) {
        this.rowId = rowId;
        this.name = name;
        this.login = login;
        this.role = role; // lembrar de verificar aqui, caso exclua no metodo acima
        this.passwordHash = passwordHash;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() { // aqui também
        return role;
    }

    public void setRole(String role) { // e aqui
        this.role = role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
     
    
}