<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html>
<html>
<head>
		<meta charset="ISO-8859-1">
		<title>Administration du groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/admin.css" />
        <script type="text/javascript" src="JS/defiEdit.js"></script>
</head>
<body>
	<header>
	<% String status = (String) request.getAttribute("status");
	if(status != null){ %> <p> Status : <%=status %> </p> <% }
	%>
	</header>

	<%
	Membre mb = (Membre) session.getAttribute("user");
	if (mb == null) { %> 
	<p>Vous n'êtes pas connectés.</p> <%
	} else {
			Groupe groupe = (Groupe) request.getAttribute("groupe");
			if (groupe == null) {
	%>
	<p>Pas de groupe administré sélectionné.</p>
	<a href="creer_groupe.jsp">Créer groupe</a>	<br>
	<%		} else {
	%>
	<div class="header">
    <h1>Administration de <span style="color.blue"><%=groupe.getNom()%></span></h1>       
    </div>
    <div class="topnav">
                <a href="accueil.jsp">Accueil</a>
                <a href="Serveur?action=afficher_profil">Mon profil</a>
                <a href="index.jsp">Index</a>
                <a href="groupe.jsp?id_grp=<%=groupe.getId() %>" >Groupe</a>
                <a href="Serveur?action=deconnexion"style="float:right">Déconnexion </a><br>
    </div>
	
	<div class="row">
	<div class="rightcolumn">   
   	<fieldset id="afficherMembres">
	<legend>Membres du groupes</legend>
	<%  Collection<Membre> membres = (Collection<Membre>) request.getAttribute("membres");
	if(membres == null){ %>	<div>	Personne dans le groupe... </div> <%
    } else {
    	%><div><%
		for (Membre membre : membres) { %>
	    	<p><%=membre.getPrenom() %> <%=membre.getNom() %></p>
		<% } 
    	%></div><%
	}%>
	</fieldset>
    	
	<fieldset id="ValiderDemandeARejoindre">   
	<legend>Valider les demandes à rejoindre votre groupe</legend>
	<% 
	Collection<Demande_A_Rejoindre> demandes_a_rejoindre = (Collection<Demande_A_Rejoindre>) request.getAttribute("demandes_a_rejoindre"); 
    if(demandes_a_rejoindre == null){ %> <p> Erreur dans la récupération des données </p> 
    <%
    }else{
    %>
		<% if(demandes_a_rejoindre.size() == 0){ %>	<p>	Aucune demande en cours ! </p> <%
    	   } else {
    	%>
    		<form method="get" action="ServeurGroupe">
    		<%
    		for (Demande_A_Rejoindre dar : demandes_a_rejoindre) { 
    			String id_pour_dar = "dar_" + dar.getId();
    		%>
    			<div>
				<input type="checkbox" id="<%=id_pour_dar%>" name="<%=id_pour_dar%>" value="ok">
				<label for="<%=id_pour_dar%>"><%=dar.getMembre().getPrenom() %> veut rejoindre le groupe</label>	
				</div>
			<% } %>
			<input type="submit" value="Valider ces demandes" class="sansLabel">
			<input type="hidden" name="action" value="validerDemandes">
			<input type="hidden" name="id_grp" value="<%=groupe.getId() %>">
			</form>
		<% } 
	}%>
	</fieldset>	
	
	
	<fieldset id="AjouterMembre">
		<legend>Ajouter un membre au groupe</legend>
	        <form method="post" action="ServeurGroupe">       
                <label for="email">Email du nouveau membre: <span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="" size="20" maxlength="60" /> <br />
                <input type="submit" value="Ajouter ce membre" class="sansLabel" /> <br />
				<input type="hidden" value="ajouterMembre" name="action"/>
				<input type="hidden" name="id_grp" value="<%=groupe.getId() %>">
       		</form>
	</fieldset>
	
	</div>
	<div class="leftcolumn">	
    <fieldset>
    <legend>Valider des défis</legend>
    
	<% Collection<Defi_A_Valider> defis_a_valider = (Collection<Defi_A_Valider>) request.getAttribute("defis_a_valider"); 
	   if(defis_a_valider.size() == 0){ %>	<p>	Aucune demande en cours ! </p> <%
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
				  <input type="submit" value="Valider ces défis">
				  <input type="hidden" name="action" value="validerDefis">
				  <input type="hidden" name="id_grp" value="<%=groupe.getId() %>">
				  </div>
		</form>
	<% } %>
	</fieldset>
		
	<fieldset id="AjouterDefi">
    <legend>Ajouter un défi</legend>
	<form method="post" action="ServeurGroupe">
                <label for="nom">Nom du défi: <span class="requis">*</span></label>
                <input type="text" id="nom" name="nom" value="" size="20" maxlength="60" />
                <br />
                <label for="description">Description:</label>
                <input type="text" id="description" name="description" value="" size="20" maxlength="60" />
                <br />
          		<label for="points">Nombre de points: <span class="requis">*</span></label>
                <input type="text" id="points" name="points" value="" size="20" maxlength="60" />
                <br />
                <input type="submit" value="Ajouter ce défi" class="sansLabel" />
                <br />
				<input type="hidden" value="ajouterDefi" name="action"/>
				<input type="hidden" name="id_grp" value="<%=groupe.getId() %>">
        </form>	
	</fieldset>
	
	<fieldset>
		<legend>Défis en cours</legend>
		<%  Collection<Defi> defis_en_cours = (Collection<Defi>) request.getAttribute("defis_en_cours");  
			if(defis_en_cours.size() == 0){
		%>	<p>	Aucun défi en cours ! </p> <%
    	   } else {
		%>
			<div>
		    <% for (Defi defi : defis_en_cours) { 
		    %>
		    
				 	<p> Défi <%= defi.getId() %> : "<%= defi.getNom() %>" (<%= defi.getDescription() %>) pour <%= defi.getPoints() %> points. </p>	
			
			<% } %>
			</div>
		
	<% } %>
	</fieldset>
	
	<fieldset>
		<legend>Editer un défi</legend>
		<% 
			if(defis_en_cours.size() == 0){
		%>	<p>	Aucun défi à éditer ! </p> <%
    	   } else {
		%>
			<!-- Sélectionner un défi à modifier -->
			<div>
			<form name="editDefi"> 
			<select name="liste" onChange="modif()">
			<option value="-1">Choisir un défi
			<% for (Defi defi : defis_en_cours) { 
		    %>
				<option value="<%= defi.getId() %>"><%= defi.getNom() %>
			<% } %>
			</select>	
			</form>
			</div>
			<br />
			
			<!-- Modifier le défi -->
			<div>
			<form id="formModif" method="post" action="ServeurGroupe" style="visibility:hidden">
			
			<label for="nom">Nom du défi:</label><input type="text" id="nom" name="nom" value="" size="20" maxlength="60" />
            <br />
            <label for="description">Description: </label>
            <input type="text" id="description" name="description" value="" size="20" maxlength="60" />
            <br />
          	<label for="points">Nombre de points: </label>
            <input type="text" id="points" name="points" value="" size="20" maxlength="60" />
            <br />
            <input type="submit" value="Editer ce défi" class="sansLabel" />
            <br />
			
			<input type="hidden" name="id_defi" id="formModif_id_defi" />
			<input type="hidden" name="action" value="editDefi"/>
			<input type="hidden" name="id_grp" value="<%=groupe.getId()%>">
			
			</form>
			</div>
			
	<% } %>
	</fieldset>
	</div>
	</div>
	
	
	
	<%
    } // else groupe
    } // else connecté 
    %>
	
	</body>
</html>