<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Produits
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
        <h1>Tous les produits</h1>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <td>Id</td>
                        <td>Nom</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>
                            <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/products/edit?id=${product.id}">Edit</a>
                            <form style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/products/delete?id=${product.id}">
                                <button class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/products/add">Ajouter un produit</a>
        </div>
    </jsp:body>
</t:layout>
