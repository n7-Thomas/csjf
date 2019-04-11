<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, controleur.*, modele.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
				
    			String status = (String) request.getAttribute("status");
				if(status != null){ %> <p> Status : <%=status %> </p> <% }
	%>
		
				<div id="afficherDefisGroupe">
					<table>
		   				<tr>
		    				<th>Description</th>
		       				<th>Points à gagner</th>
		       				<th>État</th>
		   				</tr>
		<%
						if(groupe.getDefis() != null) {
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
						} else {
							%>
							<p>groupe.getDefis est null</p>
							<%
						}
				%>
					</table>
				</div>
				
				
				<div id="demandeValiderDefi">
					<p> Remplissez le formulaire suivant pour demander à valider un de vos défis. </p>
					<form method="post" action="">
						<label for="defi">Quel défi voulez-vous valider ?</label><br/>
			       		<select name="defi" id="defi">
			       			<%
			       			if (groupe.getDefis() == null) {
			       				%>
			   					<option value="rien">Aucun</option>
			   					<%
			       			} else {
				       			for (Defi defi : groupe.getDefis()) {
				       				%>
				           			<option value="<%=defi.getNom()%>"><%=defi.getNom() %></option> 
				           			<%
				       			}
			           		}
			           		%>
			       		</select>
			       		<input type="submit" value="Envoyer" />
			       		<input type="hidden" value="ajouterDefiAValider" name="action">
			       		<% //String err = (String) request.getAttribute("erreur");
			       		//String succ = (String) request.getAttribute("succes"); %>
			       		<span class="erreur"><%//=err %></span>
			       		<span class="succes"><%//=succ %></span>
					</form>
				</div>
	
	<%
			}
		}
	%>
</body>
</html>