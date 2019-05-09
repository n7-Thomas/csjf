<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*, controleur.*,modele.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aff_filActu</title>
<link type="text/css" rel="stylesheet" href="CSS/fil_actu.css" />
</head>
<body>




<h3>Fil d'Actualité </h3>

	
<%  Membre user = (Membre) request.getAttribute("user");
	Collection<Publication> listePubli= (Collection<Publication>) request.getAttribute("listePublications");
	if(listePubli != null) {
	
		for (Publication p: listePubli){
			String publi;
			
			//Publications			
			if(p.getMembre() != null) {
				
				//Publications du user du user
				if(p.getMembre().getId() == user.getId()) {
					publi = p.getMembre().getPrenom() + ": " + p.getContenu();	
				
				//Publications des autres membres
				} else {
					publi = p.getMembre().getPrenom() + ": " + p.getContenu();
				}
				
			
			//Notifications
			}else {publi = p.getContenu();}
			%>
			<div id="publications">
			<%=publi %> <br>
			</div>
			
			<%}		

	} else { System.out.println("pas de publi"); } %>


<form action= "ServeurPageGroupe" method="post">

	Publier: <input type= "text" name= "contenu">
		
	<input type= "hidden" name= "action" value= "publier">	
	<input type="hidden" name="id_grp" value="<%=request.getAttribute("id_grp") %>">
	
</form>

</body>
</html>