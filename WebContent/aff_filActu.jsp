<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*, controleur.*,modele.*" %>

<link type="text/css" rel="stylesheet" href="CSS/fil_actu.css" />
<link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">

<div id="fil_actu">

	<div id="conversation"> 
	<h3 style="border: 2px solid with"> Fil d'actualité </h3>
	<% Membre user_actuel = (Membre) session.getAttribute("user");
		Collection<Publication> listePubli= (Collection<Publication>) request.getAttribute("listePublications");
		if(listePubli != null) {
		
			for (Publication p: listePubli){
				String publi;
				
				//Publications			
				if(p.getMembre() != null) { %>
									
					<%  
					//Publications du user 		
					if(p.getMembre().getId() == user_actuel.getId()) { %>
					<div class=publications_user>
						<div class="membre_user">
							<%= p.getMembre().getPrenom() %>
						</div>
							
						<div class="texte_user">
						 <%=p.getContenu()  %>
						</div>
						
						<div class="reactions">
						<% 
						//Les Réactions
						if(p.getReactions() != null) {
							for(Reaction r: p.getReactions()) { 
								//Les Cool %>																				
								<% if(r.isCool() ){ %>
								<div class="cool">
								<%=r.getMembre().getPrenom()%><br>
								</div>
								<%}
								
								//Les Pas Cool
								if(r.isPasCool()){ %>
								<div class="pasCool">
								<%=r.getMembre().getPrenom()%><br>
								</div>
								<%}
								
								//Les Surprit
								if(r.isSurpris()){ %>
								<div class="surpris">
								<%=r.getMembre().getPrenom()%><br>
								</div>
								<%}													
							 }
							
						}%>
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
					 <%= p.getContenu() %>
					 	<div class="bar_reactions">
					 	<ul>
					 	<li>Réagir</li>
					 		<ul>
					 		<li>Cool</li>
					 		<li>PasCool</li>
					 		</ul>
					 	</ul>
					 	
					 	</div>				 	
				    </div>
				    
				    							
				<%}
				}			
		} else { System.out.println("pas de publi");} %>
	
	</div>


<form action= "ServeurGroupe" method="post">
	</b>
	<div id="publier">
	<input type= "text" name= "contenu" placeHolder="Publier.." style="width:100%">		
	</div>
		
	<input type= "hidden" name= "action" value= "publier">	
	<input type="hidden" name="id_grp" value="<%=request.getAttribute("id_grp") %>">
	
</form>
</div>