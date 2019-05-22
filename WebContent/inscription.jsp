<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription sur CSJF</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link type="text/css" rel="stylesheet" href="CSS/inscription.css" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<script type="text/javascript" src="JS/sha256.js"></script>
	
</head>
<body>
	<div class=imageDeFond></div>
	<div class=contenu id="fondu">
    	<% Boolean existe = false; %>
    	<%@ include file="statusBar.jsp" %>
		
		<% try {
		   		existe = ((Boolean) request.getAttribute("existe"));
			    if(existe){	
			%> 		<h3><%= request.getAttribute("warning") %></h3>
			<% } 
		} catch(Exception e) {
			existe = false;
		}
		%>
    
        <form method="post" action="ServeurConnexion">
            <fieldset>
                <legend>Inscription</legend>
                <h2>Rejoins-nous</h2>
                				
				<% if (existe) { %>
				
					<div id="field">
	                <label for="nom"><i class="glyphicon glyphicon-user" style="font-size:25px;"></i><span class="requis"></span></label>
	                <input type="text" id="nom" placeHolder="Nom *" name="nom" value="<%= request.getAttribute("nom_user") %>" size="10" maxlength="60" />
	                <span class="erreur">${form.erreurs['nom']}</span>              
	
	                <input type="text" id="prenom" placeHolder="Prénom *" name="prenom" value="<%= request.getAttribute("prenom_user") %>" size="10" maxlength="60" />
	                <span class="erreur">${form.erreurs['prenom']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	  					<hr>
	                </div>
	                </div><br />
                
	                <div id="field">
	                 <label for="email"><i class="glyphicon glyphicon-envelope" style="font-size:25px;"></i><span class="requis"></span></label>
	                <input type="email" id="email" name="email" placeHolder="adresse email *" value="<%= request.getAttribute("email_user") %>" size="20" maxlength="60" />
	                <span class="erreur">${form.erreurs['email']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	                <hr>
	                </div>
	                </div><br />
	                
	                
	  			<% } else { %>
	  			
	  			
	  				<div id="field">
	                <label for="nom"><i class="glyphicon glyphicon-user" style="font-size:25px;"></i><span class="requis"></span></label>
	                <input type="text" id="nom" placeHolder="Nom *" name="nom" value="" size="10" maxlength="60" />
	                <span class="erreur">${form.erreurs['nom']}</span>              
	
	                <input type="text" id="prenom" placeHolder="Prénom *" name="prenom" value="" size="10" maxlength="60" />
	                <span class="erreur">${form.erreurs['prenom']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	  				<hr>
	                </div>
	                </div><br />
	                
	                <div id="field">
	                 <label for="email"><i class="glyphicon glyphicon-envelope" style="font-size:25px;"></i><span class="requis"></span></label>
	                <input type="email" id="email" name="email" placeHolder="adresse email *" value="" size="20" maxlength="60" />
	                <span class="erreur">${form.erreurs['email']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	                <hr>
	                </div>
	                </div><br />
	  				<% } %>
                

				<div id="field">
                <label for="motdepasse"><i class="glyphicon glyphicon-lock" style="font-size:25px;"></i><span class="requis"></span></label>
                <input type="password" id="motdepasse" placeHolder="mot de passe *"name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['motdepasse']}</span>
                <div style="width:80%;margin-left: auto;
  					margin-right: auto;">
                <hr>
                </div>
                </div><br />
				<form>
                <input type="submit" onClick="crypterMotdepasse()" value="Inscription" class="button"/>
                </form>
                <br />
                <h4>Déjà inscrit? <a href="connexion.jsp">Connecte-toi.</a></h4>
                
                <input type="hidden" name= "action" value="inscription"/>
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
	</div>
</body>
<script type="text/javascript">

function crypterMotdepasse() {
	var s = document.getElementById('motdepasse').value;
	hash = hex_sha256(s);
	document.getElementById('motdepasse').value = hash;
}

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