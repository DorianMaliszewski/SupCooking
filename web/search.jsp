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
                        <td>${recipe.createdBy}</td>
                        <td>${recipe.mark != null ? recipe.formattedMark : "Non notée" }</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:layout>
