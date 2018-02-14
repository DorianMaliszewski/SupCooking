<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout>
    <jsp:attribute name="title">Home
    </jsp:attribute>
    <jsp:body>
        <h1>Résultat de la recherche</h1>
        <div class="table-responsive">
            <strong>Cliquez sur la recette que vous souhaitez visualiser</strong>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Nom de la recette</th>
                        <th>Catégorie</th>
                        <th>Crée par</th>
                        <th>Note</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="recipe" items="${recipes}">
                        <tr onClick="location.href = '${pageContext.servletContext.contextPath}/recipes/show?id=${recipe.id}'">
                            <td><img src='${recipe.image}' width="64" height="64" style="object-fit: cover"/></td>
                            <td>${recipe.name}</td>
                            <td>${recipe.category.name}</td>
                            <td>${recipe.createdBy.username}</td>
                            <td>${recipe.mark != null ? recipe.formattedMark : "Non notée" }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:layout>
