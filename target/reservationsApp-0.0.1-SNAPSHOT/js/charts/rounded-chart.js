$(document).ready(function() {
	$.ajax({
		url: "Statistiques",
		type: 'GET',
		data: { op: 'load1' },
		success: function(data, textStatus, jqXHR) {
			second(data);
		},
		error: function(data, textStatus, jqXHR) {
			console.log(textStatus);
		}
	})

	function second(dataLoaded) {
		var ctx = document.getElementById("piechart");
		var labels = [];
		var data = [];
		var backgroundColor = [];
		for (const [key, value] of Object.entries(dataLoaded)) {
			labels.push(key);
			data.push(value);
			backgroundColor.push('rgb(' + Math.floor(Math.random() * 255) + ',' + Math.floor(Math.random() * 255) +  ',' + Math.floor(Math.random() * 255) + ')');
		}
		var piechart = new Chart(ctx, {
			type: 'pie',
			data: {
				labels: labels,
				datasets: [{
					label: 'pie Chart',
					backgroundColor: backgroundColor,
					data: data
				}]
			},
			options: {
				responsive: true
			}
		});
	}
})