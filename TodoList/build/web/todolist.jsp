<%-- 
    Document   : index
    Created on : 10/06/2023, 19:55:08
    Author     : Endrews
--%>

<%@page import="model.Item"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.User"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Todo List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <section class="vh-100" style="background-color: #000000;">


            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col col-xl-10">
                        <div class="card" style="border-radius: 15px;">
                            <div class="card text-center">
                                <div class="card-header bg-secondary text-white">
                                    <h5 class="mb-0">TO-DO LIST</h5>
                                </div>
                            </div>

                            <div class="card-body p-5">
                                <h6 class="mb-3">Afazeres:</h6>
                                <div class="container m-2">
                                    <form class="d-flex justify-content-center align-items-center mb-4" method="post">
                                        <div class="form-outline flex-fill">
                                            <input type="text" id="form3" class="form-control form-control-lg" placeholder="Nova tarefa" name="todoitem" />

                                        </div>
                                        <input value="post" name="typeForm" type="hidden">
                                        <button type="submit" class="btn btn-dark btn-lg ms-2">Adicionar</button>
                                    </form>

                                    <%

                                        if (request.getSession().getAttribute("user") != null) {
                                            User u = (User) request.getSession().getAttribute("user");
                                            List<Item> list = new ArrayList<>();
                                            list = Item.getAllItens(u.getName());

                                            int increment = 0;
                                            if (list != null && !list.isEmpty()) {

                                    %>
                                    <table class="table">
                                        <%for (Item item : list) {%>
                                        <tr>
                                            <td>
                                                <span><%=item.getName()%></span>
                                            </td>                                           
                                            <td class="text-center">
                                                <form method="post"><input value="edit" name="typeForm" type="hidden"><button value="<%=item.getId()%>" name="itemId" type="submit" class="btn btn-outline-dark btn-sm">Editar</button></form>
                                                <form method="post"><input value="delete" name="typeForm" type="hidden"><button value="<%=item.getId()%>" name="itemId" type="submit" class="btn btn-outline-dark btn-sm m1-2">Excluir</button></form>
                                            </td>
                                        </tr>
                                        <%}%>
                                    </table>
                                    <%}
                                        }%>

                                </div>
                            </div>                            
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
