<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*, controleur.*,modele.*" %>

<link type="text/css" rel="stylesheet" href="CSS/fil_actu.css" />
<link href="https://afeld.github.io/emoji-css/emoji.css" rel="stylesheet">

<div id="fil_actu">
	
<h3 style="border: 2px solid with"> Fil d'actualité </h3>
	<div id="conversation"> 
	
	
		
	<%
	    Membre user_actuel = (Membre) session.getAttribute("user");
		Collection<Publication> listePubli= (Collection<Publication>) request.getAttribute("listePublications");
		 
		if(listePubli != null) {
		
			for (Publication p: listePubli){
				String publi;
				int id = p.getId();
				
				//Publications			
				if(p.getMembre() != null) { 
									
					  
					//Publications du user 		
					if(p.getMembre().getId() == user_actuel.getId()) { %>
					<div class="publication_user">
						<div class="membre_user"> <%= p.getMembre().getPrenom()%> </div>
						
						<div class="publication_bloc1" style="flex-direction: row-reverse; float:right;">
							<span class="texte_user" > <%=p.getContenu() %> </span>								
							<span  class="bar_reactions">	😐	
					 			<span class="emoji" >					 					
						 			<h3><a href="ServeurGroupe?action=reagir&type=cool&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
						 			👍 </a></h3>
						 			<h3><a href="ServeurGroupe?action=reagir&type=pasCool&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
						 			👎 </a></h3>
						 			<h3><a href="ServeurGroupe?action=reagir&type=surpris&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
						 			😲 </a></h3>			 			
						 		</span> 	
							</span>					 		
					 	</div>
					    
							
					<div class="reactions" style="justify-content: right; padding-right:30px">
						<% 						
						//Les Réactions
						if(p.getReactions() != null) {
							
							Collection<Membre> membresCool = new ArrayList<Membre>();
							Collection<Membre> membresPasCool = new ArrayList<Membre>();
							Collection<Membre> membresSurpris = new ArrayList<Membre>();
							
							for(Reaction r: p.getReactions()) { 																						
								//Les Cool 																				
								if(r.isCool() ){ membresCool.add(r.getMembre());} 							
								//Les Pas Cool							
								if(r.isPasCool()){ membresPasCool.add(r.getMembre()); }
								//Les Surprit
								if(r.isSurpris()){ membresSurpris.add(r.getMembre()); }													
		 					} 
		 					
		 					 if(!membresCool.isEmpty()){ %>
		 					<div class="NBreactions" >
		 						  👍 
		 						<span class="listeMembres">
		 							<h5> J'aime</h5> 
		 							<%for(Membre m : membresCool){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</div>
		 					<% } 
		 					
		 					if(!membresPasCool.isEmpty()){ %>
		 					<div class="NBreactions">
		 						  👎
		 						<span class="listeMembres">
		 							<h5> J'aime pas </h5> 
		 							<%for(Membre m : membresPasCool){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</div>
		 					<%} 
		 					
		 					if(!membresSurpris.isEmpty()){ %>
		 					<div class="NBreactions">
		 						  😲
		 						<span class="listeMembres">
		 							<h5> Oh! </h5> 
		 							<%for(Membre m : membresSurpris){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</div>
		 					<%} 
		 					int nbR = membresCool.size() + membresPasCool.size() + membresSurpris.size(); 
		 					if(nbR>0){ %>  <div style="color:#555753"><%=nbR %></div>
							<% }	
					%>		
					</div>
					
					</div>	
					<% } 
														
					 
					 //Publications des autres membres
					 } else { %>			 
					 <div class=publication_autre >
					 
					 	<div class=membre_autre> <%=(p.getMembre().getPrenom() ) %> </div>
					 	<div class="publication_bloc1" style="flex-direction: row; float:left;">
					 			<div class="texte_autres"> <%=p.getContenu()  %></div>
						 		<span  class="bar_reactions">					 	
								    	😐	
						 			<span class="emoji" >					 					
						 				<a href="ServeurGroupe?action=reagir&type=cool&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
						 				👍 </a></h3>
						 				<h3><a href="ServeurGroupe?action=reagir&type=pasCool&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
						 				👎 </a></h3>
						 				<h3><a href="ServeurGroupe?action=reagir&type=surpris&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
						 				😲 </a></h3>			 			
							 		</span> 	
								</span>	
						
						</div>
						<div class="reactions" style="justify-content: left">
						<% 						
						//Les Réactions
						if(p.getReactions() != null) {
							
							Collection<Membre> membresCool = new ArrayList<Membre>();
							Collection<Membre> membresPasCool = new ArrayList<Membre>();
							Collection<Membre> membresSurpris = new ArrayList<Membre>();
							
							for(Reaction r: p.getReactions()) { 																						
								//Les Cool 																				
								if(r.isCool() ){ membresCool.add(r.getMembre());} 							
								//Les Pas Cool							
								if(r.isPasCool()){ membresPasCool.add(r.getMembre()); }
								//Les Surprit
								if(r.isSurpris()){ membresSurpris.add(r.getMembre()); }													
		 					} 
		 					
		 					 if(!membresCool.isEmpty()){ %>
		 					<div class="NBreactions" >
		 						  👍 
		 						<span class="listeMembres">
		 							<h5> J'aime</h5> 
		 							<%for(Membre m : membresCool){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</div>
		 					<% } 
		 					
		 					if(!membresPasCool.isEmpty()){ %>
		 					<div class="NBreactions">
		 						  👎
		 						<span class="listeMembres">
		 							<h5> J'aime pas </h5> 
		 							<%for(Membre m : membresPasCool){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</div>
		 					<%} 
		 					
		 					if(!membresSurpris.isEmpty()){ %>
		 					<div class="NBreactions">
		 						  😲
		 						<span class="listeMembres">
		 							<h5> Oh! </h5> 
		 							<%for(Membre m : membresSurpris){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</div>
		 					<%} 
		 					int nbR = membresCool.size() + membresPasCool.size() + membresSurpris.size(); 
		 					if(nbR>0){ %>  <div style="color:#555753"><%=nbR %></div>
							<% }	
					%>		
					</div>
						
					</div>
					<% } 
					} %>

				
				<% //Notifications
				} else { %>			
				<div class="notification>">
					<div class="notification_bloc1">
						<div class="texte_notification"> <%= p.getContenu() %> </div>			
						<div class="bar_reactions">					 	
						    <p>😐</p>	
				 			<div class="emoji" >		
				 						 					
				 			<h3><a href="ServeurGroupe?action=reagir&type=cool&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
				 			👍
				 			</a></h3>
				 			
				 			<h3><a href="ServeurGroupe?action=reagir&type=pasCool&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
				 			👎
				 			</a></h3>
				 			
				 			<h3><a href="ServeurGroupe?action=reagir&type=surpris&id_publication=<%=id%>&id_grp=<%=request.getAttribute("id_grp") %>">
				 			😲
				 			</a></h3>
				 			
				 			</div>	
					 	</div> 
					 </div>	
					 	
										 						
					<div class="reactions" style="justify-content: center;">
						<% 						
						//Les Réactions
						if(p.getReactions() != null) {
							
							Collection<Membre> membresCool = new ArrayList<Membre>();
							Collection<Membre> membresPasCool = new ArrayList<Membre>();
							Collection<Membre> membresSurpris = new ArrayList<Membre>();
							
							for(Reaction r: p.getReactions()) { 
																							
								//Les Cool 																				
								if(r.isCool() ){ 
									membresCool.add(r.getMembre());								
								} 
								
								//Les Pas Cool							
								if(r.isPasCool()){
									membresPasCool.add(r.getMembre());
								}
			
								//Les Surprit
								if(r.isSurpris()){ 
									membresSurpris.add(r.getMembre());
								}													
		 					} 
		 					
		 					 if(!membresCool.isEmpty()){ %>
		 					<span class="NBreactions">
		 						  👍 
		 						<span  class="listeMembres">
		 							<h5> J'aime</h5> 
		 							<%for(Membre m : membresCool){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</span>
		 					<% } 
		 					
		 					if(!membresPasCool.isEmpty()){ %>
		 					<span class="NBreactions">
		 						  👎
		 						<span  class="listeMembres">
		 							<h5> J'aime pas </h5> 
		 							<%for(Membre m : membresPasCool){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</span>
		 					<%} 
		 					
		 					if(!membresSurpris.isEmpty()){ %>
		 					<span class="NBreactions">
		 						  😲
		 						<span class="listeMembres">
		 							<h5> Oh! </h5> 
		 							<%for(Membre m : membresSurpris){ %>
		 								<%=m.getPrenom() %>	 <br>						
		 							<%} %>
		 						</span>
		 					</span>
		 					<%} 
		 					int nbR = membresCool.size() + membresPasCool.size() + membresSurpris.size(); 
		 					if(nbR>0){ %>  <div style="color:#555753"><%=nbR %></div>
							<% }	
		 																
						 } %>
					 </div> 
					
				</div>
					 <% 			 				 					    							
	    		}	
			}
			%></div><% 
			
		} else { System.out.println("pas de publi");} %>
	
	


	
	<form action= "ServeurGroupe" method="post">
	
	<div id="publier">
	<input type= "text" name= "contenu" placeHolder="Publier.." style="width:100%">		
	</div>
	<input type= "hidden" name= "action" value= "publier">	
	<input type="hidden" name="id_grp" value="<%=request.getAttribute("id_grp") %>">
		
		
	</form>
</div>	
</div>