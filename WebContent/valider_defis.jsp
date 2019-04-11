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
    Collection<Defi_A_Valider> defis_a_valider = (Collection<Defi_A_Valider>) request.getAttribute("defis_a_valider"); 
    
    if(groupe == null || defis_a_valider == null){
    	%> <p> Erreur dans la récupération des données </p> <%
    }else{
    %>
	<div id="afficherDefisAValider">
		<table>
	   		<tr>
	    		<th>Defi</th>
	       		<th>Membre</th>
	   		</tr>
			<% for (Defi_A_Valider defi : defis_a_valider) { %>
				<tr>
	       			<td><%=defi.getDefi().getNom()%></td>
	       			<td><%=defi.getMembre().getPrenom() %></td>
	   			</tr>
			<% } %>
		</table>
	</div>
	<% } %>


</body>
</html>