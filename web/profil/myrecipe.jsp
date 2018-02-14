<%-- 
    Document   : index
    Created on : 16 janv. 2018, 14:06:58
    Author     : MaliszewskiDorian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<t:layout>
    <jsp:attribute name="title">Profil
    </jsp:attribute>
    <jsp:body>
        <h1>Vos recettes</h1>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th></th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Catégorie</th>
                        <th>Crée par</th>
                        <th>Note</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="recipe" items="${user.recipes}">
                        <tr>
                            <td><a class="btn btn-success" href="${pageContext.servletContext.contextPath}/recipes/show?id=${recipe.id}"><i class="fas fa-search"></i> Voir</a></td>
                            <td><img src='${recipe.image}' width="64" height="64" style="object-fit: cover"/></td>
                            <td>${recipe.name}</td>
                            <td>${recipe.category.name}</td>
                            <td>${recipe.createdBy.username}</td>
                            <td>${recipe.mark != null ? recipe.formattedMark : "Non notée" }</td>
                            <td>
                                <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/edit?id=${recipe.id}"><i class="fas fa-edit"></i></a>
                                <form class="deleteForm" style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/recipes/delete?id=${recipe.id}">
                                    <button class="btn btn-danger"><i class="fas fa-trash-alt"></i>&nbsp;</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/add">Ajouter une recette</a>
        
        
        <script type="text/javascript">
            Array.from(document.getElementsByClassName("deleteForm")).forEach(function(form){
                form.onsubmit = function(event){ return confirm("Etes-vous sur de vouloir supprimer cette recette ?"); }
            });
        </script>
    </jsp:body>
</t:layout>
