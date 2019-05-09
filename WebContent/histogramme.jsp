	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
	<%
	Collection<String> classement = (Collection<String>) request.getAttribute("classement");
	boolean hasNP = (classement != null);
	if(hasNP) { 
		List<String> prenomsToGene = new ArrayList<String>();
		List<Integer> defis_before_ToGene = new ArrayList<Integer>();
		List<Integer> defis_cs_ToGene = new ArrayList<Integer>();
		List<Integer> csjfs_before_ToGene = new ArrayList<Integer>();
		List<Integer> csjfs_cs_ToGene = new ArrayList<Integer>();
		for(String str : classement){
			String[] tokens = str.split(":");
			prenomsToGene.add(tokens[0]);
			defis_before_ToGene.add(Integer.parseInt(tokens[1]));
			defis_cs_ToGene.add(Integer.parseInt(tokens[2]));
			csjfs_before_ToGene.add(Integer.parseInt(tokens[3]));
			csjfs_cs_ToGene.add(Integer.parseInt(tokens[4]));
		}
		%>
		<div id="boutons_hist">
			<button id="semaine">Cette semaine</button>
			<button id="total">Au total</button>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
		<canvas id="chart"></canvas>
		<script>	
			var labelsGene = new Array(<%= prenomsToGene.size() %>);
			var defis_before = new Array(<%= defis_before_ToGene.size() %>);
			var defis_cs = new Array(<%= defis_cs_ToGene.size() %>);
			var csjfs_before = new Array(<%= csjfs_before_ToGene.size() %>);
			var csjfs_cs = new Array(<%= csjfs_cs_ToGene.size() %>);
			<%
			for(int i = 0; i < prenomsToGene.size(); i++){
				%> labelsGene[<%=i%>] = "<%=prenomsToGene.get(i)%>";<%
				%> defis_before[<%=i%>] = <%=defis_before_ToGene.get(i)%>;<%
				%> defis_cs[<%=i%>] = <%=defis_cs_ToGene.get(i)%>;<%
				%> csjfs_before[<%=i%>] = <%=csjfs_before_ToGene.get(i)%>;<%
				%> csjfs_cs[<%=i%>] = <%=csjfs_cs_ToGene.get(i)%>;<%
			}
			%>
			
			var datasetSemaine = [ 
			{
			  	label: 'Défis',
			   	backgroundColor: 'rgb(235, 50, 50)',
			   	borderColor: 'rgb(235, 50, 50)',
			   	data: defis_cs
			},
			{
		   		label: 'CSJF',
		   		backgroundColor: 'rgb(255, 240, 120)',
		   		borderColor: 'rgb(255, 240, 120)',
		   		data: csjfs_cs
			},
			];
			
			var datasetTotal = [
			{
		    	label: 'Défis',
		    	backgroundColor: 'rgb(255,0,0)',
		    	borderColor: 'rgb(255,0,0)',
		    	data: defis_before
	   		},
	   		{
			    label: 'CSJF',
			    backgroundColor: 'rgb(255,255,0)',
			    borderColor: 'rgb(255,255,0)',
			    data: csjfs_before
		   	}
			];
			
			
			var barChartData = 
			{
				labels: labelsGene,
				datasets: datasetSemaine
		   	};
		   window.onload = function() {
				var ctx = document.getElementById('chart').getContext('2d');
				window.myBar = new Chart(ctx, {
					type: 'bar',
					data: barChartData,
					options: {
						title: {
							display: true,
							text: 'Classement sur la semaine'
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
		
			document.getElementById('semaine').addEventListener('click', function() {
					barChartData.datasets = datasetSemaine;
					window.myBar.options.title = {display: true,text: 'Classement sur la semaine'};
					window.myBar.update();
				});
			document.getElementById('total').addEventListener('click', function() {
				barChartData.datasets = datasetTotal;
				window.myBar.options.title = {display: true,text: 'Classement général'};
				window.myBar.update();
			});
		</script>
	<%
	}
	%>
