<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,controleur.*,modele.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1" />
        <title>Ajouter un défi</title>
        <link type="text/css" rel="stylesheet" href="CSS/form.css" />
    </head>
    <body>
		<% Groupe g = ((Groupe) session.getAttribute("groupe"));
		   if(g == null){
			   %> <p> Erreur vous n'avez pas sélectionné de groupe </p> <% 
		   }else{
		%>
        <form method="post" action="Serveur">
            <fieldset>
                <legend>Ajouter un défi</legend>
                <p>Ajouter un défi pour le groupe <%=g.getNom()%></p>
                <label for="nom">Nom du défi: <span class="requis">*</span></label>
                <input type="text" id="nom" name="nom" value="" size="20" maxlength="60" />
                <br />
                <label for="description">Description: <span class="requis">*</span></label>
                <input type="text" id="description" name="description" value="" size="20" maxlength="60" />
                <br />
          		<label for="points">Nombre de points: <span class="requis">*</span></label>
                <input type="text" id="points" name="points" value="" size="20" maxlength="60" />
                <br />
                <input type="submit" value="Ajouter ce défi" class="sansLabel" />
                <br />
				<input type="hidden" value="ajouterDefi" name="action"/>
            </fieldset>
        </form>
        <% } %>
</body>
</html>