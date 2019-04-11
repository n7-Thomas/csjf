<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, controleur.*, modele.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="ISO-8859-1" />
        <title>Groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/form.css" />
</head>
<body>
	<%
		//Membre mb = (Membre) session.getAttribute("user");
		if (session.getAttribute("user") == null) {
	%>
		<p>Vous n'êtes pas connectés.</p>
	<%
		} else {
			Membre mb = (Membre) session.getAttribute("user");

			if (session.getAttribute("groupe") == null) {
	%>
	<p>Pas de groupe administré sélectionné.</p>
	<a href="index.html">Aller à l'index</a><br>
	<%
			} else {
				Groupe groupe = (Groupe) session.getAttribute("groupe");
	
				if(groupe.getDefis() != null)
		%>
			
				<div id="afficherDefisGroupe">
					<table>
				   		<tr>
				    		<th>Description</th>
				       		<th>Points à gagner</th>
				       		<th>État</th>
				   		</tr>
				
				<% 	
				for (Defi defi : groupe.getDefis()) {
					if (defi == null) {
				%>
						<tr>
							<td>Aucun défi à afficher</td>
				       		<td>RIEN</td>
				       		<td>RIEN</td>
				       	</tr>
				<%
					} else {
				%>
						<tr>
					       	<td><%=defi.getDescription() %></td>
					       	<td><%=defi.getPoints() %></td>
					       	<td>Pas encore de type</td>
					   	</tr>
				<% 	
					}
				}
				%>
					</table>
				</div>
	
	<%
			}
		}
	%>
</body>
</html>