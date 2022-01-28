document.addEventListener('DOMContentLoaded', function() {
	$.ajax({
		url: 'Reservations',
		type: 'GET',
		data: { op: 'load' },
		success: function(data, textStatus, jqXHR) {
			second(data);
		},
		error: function(data, textStatus, jqXHR) {
			console.log(textStatus);
		}
	})

	function second(data) {
		var events = [];
		console.log(data);
		for (var i = 0; i < data.length; i++) {
			if (data[i].etatReservation === "valide")
				events.push({ id: data[i].id, title: data[i].salleCreneau.salle.code + " : " + convertTime12to24(data[i].salleCreneau.creneau.heureDebut) + " - " + convertTime12to24(data[i].salleCreneau.creneau.heureFin), start: formatDate(data[i].dateReservation), color: '#5cb85c' })
			else
				events.push({ id: data[i].id, title: data[i].salleCreneau.salle.code + " : " + convertTime12to24(data[i].salleCreneau.creneau.heureDebut) + " - " + convertTime12to24(data[i].salleCreneau.creneau.heureFin), start: formatDate(data[i].dateReservation) })
		}
		var calendarEl = document.getElementById('calendar');
		var calendar = new FullCalendar.Calendar(calendarEl, {
			initialView: 'dayGridMonth',
			headerToolbar: {
				left: 'prev,next today',
				center: 'title',
				right: 'dayGridMonth,timeGridWeek,listWeek'
			},
			editable: true,
			events: events,
			eventClick: function(info) {
				var id = info.event.id;
				Swal.fire({
					title: 'Vous etes sur?',
					text: "Tu ne pourras pas recuperer la reservation apres la suppression!",
					icon: 'warning',
					showCancelButton: true,
					confirmButtonColor: '#3085d6',
					cancelButtonColor: '#d33',
					confirmButtonText: 'Oui, supprimer!'
				}).then((result) => {
					if (result.isConfirmed) {
						$.ajax({
							url: 'Reservations',
							type: 'POST',
							data: { id: id, op: "delete" },
							success: function(data, textStatus, jqXHR) {
								Swal.fire({
									position: 'center',
									icon: 'success',
									title: 'Reservation annulee',
									showConfirmButton: false,
									timer: 1500
								})
								window.location.reload();
							},
							error: function(data, textStatus, jqXHR) {
								if (data.status == 400) {
									Swal.fire({
										position: 'center',
										icon: 'warning',
										title: 'Reservation valide',
										showConfirmButton: false,
										timer: 1500
									})
								}
								console.log(textStatus);
							}
						})
					}
				})
			}
		});
		calendar.setOption('locale', 'fr');
		calendar.render();

		calendar.on('dateClick', function(info) {
			var date = info.dateStr;
			var today = new Date();
			var dd = String(today.getDate()).padStart(2, '0');
			var mm = String(today.getMonth() + 1).padStart(2, '0');
			var yyyy = today.getFullYear();
			today = yyyy + '-' + mm + '-' + dd;
			if (date >= today) {
				$.ajax({
					url: 'Reservations',
					type: 'GET',
					data: { op: 'loadDisponibilite', date: date },
					success: function(data, textStatus, jqXHR) {
						remplirSelect(data);
						$("#PrimaryModalalert").modal("show");
					},
					error: function(data, textStatus, jqXHR) {
						console.log(textStatus);
					}
				})

				$("#envoyer").click(function() {
					var id = $('#idPlanning option:selected').val()
					$.ajax({
						url: "Reservations",
						type: 'POST',
						data: { id: id, date: date },
						success: function(data, textStatus, jqXHR) {
							window.location.reload();
						},
						error: function(data, textStatus, jqXHR) {
							console.log(textStatus);
						}
					})
				})
			} else {
				Swal.fire({
					position: 'center',
					icon: 'warning',
					title: 'Veuillez choisir une date correct.',
					showConfirmButton: false,
					timer: 1500
				})
			}
		});

	}

	function formatDate(date) {
		var d = new Date(date),
			month = '' + (d.getMonth() + 1),
			day = '' + d.getDate(),
			year = d.getFullYear();

		if (month.length < 2)
			month = '0' + month;
		if (day.length < 2)
			day = '0' + day;

		return [year, month, day].join('-');
	}

	const convertTime12to24 = (time12h) => {
		const [time, modifier] = time12h.split(' ');

		let [hours, minutes] = time.split(':');

		if (hours === '12') {
			hours = '00';
		}

		if (modifier === 'PM') {
			hours = parseInt(hours, 10) + 12;
		}

		return `${hours}:${minutes}`;
	}

	function remplirSelect(data) {
		var ligne = "";
		for (var i = 0; i < data.length; i++) {
			ligne += "<option value='" + data[i].id + "'>" + data[i].salle.code + " : " + data[i].creneau.heureDebut + " - " + data[i].creneau.heureFin + "</option>";
		}
		$("#idPlanning").html(ligne);
	}
});