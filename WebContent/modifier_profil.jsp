<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="controleur.*, modele.*, java.util.*, javax.*"%>
<!DOCTYPE html>
<% Membre mb = ((Membre) session.getAttribute("user")); %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Modification du profil.</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link type="text/css" rel="stylesheet" href="CSS/modif_profil.css" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
	<div class=imageDeFond></div>
	<div class=contenu id="fondu">
	<form method="post" action="Serveur">
            <fieldset>
                <legend>Modifie tes données.</legend>
                <h2>Modifie tes données</h2>
                <br />
				<div id="field">
					<label for="nom"><i class="glyphicon glyphicon-user" style="font-size:25px;"></i><span class="requis"></span></label>
	                <input type="text" id="nom" name="nom" value="<%= mb.getNom()%>" size="10" maxlength="60" />
	                <span class="erreur">${form.erreurs['nom']}</span>              
	
	                <input type="text" id="prenom" name="prenom" value="<%= mb.getPrenom()%>" size="10" maxlength="60" />
	                <span class="erreur">${form.erreurs['prenom']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	  					<hr>
	                </div>
	            </div><br />

	            <div id="field">
	            	<label for="email"><i class="glyphicon glyphicon-envelope" style="font-size:25px;"></i><span class="requis"></span></label>
	                <input type="email" id="email" name="email" value="<%= mb.getEmail()%>" size="20" maxlength="60" />
	                <span class="erreur">${form.erreurs['email']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	                <hr>
	                </div>
				</div><br />
				
				<div id="field">
					<label for="motdepasse"><i class="glyphicon glyphicon-lock" style="font-size:25px;"></i><span class="requis"></span></label>
	                <input type="password" id="motdepasse" name="motdepasse" value="<%= mb.getMotdepasse()%>" size="20" maxlength="20" />
	                <span class="erreur">${form.erreurs['motdepasse']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	                <hr>
	                </div>
	            </div><br />
	            
	            	<div id="field">
	                <label for="location"><i class="glyphicon glyphicon-envelope" style="font-size:25px;"></i><span class="requis"></span></label>
	                <input type="text" id="location" name="location" placeHolder="location *" value="<%= mb.getLocation() %>" size="20" maxlength="60" />
	                <span class="erreur">${form.erreurs['location']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	                <hr>
	                </div>
	                </div><br />
	
	                <input type="submit" value="Enregistrer" class="button" />
	                <br />
	                
	                <input type="hidden" name= "action" value="enregistrer_modif">
            </fieldset>
    </form>
    </div>
</body>
<script type="text/javascript">

function fondu(nomDiv){
	  var div = document.getElementById(nomDiv).style;// récupère div
	  var i = 0;// initialise i
	  var f = function()// attribut à f une fonction anonyme
	  {
	    div.opacity = i;// attribut à l'opacité du div la valeur d'i
	    i = i+0.02;// l'incrémente
		
	    if(i<=1)// si c'est toujours pas égal à 1
	    {
	      setTimeout(f,20);// attend 20 ms, et relance la fonction
	    }
	  };
	  
	  f();// l'appel une première fois pour lancer la boucle
	}

// appel la fonction avec l'id à transmettre
fondu("fondu");
</script>
</html>