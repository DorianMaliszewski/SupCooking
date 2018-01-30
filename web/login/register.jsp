<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:attribute name="title">Login
    </jsp:attribute>
    <jsp:body>
        <h1>Inscription</h1>
        <form method="post" action="${pageContext.servletContext.contextPath}/register">
            <div class="form-group">
                <label for="username">Identifiant</label>
                <input type="text" class="form-control" id="username" name="username" aria-describedby="usernameHelp" placeholder="ID">
                <small id="usernameHelp" class="form-text text-muted">Choisissez un identifiant.</small>
            </div>
            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Mot de passe" aria-describedby="passHelp">
                <small id="passHelp" class="form-text text-muted">Choisissez un motde passe 6 caractère minimum.</small>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirmation</label>
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirmer le mot de passe">
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="firstname">Prénom</label>
                    <input type="text" class="form-control" name="firstName" placeholder="Prénom">
                </div>
                <div class="form-group col-md-6">
                    <label for="lastname">Nom</label>
                    <input type="text" class="form-control" name="lastName" placeholder="Nom">
                </div>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" name="email" class="form-control" id="email" placeholder="Email">
            </div>
            <div class="form-group">
                <label for="postalCode">Code postal</label>
                <input type="text" name="postalCode" class="form-control" id="postalCode" placeholder="XXXXX">
            </div>
            <button type="submit" class="btn btn-primary">S'inscrire</button>
        </form>
    </jsp:body>
</t:layout>