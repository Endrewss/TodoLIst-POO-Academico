<%-- 
    Document   : index
    Created on : 10/06/2023, 19:55:08
    Author     : Endrews
--%>

<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <!-- O IDEAL SERIA TRANSPOR ESSA NAV PARA OUTRO ARQUIVO E APAGAR E REFERENCIAR EM CADA HTML PARA QUE AS EDIÇÕES SEJAM HERDADAS POR TODOS -->
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <section class="vh-100" style="background-color: #000000;">


            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col col-xl-10">
                        <div class="card" style="border-radius: 15px;">
                            <div class="card text-center">
                                <div class="card-header bg-secondary text-white">
                                    <h5 class="mb-0">ENTRAR</h5>
                                </div>
                            </div>

                            <div class="card-body p-5">
                                <h6 class="mb-3">Insira suas informações:</h6>
                                <div class="container m-2">
                                    <%
                                        HashMap<String, String> errors = new HashMap<>();
                                        if (request.getAttribute("errors") != null) {
                                            errors = (HashMap<String, String>) request.getAttribute("errors");
                                        }
                                    %>
                                    <form class="d-flex justify-content-center align-items-center mb-4" method="post">
                                        <div class="form-outline flex-fill">
                                            <input required type="text" id="form3" class="form-control form-control-lg" placeholder="Nome" name="name" />
                                            <%if (errors.get("e_name") != null) {
                                            %>
                                            <p style="color:#fc3a51"><%=errors.get("e_name")%></p>
                                            <%}%>
                                        </div>
                                        <div class="form-outline flex-fill" style="margin-left: 12px">
                                            <input required minlength="6" type="password" id="form3" class="form-control form-control-lg" placeholder="Senha" name="password" />
                                            <%if (errors.get("e_password") != null) {
                                            %>
                                            <p style="color:#fc3a51"><%=errors.get("e_password")%></p>
                                            <%}%>
                                        </div>
                                        <%if (errors.get("e_nouser") != null) {
                                        %>
                                        <p style="color:#fc3a51"><%=errors.get("e_nouser")%></p>
                                        <%}%>
                                        <button type="submit" class="btn btn-dark btn-lg ms-2">Entrar</button>
                                    </form>
                                </div>
                            </div>                            
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    </body>
</html>
