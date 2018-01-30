<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<t:layout>
    <jsp:attribute name="title">${recipe.name}
    </jsp:attribute>
    <jsp:body>
        <h1>${recipe.name}</h1><span style="float:right" ><btn type="button" id="favouriteButton" class="btn btn-outline-primary"><i style="color:red;" id="heartIcon" class="${!user.markedRecipes.contains(recipe) ? "far" : "fas" } fa-heart"></i> Ajouter à mes favoris</btn></span>
        <div class="row">
            <div class="col-md-4">
                Note
            </div>
            <div class="col-md-8">
                ${recipe.mark}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Image
            </div>
            <div class="col-md-8">
                <img src="${recipe.image}" alt="${recipe.image}"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Description
            </div>
            <div class="col-md-8">
                ${recipe.description}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Catégorie
            </div>
            <div class="col-md-8">
                ${recipe.category.name}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Crée par
            </div>
            <div class="col-md-8">
                ${recipe.createdBy}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Nombre de vue
            </div>
            <div class="col-md-8">
                ${recipe.numberOfView}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Nombre de notes
            </div>
            <div class="col-md-8">
                ${recipe.numberOfMark}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Difficulté
            </div>
            <div class="col-md-8">
                ${recipe.difficulty}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Temps de cuisson
            </div>
            <div class="col-md-8">
                ${recipe.cookingTime} minutes
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Temps de préparation
            </div>
            <div class="col-md-8">
                ${recipe.preparationTime} minutes
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Nombre d'ingrédient
            </div>
            <div class="col-md-8">
                ${fn:length(recipe.products)}
            </div>
        </div>
            <hr>
        <div class="row">
            <h3>Ingrédients</h3>
            <c:forEach items="${recipe.products}" var="product">
                <p>${product.product.name}</p>
            </c:forEach>
        </div>
        
            <script>
                /**
                 * Change le css du coeur pour les favoris
                 * @param Event e
                 * @returns void
                 */
                function changeHeartIcon(e){
                    e.preventDefault();
                    let elem = e.target;
                    var heartIcon = elem.querySelector("#heartIcon");
                    if(heartIcon.dataset.prefix === "far"){
                        heartIcon.dataset.prefix = "fas";
                    }
                    else
                    {
                        heartIcon.dataset.prefix = "far";
                    }
                }
                
                //Lorsque la souris entre et sort du bouton on déclenche l'évènement
                document.getElementById("favouriteButton").onmouseover = changeHeartIcon;
                document.getElementById("favouriteButton").onmouseleave = changeHeartIcon;
                
                document.getElementById("favouriteButton").click(function(e){
                   e.preventDefault();
                   $.ajax({
                    method: "POST",
                    url: "/addMarkedRecipe",
                    data: { id: ${recipe.id}}
                  })
                    .done(function( msg ) {
                      alert( "Data Saved: " + msg );
                    });
                });
            </script>
    </jsp:body>
</t:layout>
