<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Accueil</title>
<link type="text/css" rel="stylesheet" href="CSS/profil.css" />
</head>
<body>
	<div id="header">
		<h1>Accueil</h1>
		<%@ include file="navigationBar.jsp"%>
		<%@ include file="statusBar.jsp"%>
	</div>

	<div id="contenu" style="border: 2px solid black;">
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
		<p>Ceci est une page d'accueil de qualité, nous mettrons des trucs
			cools dessus j'vous jure</p>
		<h3>Lorem</h3>
		<p>Ca faisait vide alors je mets un Lorem
		<p>
		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
			Phasellus eleifend sed eros nec viverra. Etiam ipsum enim, pulvinar
			ac pulvinar ac, pretium feugiat dolor. Vestibulum quis placerat
			tellus. Morbi eget accumsan ligula. Mauris tincidunt orci felis, sed
			dignissim eros dapibus et. Proin in tincidunt eros. Donec ac volutpat
			orci. Aliquam sollicitudin tempor neque, a pretium metus ultrices
			feugiat. Suspendisse potenti. Etiam ultricies lectus lobortis
			fringilla sagittis. Quisque egestas pretium dolor, sed blandit tortor
			accumsan non. Quisque ipsum nulla, faucibus in mollis a, pharetra
			ultricies eros. Duis ullamcorper pretium ex sed dignissim. Donec nibh
			ligula, tempor non feugiat et, viverra eget massa.</p>

		<h3>Tests</h3>
		<a href="testPageGroupe.jsp">Test page du groupe</a> <br> <a
			href="ServeurTests?action=init1">Test 1</a>
		<p>Création de Thomas, Connexion avec Thomas</p>
		<br> <a href="ServeurTests?action=init2">Test 2</a>
		<p>Création d'une base de données de 5 membres, 1 demande à rejoindre, 1 groupe, 1 défi, 1 défi validé, 1 défi à valider</p>

	</div>
</body>
</html>