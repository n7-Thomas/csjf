<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*, controleur.*,modele.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aff_filActu</title>
</head>
<body>

Fil d'Actualité <br>

<form method= "get" action = "serveur">


<% List<Notification> listeNotif = (List<Notification>) request.getAttribute("listeNotifications");
	
	for (Notification n: listeNotif){

			String notif = n.getMessage();
			%>
			<%=notif %> <br>
			
			<% 			
	}%>
	
<% List<Publication> listePubli= (List<Publication>) request.getAttribute("listePublication");
	
	for (Publication p: listePubli){

			String publi = p.getMembre().getPrenom() + ": " + p.getContenu();
			%>
			<%=publi %> <br>
			
			<% 			
	}%>
	

</form>

</body>
</html>