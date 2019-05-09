<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="controleur.*, modele.*, java.util.*, javax.*"%>
<!DOCTYPE html>
<% Membre mb = ((Membre) session.getAttribute("user")); %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<title>Profil de <%= mb.getPrenom() + " " + mb.getNom() %></title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link type="text/css" rel="stylesheet" href="CSS/profil.css" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

</head>

<body>
	<div class=imageDeFond></div>
	<div class=contenu2 id="fondu">
	<div id="header">
    	<h1>Profil</h1>    
    	<%@ include file="navigationBar.jsp" %>    
	</div>
	<div id="contenu">

            	<div class="rightcolumn">
                	<div class="card">
                		<h2>Coordonnées.</h2>
		                <p><i class="glyphicon glyphicon-user"></i> : <%= mb.getPrenom() + " " + mb.getNom() %></p>
		                <p><i class="glyphicon glyphicon-envelope"></i> : <%= mb.getEmail() %></p>
                		<a href="Serveur?action=modifier_profil"> Modifier son profil.</a><br>
                	</div>
                </div>
                
                <div class="leftcolumn">
	                <div class="card">
		                <h2> Groupes auxquels <%= mb.getPrenom() + " " + mb.getNom() %> appartient</h2>
		                
		                <h4> Groupes dont <%= mb.getPrenom() + " " + mb.getNom() %> est membre :</h4>
		                <%
		                Collection<Groupe> groupes = (Collection<Groupe>) request.getAttribute("groupes_appartenus");
						if (groupes != null){
		       				for (Groupe g : groupes) {
		                		String groupe_nom = g.getNom(); 
		               			%>
		               			<p> <%= groupe_nom %> </p>
		                	<%} 
		       			} else { 
		       			%>
		                <p> Vous n'êtes dans aucun groupe actuellement</p>
		                
		                <%} %>              
		                <h4> Groupes dont <%= mb.getPrenom() + " " + mb.getNom() %> est admin :</h4>
		                <% 
		                Collection<Groupe> groupes_admin = (Collection<Groupe>) request.getAttribute("groupes_admins");
		                if (groupes_admin != null){
		           			for (Groupe g : groupes_admin) {
		                    	String groupe_nom = g.getNom(); 
		                %>		<p><%= groupe_nom %></p>
		                	<%}  
		                } else {
		                 %>
		                <p> Vous n'administrez aucun groupe actuellement. 
		                <a href="creer_groupe.jsp">Créer un groupe</a> <br> </p>
						<%
		                }
						%>
					</div>
				</div>
<<<<<<< HEAD
			</div>
	</body>
</div>
=======
		</div>
	</div>
</body>
>>>>>>> 1afea1a4374b5014f6900c9c53f731d72d278880
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
	      setTimeout(f,10);// attend 20 ms, et relance la fonction
	    }
	  };
	  
	  f();// l'appel une première fois pour lancer la boucle
	}

// appel la fonction avec l'id à transmettre
fondu("fondu");
</script>
</html>