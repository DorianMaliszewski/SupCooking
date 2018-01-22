<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<t:layout>
    <jsp:attribute name="title">Profil
    </jsp:attribute>
    <jsp:body>
        <c:if test="${!empty message}">
            <c:choose>
                <c:when test="${!empty success}">
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
        <h1>${user.firstName}&nbsp;${user.lastName}</h1>
        <div class="row">
            <div class="col-md-4">
                Username : 
            </div>
            <div class="col-md-8">
                ${user.username}
            </div>
            <div class="col-md-4">
                Nom complet :
            </div>
            <div class="col-md-8">
                ${user.firstName}&nbsp;${user.lastName}
            </div>
            <div class="col-md-4">
                Email :
            </div>
            <div class="col-md-8">
                ${user.email}
            </div>
            <div class="col-md-4">
                Nombre de recettes publi�es :
            </div>
            <div class="col-md-8">
                ${fn: length(user.recipes)}
            </div>
        </div>
            <hr>
        <h1>Vos recettes</h1>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <td></td>
                        <td>Id</td>
                        <td>Image</td>
                        <td>Name</td>
                        <td>Description</td>
                        <td>Cat�gorie</td>
                        <td>Cr�e par</td>
                        <td>Note</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="recipe" items="${user.recipes}">
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
    </jsp:body>
</t:layout>