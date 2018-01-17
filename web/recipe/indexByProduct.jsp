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
        <c:if test="${message != null}">
            <c:choose>
                <c:when test="${success != null}">
                    <div class="alert alert-${success == true ? "success" : "danger"}" role="alert">
                        ${message}
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info" role="alert">
                        ${message}
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>
        <h1>Toutes les recettes par produits</h1>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <td>Id</td>
                        <td>Image</td>
                        <td>Name</td>
                        <td>Description</td>
                        <td>Catégorie</td>
                        <td>Crée par</td>
                        <td>Note</td>
                    </tr>
                </thead>
                <tbody>

                <c:forEach var="recipe" items="${recipes}">
                    <tr>
                        <td>${recipe.id}</td>
                        <td>${recipe.image}</td>
                        <td>${recipe.name}</td>
                        <td>${recipe.description}</td>
                        <td>${recipe.category.name}</td>
                        <td>${recipe.createdBy}</td>
                        <td>${recipe.mark}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:layout>
