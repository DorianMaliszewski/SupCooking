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
                        <td>Id</td>
                        <td>Image</td>
                        <td>Name</td>
                        <td>Description</td>
                        <td>Catégorie</td>
                        <td>Crée par</td>
                        <td>Note</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="recipe" items="${recipes}">
                        <tr>
                            <td><a href="${pageContext.servletContext.contextPath}/recipes/show?id=${recipe.id}">Voir</a></td>
                            <td>${recipe.id}</td>
                            <td>${recipe.image}</td>
                            <td>${recipe.name}</td>
                            <td>${recipe.description}</td>
                            <td>${recipe.category.name}</td>
                            <td>${recipe.createdBy}</td>
                            <td>${recipe.mark}</td>
                            <td>
                                <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/edit?id=${recipe.id}">Edit</a>
                                <form style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/recipes/delete?id=${recipe.id}">
                                    <button class="btn btn-danger">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/add">Ajouter une recette</a>
    </jsp:body>
</t:layout>
