<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*, controleur.*,modele.*" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>aff_filActu</title>
<link type="text/css" rel="stylesheet" href="CSS/fil_actu.css" />
</head>
<body>



<h3>Fil d'Actualité </h3>

<div id="fil_actu">

	<div id="conversation">
	
	<% Membre user = (Membre) request.getAttribute("user");
		Collection<Publication> listePubli= (Collection<Publication>) request.getAttribute("listePublications");
		if(listePubli != null) {
		
			for (Publication p: listePubli){
				String publi;
				
				//Publications			
				if(p.getMembre() != null) { %>
					
					
					<%  
					//Publications du user 		
					if(p.getMembre().getId() == user.getId()) { %>
					<div class=publications_user>
						<div class="membre_user">
							<%=(p.getMembre().getPrenom())%>
						</div>
									
						<div class="texte_user">
						 <%=p.getContenu()  %>
						</div>
					</div>
					 <% 
					 //Publications des autres membres
					 } else { %>			 
					 <div class=publications_autres>
					 	<div class=membre_autre>
					 	<%=(p.getMembre().getNom()) %>
					 	</div>
					 
						<div class="texte_autres">
							<%=p.getContenu()  %>
						</div>
					</div>
					<% }

				//Notifications
				} else { %>
					<div class="notifications">
					 <% publi = p.getContenu(); %>
					 <%=publi %>
				    </div>
								
				<%}
				}			
		} else { System.out.println("pas de publi");} %>
	
	</div>


<form action= "ServeurPageGroupe" method="post">

	<div id="publier">
	Publier: <input type= "text" name= "contenu">		
	</div>
		
	<input type= "hidden" name= "action" value= "publier">	
	<input type="hidden" name="id_grp" value="<%=request.getAttribute("id_grp") %>">
	
	
	
</form>

</div>

</body>
</html>