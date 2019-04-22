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
		
		<div id="contenu">
			<%
			if(isConnected){
			%>
			<!-- FIELDSET DE BONJOUR -->
			<fieldset>
				<h2>Bonjour <%=user.getPrenom() %>, comment allez vous ?</h2>
			</fieldset>
			<%	
			}
			%>
			
			<!-- FIELDSET PAGE PRINCIPALE -->
			<fieldset>
				<h2>CSJF, qu'est ce que c'est ?</h2>
				<p>Ceci est une page d'accueil de qualité, nous mettrons des trucs cools dessus j'vous jure</p>
				<div class="tests">
					<h3>Tests</h3>
					<a href="ServeurTests?action=init1">Init Test 1 : Création de Thomas, Connexion avec Thomas</a> <br>
					<a href="ServeurTests?action=init2">Init Test 2 : Création de Manu</a> <br>
					<a href="ServeurTests?action=init3">Init Test 3 : Ajout d'un défi à valider entre défi 1 et membre 2 </a> <br>
					<a href="ServeurTests?action=init4">Init Test 4 : Ajout d'un admin, d'un groupe, d'un membre, d'un defi, d'un defi à valider, d'une demande à rejoindre</a> <br>
				</div>
			</fieldset>
		</div>
	</body>
</html>