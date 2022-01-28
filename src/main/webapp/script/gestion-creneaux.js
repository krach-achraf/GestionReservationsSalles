$(document).ready(function() {
	var operation = "ajout";
	$.ajax({
		url: 'Creneaux',
		type: 'GET',
		success: function(data, textStatus, jqXHR) {
			remplir(data);
		},
		error: function(data, textStatus, jqXHR) {
			console.log(textStatus);
		}
	})

	$("#envoyerCrn").click(function() {
		var id = $("#idCreneau").val();
		var heureDebut = $("#heureDebut").val();
		var heureFin = $("#heureFin").val();
		if (heureDebut >= heureFin) {
			Swal.fire({
				position: 'center',
				icon: 'warning',
				title: 'Veuillez choisir des heures convenables.',
				showConfirmButton: false,
				timer: 1500
			})
		} else {
			if (operation === "ajout") {
				$.ajax({
					url: 'Creneaux',
					type: 'POST',
					data: { heureDebut: heureDebut, heureFin: heureFin, op: 'add' },
					success: function(data, textStatus, jqXHR) {
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Creneau ajoutee.',
							showConfirmButton: false,
							timer: 1500
						})
						remplir(data);
						$("#idCreneau").val(0);
					},
					error: function(data, textStatus, jqXHR) {
						if (data.status == 405) {
							Swal.fire({
								position: 'center',
								icon: 'warning',
								title: 'Le creneau est deja ajoute.',
								showConfirmButton: false,
								timer: 1500
							})
						} else if (data.status == 406) {
							Swal.fire({
								position: 'center',
								icon: 'warning',
								title: 'Tous les champs sont obligatoires',
								showConfirmButton: false,
								timer: 1500
							})
						}
						console.log(textStatus);
					}
				})
			} else if (operation === "modification") {
				$.ajax({
					url: "Creneaux",
					type: 'POST',
					data: { id: id, heureDebut: heureDebut, heureFin: heureFin, op: 'update' },
					success: function(data, textStatus, jqXHR) {
						$("#idCreneau").val(0 + "");
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Creneau modifie',
							showConfirmButton: false,
							timer: 1500
						})
						remplir(data);
						operation = "ajout";
					},
					error: function(data, textStatus, jqXHR) {
						if (data.status == 406) {
							Swal.fire({
								position: 'center',
								icon: 'warning',
								title: 'Tous les champs sont obligatoires',
								showConfirmButton: false,
								timer: 1500
							})
						}
						console.log(textStatus);
					}
				})
			}
		}

	})

	$("#content").on("click", ".delete", function() {
		var id = $(this).attr("val");
		Swal.fire({
			title: 'Vous etes sur?',
			text: "Tu ne pourras pas recuperer le creneau apres la suppression!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Oui, supprimer!'
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					url: 'Creneaux',
					type: 'POST',
					data: { id: id, op: "delete" },
					success: function(data, textStatus, jqXHR) {
						Swal.fire(
							'Suprimee!',
							'Creneau supprimee.',
							'success'
						)
						remplir(data);
					},
					error: function(data, textStatus, jqXHR) {
						if (data.status == 408) {
							Swal.fire({
								position: 'center',
								icon: 'warning',
								title: 'Impossible de supprimer le creneau',
								showConfirmButton: false,
								timer: 1500
							})
						}
						console.log(textStatus);
					}
				})
			}
		})
	})

	$("#content").on("click", ".update", function() {
		$("#idCreneau").val($(this).attr("val"));
		$("#heureDebut").val(formatTime($(this).closest('tr').find('td').eq(1).text()));
		$("#heureFin").val(formatTime($(this).closest('tr').find('td').eq(2).text()));
		operation = "modification";
	})

	function remplir(data) {
		var ligne = "";
		for (var i = 0; i < data.length; i++) {
			ligne += "<tr><td>" + data[i].id + "</td><td>" + data[i].heureDebut + "</td><td>" + data[i].heureFin + "</td>" + "<td><button type='button' class='delete btn btn-custon-rounded-four btn-danger'val ='" + data[i].id + "'>Supprimer</button><button style='margin-left:5px' type='button' class='update btn btn-custon-rounded-four btn-primary' val ='" + data[i].id + "'>Modifier</button></td></tr>";
		}
		$("#content").html(ligne);
	}

	function formatTime(heure) {
		var hMinSec = heure.split(':');
		var pmAm = hMinSec[2].split(' ');
		if (pmAm[1] === "PM") {
			if (parseInt(hMinSec[0]) == 12)
				var nvHeure = "12:" + hMinSec[1];
			else
				var nvHeure = (parseInt(hMinSec[0]) + 12) + ":" + hMinSec[1];
		}
		else {
			if (parseInt(hMinSec[0]) == 12)
				var nvHeure = "00:" + hMinSec[1];
			else
				var nvHeure = hMinSec[0] + ":" + hMinSec[1];
		}

		return nvHeure;
	}
})