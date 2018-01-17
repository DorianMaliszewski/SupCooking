<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var = "prefixe" scope = "page" value = '${!empty category ? "Modifier" : "Ajouter"}'/>
<t:layout>
    <jsp:attribute name="title">${prefixe} une catégorie
    </jsp:attribute>
    <jsp:body>
        <h1>${prefixe} une catégorie</h1>
        <c:choose>
            <c:when test="${!empty category}">
                <form method="post" action="${pageContext.servletContext.contextPath}/categories/edit?id=${category.id}">
            </c:when>    
            <c:otherwise>
                <form method="post" action="${pageContext.servletContext.contextPath}/categories/add">
            </c:otherwise>
        </c:choose>
            <div class="form-group">
                <label for="name">Nom de la catégorie</label>
                <input <c:if test="${!empty category}">value="${category.name}"</c:if> type="text" class="form-control" id="name" name="name" aria-describedby="nameHelp" placeholder="Nom de la catégorie">
                <small id="nameHelp" class="form-text text-muted">3 caractères minimum.</small>
            </div>
            <button type="submit" class="btn btn-primary">${prefixe}</button>
        </form>
    </jsp:body>
</t:layout>