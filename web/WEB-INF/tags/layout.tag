<%-- 
    Document   : layout
    Created on : 16 janv. 2018, 14:16:29
    Author     : MaliszewskiDorian
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Page template" pageEncoding="utf-8"%>

<%@attribute name="title" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>SupCooking - ${title}</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
        <script
			  src="http://code.jquery.com/jquery-3.3.1.min.js"
			  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
			  crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css" />
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
                <a class="navbar-brand" href="${pageContext.servletContext.contextPath}">
                    <img width="50" height="45" src="${pageContext.servletContext.contextPath}/css/brand.png"/>
                    &nbsp;
                    SupCooking
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/">Accueil</a>
                        </li>
                        <li class="nav-item ${pageContext.request.servletContext.contextPath}">
                            <a class="nav-link" href="${pageContext.request.contextPath}/recipes">Toutes les recettes</a>
                        </li>
                        <c:if test="${!empty user}">
                            <li class="nav-item ${pageContext.request.servletContext.contextPath}">
                                <a class="nav-link" href="${pageContext.request.contextPath}/myrecipes">Mes recettes</a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="javascript:void(0);" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Administration</a>
                                <div class="dropdown-menu" aria-labelledby="dropdown01">
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/products">Produits</a>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/categories">Categories</a>
                                </div>
                            </li>
                        </c:if>
                    </ul>
                    <c:choose>
                        <c:when test="${!empty user}">
                            <a style="margin-right: 5px;" class="btn btn-outline-info" href="${pageContext.request.contextPath}/profile"><i class="fas fa-user"></i> Profil</a>
                            <a style="margin-right: 5px;" class="btn btn-outline-info" href="${pageContext.request.contextPath}/logout">Se déconnecter</a>
                        </c:when>
                        <c:otherwise>
                            <a style="margin-right: 5px;" class="btn btn-outline-info" href="${pageContext.request.contextPath}/login">Se connecter</a>
                        </c:otherwise>
                    </c:choose>
                            <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/search" method="get">
                        <input class="form-control mr-sm-2" name="s" placeholder="Rechercher" aria-label="Search" type="text">
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><i class="fas fa-search"></i></button>
                    </form>
                </div>
            </nav>
        </header>
        <main role="main" class="container">
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
            <jsp:doBody/>
        </main>
        <footer class="footer">
            <div class="container">SupCooking</div>
        </footer>
    </body>
</html>