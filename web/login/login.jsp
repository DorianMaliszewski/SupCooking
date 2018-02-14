<%-- 
    Document   : index
    Created on : 16 janv. 2018, 15:10:50
    Author     : MaliszewskiDorian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<style>
    body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #eee;
    }

    .form-signin {
        max-width: 330px;
        padding: 15px;
        margin: 0 auto;
    }
    .form-signin .form-signin-heading,
    .form-signin .checkbox {
        margin-bottom: 10px;
    }
    .form-signin .checkbox {
        font-weight: 400;
    }
    .form-signin .form-control {
        position: relative;
        box-sizing: border-box;
        height: auto;
        padding: 10px;
        font-size: 16px;
    }
    .form-signin .form-control:focus {
        z-index: 2;
    }
    .form-signin input[type="email"] {
        margin-bottom: -1px;
        border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
    }
    .form-signin input[type="password"] {
        margin-bottom: 10px;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
    }
</style>
<t:layout>
    <jsp:attribute name="title">Login
    </jsp:attribute>
    <jsp:body>
        <form class="form-signin" method="post" action="${pageContext.servletContext.contextPath}/login">
            <h2 class="form-signin-heading">Connectez-vous</h2>
            <label for="inputEmail" class="sr-only">Identifiant</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="ID" required autofocus>
            <label for="inputPassword" class="sr-only">Mot de passe</label>
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Mot de passe" required>
            <p>Vous n'avez pas de compte ? <a href="${pageContext.servletContext.contextPath}/register">S'inscrire</a></p>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Se connecter</button>
        </form>
    </jsp:body>
</t:layout>
