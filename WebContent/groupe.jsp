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
	<% Groupe groupe = (Groupe) request.getAttribute("groupe"); %>
	<div id="afficherDefisGroupe">
		<table>
	   		<tr>
	    		<th>Description</th>
	       		<th>Points à gagner</th>
	       		<th>État</th>
	   		</tr>
	
			<% for (Defi defi : groupe.getDefis()) { %>
				<tr>
	       			<td><%=defi.getDescription() %></td>
	       			<td><%=defi.getPoints() %></td>
	       			<td><%=defi.getType() %></td>
	   			</tr>
			<% } %>
		</table>
	</div>
	
	<div id="demandeValiderDefi">
		<p> Remplissez le formulaire suivant pour demander à valider un de vos défis. </p>
		<form>
			<label for="defi">Quel défi voulez-vous valider ?</label><br />
       		<select name="defi" id="defi">
       			<% for (Defi defi : groupe.getDefis()) { %>
           			<option value="<%=defi.getNom()%>"><%=defi.getNom() %></option> <% } %>
       		</select>
       		<input type="submit" value="Envoyer" />
       		<% String err = (String) request.getAttribute("erreur");
       		String succ = (String) request.getAttribute("succes"); %>
       		<span class="erreur"><%=err %></span>
       		<span class="succes"><%=succ %></span>
		</form>
	</div>
</body>
</html>