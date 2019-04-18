<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,controleur.*,modele.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1" />
        <title>Création d'un groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/form.css" />
    </head>
    <body>
    	<% String status = (String) request.getAttribute("status");
		if(status != null){ %> <p> Status : <%=status %> </p> <% }
		%>
    
  
        <form method="post" action="ServeurGroupe">
            <fieldset>
                <legend>Creer un groupe dont vous serez l'administrateur</legend>
                <label for="nom">Nom du groupe: <span class="requis">*</span></label>
                <input type="text" id="nom" name="nom" size="20" maxlength="60" />
                <br />
                <input type="submit" value="Créer ce groupe" class="sansLabel" />
                <br />
				<input type="hidden" value="creerGroupe" name="action">
            </fieldset>
        </form>
</body>
</html>