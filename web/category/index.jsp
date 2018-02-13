<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Categories
    </jsp:attribute>
    <jsp:body>        
        <h1>Toutes les categories</h1>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nom</th>
                        <c:if test="${user.role == 'ROLE_ADMIN'}"><th></th></c:if>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.name}</td>
                        <c:if test="${user.role == 'ROLE_ADMIN'}">
                            <td>
                                <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/categories/edit?id=${category.id}"><i class="fas fa-edit"></i></a>
                                <form style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/categories/delete?id=${category.id}">
                                    <button class="btn btn-danger"><i class="fas fa-trash-alt"></i>&nbsp;</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/categories/add">Ajouter une categorie</a>
        </div>
    </jsp:body>
</t:layout>
