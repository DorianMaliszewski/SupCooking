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
        <hr/>
        <h4>Ingrédients</h4>
            
            <c:forEach items="${recipe.products}" var="product">
                <div class="row">
                    <p class="col-md-4">${product.product.name}</p> <p class="col-md-4">${product.quantity} ${product.unit}</p>
                </div>
            </c:forEach>
        <hr/>
        
        <img class="mx-auto d-block" style="object-fit: cover" src="${recipe.image}" alt="${recipe.image}" width="600" height="300"/>
        
        <table class="table table-striped">
            <tr>
                <td>Note</td>
                <td>
                     ${recipe.formattedMark} - ${recipe.numberOfMark} notes
                </td>
            </tr>
            
            <tr>
                <td>Catégorie</td>
                <td>${recipe.category.name}</td>
            </tr>
            
            <tr>
                <td>Crée Par</td>
                <td>${recipe.createdBy}</td>
            </tr>
            
            <tr> 
                <td>Temps de cuisson</td>
                <td>${recipe.cookingTime} minutes</td>
            </tr>
            
            <tr> 
                <td>Temps de préparation</td>
                <td>${recipe.preparationTime} minutes</td>
            </tr>
            
            <tr>
                <td>Difficulté</td>
                <td>
                    <c:choose>
                        <c:when test="${recipe.difficulty == 1}">Très Facile</c:when>
                        <c:when test="${recipe.difficulty == 2}">Facile</c:when>
                        <c:when test="${recipe.difficulty == 3}">Moyen</c:when>
                        <c:when test="${recipe.difficulty == 4}">Difficile</c:when>
                        <c:when test="${recipe.difficulty == 5}">Cordon Bleu</c:when>
                        <c:otherwise>Non communique</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            
            <tr>
                <td>Nombre d'ingrédient</td>
                <td>${fn:length(recipe.products)}</td>
            </tr>
        </table>
        
        <h3>Description</h3>
        
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                ${recipe.HTMLDescription}
            </div>
            <div class="col-md-2"></div>
        </div>
        
        <div class="row">
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <hr/>
            </div>
            <div class="col-md-1"></div>
        </div>
            <script>
                /**
                 * Change le css des etoiles
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
                
                /**
                 * Permet de note la recette puis fais disparaitre les etoiles pour eviter le spam
                 * @param Event e
                 * @returns void
                 */
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
