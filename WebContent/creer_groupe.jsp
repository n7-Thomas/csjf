<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,controleur.*,modele.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1" />
        <title>Création d'un groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/profil.css" />
        <link type="text/css" rel="stylesheet" href="CSS/form.css" />
        <link type="text/css" rel="stylesheet" href="CSS/header.css" />
    </head>
    
    <body>
    	
		<header>
        	<h1>Creer un groupe</h1>
        	<%@ include file="navigationBar.jsp" %> 
        	<%@ include file="statusBar.jsp" %>
		</header>
		
		<div id="contenu">
			<fieldset>
			    <legend>Creer un groupe dont vous serez l'administrateur</legend>
	        	<form method="post" action="ServeurGroupe">
	                <label for="nom">Nom du groupe: <span class="requis">*</span></label>
	                <input type="text" id="nom" name="nom" size="20" maxlength="60" />
	                <br />
	                <input type="submit" value="Créer ce groupe" class="sansLabel" />
	                <br />
					<input type="hidden" value="creerGroupe" name="action">       
	        	</form>
	        </fieldset>
        </div>
	</body>
</html>