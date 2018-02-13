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
        <h1>Tous les produits</h1>
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
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <c:if test="${user.role == 'ROLE_ADMIN'}">
                            <td>
                                <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/products/edit?id=${product.id}"><i class="fas fa-edit"></i></a>
                                <form style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/products/delete?id=${product.id}">
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
            <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/products/add">Ajouter un produit</a>
        </div>
    </jsp:body>
</t:layout>
