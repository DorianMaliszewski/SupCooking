<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var = "prefixe" scope = "page" value = '${!empty recipe ? "Modifier" : "Ajouter"}'/>
<t:layout>
    <jsp:attribute name="title">${prefixe} une recette
    </jsp:attribute>
    <jsp:body>
        
        <h1>${prefixe} un produit</h1>
        <c:choose>
            <c:when test="${!empty recipe}">
                <form method="post" action="${pageContext.servletContext.contextPath}/recipes/edit?id=${recipe.id}">
            </c:when>    
            <c:otherwise>
                <form method="post" action="${pageContext.servletContext.contextPath}/recipes/add">
            </c:otherwise>
        </c:choose>
        <div class="form-group">
            <label for="name">Nom du produit</label>
            <input <c:if test="${!empty recipe}">value="${recipe.name}"</c:if> type="text" class="form-control" id="name" name="name" aria-describedby="nameHelp" placeholder="Nom du produit">
            <small id="nameHelp" class="form-text text-muted">3 caractères minimum.</small>
        </div>
        <button type="submit" class="btn btn-primary">${prefixe}</button>
    </form>
</jsp:body>
</t:layout>