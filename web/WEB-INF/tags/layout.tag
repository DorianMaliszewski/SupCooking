<%-- 
    Document   : layout
    Created on : 16 janv. 2018, 14:16:29
    Author     : MaliszewskiDorian
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Page template" pageEncoding="UTF-8"%>

<%@attribute name="title" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>SupCooking - ${title}</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
        <style>
            html {
                position: relative;
                min-height: 100%;
            }
            body {
                /* Margin bottom by footer height */
                margin-bottom: 80px;
            }
            .footer {
                position: absolute;
                bottom: 0;
                width: 100%;
                /* Set the fixed height of the footer here */
                height: 60px;
                line-height: 60px; /* Vertically center the text there */
                background-color: #f5f5f5;
            }


            /* Custom page CSS
            -------------------------------------------------- */
            /* Not required for template or sticky footer method. */

            body > .container {
                padding: 80px 15px 0;
            }

            .footer > .container {
                padding-right: 15px;
                padding-left: 15px;
            }

            code {
                font-size: 80%;
            }
        </style>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
                <a class="navbar-brand" href="#">SupCooking</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.request.contextPath}/">Accueil<span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item ${pageContext.request.servletContext.contextPath}">
                            <a class="nav-link" href="${pageContext.request.contextPath}/recipes">Toutes les recettes</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/recipes/category">Par catégorie</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/recipes/product">Par produits</a>
                        </li>
                        <c:if test="${!empty user}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="javascript:void(0);" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Administration</a>
                                <div class="dropdown-menu" aria-labelledby="dropdown01">
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/products">Produits</a>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/categories">Categories</a>
                                </div>
                            </li>
                        </c:if>
                    </ul>
                    <a style="margin-right: 5px;" class="btn btn-outline-info" href="${pageContext.request.contextPath}${empty user ? "/login" : "/profile"}">${empty user ?"Se connecter" : "Profil"}</a>
                    <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/search">
                        <input class="form-control mr-sm-2" placeholder="Rechercher" aria-label="Search" type="text">
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Rechercher</button>
                    </form>
                </div>
            </nav>
        </header>
        <main role="main" class="container">
            <jsp:doBody/>
        </main>
        <footer class="footer">
            <div class="container">SupCooking</div>
        </footer>
    </body>
</html>