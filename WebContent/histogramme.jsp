	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
	<%
	Collection<String> classement = (Collection<String>) request.getAttribute("classement");
	boolean hasNP = (classement != null);
	if(hasNP) { 
		List<String> prenomsToGene = new ArrayList<String>();
		List<Integer> defisToGene = new ArrayList<Integer>();
		List<Integer> csjfsToGene = new ArrayList<Integer>();
		for(String str : classement){
			String[] tokens = str.split(":");
			prenomsToGene.add(tokens[0]);
			defisToGene.add(Integer.parseInt(tokens[1]));
			csjfsToGene.add(Integer.parseInt(tokens[2]));
		}
		%>
		<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
		<canvas id="chart"></canvas>
		<script>	
			var labelsGene = new Array(<%= prenomsToGene.size() %>);
			var csjfGene = new Array(<%= defisToGene.size() %>);
			var defiGene = new Array(<%= csjfsToGene.size() %>);
			<%
			for(int i = 0; i < prenomsToGene.size(); i++){
				%> labelsGene[<%=i%>] = "<%=prenomsToGene.get(i)%>";<%
				%> csjfGene[<%=i%>] = "<%=defisToGene.get(i)%>";<%
				%> defiGene[<%=i%>] = <%=csjfsToGene.get(i)%>;<%
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
			    	label: 'Défis',
			    	backgroundColor: window.chartColors.green,
			    	borderColor: window.chartColors.green,
			    	data: defisGene
		   		},{
			    	label: 'CSJF',
			    	backgroundColor: window.chartColors.red,
			    	borderColor: window.chartColors.red,
			    	data: csjfGene
		   		} ]
		   },
		   // Configuration options go here
		   options: {}
		   });	
		</script>
	<%
	}
	%>
