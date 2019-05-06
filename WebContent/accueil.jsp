<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html>
<html>
	<head>
			<meta charset="ISO-8859-1">
			<title>Accueil</title>
	        <link type="text/css" rel="stylesheet" href="CSS/profil.css" />
	</head>
	<body>
		<div id="header">
			<h1>Accueil</h1>  
			<%@ include file="navigationBar.jsp" %>
			<%@ include file="statusBar.jsp" %> 
		</div>
		
	<div id="contenu" style="border: 2px solid black;">
		<%
			if (isConnected) {
		%>
		<!-- FIELDSET DE BONJOUR -->
		<fieldset>
			<h2>
				Bonjour
				<%=user.getPrenom()%>, comment allez vous ?
			</h2>
		</fieldset>
		<%
			}
		%>
		<h2>CSJF, qu'est ce que c'est ?</h2>
		<p>Ceci est une page d'accueil de qualité, nous mettrons des trucs
			cools dessus j'vous jure</p>
		<h3>Mes groupes:</h3>
	
         <%
 
                Collection<Groupe> groupes = (Collection<Groupe>) request.getAttribute("groupes_appartenus"); 
   
				if (groupes != null){%>
					<h5>Auquels j'appartiens</h5>
       				<% for (Groupe g : groupes) {
                		String groupe_nom = g.getNom(); 
               			%>
               			<p> <%= groupe_nom %> </p>
                	<%} 
       			} else { 
       			%>
                <p> Vous n'êtes dans aucun groupe actuellement</p>
                <% }
				Collection<Groupe> groupes_admin = (Collection<Groupe>) request.getAttribute("groupes_admins");
                if (groupes_admin != null){%>
                	<h5>Que j'administre</h5>
           			<% for (Groupe g : groupes_admin) {
                    	String groupe_nom = g.getNom(); 
                %>		<p><%= groupe_nom %></p>
                <%}  
           		  } else {
                %><p> Vous n'administrez aucun groupe actuellement. </p>
				<%} 
				%>
		
		<h3>Les groupes existants:</h3>
		
		
		<p>	

		<%Collection<Groupe> Autresgroupes = (Collection<Groupe>) request.getAttribute("groupes"); %>
		<ul> <% for(Groupe g: Autresgroupes){
				String groupe = g.getNom(); %>
		<li><%=groupe%> <li/>		
		<%}%> </ul>
		</p>
						
				
		<h3>Tests</h3>
		<a href="testPageGroupe.jsp">Test page du groupe</a> <br>
		<a href="ServeurTests?action=init1">Init Test 1 : Création de
			Thomas, Connexion avec Thomas</a> <br> <a
			href="ServeurTests?action=init2">Init Test 2 : Création de Manu</a>
		<br> <a href="ServeurTests?action=init3">Init Test 3 : Ajout d'un
			défi à valider entre défi 1 et membre 2 </a> <br> <a
			href="ServeurTests?action=init4">Init Test 4 : Ajout d'un admin,
			d'un groupe, d'un membre, d'un defi, d'un defi à valider, d'une
			demande à rejoindre</a> <br>

	</div>
</body>
</html>