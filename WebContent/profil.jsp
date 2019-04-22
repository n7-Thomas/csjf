<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="controleur.*, modele.*, java.util.*, javax.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<div class="header">
    	<h1>Profil <i class="material-icons" style="font-size:60px;">computer</i></h1>          
		<%@ include file="navigationBar.jsp" %> 
	</div> 
	
	<fieldset>
			<div class="row">
            <div class="rightcolumn">
                <div class="card">
                <h2>Coordonnées.</h2>
                
                <p><i class="glyphicon glyphicon-user"></i> : <%= mb.getPrenom() + " " + mb.getNom() %></p>
                <p><i class="glyphicon glyphicon-envelope"></i> : <%= mb.getEmail() %></p>
                

                <a href="Serveur?action=modifier_profil"> Modifier son profil.</a><br></div></div>
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
                <% } %>
                <h4> Groupes dont <%= mb.getPrenom() + " " + mb.getNom() %> est admin :</h4>
                <% 
                Collection<Groupe> groupes_admin = (Collection<Groupe>) request.getAttribute("groupes_admins");
                if (groupes_admin != null){
           			for (Groupe g : groupes_admin) {
                    	String groupe_nom = g.getNom(); 
                %>		<p><%= groupe_nom %></p>
                <%}  
           		  } else {
                %><p> Vous n'administrez aucun groupe actuellement. 
                
                <a href="creer_groupe.jsp">Créer un groupe</a> <br> </p>
				<%} 
				%>
				</div></div></div>
				
				
				
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat} </p>
         </fieldset>
</body>
</html>