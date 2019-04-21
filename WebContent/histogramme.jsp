<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Administration du groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/profil.css" />
        <link type="text/css" rel="stylesheet" href="CSS/form.css" />
        <script type="text/javascript" src="JS/defiEdit.js"></script>
	</head>
	<body>
		<%
		// PAS BESOIN DE RECUPERER MEMBRE ON LE FAIT DANS LA NAVIGATION BAR
		//Membre mb = (Membre) session.getAttribute("user");
		//boolean isConnected = (mb != null);
		Groupe groupe = (Groupe) request.getAttribute("groupe");
		boolean hasGroupe = (groupe != null);
		Collection<String> classement = (Collection<String>) request.getAttribute("classement");
		boolean hasNP = (classement != null);
		%>
		
		<div id="header">
	    	<h1>Histogramme de <span style="color.blue"><%=groupe.getNom()%></span></h1>      
	    	<%@ include file="navigationBar.jsp" %> 
	    	<%@ include file="statusBar.jsp" %>  
	    </div>
		 
		<div id="contenu">
		<% if(isConnected && hasGroupe && hasNP) { %>
			
			<div id="histogramme">
				<ul>
					<%
						for(String str : classement){
							%><li><%=str%></li><%
						}
					%>
				</ul>
			</div>	
		<% } else {
				if(!isConnected){ %>
					<p> Vous n'êtes pas connectés ! </p>
				<% } else if(!hasGroupe){ %>
					<p> Aucun groupe à administrer ! </p>
				<% } else {
				%>	<p> Aucune donnée.. </p> <%
				}
				%>
		<% } %>
		</div>	
	</body>
</html>