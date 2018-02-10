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
            <table class="table">
                <thead>
                    <tr>
                        <td></td>
                        <td>Image</td>
                        <td>Name</td>
                        <td>Catégorie</td>
                        <td>Crée par</td>
                        <td>Note</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="recipe" items="${recipes}">
                        <tr>
                            <td><a class="btn btn-success" href="${pageContext.servletContext.contextPath}/recipes/show?id=${recipe.id}"><i class="fas fa-search"></i> Voir</a></td>
                            <td><img src='${recipe.image}' width="64" height="64"/></td>
                            <td>${recipe.name}</td>
                            <td>${recipe.category.name}</td>
                            <td>${recipe.createdBy.username}</td>
                            <td>${recipe.mark != null ? recipe.formattedMark : "Non notée" }</td>
                            <%--<td>
                                <c:if test="${!empty user && user.recipes.contains(recipe)}">
                                    <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/edit?id=${recipe.id}"><i class="fas fa-edit"></i></a>
                                    <form style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/recipes/delete?id=${recipe.id}">
                                        <button class="btn btn-danger"><i class="fas fa-trash-alt"></i>&nbsp;</button>
                                    </form>
                                </c:if>
                            </td>--%>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/add">Ajouter une recette</a>
    </jsp:body>
</t:layout>
