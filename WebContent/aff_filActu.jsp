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

	
<% Collection<Publication> listePubli= (Collection<Publication>) request.getAttribute("listePublication");
	
	for (Publication p: listePubli){

			String publi = p.getMembre().getPrenom() + ": " + p.getContenu();
			%>
			<%=publi %> <br>
			
			<% 			

	}%>


<form action= "ServeurPageGroupe" method="post">

	Créer une publication: <input type= "text" name= "contenu"><br/> 
	
	<input type= "submit"  value= "OK">
	<input type= "hidden" name= "action" value= "publier">	
	<input type="hidden" name="id_grp" value="<%=request.getAttribute("id_grp") %>">
	
</form>

</body>
</html>