<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Accueil</title>
	<link type="text/css" rel="stylesheet" href="CSS/header.css" />
	<link type="text/css" rel="stylesheet" href="CSS/profil.css" />
</head>
<body>
	<header>
		<h1>Accueil</h1>
		<%@ include file="navigationBar.jsp"%>
		<%@ include file="statusBar.jsp"%>
	</header>

	<div id="contenu" style="border: 2px solid black; margin: 50px">
		<%
			if (isConnected) {
		%>
		<!-- FIELDSET DE BONJOUR -->
		<fieldset>
			<h2>
				Bonjour <%=user.getPrenom()%>, comment allez vous ?
			</h2>
		</fieldset>
		<%
			}
		%>
		<h2>CSJF, qu'est ce que c'est ?</h2>
		<p>CSJF est un réseau social permettant de compétiter entre potes en proposant des défis amusants et sportifs ! </p>
		<p>Rejoins un groupe, réalise les défis, fais du sport chaque semaine et tu seras à la fois au top de la forme, et le plus chaud de tes potes</p>
		<p>Créer un groupe, administre le, invite tes amis, crées des défis et vois les devenir plus sportifs au cours de chaque semaine</p>
		<p>N'hésite plus, rejoins nous sur CSJF ! </p>


		<h3>Les groupes existants:</h3>
		<div>	
		<%
		Collection<Groupe> Autresgroupes = (Collection<Groupe>) request.getAttribute("groupes"); 
		if(Autresgroupes != null){
		%>	<ul> 
		
		<% for(Groupe g: Autresgroupes){
				%><li><%= g.getNom()%> </li>		
		<%}%>
		 </ul> <%			
		}
		
		%>
		
		</div>


		<h3>Tests</h3>
		<a href="ServeurTests?action=init1">Test 1</a>
		<p>Création de Thomas, Connexion avec Thomas</p>
		<br> <a href="ServeurTests?action=init2">Test 2</a>
		<p>Création d'une base de données de 5 membres, 1 demande à rejoindre, 1 groupe, 1 défi, 1 défi validé, 1 défi à valider</p>

	</div>
</body>
</html>