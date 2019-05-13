	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
	

	<%
	Collection<String> statistiques = (Collection<String>) request.getAttribute("statistiques");
	boolean hasStats = (statistiques != null);
	if(hasStats) {
		
		List<String> mois = new ArrayList<String>();
		List<Integer> defis_par_mois = new ArrayList<Integer>();
		List<Integer> csjfs_par_mois = new ArrayList<Integer>();
		
		for(String str : statistiques){
			String[] tokens = str.split(":");
			mois.add(tokens[0]);
			defis_par_mois.add(Integer.parseInt(tokens[1]));
			csjfs_par_mois.add(Integer.parseInt(tokens[2]));
		}
		%>
		<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
		<canvas id="chart"></canvas>
		<script>	
			var labelsMois = new Array(<%= mois.size() %>);
			var defis = new Array(<%= defis_par_mois.size() %>);
			var csjfs = new Array(<%= csjfs_par_mois.size() %>);

			<%
			for(int i = 0; i < mois.size(); i++){
				%> labelsMois[<%=i%>] = "<%=mois.get(i)%>";<%
				%> defis[<%=i%>] = <%=defis_par_mois.get(i)%>;<%
				%> csjfs[<%=i%>] = <%=csjfs_par_mois.get(i)%>;<%
			}
			%>
			
			var datasetSemaine = [ 
			{
			  	label: 'Défis',
			   	backgroundColor: 'rgb(235, 50, 50)',
			   	borderColor: 'rgb(235, 50, 50)',
			   	data: defis,
			   	fill: false
			},
			{
		   		label: 'CSJF',
		   		backgroundColor: 'rgb(255, 240, 120)',
		   		borderColor: 'rgb(255, 240, 120)',
		   		data: csjfs,
		   		fill: false,
			},
			];
					
			
			var barChartData = 
			{
				labels: labelsMois,
				datasets: datasetSemaine
		   	};
		   window.onload = function() {
				var ctx = document.getElementById('chart').getContext('2d');
				window.myBar = new Chart(ctx, {
					type: 'line',
					data: barChartData,
					options: {
						title: {
							display: true,
							text: 'Statistiques',
						},
						tooltips: {
							mode: 'index',
							intersect: false
						},
						responsive: true,
						hover: {
							mode: 'nearest',
							intersect: true
						},
						scales: {
							xAxes: [{
								display: true,
								scaleLabel: {
									display: true,
									labelString: 'Month'
								}
							}],
							yAxes: [{
								display: true,
								scaleLabel: {
									display: true,
									labelString: 'Value'
								}
							}]
						}
					}
				});
			};
		</script>
	<%
	}
	%>
