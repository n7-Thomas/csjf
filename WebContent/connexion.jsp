<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link type="text/css" rel="stylesheet" href="CSS/connexion.css" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link href='http://fonts.googleapis.com/css?family=ExpletusSans' rel='stylesheet' type='text/css'>
</head>
<div class=imageDeFond></div>
<div class=contenu id="fondu">
<body>
		<% boolean connecte = true; %>
    	<% String status = (String) request.getAttribute("status");
		if(status != null){ %> <p> Status : <%=status %> </p> <% }
		%>
		
		<% try {
			connecte = ((Boolean) request.getAttribute("connecte"));
			if(connecte == false){	
		%> <% String message = (String) request.getAttribute("message");
			if(message != "") { %> <h3><p><%=message%> </p></h3> 
			<% } else {
				String erreur = (String) request.getAttribute("erreur"); %>
				<h3><p><%= erreur %></p></h3> <%
				} %>
		<% }
			} catch(Exception e) {
				connecte = true;
			}
		%>
		
        <form method="post" action="ServeurConnexion">

                <h2><p>connecte-toi</p></h2><br /><br />
				
				<% if (connecte == false) { %>
					<div id="field">
	                <label for="nom"><i class="glyphicon glyphicon-envelope" style="font-size:25px;"></i><span class="requis">     </span></label>
	                <input type="email" id="email" placeHolder="adresse email" name="email" value="<%= request.getAttribute("email_faux") %>" size="20" maxlength="60" />
	                <span class="erreur">${form.erreurs['email']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	                <hr>
	                </div>
	                </div><br />
	           <% } else { %>
	           
	           		<div id="field">
	                <label for="nom"><i class="glyphicon glyphicon-envelope" style="font-size:25px;"></i><span class="requis">     </span></label>
	                <input type="email" id="email" placeHolder="adresse email" name="email" value="" size="20" maxlength="60" />
	                <span class="erreur">${form.erreurs['email']}</span>
	                <div style="width:80%;margin-left: auto;
	  					margin-right: auto;">
	                <hr>
	                </div>
	                </div><br />
	           <% } %>

				<div id="field">
                <label for="motdepasse"><i class="glyphicon glyphicon-lock" style="font-size:25px;"></i><span class="requis">    </span></label>
                <input type="password" id="motdepasse" placeHolder="mot de passe" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['motdepasse']}</span>
                <div style="width:80%;margin-left: auto;
  						margin-right: auto;">
                <hr>
                </div>
                </div><br />

                <input type="submit" value="Connexion"  class="button"/>
                <br />
                <h4><p>Pas encore de compte?</p> <a href="inscription.jsp">Inscris-toi.</h4>
                
                <input type="hidden" name= "action" value="testconnexion">
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>

        </form>
	
</body>
</div>
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