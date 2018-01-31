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
            <p class="lead">Votre référence pour la cuisine étudiante</p>
            <p class="lead">${recipesNumber} recette${recipesNumber > 1 ? "s" : ""} en ligne</p>
            <p class='lead'>${usersNumber} utilisateur${usersNumber > 1 ? "s" : ""} inscrit${usersNumber > 1 ? "s" : ""}</p>
        </div>
        <hr>
        <h2>Nos dernières recettes</h2>
        <div class="row">
            <c:forEach var="recipe" items="${recipes}">
                <div class="col-md-4">
                    <div class="card mb-4 box-shadow">
                        <img class="card-img-top" data-src="${recipe.image}" alt="${recipe.name}" style="height: 225px; width: 100%; display: block;" src="${recipe.image}" data-holder-rendered="true">
                        <div class="card-body">
                            <p class="card-text">${recipe.numberOfView} vue${recipe.numberOfView > 1 ? "s" : ""} - ${recipe.numberOfMark} note${recipe.numberOfMark > 1 ? "s" : ""} - ${recipe.mark} / 5</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                    <a href="${pageContext.servletContext.contextPath}/recipe/show?id=${recipe.id}" class="btn btn-sm btn-outline-success" title="Voir la recette"><i class="fas fa-search"></i></a>
                                    <button data-src="${recipe.id}" onclick="changeFavourite(this)" type="button" class="btn btn-sm btn-outline-primary add-favourite" title="Ajouter à mes favoris"><i class="far fa-heart"></i></button>
                                </div>
                                <small class="text-muted">${recipe.preparationTime} mins de préparation</small>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <script type="text/javascript">
            function changeFavourite(elem){
                console.log(elem.dataset)
                   $.ajax({
                    method: "POST",
                    url: "${pageContext.servletContext.contextPath}/addMarkedRecipe",
                    data: { id: elem.dataset.src }
                  })
                    .done(function( msg ) {
                      alert( "Data Saved: " + msg);
                    });
            }
        </script>
    </jsp:body>
</t:layout>
