<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<t:layout>
    <jsp:attribute name="title">${recipe.name}
    </jsp:attribute>
    <jsp:body>
        <h1>${recipe.name}</h1>
        <div class="row">
            <div class="col-md-4">
                Note
            </div>
            <div class="col-md-8">
                ${recipe.mark}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Image
            </div>
            <div class="col-md-8">
                <img src="${recipe.image}" alt="${recipe.image}"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Description
            </div>
            <div class="col-md-8">
                ${recipe.description}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Catégorie
            </div>
            <div class="col-md-8">
                ${recipe.category.name}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Crée par
            </div>
            <div class="col-md-8">
                ${recipe.createdBy}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Nombre de vue
            </div>
            <div class="col-md-8">
                ${recipe.numberOfView}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Nombre de notes
            </div>
            <div class="col-md-8">
                ${recipe.numberOfMark}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Difficulté
            </div>
            <div class="col-md-8">
                ${recipe.difficulty}
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Temps de cuisson
            </div>
            <div class="col-md-8">
                ${recipe.cookingTime} minutes
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Temps de préparation
            </div>
            <div class="col-md-8">
                ${recipe.preparationTime} minutes
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                Nombre d'ingrédient
            </div>
            <div class="col-md-8">
                ${fn:length(recipe.products)}
            </div>
        </div>
            <hr>
        <div class="row">
            <h3>Ingrédients</h3>
            <c:forEach items="${recipe.products}" var="product">
                <p>${product.product.name}</p>
            </c:forEach>
        </div>
    </jsp:body>
</t:layout>
