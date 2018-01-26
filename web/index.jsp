<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Home
    </jsp:attribute>
    <jsp:body>
        <div class="jumbotron">
            <h1 class="display-4">SupCooking c'est...</h1>
            <p class="lead">${recipesNumber} recettes en ligne</p>
            <p class='lead'>${usersNumber} utilisateurs inscrits</p>
        </div>
    </jsp:body>
</t:layout>
