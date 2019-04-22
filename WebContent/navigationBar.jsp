<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

<div class="box">
  <div>
<%
	Membre user = (Membre) session.getAttribute("user");
	boolean isConnected = (user != null);
		
%> 
	<nav id="main_nav">
		<ul>
			<li>
	    		<a href="Serveur?action=afficher_pageAccueil">Accueil</a>
	    	</li>
<%
	// BARRE DE NAVIGATION
	if(!isConnected){
%>
			<li>
	    		<a href="connexion.jsp">Se connecter</a>
	    	</li>
	    	<li>
				<a href="inscription.jsp">S'inscrire</a>
			</li>
<%	} else { %>
			<li>
					<a href="">Groupes</a>
					<ul>
						<li>
							<a href="demande_a_rejoindre.jsp">Rejoindre un groupe</a>
						</li>
					<%
		            Collection<Groupe> groupes = (Collection<Groupe>) request.getAttribute("groupes_appartenus");
					if (groupes != null){
		       			for (Groupe g : groupes) {
		                	String groupe_nom = g.getNom(); 
		                	int id_g = g.getId();
		               		%>
             				<li><a href="ServeurGroupe?action=pageGroupe&id_grp=<%=id_g%>"><%=groupe_nom%></a></li>
               				<%
               			} 
       				} %>
					</ul>
				</li>
				<li>
					<a href="">Administration</a>
					<ul>
						<li>
							<a href="creer_groupe.jsp">Créer un groupe</a>
						</li>
						<%
			            Collection<Groupe> groupe_admin = (Collection<Groupe>) request.getAttribute("groupes_admins");
						if (groupe_admin != null){
			       			for (Groupe g : groupe_admin) {
			                	String groupes_nom = g.getNom(); 
			                	int id_gs = g.getId();
			               		%>
             					<li><a href="ServeurGroupe?action=admin&id_grp=<%=id_gs%>"><%=groupes_nom%></a></li>
               					<%
               				} 
       					} %>
					</ul>
				</li>
				<li>
					<a href="ServeurConnexion?action=afficher_profil"><i class="glyphicon glyphicon-user" style="font-size:20px"></i></a>
				</li>
				<li style="float:right">
					<a href="Serveur?action=deconnexion"><i class="glyphicon glyphicon-log-out" style="font-size:20px"></i> </a>
				</li>

	<%}%>
		</ul>
	</nav>
</div>
</div>
