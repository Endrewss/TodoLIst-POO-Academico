/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import database.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Endrews
 */
public class Item {

    private String name;
    private Integer id;

    public static String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS todoitem ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "name VARCHAR(200) NOT NULL, "
                + "user_key VARCHAR(250), "
                + "FOREIGN KEY (user_key) REFERENCES users(name)"
                + ")";
    }

    public static List<Item> getAllItens(String user_key) throws Exception {
        List<Item> todoitem = new ArrayList<>();
        try {
            Connection con = ConnectionDB.getConnection();
            String query = "SELECT * FROM todoitem where user_key = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, user_key);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setName(rs.getString("name"));
                item.setId(Integer.valueOf(rs.getString("id")));
                todoitem.add(item);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
        }

        return todoitem;
    }

    public static Item getItem(Integer id, String userName) throws Exception {
        Item item = null;
        try {
            Connection con = ConnectionDB.getConnection();

            // Buscar o item
            String selectQuery = "SELECT * FROM todoitem WHERE id = ? AND user_key = ?";
            PreparedStatement stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, id);
            stmt.setString(2, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Integer itemId = rs.getInt("id");
                String name = rs.getString("name");
                // ... Obter outros valores necessários

                item = new Item(name,itemId);
                // ... Definir outros valores no objeto Item, se necessário
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            // Tratar exceção
        }
        return item;
    }

    public static void insertItem(String name, String userName) throws Exception {
        try {
            Connection con = ConnectionDB.getConnection();

            // Inserir o novo contato
            String insertQuery = "INSERT INTO todoitem (name, user_key) "
                    + "VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(insertQuery);
            stmt.setString(1, name);
            stmt.setString(2, userName);
            stmt.execute();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            // Tratar exceção
        }
    }
    
    public static boolean findByItemId(Integer id, String userName) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionDB.getConnection();
            String sql = "SELECT * from todoitem WHERE id=? AND user_key = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, userName);
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

    public static void deleteItem(Integer id, String userName) throws Exception {
        try {
            Connection con = ConnectionDB.getConnection();

            // Inserir o novo contato
            String deleteQuery = "DELETE FROM todoitem where id = ? AND user_key =?";
            PreparedStatement stmt = con.prepareStatement(deleteQuery);
            stmt.setInt(1, id);
            stmt.setString(2, userName);
            stmt.execute();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            // Tratar exceção
        }
    }

    public static void updateItem(String name, Integer id, String userName) throws Exception {
        // Atualizar o contato pelo telefone
        try (Connection con = ConnectionDB.getConnection()) {
            // Atualizar o contato pelo telefone
            String updateQuery = "UPDATE todoitem SET name = ? WHERE id = ? AND user_key = ?";
            try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
                stmt.setString(1, name);
                stmt.setInt(2, id);
                stmt.setString(3, userName);
                stmt.executeUpdate();
                stmt.close();
            }
            con.close();

        } catch (SQLException e) {
            // Tratar exceção
        }
    }

    public Item() {
    }

    public Item(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public Item(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
