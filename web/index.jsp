<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Home
    </jsp:attribute>
    <jsp:body>
        <div class="jumbotron">
            <h1 class="display-4">SupCooking c'est...</h1>
            <p>Votre référence pour gérer votre alimentation étudiante</p>
            <p class="lead">${recipesNumber} recettes en ligne</p>
            <p class='lead'>${usersNumber} utilisateurs inscrits</p>
        </div>
        <h1>Nos dernière recettes</h1>
        <div class="row">
            <c:forEach var="recipe" items="${recipes}">
                <div class="col-md-4">
                    <h2>${recipe.name}</h2>
                    <p>${recipe.numberOfView} vues - ${recipe.mark}/5 - ${recipe.numberOfMark} notes</p>
                    <p><a class="btn btn-secondary" href="#" role="button">Voir la recette &raquo;</a></p>
                </div>
            </c:forEach>
        </div>
    </jsp:body>
</t:layout>
