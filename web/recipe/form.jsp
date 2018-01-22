<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var = "prefixe" scope = "page" value = '${!empty recipe ? "Modifier" : "Ajouter"}'/>
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
                <input <c:if test="${!empty recipe}">value="${recipe.name}"</c:if> type="text" class="form-control" id="name" name="name" aria-describedby="nameHelp" placeholder="Nom de la recette">
                <small id="nameHelp" class="form-text text-muted">3 caractères minimum.</small>
            </div>

            <div class="form-group">
                <label for="image">Image</label>
                <input <c:if test="${!empty recipe}">value="${recipe.image}"</c:if> type="file" class="form-control" id="name" name="name" aria-describedby="imageHelp" placeholder="Nom de la recette">
                <small id="imageHelp" class="form-text text-muted">Une image est requise.</small>
            </div>

            <div class="form-group">
                <label for="categorie">Categorie</label>
                <select name="product[]" class="form-control" >
                    <option>Sélectionnez</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}" ${recipe.category.id == c.id ? "selected" : ""}>${c.name}</option>
                    </c:forEach>
                </select>
                <small id="categoryHelp" class="form-text text-muted">Une categorie doit être assignée à la recette.</small>
            </div>

            <div class="form-group">
                <label for="name">Temps de prépartion</label>
                <input <c:if test="${!empty recipe}">value="${recipe.preparationTime}"</c:if> type="time" class="form-control" id="preparationTime" name="preparationTime" aria-describedby="preparationTimeHelp" placeholder="Temps de préparation">
                <small id="nameHelp" class="form-text text-muted">Temps en minutes, ne peut pas être inférieur ou égal à 0.</small>
            </div>

            <div class="form-group">
                <label for="name">Temps de cuisson</label>
                <input <c:if test="${!empty recipe}">value="${recipe.cookingTime}"</c:if> type="time" class="form-control" id="cookingTime" name="cookingTime" aria-describedby="cookingTimeHelp" placeholder="Temps de cuisson">
                <small id="nameHelp" class="form-text text-muted">Temps en minutes, ne peut pas être inférieur à 0.</small>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea placeholder="Description de la recette" form="recipeFrom" class="form-control" >
                    <c:if test="${!empty recipe}">${recipe.name}</c:if>
                </textarea>
                <small id="descriptionHelp" class="form-text text-muted">Il faut une description de la recette.</small>
            </div>
            <div class="form-group">
                <label for="produits">Produits</label>
                <div id="divProducts">
                    <c:choose>
                        <c:when test="${!empty recipe.products}">
                            <c:forEach var="produit" items="${recipe.products}">
                                <select name="product[]" class="form-control" >
                                    <option>Sélectionnez...</option>
                                    <c:forEach var="p" items="${products}">
                                        <option value="${p.id}">${p.name}</option>
                                    </c:forEach>
                                </select>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <select name="product[]" class="form-control" >
                                <option>Sélectionnez</option>
                                <c:forEach var="p" items="${products}">
                                    <option value="${p.id}">${p.name}</option>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <button class="btn btn-info" id="addProduct" click="addProduct(this)">Ajouter un produit</button>
                <small id="productHelp" class="form-text text-muted">La recette doit comprendre au minimum un produit.</small>
            </div>
            <button type="submit" class="btn btn-primary">${prefixe}</button>
        </form>
        <script type="text/javascript">
            document.getElementById("addProduct").onclick = function(e){
                e.preventDefault();
                let div = document.getElementById("divProducts")
                let select = document.createElement("select");
                select.innerHTML =  '<c:forEach var="p" items="${products}">' +
                                    '<option>Sélectionnez</option>' +
                                        '<option value="${p.id}">${p.name}</option>' +
                                    '</c:forEach>';
                select.className = "form-control";
                select.name = "products[]";
                div.appendChild(select);
                return false;
            }
        </script>
    </jsp:body>
</t:layout>