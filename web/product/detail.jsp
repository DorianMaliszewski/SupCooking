<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:attribute name="title">Details d'un produit
    </jsp:attribute>
    <jsp:body>
        <h1>Produit #${product.id}</h1>
        <div class="row">
            <div class="col-md-4">
                Id :
            </div>
            <div class="col-md-8">
                ${product.id}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Nom :
            </div>
            <div class="col-md-8">
                ${product.name}
            </div>
        </div>
    </form>
</jsp:body>
</t:layout>