<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html>
<html>
<head>
		<meta charset="ISO-8859-1">
		<title>Administration du groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/form.css" />
        <link type="text/css" rel="stylesheet" href="CSS/pageAdmin.css" />
</head>
<body>
	<header>
	<% String status = (String) request.getAttribute("status");
	if(status != null){ %> <p> Status : <%=status %> </p> <% }
	%>
	</header>
	<%
	Membre mb = (Membre) session.getAttribute("user");
	if (mb == null) {
	%>
	<p>Vous n'êtes pas connectés.</p>
	<%
		} else {
			Groupe groupe = (Groupe) session.getAttribute("groupe");

			if (groupe == null) {
	%>
	
	<p>Pas de groupe administré sélectionné.</p>
	<a href="creer_groupe.jsp">Créer groupe</a>	<br>
	<%
		} else {
	%>
	
	<h1><%=groupe.getNom()%></h1>
	<h2>Administration</h2>
	
	<%Collection<Defi_A_Valider> defis_a_valider = (Collection<Defi_A_Valider>) request.getAttribute("defis_a_valider"); 
    
    if(groupe == null || defis_a_valider == null){
    	%> <div> Erreur dans la récupération des données </div> <%
    }else{
    %>
    
   	<fieldset id="afficherMembres">
	<legend>Membres du groupes</legend>
		<%  Collection<Membre> membres = (Collection<Membre>) request.getAttribute("membres");
			if(membres == null){
		%>	<div>	Personne dans le groupe... </div> <%
    	   } else {
			    for (Membre membre : membres) { %>
	       			<div><%=membre.getPrenom() %></div>

			<% } 
			}%>
	</fieldset>
    
    <fieldset>
    <legend>Valider des défis</legend>
		<% if(defis_a_valider.size() == 0){
		%>	<p>	Aucune demande en cours ! </p> <%
    	   } else {
		%>
		<form method="get" action="ServeurGroupe">
		
		    <% for (Defi_A_Valider defi : defis_a_valider) { 
		    		String id_pour_dav = "defi_" + defi.getId();
		    %>
		    <div>
				  <input type="checkbox" id="<%=id_pour_dav%>" name="<%=id_pour_dav%>" value="ok">
				  <label for="<%=id_pour_dav%>"><%=defi.getMembre().getPrenom() %> veut valider le défi <%=defi.getDefi().getNom() %></label>	
			</div>
			<% } %>
		
		<div>
			<button type="submit">Valider ces défis</button>
			<input type="hidden" name="action" value="validerDefis">
		</div>
		</form>
	<% }
	}%>
	</fieldset>
		
	<fieldset id="AjouterDefi">
    <legend>Ajouter un défi</legend>
	<form method="post" action="ServeurGroupe">
                <label for="nom">Nom du défi: <span class="requis">*</span></label>
                <input type="text" id="nom" name="nom" value="" size="20" maxlength="60" />
                <br />
                <label for="description">Description: <span class="requis">*</span></label>
                <input type="text" id="description" name="description" value="" size="20" maxlength="60" />
                <br />
          		<label for="points">Nombre de points: <span class="requis">*</span></label>
                <input type="text" id="points" name="points" value="" size="20" maxlength="60" />
                <br />
                <input type="submit" value="Ajouter ce défi" class="sansLabel" />
                <br />
				<input type="hidden" value="ajouterDefi" name="action"/>
        </form>	
	</fieldset>
	
	
	<fieldset id="ValiderDemandeARejoindre">   
	<legend>Valider les demandes à rejoindre votre groupe</legend>
	<% 
	Collection<Demande_A_Rejoindre> demandes_a_rejoindre = (Collection<Demande_A_Rejoindre>) request.getAttribute("demandes_a_rejoindre"); 
  
    if(demandes_a_rejoindre == null){
    	%> <p> Erreur dans la récupération des données </p> 
    <%
    }else{
    %>
			<% if(demandes_a_rejoindre.size() == 0){
			   %>	<p>	Aucune demande en cours ! </p> <%
    		   } else {
    				for (Demande_A_Rejoindre dar : demandes_a_rejoindre) { %>
						<p><%=dar.getMembre().getNom()%></p>
					<% } %>
	<% } 
	}%>
	</fieldset>	
	<fieldset id="AjouterMembre">
		<legend>Ajouter un membre au groupe</legend>
	        <form method="post" action="ServeurGroupe">       
                <label for="email">Email du nouveau membre: <span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="" size="20" maxlength="60" />
                <br />
                <input type="submit" value="Ajouter ce membre" class="sansLabel" />
                <br />
				<input type="hidden" value="ajouterMembre" name="action"/>
       		</form>
	</fieldset>

	<!-- <a href="ajouter_membre.jsp">Ajouter des membres</a> <br>
	<a href="ajouter_defi.jsp">Ajouter des défis</a> <br>
	<a href="ServeurGroupe?action=validerDefis">Valider des défis</a> <br>
	<a href="ServeurGroupe?action=validerDemandesARejoindre">Valider des demandes à rejoindre</a> <br> -->
	
	<%
	}
	}
	%>
</body>
</html>