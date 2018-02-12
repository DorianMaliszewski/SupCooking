<%-- 
    Document   : form
    Created on : 20 janv. 2018, 20:10:40
    Author     : dorian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Profil
    </jsp:attribute>
    <jsp:body>
        <h1>Modifier mes informations</h1>
        <form id="myForm" method="POST" action="${pageContext.servletContext.contextPath}/profile/edit">
            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Mot de passe" aria-describedby="passHelp">
                <small id="passHelp" class="form-text text-muted">Ne rentrez pas de mot de passe pour ne pas le changer</small>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirmation</label>
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirmer le mot de passe">
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="firstname">Prénom</label>
                    <input type="text" class="form-control" name="firstName" placeholder="Prénom" value="${user.firstName}" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="lastname">Nom</label>
                    <input type="text" class="form-control" name="lastName" placeholder="Nom" value="${user.lastName}" required>
                </div>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" name="email" class="form-control" id="email" placeholder="Email" value="${user.email}" required>
            </div>
            <div class="form-group">
                <label for="postalCode">Code postal</label>
                <input type="text" name="postalCode" class="form-control" id="postalCode" placeholder="XXXXX" value="${user.postalCode}" required>
            </div>
            <button type="submit" class="btn btn-primary">Modifier</button>
        </form>
            
        <script type="text/javascript">
            document.getElementById("myForm").onsubmit = function(event){
                var password = document.getElementById("password").value;
                var confirmPassword = document.getElementById("confirmPassword").value;
                if(password.length !== 0){
                    if(password === confirmPassword){
                        return true;
                    }
                    else{
                        document.getElementById("invalid-confirmPass").style.display = "inline";
                        document.getElementById("confirmPassword").className = "form-control is-invalid";                        
                    }
                }
                else{
                    return true;
                }
            }
            
            document.getElementById("confirmPassword").onchange = function(event){
                document.getElementById("invalid-confirmPass").style.display = "none";
                document.getElementById("confirmPassword").className = "form-control";
            }
            
        </script>
        
    </jsp:body>
</t:layout>