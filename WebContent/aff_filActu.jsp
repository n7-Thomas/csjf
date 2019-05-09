<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*, controleur.*,modele.*" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aff_filActu</title>
<link type="text/css" rel="stylesheet" href="CSS/profil.css" />
</head>
<body>

<h3>Fil d'Actualité </h3>

	
<% Collection<Publication> listePubli= (Collection<Publication>) request.getAttribute("listePublications");
	if(listePubli != null) {
	
		for (Publication p: listePubli){
			
			String publi = p.getMembre().getPrenom() + ": " + p.getContenu();			
			
			%>
			<%=publi %> <br>
			
			<%}		

	} else { System.out.println("pas de publi"); } %>


<form action= "ServeurPageGroupe" method="post">

	Publier: <input type= "text" name= "contenu">
	
	<input type= "submit"  value= "OK">
	<input type= "hidden" name= "action" value= "publier">	
	<input type="hidden" name="id_grp" value="<%=request.getAttribute("id_grp") %>">
	
</form>

</body>
</html>