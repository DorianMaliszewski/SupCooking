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
              <div id="myCarousel" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                  <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                  <li data-target="#myCarousel" data-slide-to="1"></li>
                  <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                  <div class="carousel-item active">
                      <img class="first-slide" src="${pageContext.servletContext.contextPath}/css/slide1.jpg" style="object-fit: cover" alt="First slide">
                    <div class="container">
                      <div class="carousel-caption text-left">
                        <h1>SupCooking</h1>
                        <p>Votre référence pour la cuisine étudiante.</p>
                        <p><a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/register" role="button">S'inscrire</a></p>
                      </div>
                    </div>
                  </div>
                  <div class="carousel-item">
                    <img class="second-slide" src="${pageContext.servletContext.contextPath}/css/slide2.jpeg" style="object-fit: cover" alt="Second slide">
                    <div class="container">
                      <div class="carousel-caption">
                        <h1>SupCooking c'est ...</h1>
                        <p>${recipesNumber} recette${recipesNumber > 1 ? "s" : ""} en ligne</p>
                        <p>${usersNumber} utilisateur${usersNumber > 1 ? "s" : ""} inscrit${usersNumber > 1 ? "s" : ""}</p>
                      </div>
                    </div>
                  </div>
                  <div class="carousel-item">
                    <img class="third-slide" src="${pageContext.servletContext.contextPath}/css/slide3.jpg" style="object-fit: cover" alt="Third slide">
                    <div class="container">
                      <div class="carousel-caption text-right">
                        <h1>Gérer vos répas comme un chef</h1>
                        <p>Supcooking est à destination des étudiants afin de leurs permettre de gérer leurs repas de leur vie quotidienne</p>
                        <p><a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/recipes" role="button">Voir nos recettes</a></p>
                      </div>
                    </div>
                  </div>
                </div>
                <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  <span class="sr-only">Next</span>
                </a>
              </div>

        <hr>
        <h2>Nos dernières recettes</h2>
        <div class="row">
            <c:forEach var="recipe" items="${recipes}">
                <div class="col-md-4">
                    <div class="card mb-4 box-shadow">
                        <img class="card-img-top" data-src="${recipe.image}" alt="${recipe.name}" style="height: 225px; width: 100%; display: block;object-fit: cover" src="${recipe.image}" data-holder-rendered="true">
                        <div class="card-body">
                            <p class="card-text">${!empty recipe.numberOfView ? recipe.numberOfView : "0" } vue${recipe.numberOfView > 1 ? "s" : ""} - ${!empty recipe.numberOfMark ? recipe.numberOfMark : "0" } note${recipe.numberOfMark > 1 ? "s" : ""} - ${recipe.formattedMark}</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                    <a href="${pageContext.servletContext.contextPath}/recipes/show?id=${recipe.id}" class="btn btn-sm btn-outline-success" title="Voir la recette"><i class="fas fa-search"></i></a>
                                </div>
                                <small class="text-muted">${recipe.preparationTime} mins de préparation</small>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </jsp:body>
</t:layout>
