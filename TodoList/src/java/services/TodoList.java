package services;

import database.ConnectionDB;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "todolist", urlPatterns = {"/todolist"})
public class TodoList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            if (u != null) {
                try {
                    if (User.findByName(u.getName())) {
                        request.getSession().setAttribute("user", u);
                        request.getRequestDispatcher("todolist.jsp").forward(request, response);
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
                        if (request.getParameter("logout") != null) {
                            request.getSession().removeAttribute("user");
                            response.sendRedirect("./todolist");
                        }
                        
                        if (request.getParameter("typeForm") != null) {
                            if (request.getParameter("typeForm").equals("edit")) {
                                if (request.getParameter("itemId") != null) {
                                    request.getSession().setAttribute("itematt", Item.getItem(Integer.valueOf(request.getParameter("itemId")), u.getName()));
                                    //request.getRequestDispatcher("updateitem.jsp").forward(request, response);
                                    response.sendRedirect("./updateitem");
                                }
                            } else if (request.getParameter("typeForm").equals("delete")) {
                                if (request.getParameter("itemId") != null) {
                                    Item.deleteItem(Integer.valueOf(request.getParameter("itemId")), u.getName());
                                    response.sendRedirect("./todolist");
                                }
                            } else {

                                if (request.getParameter("todoitem") != null) {
                                    if (!request.getParameter("todoitem").isEmpty()) {

                                        //Criar item
                                        try {
                                            Connection c = ConnectionDB.getConnection();
                                            Statement s = c.createStatement();
                                            s.execute(Item.getCreateStatement());
                                            Item.insertItem(request.getParameter("todoitem"), u.getName());

                                            response.sendRedirect("./todolist");

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
                                        request.getRequestDispatcher("todolist.jsp").forward(request, response);
                                    }
                                } else {
                                    request.getRequestDispatcher("todolist.jsp").forward(request, response);
                                }
                            }
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
