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
		<li class="dropdown">
    		<a href="javascript:void(0)" class="dropbtn">Groupes</a>
    		<div class="dropdown-content">
		<%
            Collection<Groupe> groupes = (Collection<Groupe>) request.getAttribute("groupes_appartenus");
			if (groupes != null){
       			for (Groupe g : groupes) {
                	String groupe_nom = g.getNom(); 
                	int id_g = g.getId();
               		%>
             	<a href="ServeurGroupe?action=pageGroupe&id_grp=<%=id_g%>"><%=groupe_nom%></a>
               	<%
               	} 
       		} %>
       		</div>
  		</li>
		<a href="demande_a_rejoindre.jsp">Rejoindre un groupe</a>
		
		<li class="dropdown">
    		<a href="javascript:void(1)" class="dropbtn">Administration</a>
    		<div class="dropdown-content">
		<%
            Collection<Groupe> groupe_admin = (Collection<Groupe>) request.getAttribute("groupes_admins");
			if (groupe_admin != null){
       			for (Groupe g : groupe_admin) {
                	String groupes_nom = g.getNom(); 
                	int id_gs = g.getId();
               		%>
             	<a href="ServeurGroupe?action=admin&id_grp=<%=id_gs%>"><%=groupes_nom%></a>
               	<%
               	} 
       		} %>
       	</div>
  		</li>
		<a href="creer_groupe.jsp">Créer un groupe</a>
		<a href="ServeurConnexion?action=afficher_profil">Mon Profil</a>
		<a href="Serveur?action=deconnexion"style="float:right">Déconnexion </a>
		<%}%>
</div>
