<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
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
		<div id="bloc_gauche">
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
						String id_grp = (String) request.getAttribute("id_grp");
						String status = (String) request.getAttribute("status");
						ArrayList<Object> defis = (ArrayList<Object>) request.getAttribute("defis");
			%>
			<div id="conteneur-menu2">
				<ul>
					<li><a href="Serveur?action=afficher_pageAccueil">Accueil</a></li>
					<li><a href="creer_groupe.jsp">Créer un groupe</a></li>
					<li><a href="demande_a_rejoindre.jsp">Rejoindre un groupe</a></li>
					<li><a href="ServeurPageGroupe?action=afficher_filActu&id_grp=<%=id_grp%>"> Afficher le fil d'Actualité</a></li> 
					<li class="top_puce"><a
						href="ServeurConnexion?action=afficher_profil">Mon Profil</a></li>
				</ul>
			</div>
			
			<%
				if (status != null) {
			%>
			<p id="status">
				Status :
				<%=status%>
			</p>
			<%
				}
			%>
			
			<div id="afficherMembresGroupe">
				<div id="titre_membres">
					Membres
				</div>
				<%
					Collection<Membre> membres = (Collection<Membre>) request.getAttribute("membres");
							if (membres != null) {
								for (Membre m : membres) {
									if (m == null) {
				%>
				<div class="membre_afficher">
					Aucun membre
				</div>
				<%
					} else {
				%>
				<div class="membre_afficher">
						<%=m.getPrenom() + " " + m.getNom().charAt(0) + "."%>
				</div>
				<%
					}
				}
			}
				%>
			</div>
			
			<div id="csjf_div">
				<p id="csjf_titre">CSJF</p>
				<p id="csjf_soustitre">(Cette semaine j'ai fait...)</p>
				<form method="post" action="ServeurGroupe">
				   	<label for="csjf" id="csjf_label"><p>Tu as fait quelque chose de bien cette semaine ?<br/>Dis-le nous !</p></label><br />
				   	<div id="zone_texte">
					   	<textarea name="csjf" id="csjf" rows="7" cols="50"></textarea>
					   	<input type="submit" value="Envoyer!" class="bouton_envoyer" />
				   	</div>
					<input type="hidden" value="envoyerCSJF" name="action">
					<input type="hidden" value="<%=groupe.getId()%>" name="id_grp">
				</form>
			</div>
		</div>
		

		<div id="bloc_milieu">
		
			<div id="afficherDefisGroupe">
				<form method="post" action="ServeurGroupe">
					<div id="titre_defis">
						<div class="nom_aff">Nom</div>
						<div class="desc_aff">Description</div>
						<div class="points_aff">Points</div>
						<div class="type_aff">Catégorie</div>
						<div class="envoyer_aff">Validation</div>
					</div>
					<%
						if (defis != null) {
							for (Object o : defis) {
								if (o == null) {
					%>
					<div class="defi_afficher">
						<div class="nom_aff">Aucun défi</div>
						<div class="desc_aff">Rien</div>
						<div class="points_aff">Rien</div>
						<div class="type_aff">Rien</div>
						<div class="envoyer_aff">Rien</div>
					</div>
					<%
								} else if (o instanceof Defi) {
									Defi defi = (Defi) o;
					%>
					<div class="defi_afficher">
						<div class="nom_aff">
							<%=defi.getNom()%>
						</div>
						<div class="desc_aff">
							<%=defi.getDescription()%>
						</div>
						<div class="points_aff">
							<%=defi.getPoints()%>
						</div>
						<div class="type_aff">
							<%=defi.getType()%>
						</div>
						<div class="envoyer_aff">
							<input type="submit" value="Valider" class="bouton_envoyer" />
						</div>
					</div>
					<%
								} else if (o instanceof Defi_A_Valider) {
									Defi_A_Valider defi_a_valider = (Defi_A_Valider) o;
									Defi defi = defi_a_valider.getDefi();
					%>
					<div class="defi_afficher">
						<div class="nom_aff">
							<%=defi.getNom()%>
						</div>
						<div class="desc_aff">
							<%=defi.getDescription()%>
						</div>
						<div class="points_aff">
							<%=defi.getPoints()%>
						</div>
						<div class="type_aff">
							<%=defi.getType()%>
						</div>
						<div class="envoyer_aff">
							En cours de validation
						</div>
					</div>
					<%
								} else {
									Defi_Valide defi_valide = (Defi_Valide) o;
									Defi defi = defi_valide.getDefi();
					%>
					<div class="defi_afficher">
						<div class="nom_aff">
							<%=defi.getNom()%>
						</div>
						<div class="desc_aff">
							<%=defi.getDescription()%>
						</div>
						<div class="points_aff">
							<%=defi.getPoints()%>
						</div>
						<div class="type_aff">
							<%=defi.getType()%>
						</div>
						<div class="envoyer_aff">
							Validé
						</div>
					</div>
					<%
								}
							}
						}
					%>
				</form>
			</div>
			
			<div id="histogramme">
				<%@ include file="histogramme.jsp"%>
			</div>
		</div>
		
		
		<div id="bloc_droite">
		
		</div>
		<%
			}
		}
		%>
	</div>
</body>
</html>