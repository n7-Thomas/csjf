<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Administration du groupe</title>
</head>
<body>
	<% String status = (String) request.getAttribute("status");
		if(status != null){ %> <p> Status : <%=status %> </p> <% }
	%>
	
	<%
		Membre mb = (Membre) session.getAttribute("user");
		if (mb == null) {
	%>
	<p>Vous n'êtes pas connectés.</p>
	<%
		} else {
			Groupe grp = (Groupe) session.getAttribute("groupe");

			if (grp == null) {
	%>
	<p>Pas de groupe administré sélectionné.</p>
	<a href="creer_groupe.jsp">Créer groupe</a>	<br>
	<%
		} else {
	%>
	<p>	Administrer le groupe <%=grp.getNom()%></p>

	<a href="ajouter_membre.jsp">Ajouter des membres</a> <br>
	<a href="ajouter_defi.jsp">Ajouter des défis</a> <br>
	<a href="ServeurGroupe?action=validerDefis">Valider des défis</a> <br>
	<a href="ServeurGroupe?action=validerDemandesARejoindre">Valider des demandes à rejoindre</a> <br>
	<%
		}

		}
	%>



</body>
</html>