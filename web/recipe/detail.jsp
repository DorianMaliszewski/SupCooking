<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<t:layout>
    <jsp:attribute name="title">${recipe.name}
    </jsp:attribute>
    <jsp:body>
        <style>
            .starIcon{
                background-color: Transparent;
                background-repeat:no-repeat;
                border: none;
                cursor:pointer;
                overflow: hidden;
                outline:none;
            }
        </style>
        <div class="row">
            <h1 class="col-md-9">${recipe.name}</h1>
            <span class="col-md-3" id="starZone">
                <button data-index=1 class="starIcon"> <i style="color:red;" class="far fa-star"></i> </button>
                <button data-index=2 class="starIcon"> <i style="color:red;" class="far fa-star"></i> </button>
                <button data-index=3 class="starIcon"> <i style="color:red;" class="far fa-star"></i> </button>
                <button data-index=4 class="starIcon"> <i style="color:red;" class="far fa-star"></i> </button>
                <button data-index=5 class="starIcon"> <i style="color:red;" class="far fa-star"></i> </button>
            </span>    
        </div>
        <div class="row">
            <div class="col-md-4">
                Note
            </div>
            <div class="col-md-8">
                <fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits="2" value="${!empty recipe.mark ? recipe.mark/recipe.numberOfMark : 0}" /> / 5 - ${recipe.numberOfMark} notes 
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
                ${recipe.HTMLDescription}
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
            <h3>Ingrédients</h3>
            <c:forEach items="${recipe.products}" var="product">
                <div class="row">
                    <p>${product.product.name}</p>
                </div>
            </c:forEach>
        
            <script>
                /**
                 * Change le css du coeur pour les favoris
                 * @param Event e
                 * @returns void
                 */
                function fillStarsIcon(e){
                    e.preventDefault();
                    let elem = e.target;
                    let index  = elem.dataset.index;
                    var starsIcon = document.querySelectorAll(".fa-star");
                    Array.from(starsIcon).forEach(function(starIcon, indice){
                        if(indice < index){
                            starIcon.dataset.prefix = "fas";
                        }else{
                            starIcon.dataset.prefix = "far";
                        }
                    })
                }
                
                function unfillStarsIcon(e){
                    e.preventDefault();
                    let elem = e.target;
                    let index  = elem.dataset.index;
                    var starsIcon = document.querySelectorAll(".fa-star");
                    Array.from(starsIcon).forEach(function(starIcon, indice){
                            starIcon.dataset.prefix = "far";
                    });
                }
                
                function noteThisRecipe(e){
                    console.log("Hello")
                    $.ajax({
                        url: '${pageContext.servletContext.contextPath}/markRecipe',
                        method: 'POST',
                        data: { id: ${recipe.id}, mark: e.target.dataset.index },
                        success: function(data){
                            if(data == "OK"){
                                alert("Merci d'avoir noté cette recette");
                            }
                            else{
                                alert("Une erreur est survenue lors de l'enregistrement de votre note")
                            }
                        },
                        error: function(error){
                            console.log("Erreur", error);
                        }
                    });
                    document.getElementById("starZone").style.display = "none";
                }
                
                //Lorsque la souris entre et sort du bouton on déclenche l'évènement
                Array.from(document.getElementsByClassName("starIcon")).forEach(function(icon){
                    icon.onmouseenter = fillStarsIcon;
                    icon.onmouseleave = unfillStarsIcon;
                    icon.onclick= noteThisRecipe;
                });
                
            </script>
    </jsp:body>
</t:layout>
