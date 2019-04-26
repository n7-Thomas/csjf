<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="controleur.*, modele.*, java.util.*, javax.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% Membre mb = ((Membre) session.getAttribute("user")); %>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Page d'accueil%></title>
		<link type="text/css" rel="stylesheet" href="CSS/accueil.css" />
	</head>
<body>

<fieldset>
	<% Membre mbr_co = (Membre) session.getAttribute("user");%>

	<h1> Hello <%=mbr_co.getPrenom() %>, bienvenue sur ta page d'Accueil ! </h1>

	<a href="ServeurConnexion?action=afficher_profil"> Voir mon profil</a> <br>
	
	<h2> Mes groupes:</h2>
	
                <%
                System.out.println("\n\n\n bda: ");
                Collection<Groupe> groupes = (Collection<Groupe>) request.getAttribute("groupesAppartenus"); 
            	System.out.println("\n\n\n AH : " + groupes);
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
				Collection<Groupe> groupes_admin = (Collection<Groupe>) request.getAttribute("groupesAdmin");
                if (groupes_admin != null){
           			for (Groupe g : groupes_admin) {
                    	String groupe_nom = g.getNom(); 
                %>		<p><%= groupe_nom %></p>
                <%}  
           		  } else {
                %><p> Vous n'administrez aucun groupe actuellement. <a href="creer_groupe.jsp">Créer un groupe</a> <br> </p>
				<%} 
				%>

	
	
	<h2> Les Groupes existant: </h2>

	<%Collection<Groupe> Autresgroupes = (Collection<Groupe>) request.getAttribute("groupes"); %>

	<ul>
	<% 
	
	for(Groupe g: Autresgroupes){
				
		String groupe = g.getNom();
		%>
		<li><%=groupe%> <li/>		

		<%}%> </ul>

		
	<a href="demande_a_rejoindre.jsp"> Rejoindre un groupe</a> <br>
	
</fieldset>		

</body>
</html>