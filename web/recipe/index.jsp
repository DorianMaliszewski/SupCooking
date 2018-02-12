<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Recettes
    </jsp:attribute>
    <jsp:body>
        <h1>Toutes les recettes</h1>
        <div class="table-responsive">
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
                            <td><img src='${recipe.image}' width="64" height="64"/></td>
                            <td>${recipe.name}</td>
                            <td>${recipe.category.name}</td>
                            <td>${recipe.createdBy.username}</td>
                            <td>${recipe.mark != null ? recipe.formattedMark : "Non notée" }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/add">Ajouter une recette</a>
    </jsp:body>
</t:layout>
