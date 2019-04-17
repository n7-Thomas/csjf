<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="controleur.*, modele.*, java.util.*, javax.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% Membre mb = ((Membre) session.getAttribute("user")); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profil de <%= mb.getPrenom() + " " + mb.getNom() %></title>
</head>
<body>
	<fieldset>
                <h2>Coordonnées.</h2>
                
                <p>Nom : <%= mb.getNom() %></p>
                <p>Prénom : <%= mb.getPrenom() %></p>
                <p>Email : <%= mb.getEmail() %></p>
                
                <h2> Groupes auxquels <%= mb.getPrenom() + " " + mb.getNom() %> appartient</h2>
                <%
                Collection<Groupe> groupes = (Collection<Groupe>) request.getAttribute("groupes_appartenus"); // A IMPLEMENTER 
				if (groupes != null){
       				for (Groupe g : groupes) {
                		String groupe_nom = g.getNom(); 
               	%>
               			<p> <%= groupe_nom %> </p>
                <%} 
       			} else { 
       			%>
                <p> Vous n'êtes dans aucun groupe actuellement</p>
                <% }
                Collection<Groupe> groupes_admin = (Collection<Groupe>) request.getAttribute("groupes_admins"); // A IMPLEMENTER
                if (groupes_admin != null){
           			for (Groupe g : groupes_admin) {
                    	String groupe_nom = g.getNom(); 
                %>		<p><%= groupe_nom %></p>
                <%}  
           		  } else {
                %><p> Vous n'administrez aucun groupe actuellement. <a href="creer_groupe.jsp">Créer un groupe</a> <br> </p>
				<%} 
				%>
                
                <a href="connexion.jsp"> <%= session.invalidate() %> Déconnexion </a> <br> </p>
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
         </fieldset>
</body>
</html>