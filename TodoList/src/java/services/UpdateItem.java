package services;

import database.ConnectionDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Item;
import model.User;

/**
 *
 * @author Endrews
 */
@WebServlet(name = "updateitem", urlPatterns = {"/updateitem"})
public class UpdateItem extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            if (u != null) {
                try {
                    if (User.findByName(u.getName())) {
                        request.getSession().setAttribute("user", u);
                        request.getRequestDispatcher("updateitem.jsp").forward(request, response);
                    }
                } catch (Exception e) {
                    System.out.println("ERRO: " + e.getLocalizedMessage());
                }
            }
        } else {
            response.sendRedirect("./login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap<String, String> hash = new HashMap<>();

        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            if (u != null) {
                try {
                    if (User.findByName(u.getName())) {

                        if (request.getParameter("todoitem") != null) {
                            if (!request.getParameter("todoitem").isEmpty()) {

                                //Utualizar item
                                try {
                                    Connection c = ConnectionDB.getConnection();
                                    Statement s = c.createStatement();
                                    s.execute(Item.getCreateStatement());

                                    if (Item.findByItemId(Integer.valueOf(request.getParameter("idItem")), u.getName())) {
                                        Item.updateItem(request.getParameter("todoitem"), Integer.valueOf(request.getParameter("idItem")), u.getName());
                                        request.getSession().removeAttribute("itematt");
                                        response.sendRedirect("./todolist");
                                    } else {
                                        response.sendRedirect("./updateitem");
                                    }
                                    s.close();
                                    c.close();
                                } catch (Exception e) {
                                    System.out.println("ERROR: " + e.getLocalizedMessage());
                                }

                            } else {
                                if (request.getParameter("todoitem").isEmpty()) {
                                    hash.put("e_todoitem", "Campo obrigatório");
                                }
                                request.setAttribute("errors", hash);
                                request.getRequestDispatcher("updateitem.jsp").forward(request, response);
                            }
                        } else {
                            request.getRequestDispatcher("updateitem.jsp").forward(request, response);
                        }

                    }
                } catch (Exception e) {
                    System.out.println("ERRO: " + e.getLocalizedMessage());
                }
            }
        } else {
            response.sendRedirect("./login");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
