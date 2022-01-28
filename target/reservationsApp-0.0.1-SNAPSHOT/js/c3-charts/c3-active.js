$(document).ready(function() {
	$.ajax({
		url: "Statistiques",
		type: 'GET',
		data: { op: 'load2' },
		success: function(data, textStatus, jqXHR) {
			second(data);
		},
		error: function(data, textStatus, jqXHR) {
			console.log(textStatus);
		}
	})

	function second(dataLoaded) {
		var columns = ['Reservations'];
		var categories = [];
		for (const [key, value] of Object.entries(dataLoaded)) {
			columns.push(value);
			categories.push(key);
		}
		c3.generate({
			bindto: '#slineChart',
			data: {
				columns: [
					columns
				],
				colors: {
					data1: '#006DF0'
				},
				axes: {
					data1: 'x',
					data1: 'y'
				},
				type: 'spline'
			},
			axis: {
				x: {
					type: 'category',
					categories: categories,
					tick: {
						multiline: false,
						culling: {
							max: 1
						},
					},
					label: {
						text: 'Mois',
						position: 'center-bottom',
					},
				},
				y: {
					label: {
						text: 'Nombre de reservations',
						position: 'outer-middle'
					}
				},
			}
		});
	}

})

