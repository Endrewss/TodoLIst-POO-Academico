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
        <title>Atualizar</title>
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
                                <h6 class="mb-3">Atualizar</h6>
                                <div class="container m-2">
                                    <form class="d-flex justify-content-center align-items-center mb-4" method="post">
                                        <div class="form-outline flex-fill">
                                            <%
                                                if (request.getSession().getAttribute("user") != null) {
                                                    User u = (User) request.getSession().getAttribute("user");

                                                    if (request.getSession().getAttribute("itematt") != null) {
                                                        Item item = (Item) request.getSession().getAttribute("itematt");
                                                        if (item != null) {
                                            %>
                                            <input type="text" id="form3" class="form-control form-control-lg" placeholder="Alterar nome" value="<%=item.getName()%>" name="todoitem" />
                                            <input type="hidden" value="<%=item.getId()%>" name="idItem" />
                                            <%}
                                                    }
                                                }%>
                                        </div>

                                        <button type="submit" class="btn btn-dark btn-lg ms-2">Editar</button>
                                    </form>

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
