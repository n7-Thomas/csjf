<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, controleur.*, modele.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <title>Groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/groupe.css" />
</head>
<body>
	<div id="bandeau">
		<h1 id="titre">Page du groupe</h1>
	</div>
	<div id="corps">
	<%
		if (session.getAttribute("user") == null) {
	%>
			<p>Vous n'êtes pas connectés.</p>
	<%
		} else {
			Membre mb = (Membre) session.getAttribute("user");

			if (request.getAttribute("groupe") == null) {
	%>
			<p>Pas de groupe sélectionné.</p>
			<a href="index.html">Aller à l'index</a><br>
	<%
			} else {
				Groupe groupe = (Groupe) request.getAttribute("groupe");
				
    			String status = (String) request.getAttribute("status");
				if(status != null){ %> <p> Status : <%=status %> </p> <% } 
				Collection<Defi> defis = (Collection<Defi>) request.getAttribute("defis"); %>
	<div id="bloc_gauche">
		<div id="conteneur-menu2">
			<ul>
				<li><a href="demande_a_rejoindre.jsp">Rejoindre un groupe</a></li>
				<li><a href="ServeurGroupe?action=admin&id_grp=1">Administration du groupe 1</a></li>
				<li><a href="ServeurGroupe">Accueil</a></li>
			</ul>
		</div>
		
		<div id="demandeValiderDefi">
			<p> Remplissez le formulaire suivant pour demander à valider un de vos défis. </p>
			<form method="post" action="ServeurGroupe">
				<label for="defi">Quel défi voulez-vous valider ?</label><br/>
	       			<%
	       			if (defis == null) {
	       				%>
	   					<p>Pas de défi disponible pour demander validation</p>
	   					<%
	       			} else {
	       				%>
	       				<label for="defi">Quel défi voulez-vous valider ?</label><br/>
	       			 	<select name="id_defi" id="defi">
	       			<%
		       			for (Defi defi : defis) {
		       				%>
		           			<option value="<%=defi.getId() %>"><%=defi.getNom() %></option>
		           			<%
		       			}
	           		}
	           		%>
	       				</select>
	       		<input type="submit" value="Envoyer!" />
	       		<input type="hidden" value="ajouterDefiAValider" name="action">
	       		<input type="hidden" value="<%=groupe.getId() %>" name="id_groupe">
	       		<% //String err = (String) request.getAttribute("erreur");
	       		//String succ = (String) request.getAttribute("succes"); %>
	       		<span class="erreur"><%//=err %></span>
	       		<span class="succes"><%//=succ %></span>
			</form>
		</div>
	</div>
	
	<div id="bloc_droite">
					
		<div id="afficherMembresGroupe">
			<table>
   				<tr>
   					<th>Nom</th>
    				<th>Prénom</th>
       				<th>Email</th>
   				</tr>
<%
				Collection<Membre> membres = (Collection<Membre>) request.getAttribute("membres");
				if(membres != null) {
					for (Membre m : membres) {
						if (m == null) {				
%>
							<tr>
								<td>Aucun membre à afficher</td>
								<td>RIEN</td>
					       		<td>RIEN</td>
					       	</tr>
		<%
						} else {
		%>
							<tr>
								<td><%=m.getNom() %></td>
						       	<td><%=m.getPrenom() %></td>
						       	<td><%=m.getEmail() %></td>
						   	</tr>
		<% 	
						}
					}
				}
		%>
			</table>
		</div>

		<div id="afficherDefisGroupe">
			<table>
   				<tr>
   					<th>Défi</th>
    				<th>Description</th>
       				<th>Points à gagner</th>
       				<th>Type</th>
   				</tr>
<%
				
				if(defis != null) {
					for (Defi defi : defis) {
						if (defi == null) {				
%>
							<tr>
								<td>Aucun défi à afficher</td>
								<td>RIEN</td>
					       		<td>RIEN</td>
					       		<td>RIEN</td>
					       	</tr>
		<%
						} else {
		%>
							<tr>
								<td><%=defi.getNom() %></td>
						       	<td><%=defi.getDescription() %></td>
						       	<td><%=defi.getPoints() %></td>
						       	<td><%=defi.getType() %></td>
						   	</tr>
		<% 	
						}
					}
				}
		%>
			</table>
		</div>
		</div>
<%
		}
	}
%>
	</div>
</body>
</html>