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
                        <td></td>
                        <td>Id</td>
                        <td>Image</td>
                        <td>Name</td>
                        <td>Description</td>
                        <td>Catégorie</td>
                        <td>Crée par</td>
                        <td>Note</td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="recipe" items="${user.recipes}">
                        <tr>
                            <td><a href="${pageContext.servletContext.contextPath}/recipes/show?id=${recipe.id}">Voir</a></td>
                            <td>${recipe.id}</td>
                            <td><img src='${recipe.image}' width="64" height="64"/></td>
                            <td>${recipe.name}</td>
                            <td>${recipe.description}</td>
                            <td>${recipe.category.name}</td>
                            <td>${recipe.createdBy.username}</td>
                            <td>${recipe.mark != null ? recipe.formattedMark : "Non notée" }</td>
                            <td>
                                <a class="btn btn-info" href="${pageContext.servletContext.contextPath}/recipes/edit?id=${recipe.id}"><i class="fas fa-edit"></i></a>
                                <form id="deleteForm" style="display: inline;" method="post" action="${pageContext.servletContext.contextPath}/recipes/delete?id=${recipe.id}">
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
            document.getElementById("deleteForm").onsubmit = function(event){
                var rep = confirm("Etes-vous sur de vouloir supprimer cette recette ?");
                if(rep){
                    console.log("Accepter");
                    return true;
                }
                else{
                    console.log("Refuser");
                    return false;
                }
            }
        </script>
    </jsp:body>
</t:layout>
