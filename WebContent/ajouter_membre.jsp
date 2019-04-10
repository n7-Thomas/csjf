<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,controleur.*,modele.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajouter un membre à la main</title>
</head>
<body>
		<% Groupe g = ((Groupe) session.getAttribute("groupe"));
		   if(g == null){
			   %> <p> Erreur vous n'avez pas sélectionné de groupe </p> <% 
		   }else{
		%>
        <form method="post" action="Serveur">
            <fieldset>
                <legend>Ajouter un membre au groupe</legend>
                <p>Ajouter un membre pour le groupe <%=g.getNom()%></p>
                <label for="email">Email du nouveau membre: <span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="" size="20" maxlength="60" />
                <br />
                <input type="submit" value="Ajouter ce membre" class="sansLabel" />
                <br />
				<input type="hidden" value="ajouterMembre" name="action"/>
            </fieldset>
        </form>
        <% } %>
</body>
</html>