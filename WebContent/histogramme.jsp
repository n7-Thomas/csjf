<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, controleur.*, modele.*"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Administration du groupe</title>
        <link type="text/css" rel="stylesheet" href="CSS/profil.css" />
        <link type="text/css" rel="stylesheet" href="CSS/form.css" />
        <script type="text/javascript" src="JS/defiEdit.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
	</head>
	<body>
		<%
		// PAS BESOIN DE RECUPERER MEMBRE ON LE FAIT DANS LA NAVIGATION BAR
		//Membre mb = (Membre) session.getAttribute("user");
		//boolean isConnected = (mb != null);
		Groupe groupe = (Groupe) request.getAttribute("groupe");
		boolean hasGroupe = (groupe != null);
		Collection<String> classement = (Collection<String>) request.getAttribute("classement");
		boolean hasNP = (classement != null);
		%>
		<div id="header">
	    	<h1>Histogramme de <span style="color.blue"><%=groupe.getNom()%></span></h1>      
	    	<%@ include file="navigationBar.jsp" %> 
	    	<%@ include file="statusBar.jsp" %>  
    	</div>
    	<%
		if(isConnected && hasGroupe && hasNP) { 
			//Map<String,Integer> classementHashMap = new HashMap<String, Integer>();
			List<String> prenoms = new ArrayList<String>();
			List<Integer> scores = new ArrayList<Integer>();
			for(String str : classement){
				String[] tokens = str.split(":");
				//classementHashMap.put(tokens[0], Integer.parseInt(tokens[1]));
				prenoms.add(tokens[0]);
				scores.add(Integer.parseInt(tokens[1]));
			}
		%>
							 
			<div id="contenu">
			<% if(isConnected && hasGroupe && hasNP) { %>
				<canvas id="chart"></canvas>
			<%} %>
			
			
			<script>
			
			var labelsGene = new Array(<%= prenoms.size() %>);
			var dataGene = new Array(<%= scores.size() %>);
			<%
			for(int i = 0; i < prenoms.size(); i++){
				%> dataGene[<%=i%>] = <%=scores.get(i)%>;<%
				%> labelsGene[<%=i%>] = "<%=prenoms.get(i)%>";<%
			}
			%>
			
			var ctx = document.getElementById('chart').getContext('2d');
			var chart = new Chart(ctx, {
			// The type of chart we want to create
			type: 'bar',
		    // The data for our dataset
		    data: {
			    labels: labelsGene,
			    datasets: [{
			    label: 'Classement',
			    backgroundColor: 'rgb(255, 99, 132)',
			    borderColor: 'rgb(255, 99, 132)',
			    data: dataGene
		   }]
		   },
		   // Configuration options go here
		   options: {}
		   });	
		</script>
		<%			
		 } else {
				if(!isConnected){ %>
					<p> Vous n'êtes pas connectés ! </p>
				<% } else if(!hasGroupe){ %>
					<p> Aucun groupe à administrer ! </p>
				<% } else {
				%>	<p> Aucune donnée.. </p> <%
				}
				%>
		<% } %>
	

		</div>	
	</body>
</html>