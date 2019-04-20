<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, controleur.*, modele.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Valider des défis</title>
</head>
<body>
<% String status = (String) request.getAttribute("status");
if(status != null){ %> <p> Status : <%=status %> </p> <% }
%>
<% 
	Groupe groupe = (Groupe) session.getAttribute("groupe"); 
    Collection<Demande_A_Rejoindre> demandes_a_rejoindre = (Collection<Demande_A_Rejoindre>) request.getAttribute("demandes_a_rejoindre"); 
    
    if(groupe == null || demandes_a_rejoindre == null){
    	%> <p> Erreur dans la récupération des données </p> <%
    }else{
    %>
	<div id="afficherDemandes">
			<% for (Demande_A_Rejoindre dar : demandes_a_rejoindre) { %>
				<p><%=dar.getMembre().getNom()%></p>
			<% } %>
	</div>
	<% } %>


</body>
</html>