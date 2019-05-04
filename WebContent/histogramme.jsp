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
			var csjfsGene = new Array(<%= defisToGene.size() %>);
			var defisGene = new Array(<%= csjfsToGene.size() %>);
			<%
			for(int i = 0; i < prenomsToGene.size(); i++){
				%> labelsGene[<%=i%>] = "<%=prenomsToGene.get(i)%>";<%
				%> csjfsGene[<%=i%>] = <%=defisToGene.get(i)%>;<%
				%> defisGene[<%=i%>] = <%=csjfsToGene.get(i)%>;<%
			}
			%>
			
			var barChartData = 
			{
			labels: labelsGene,
			datasets: 
				[{
			    	label: 'Défis',
			    	backgroundColor: 'rgb(255,0,0)',
			    	borderColor: 'rgb(255,0,0)',
			    	data: defisGene
		   		},{
			    	label: 'CSJF',
			    	backgroundColor: 'rgb(255,255,0)',
			    	borderColor: 'rgb(255,255,0)',
			    	data: csjfsGene
		   		} ]
		   };
		   window.onload = function() {
				var ctx = document.getElementById('chart').getContext('2d');
				window.myBar = new Chart(ctx, {
					type: 'bar',
					data: barChartData,
					options: {
						title: {
							display: true,
							text: 'Classement'
						},
						tooltips: {
							mode: 'index',
							intersect: false
						},
						responsive: true,
						scales: {
							xAxes: [{
								stacked: true,
							}],
							yAxes: [{
								stacked: true
							}]
						}
					}
				});
			};

		</script>
	<%
	}
	%>
