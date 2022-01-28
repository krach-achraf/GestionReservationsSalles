$(document).ready(function() {
	var operation = "ajout";
	$.ajax({
		url: 'Planning',
		type: 'GET',
		success: function(data, textStatus, jqXHR) {
			remplir(data);
		},
		error: function(data, textStatus, jqXHR) {
			console.log(textStatus);
		}
	})

	$("#envoyerPln").click(function() {
		var id = $("#idPlanning").val();
		var idCreneau = $("#idCreneauP").val();
		var idSalle = $("#idSalle").val();
		console.log(idCreneau);console.log(idSalle);
		if (operation === "ajout") {
			$.ajax({
				url: 'Planning',
				type: 'POST',
				data: { idCreneau: idCreneau, idSalle: idSalle, op: 'add' },
				success: function(data, textStatus, jqXHR) {
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: 'Planning ajoutee',
						showConfirmButton: false,
						timer: 1500
					})
					remplir(data);
					$("#idPlanning").val(0);
				},
				error: function(data, textStatus, jqXHR) {
					if (data.status == 405) {
						Swal.fire({
							position: 'center',
							icon: 'warning',
							title: 'Le planning est deja ajoute.',
							showConfirmButton: false,
							timer: 1500
						})
					}
					console.log(textStatus);
				}
			})
		} else if (operation === "modification") {
			$.ajax({
				url: "Planning",
				type: 'POST',
				data: { id: id, idCreneau: idCreneau, idSalle: idSalle, op: 'update' },
				success: function(data, textStatus, jqXHR) {
					$("#idPlanning").val(0 + "");
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: 'Salle modifie',
						showConfirmButton: false,
						timer: 1500
					})
					remplir(data);
					operation = "ajout";
				},
				error: function(data, textStatus, jqXHR) {
					console.log(textStatus);
				}
			})
		}
	})
	
	$("#contenu").on("click", ".update", function() {
		$("#idPlanning").val($(this).attr("val"));
		operation = "modification";
	})
	
	$("#contenu").on("click", ".delete", function() {
		var id = $(this).attr("val");
		Swal.fire({
			title: 'Vous etes sur?',
			text: "Tu ne pourras pas recuperer le planning apres la suppression!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Oui, supprimer!'
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					url: 'Planning',
					type: 'POST',
					data: { id: id, op: "delete" },
					success: function(data, textStatus, jqXHR) {
						Swal.fire(
							'Suprimee!',
							'Plannnig supprimee.',
							'success'
						)
						remplir(data);
					},
					error: function(data, textStatus, jqXHR) {
						if (data.status == 406) {
							Swal.fire({
								position: 'center',
								icon: 'warning',
								title: 'Impossible de supprimer ce ligne',
								showConfirmButton: false,
								timer: 2000
							})
						}
						console.log(textStatus);
					}
				})
			}
		})
	})

	function remplir(data) {
		var ligne = "";
		for (var i = 0; i < data.length; i++) {
			ligne += "<tr><td>" + data[i].id + "</td><td>" + data[i].salle.code + "</td><td>" + data[i].creneau.heureDebut + " - " + data[i].creneau.heureFin + "</td>" + "<td><button type='button' class='delete btn btn-custon-rounded-four btn-danger'val ='" + data[i].id + "'>Supprimer</button><button style='margin-left:5px' type='button' class='update btn btn-custon-rounded-four btn-primary' val ='" + data[i].id + "'>Modifier</button></td></tr>";
		}
		$("#contenu").html(ligne);
	}
})