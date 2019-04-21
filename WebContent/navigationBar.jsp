<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>


<div class="topnav">
<%
	Membre user = (Membre) session.getAttribute("user");
	boolean isConnected = (user != null);
		
	// BARRE DE NAVIGATION
	if(!isConnected){
%>
	    <a href="connexion.jsp">Se connecter</a>
		<a href="inscription.jsp">S'inscrire</a>>
<%	} else { %>
		<a href="ServeurGroupe?action=pageGroupe&id_grp=1">Groupe 1</a>
		<a href="demande_a_rejoindre.jsp">Rejoindre un groupe</a>
		<a href="ServeurGroupe?action=admin&id_grp=1">Administrer groupe 1</a>
		<a href="creer_groupe.jsp">Créer un groupe</a>
		<a href="ServeurConnexion?action=afficher_profil">Mon Profil</a>
		<a href="Serveur?action=deconnexion"style="float:right">Déconnexion </a>
		<%}%>
</div>
