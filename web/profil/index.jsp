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
        <a style="float:right" class="btn btn-info" href="${pageContext.servletContext.contextPath}/profile/edit">Modifier mes informations</a>
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
                Nombre de recettes publiées :
            </div>
            <div class="col-md-8">
                ${fn:length(user.recipes)}
            </div>
        </div>
            <hr>
        <h1>Vos recettes</h1>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <td></td>
                        <td>Image</td>
                        <td>Name</td>
                        <td>Catégorie</td>
                        <td>Crée par</td>
                        <td>Note</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="recipe" items="${user.recipes}">
                        <tr>
                            <td><a class="btn btn-success" href="${pageContext.servletContext.contextPath}/recipes/show?id=${recipe.id}"><i class="fas fa-search"></i> Voir</a></td>
                            <td><img src="${recipe.image}" width="64" height="64"/></td>
                            <td>${recipe.name}</td>
                            <td>${recipe.category.name}</td>
                            <td>${recipe.createdBy.username}</td>
                            <td>${recipe.mark != null ? recipe.formattedMark : "Non notée" }</td>
                            <td>
                                <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/edit?id=${recipe.id}"><i class="fas fa-edit"></i></a>
                                <form style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/recipes/delete?id=${recipe.id}">
                                    <button class="btn btn-danger"><i class="fas fa-trash-alt"></i>&nbsp;</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:layout>
