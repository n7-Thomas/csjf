	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
	<%
	Collection<String> classement = (Collection<String>) request.getAttribute("classement");
	boolean hasNP = (classement != null);
	if(hasNP) { 
		List<String> prenoms = new ArrayList<String>();
		List<Integer> scores = new ArrayList<Integer>();
		for(String str : classement){
			String[] tokens = str.split(":");
			prenoms.add(tokens[0]);
			scores.add(Integer.parseInt(tokens[1]));
		}
		%>
		<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
		<canvas id="chart"></canvas>
		<script>	
			var labelsGene = new Array(<%= prenoms.size() %>);
			var dataGene = new Array(<%= scores.size() %>);
			<%
			for(int i = 0; i < prenoms.size(); i++){
				%> labelsGene[<%=i%>] = "<%=prenoms.get(i)%>";<%
				%> dataGene[<%=i%>] = <%=scores.get(i)%>;<%
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
	}
	%>
