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
        <h1>Toutes les categories</h1>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <td>Id</td>
                        <td>Nom</td>
                        <td>Actions</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.name}</td>
                        <td>
                            <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/categories/edit?id=${category.id}">Edit</a>
                            <form style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/categories/delete?id=${category.id}">
                                <button class="btn btn-danger">Delete</button>
                            </form>
                        </td>
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
