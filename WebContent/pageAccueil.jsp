<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="controleur.*, modele.*, java.util.*, javax.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% Membre mb = ((Membre) session.getAttribute("user")); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Page d'accueil%></title>
</head>
<body>


<% Membre mbr_co = (Membre) session.getAttribute("user");%>
	<h3><p> Hello <%=mbr_co.getPrenom() %>, bienvenu sur ta page d'Accueil ! <p> </h3>

<a href="profil.jsp"> Mon profil</a> <br>


<h5> Les Groupes déjà existant: <br> <h5/>
<br/>
<p> <%Collection<Groupe> groupes = (Collection<Groupe>) request.getAttribute("groupes");
		for(Groupe g: groupes){
			
			String groupe = g.getNom();
			%>
			<%=groupe%> <br>
		<%}%> <p/>
			

</body>
</html>