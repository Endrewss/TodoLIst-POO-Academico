/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.ConnectionDB;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Lucas Silva
 */
public class User {

    private long rowId;
    private String name;
    private String passwordHash;

    // implementar mais requisitos na tabela caso o banco não se baseia no nosso modelo de negocio
    public static String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS users ("
                + "name VARCHAR(200) UNIQUE NOT NULL PRIMARY KEY,"
                + "password_hash VARCHAR NOT NULL"
                + ")";
    }

    public static ArrayList<User> getUsers() throws Exception {
        ArrayList<User> list = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT rowid, * from users");

        while (rs.next()) {
            long rowId = rs.getLong("rowid");
            String name = rs.getString("name");
            String passwordHash = rs.getString("password_hash");
            list.add(new User(rowId, name, passwordHash)); // adiciona na lista o novo usuário com todos os parametros.
        }
        rs.close();
        stmt.close();
        con.close();
        return list;
    }

    public static User getUser(String name, String password) throws Exception {
        User user = null;
        Connection con = ConnectionDB.getConnection();
        String sql = "SELECT rowid, * from users WHERE name=? AND password_hash=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, ConnectionDB.getMd5Hash(password));
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            long rowId = rs.getLong("rowid");
            String name2 = rs.getString("name");
            String passwordHash = rs.getString("password_hash");
            user = new User(rowId, name2, passwordHash);
        }
        rs.close();
        stmt.close();
        con.close();
        return user;
    }

    public static boolean findByName(String name) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionDB.getConnection();
            String sql = "SELECT rowid, * from users WHERE name=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            // Verifica se o usuário foi encontrado
            if (rs.next()) {
                return true;
            }

            return false;
        } finally {
            // Certifique-se de fechar os recursos (ResultSet, PreparedStatement e Connection)

            rs.close();
            stmt.close();
            con.close();

        }
    }

    public static void insertUser(String name, String password) throws Exception {
        Connection con = ConnectionDB.getConnection();
        String sql = "INSERT INTO users(name, password_hash)"
                + "VALUES(?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, ConnectionDB.getMd5Hash(password));
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void updateUser(String name, String password) throws Exception {
        Connection con = ConnectionDB.getConnection();
        String sql = "UPDATE users SET name=?, password_hash=? WHERE name=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, ConnectionDB.getMd5Hash(password));
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void deleteUser(long rowId) throws Exception {
        Connection con = ConnectionDB.getConnection();
        String sql = "DELETE FROM users WHERE rowid = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setLong(1, rowId);
        stmt.execute();
        stmt.close();
        con.close();
    }

    public User(long rowId, String name, String passwordHash) {
        this.rowId = rowId;
        this.name = name;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
