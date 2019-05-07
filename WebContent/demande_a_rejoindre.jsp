<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,controleur.*,modele.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1" />
        <title>Demande à rejoindre un groupe</title>
		<link type="text/css" rel="stylesheet" href="CSS/profil.css" />
        <link type="text/css" rel="stylesheet" href="CSS/form.css" />
    </head>
	<body>
		<div id="header">
        	<h1>Rejoindre un groupe</h1>
			<%@ include file="navigationBar.jsp" %>  
			<%@ include file="statusBar.jsp" %>
    	</div>
    	
    	<div id="contenu">
	        <fieldset>
	        	<legend>Demande à rejoindre un groupe</legend>	
	        	<form method="post" action="ServeurGroupe">
	                <label for="nom">Nom du groupe: <span class="requis">*</span></label>
	                <input type="text" id="nom" name="nom" placeHolder="Nom" size="20" maxlength="60" />  <br />
	                <input type="submit" value="Demande à rejoindre ce groupe" class="sansLabel" /> <br />
					<input type="hidden" value="demanderRejoindreGroupe" name="action">
	            </form>
	        </fieldset>
        </div>
	</body>
</html>