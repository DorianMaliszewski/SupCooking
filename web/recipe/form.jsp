<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var = "prefixe" scope = "page" value = '${!empty recipe ? "Modifier" : "Ajouter"}'/>
<c:set var="compteur" scope="page" value="0" />
<t:layout>
    <jsp:attribute name="title">${prefixe} une recette
    </jsp:attribute>
    <jsp:body>
        
        <h1>${prefixe} une recette</h1>
        <c:choose>
            <c:when test="${!empty recipe}">
                <form id="recipeForm" method="post" enctype="multipart/form-data" action="${pageContext.servletContext.contextPath}/recipes/edit?id=${recipe.id}">
            </c:when>    
            <c:otherwise>
                <form id="recipeForm" method="post" enctype="multipart/form-data" action="${pageContext.servletContext.contextPath}/recipes/add">
            </c:otherwise>
        </c:choose>
            <div class="form-group">
                <label for="name">Nom de la recette</label>
                <input <c:if test="${!empty recipe}">value="${recipe.name}"</c:if> type="text" class="form-control" id="name" name="name" aria-describedby="nameHelp" placeholder="Nom de la recette" required/>
                <small id="nameHelp" class="form-text text-muted">3 caractères minimum.</small>
            </div>

            <div class="form-group">
                <label for="image">Image</label>
                <input <c:if test="${!empty recipe}">value="${recipe.image}"</c:if> type="file" accept="image/*" class="form-control" id="image" name="image" aria-describedby="imageHelp" placeholder="Image de la recette"/>
                <small id="imageHelp" class="form-text text-muted">Si vous ne souhaitez pas modifier l'image actuelle alors ne rien sélectionner</small>
            </div>

            <div class="form-group">
                <label for="category_id">Categorie</label>
                <select name="category_id" class="form-control" required>
                    <option value="">Sélectionnez</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}" ${recipe.category.id == c.id ? "selected" : ""}>${c.name}</option>
                    </c:forEach>
                </select>
                <small id="categoryHelp" class="form-text text-muted">Une categorie doit être assignée à la recette.</small>
            </div>

            <div class="form-group">
                <label for="difficulty">Difficulté</label>
                <select name='difficulty'>
                    <option value="0" ${!empty recipe && recipe.difficulty == 0 ? "selected" : ""}>Sélectionnez</option>
                    <option value="1" ${!empty recipe && recipe.difficulty == 1 ? "selected" : ""}>Très Facile</option>
                    <option value="2" ${!empty recipe && recipe.difficulty == 2 ? "selected" : ""}>Facile</option>
                    <option value="3" ${!empty recipe && recipe.difficulty == 3 ? "selected" : ""}>Moyen</option>
                    <option value="4" ${!empty recipe && recipe.difficulty == 4 ? "selected" : ""}>Difficile</option>
                    <option value="5" ${!empty recipe && recipe.difficulty == 5 ? "selected" : ""}>Cordon Bleu</option>
                </select>
                <small id="difficultyHelp" class="form-text text-muted">Difficulté pour réaliser la recette</small>
            </div>
                
            <div class="form-group">
                <label for="preparationTime">Temps de préparation</label>
                <input <c:if test="${!empty recipe}">value="${recipe.preparationTime}"</c:if> type="number" min='0' class="form-control" id="preparationTime" name="preparationTime" aria-describedby="preparationTimeHelp" placeholder="Temps de préparation" required>
                <small id="nameHelp" class="form-text text-muted">Temps en minutes, ne peut pas être inférieur à 0.</small>
            </div>

            <div class="form-group">
                <label for="cookingTime">Temps de cuisson</label>
                <input <c:if test="${!empty recipe}">value="${recipe.cookingTime}"</c:if> type="number" min='0' class="form-control" id="cookingTime" name="cookingTime" aria-describedby="cookingTimeHelp" placeholder="Temps de cuisson" required>
                <small id="nameHelp" class="form-text text-muted">Temps en minutes, ne peut pas être inférieur à 0.</small>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea rows="5" placeholder="Description de la recette" form="recipeForm" name="description" class="form-control" required>${!empty recipe ? recipe.description : null}</textarea>
                <small id="descriptionHelp" class="form-text text-muted">Il faut une description de la recette.</small>
            </div>
            <div id="divProducts">
                <label>Produits</label>
                    <c:choose>
                        <c:when test="${!empty recipe.products}">
                            <c:forEach var="produit" items="${recipe.products}" varStatus="loop">
                                <c:set scope="page" var="compteur" value="${loop.index}"/>
                                <div class="form-row">
                                <div class="form-group col-7">
                                <select name="product_${loop.index}[]" class="form-control" required>
                                    <option value="">Sélectionnez</option>
                                    <c:forEach var="p" items="${products}">
                                        <option value="${p.id}" ${produit.product.id == p.id ? "selected" : ""}>${p.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col form-group">
                                <input type="number" step="0.1" class="form-control" name="product_${loop.index}[]" placeholder="Quantite" value='${produit.quantity}' required/>
                            </div>
                            <div class="col form-group">
                                <select name="product_${loop.index}[]" class="form-control" >
                                    <option value=""    ${produit.unit == "" ? "selected" : ""}>      Sélectionnez l'unité  </option>
                                    <option value="mg"  ${produit.unit == "mg" ? "selected" : ""}>      mg                    </option>
                                    <option value="mg"  ${produit.unit == "mg" ? "selected" : ""}>      mg                    </option>
                                    <option value="g"   ${produit.unit == "g" ? "selected" : ""}>       g                     </option>
                                    <option value="kg"  ${produit.unit == "kg" ? "selected" : ""}>      kg                    </option>
                                    <option value="mL"  ${produit.unit == "mL" ? "selected" : ""}>      mL                    </option>
                                    <option value="L"   ${produit.unit == "L" ? "selected" : ""}>       L                     </option>
                                    <option value="cas" ${produit.unit == "cas" ? "selected" : ""}>     Cuillère à soupe      </option>
                                    <option value="cac" ${produit.unit == "cac" ? "selected" : ""}>     Cuillère à café       </option>
                                    <option value="pincee" ${produit.unit == "pincee" ? "selected" : ""}>     Pincée       </option>
                                </select>
                            </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="form-row">
                            <div class="form-group col-7">
                                <select name="product_0[]" class="form-control" required>
                                    <option value="">Sélectionnez</option>
                                    <c:forEach var="p" items="${products}">
                                        <option value="${p.id}">${p.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col form-group">
                                <input type="number" step="0.1" class="form-control" name="product_0[]" placeholder="Quantite" required/>
                            </div>
                            <div class="col form-group">
                                <select name="product_0[]" class="form-control" >
                                    <option value="">Sélectionnez l'unité</option>
                                    <option value="mg">mg</option>
                                    <option value="g">g</option>
                                    <option value="kg">kg</option>
                                    <option value="mL">mL</option>
                                    <option value="l">L</option>
                                    <option value="cas">Cuillère à soupe</option>
                                    <option value="cac">Cuillère à café</option>
                                    <option value="pincee" ${produit.unit == "pincee" ? "selected" : ""}>     Pincée       </option>
                                </select>
                            </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
            </div>
            <div class="form-group">
                <button class="btn btn-info" id="addProduct">Ajouter un produit</button>
                <button class="btn btn-info" id="deleteProduct">Retirer un produit</button>
                <small id="productHelp" class="form-text text-muted">La recette doit comprendre au minimum un produit.</small>
            </div>
            <button type="submit" class="btn btn-primary">${prefixe}</button>
        </form>
        <script type="text/javascript">
            var cpt = ${compteur + 1};
            document.getElementById("addProduct").onclick = function(e){
                e.preventDefault();
                let div = document.getElementById("divProducts");
                var divCreated = document.createElement('div');
                divCreated.className = "form-row";
                $(divCreated).html('<div class="form-group col-7">'+
                                '<select name="product_'+cpt+'[]" class="form-control" required>'+
                                    '<option value="">Sélectionnez</option>'+
                                    <c:forEach var="p" items="${products}">
                                        "<option value='${p.id}'>${p.name}</option>"+
                                    </c:forEach>
                                '</select>'+
                            '</div>'+
                            '<div class="col form-group">'+
                                '<input type="number" step="0.1" class="form-control" name="product_'+cpt+'[]" placeholder="Quantite" required/>'+
                            '</div>'+
                            '<div class="col form-group">'+
                                '<select name="product_'+cpt+'[]" class="form-control" >'+
                                    '<option>Sélectionnez l\'unité</option>'+
                                    '<option value="mg">mg</option>'+
                                    '<option value="g">g</option>'+
                                    '<option value="kg">kg</option>'+
                                    '<option value="mL">mL</option>'+
                                    '<option value="l">L</option>'+
                                    '<option value="cas">Cuillère à soupe</option>'+
                                    '<option value="cac">Cuillère à café</option>'+
                                    '<option value="pincee" ${produit.unit == "pincee" ? "selected" : ""}>     Pincée       </option>' +
                                '</select>'+
                            '</div>');
                cpt++;
                div.appendChild(divCreated);
            };
            
            document.getElementById("deleteProduct").onclick = function(){
                var divProduit = document.getElementById("divProducts");
                if(divProduit.children.length > 2){
                    divProduit.lastElementChild.remove();
                    cpt--;
                }
                return false;
                
            }
            function deleteProductLine(elem){
                var div = elem.parentNode.parentNode;
                cpt--;
                div.remove();
                return false;
            }
            
        </script>
    </jsp:body>
</t:layout>