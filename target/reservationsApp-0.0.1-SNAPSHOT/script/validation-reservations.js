$(document).ready(function() {
	$.ajax({
		url: "Reservations",
		type: 'GET',
		data: { op: 'loadResValid' },
		success: function(data, textStatus, jqXHR) {
			remplirV(data);
		},
		error: function(data, textStatus, jqXHR) {
			console.log(textStatus);
		}
	})
	
	$.ajax({
		url: "Reservations",
		type: 'GET',
		data: { op: 'loadResEnAtt' },
		success: function(data, textStatus, jqXHR) {
			remplirEV(data);
		},
		error: function(data, textStatus, jqXHR) {
			console.log(textStatus);
		}
	})
	
	$("#contentEV").on("click", ".delete", function() {
		var id = $(this).attr("val");
		Swal.fire({
			title: 'Vous etes sur?',
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
					data: { id: id, op: "invalide" },
					success: function(data, textStatus, jqXHR) {
						window.location.reload();
					},
					error: function(data, textStatus, jqXHR) {
						console.log(textStatus);
					}
				})
			}
		})
	})
	
	$("#contentEV").on("click", ".update", function() {
		var id = $(this).attr("val");
		Swal.fire({
			title: 'Vous etes sur?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Oui, valider!'
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					url: 'Reservations',
					type: 'POST',
					data: { id: id, op: "valide" },
					success: function(data, textStatus, jqXHR) {
						window.location.reload();
					},
					error: function(data, textStatus, jqXHR) {
						console.log(textStatus);
					}
				})
			}
		})
	})
	
	function remplirV(data) {
		var ligne = "";
		for (var i = 0; i < data.length; i++) {
			ligne += "<tr><td>" + data[i].id + "</td><td>" + data[i].salleCreneau.salle.code + " - " + data[i].salleCreneau.salle.type + " - " + data[i].salleCreneau.salle.capacite  + "</td><td>" + data[i].salleCreneau.creneau.heureDebut + " - " + data[i].salleCreneau.creneau.heureFin + "</td><td>" + data[i].client.nom + " " + data[i].client.prenom + "</td><td>" + data[i].dateReservation + "</td></tr>";
		}
		$("#contentV").html(ligne);
	}
	
	function remplirEV(data) {
		var ligne = "";
		for (var i = 0; i < data.length; i++) {
			ligne += "<tr><td>" + data[i].id + "</td><td>" + data[i].salleCreneau.salle.code + " - " + data[i].salleCreneau.salle.type + " - " + data[i].salleCreneau.salle.capacite  + "</td><td>" + data[i].salleCreneau.creneau.heureDebut + " - " + data[i].salleCreneau.creneau.heureFin + "</td><td>" + data[i].client.nom + " " + data[i].client.prenom + "</td><td>" + data[i].dateReservation + "</td><td><button type='button' class='delete btn btn-custon-rounded-four btn-danger' val='" + data[i].id + "'>Invalider</button><button type='button' class='update btn btn-custon-rounded-four btn-primary' val='" + data[i].id + "' style='margin-left: 10px;margin-right: 10px'>Valider</button></td></tr>";
		}
		$("#contentEV").html(ligne);
	}
})