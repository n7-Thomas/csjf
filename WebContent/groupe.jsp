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
					ArrayList defis = (ArrayList) request.getAttribute("defis");
		%>
		<div id="bloc_gauche">
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

			<div id="demandeValiderDefi">
				<p id="titre_demande">
					Remplissez le formulaire suivant pour demander <br /> à valider un
					de vos défis.
				</p>
				<form method="post" action="ServeurGroupe">
					<label for="defi" id="label">Quel défi voulez-vous valider
						?</label><br />
					<%
						if (defis == null) {
					%>
					<p>Pas de défi disponible pour demander validation</p>
					<%
						} else {
					%>
					<select name="id_defi" id="liste">
						<%
							for (Object o : defis) {
								if (o instanceof Defi) {
									Defi defi = (Defi) o;
						%>
						<option value="<%=defi.getId()%>" name="id_defi"><%=defi.getNom()%></option>
						<%
								}
							}
						}
						%>
					</select> <input type="submit" value="Envoyer!" class="bouton_envoyer" /> 
					<input type="hidden" value="ajouterDefiAValider" name="action"> 
					<input type="hidden" value="<%=groupe.getId()%>" name="id_grp">
				</form>
			</div>

			<div id="histogramme">
				<%@ include file="histogramme.jsp"%>
			</div>
		</div>

		<div id="bloc_droite">
		
			<div id="afficherDefisGroupe">
				<div id="titre_defis">
					<div class="nom_aff">Nom</div>
					<div class="desc_aff">Description</div>
					<div class="points_aff">Points</div>
					<div class="type_aff">Catégorie</div>
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
						Non validé
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
						Validé
					</div>
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

			<div id="afficherMembresGroupe">
				<div id="titre_membres">
					<div class="nom1_aff">Nom</div>
					<div class="prenom_aff">Prénom</div>
					<div class="email_aff">Email</div>
				</div>
				<%
					Collection<Membre> membres = (Collection<Membre>) request.getAttribute("membres");
							if (membres != null) {
								for (Membre m : membres) {
									if (m == null) {
				%>
				<div class="membre_afficher">
					<div class="nom1_aff">Aucun membre</div>
					<div class="prenom_aff">Rien</div>
					<div class="email_aff">Rien</div>
				</div>
				<%
					} else {
				%>
				<div class="membre_afficher">
					<div class="nom1_aff">
						<%=m.getNom()%>
					</div>
					<div class="prenom_aff">
						<%=m.getPrenom()%>
					</div>
					<div class="email_aff">
						<%=m.getEmail()%>
					</div>
				</div>
				<%
					}
								}
							}
				%>
			</div>
		</div>
		<%
			}
			}
		%>
	</div>
</body>
</html>