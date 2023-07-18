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
import model.User;

/**
 *
 * @author Endrews
 */
@WebServlet(name = "cadastro", urlPatterns = {"/cadastro"})
public class Cadastro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            if (u != null) {
                try {
                    if (User.findByName(u.getName())) {

                        request.getSession().setAttribute("user", u);

                        response.sendRedirect("./todolist");
                    }
                } catch (Exception e) {
                    System.out.println("ERRO: " + e.getLocalizedMessage());
                }
            }
        } else {
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap<String, String> hash = new HashMap<>();

        if (request.getParameter("name") != null && request.getParameter("password") != null) {
            if (!request.getParameter("name").isEmpty() && !request.getParameter("password").isEmpty()
                    || (!request.getParameter("name").isEmpty() || !request.getParameter("password").isEmpty())) {

                //Entrar na aplicação
                try {
                    Connection c = ConnectionDB.getConnection();
                    Statement s = c.createStatement();
                    s.execute(User.getCreateStatement());

                    if (!User.findByName(request.getParameter("name"))) {
                        User.insertUser(request.getParameter("name"), request.getParameter("password"));
                        User u = User.getUser(request.getParameter("name"), request.getParameter("password"));
                        request.getSession().setAttribute("user", u);
                        response.sendRedirect("./todolist");
                    } else {
                        hash.put("e_userexist", "Já existe um usuário com essas credenciais!");
                        s.close();
                        c.close();
                        request.setAttribute("errors", hash);
                        request.getRequestDispatcher("cadastro.jsp").forward(request, response);
                    }

                    s.close();
                    c.close();
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getLocalizedMessage());
                }

            } else {
                if (request.getParameter("name").isEmpty()) {
                    hash.put("e_name", "Campo obrigatório");
                }
                if (request.getParameter("password").isEmpty()) {
                    hash.put("e_password", "Campo obrigatório");
                }
                request.setAttribute("errors", hash);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("cadastro.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
