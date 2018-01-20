<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">Home
    </jsp:attribute>
    <jsp:body>
        <h1>Accueil</h1>
        <div class="row">
            <h2>Bienvenue ${user.username}</h2>
        </div>
    </jsp:body>
</t:layout>
