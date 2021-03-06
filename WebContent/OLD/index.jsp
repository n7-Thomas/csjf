<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Index</title>
	<link type="text/css" rel="stylesheet" href="CSS/header.css" />
</head>
<body>

<% String status = (String) request.getAttribute("status");
if(status != null){ %> <p> Status : <%=status %> </p> <% } %>

<% Membre mbr_co = (Membre) session.getAttribute("user");

if(mbr_co != null){ %> <p> Bonjour, <%=mbr_co.getNom() %> !</p> <% } else { %> <p> Vous n'êtes pas connecté </p>  
<% } %>



<a href="aff_filActu.jsp?id_grp=1"> Voir le fil d'actualité de mon groupe </a> <br> 


<% if (mbr_co == null){ %>
<h2>Inscription et Connexion</h2>
<a href="inscription.jsp">S'inscrire gros</a> <br>
<a href="connexion.jsp">Connexion</a> <br>
<% } else { %>

<a href="Serveur?action=afficher_pageAccueil"> Acceder à votre page d'accueil</a> <br>

<h2>Activités</h2>
<a href="demande_a_rejoindre.jsp">Formulaire pour demander à rejoindre un groupe</a> <br>
<a href="ServeurGroupe?action=admin&id_grp=1">Administration du groupe 1</a> <br>
<a href="ServeurGroupe?action=pageGroupe&id_grp=1">Accéder à ma page de groupe</a> <br>
<% } %>

<h2>Tests</h2>
<a href="ServeurTests?action=init1">Init Test 1 : Création de Thomas, Connexion avec Thomas</a> <br>
<a href="ServeurTests?action=init2">Init Test 2 : Création de Manu</a> <br>
<a href="ServeurTests?action=init3">Init Test 3 : Ajout d'un défi à valider entre défi 1 et membre 2 </a> <br>
<a href="ServeurTests?action=init4">Init Test 4 : Ajout d'un admin, d'un groupe, d'un membre, d'un defi, d'un defi à valider, d'une demande à rejoindre</a> <br>


</body>
</html>