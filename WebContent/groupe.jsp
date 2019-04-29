<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, controleur.*, modele.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <title>Groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/groupe.css" />
</head>
<body>
	<div id="bandeau">
		<h1 id="titre">Page du groupe</h1>
	</div>
	<div id="corps">
	<%
		if (session.getAttribute("user") == null) {
	%>
			<p>Vous n'êtes pas connectés.</p>
	<%
		} else {
			Membre mb = (Membre) session.getAttribute("user");

			if (request.getAttribute("groupe") == null) {
	%>
			<p>Pas de groupe sélectionné.</p>
			<a href="index.html">Aller à l'index</a><br>
	<%
			} else {
				Groupe groupe = (Groupe) request.getAttribute("groupe");
				
    			String status = (String) request.getAttribute("status");
				if(status != null){ %> <p> Status : <%=status %> </p> <% } 
				Collection<Defi> defis = (Collection<Defi>) request.getAttribute("defis"); %>
	<div id="bloc_gauche">
		<div id="conteneur-menu2">
			<ul>
				<li><a href="demande_a_rejoindre.jsp">Rejoindre un groupe</a></li>
				<li><a href="ServeurGroupe?action=admin&id_grp=1">Administration du groupe 1</a></li>
				<li><a href="ServeurGroupe">Accueil</a></li>
			</ul>
		</div>
		
		<div id="demandeValiderDefi">
			<p id="titre_demande">Remplissez le formulaire suivant pour demander <br/> à valider un
				de vos défis.</p>
			<form method="post" action="ServeurGroupe">
				<label for="defi" id ="label">Quel défi voulez-vous valider ?</label><br/>
	       			<%
	       			if (defis == null) {
	       				%>
	   					<p>Pas de défi disponible pour demander validation</p>
	   					<%
	       			} else {
	       				%>
	       				<select name="id_defi" id="liste">
	       			<%
		       			for (Defi defi : defis) {
		       				%>
		           			<option value="<%=defi.getId() %>"><%=defi.getNom() %></option>
		           			<%
		       			}
	           		}
	           		%>
	       				</select>
	       			<input type="submit" value="Envoyer!" id="bouton_envoyer"/> 
					<input type="hidden" value="ajouterDefiAValider" name="action"> 
					<input type="hidden" value="idDefi" name="id_groupe">
			</form>
		</div>
	</div>
	
	<div id="bloc_droite">
					
		<div id="afficherMembresGroupe">
			<div id="afficherMembresGroupe">
				<div id="titre_membres">
					<div class="nom1_aff">
						Nom
					</div>
					<div class="prenom_aff">
						Prénom
					</div>
					<div class="email_aff">
						Email
					</div>
				</div>
<%
				Collection<Membre> membres = (Collection<Membre>) request.getAttribute("membres");
				if(membres != null) {
					for (Membre m : membres) {
						if (m == null) {				
%>
							<div class="membre_afficher">
								<div class="nom1_aff">
									Aucun membre
								</div>
								<div class="prenom_aff">
									Rien
								</div>
								<div class="email_aff">
									Rien
								</div>
							</div>
		<%
						} else {
		%>
							<div class="membre_afficher">
								<div class="nom1_aff">
									<%=m.getNom() %>
								</div>
								<div class="prenom_aff">
									<%=m.getPrenom() %>
								</div>
								<div class="email_aff">
									<%=m.getEmail() %>
								</div>
							</div>
		<% 	
						}
					}
				}
		%>
			</div>
		</div>

		<div id="afficherDefisGroupe">
			<div id="afficherDefisGroupe">
				<div id="titre_defis">
					<div class="nom_aff">
						Nom
					</div>
					<div class="desc_aff">
						Description
					</div>
					<div class="points_aff">
						Points
					</div>
					<div class="type_aff">
						Catégorie
					</div>
				</div>
<%
				
				if(defis != null) {
					for (Defi defi : defis) {
						if (defi == null) {				
%>
							<div class="defi_afficher">
								<div class="nom_aff">
									Aucun défi
								</div>
								<div class="desc_aff">
									Rien
								</div>
								<div class="points_aff">
									Rien
								</div>
								<div class="type_aff">
									Rien
								</div>
							</div>
		<%
						} else {
		%>
							<div class="defi_afficher">
								<div class="nom_aff">
									<%=defi.getNom() %>
								</div>
								<div class="desc_aff">
									<%=defi.getDescription() %>
								</div>
								<div class="points_aff">
									<%=defi.getPoints() %>
								</div>
								<div class="type_aff">
									<%=defi.getType() %>
								</div>
							</div>
		<% 	
						}
					}
				}
		%>
			</div>
		</div>
		</div>
<%
		}
	}
%>
	</div>
</body>
</html>