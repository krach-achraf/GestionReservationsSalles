$(document).ready(function() {
	var operation = "ajout";
	$.ajax({
		url: "Salles",
		type: 'GET',
		success: function(data, textStatus, jqXHR) {
			remplir(data);
		},
		error: function(data, textStatus, jqXHR) {
			console.log(textStatus);
		}
	})

	$("#envoyer").click(function() {
		var id = $("#idSalle").val();
		var code = $("#code").val();
		var capacite = $("#capacite").val();
		var type = $("input[name='type']:checked").val();
		if (operation === "ajout") {
			$.ajax({
				url: "Salles",
				type: 'POST',
				data: { code: code, capacite: capacite, type: type, op: 'add' },
				success: function(data, textStatus, jqXHR) {
					$("#idSalle").val(0 + "");
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: 'Salle ajoutee',
						showConfirmButton: false,
						timer: 1500
					})
					$("#code").val("");
					$("#capacite").val("");
					remplir(data);
				},
				error: function(data, textStatus, jqXHR) {
					if (data.status == 405) {
						Swal.fire({
							position: 'center',
							icon: 'warning',
							title: 'La salle est deja ajoute.',
							showConfirmButton: false,
							timer: 1500
						})
					}else if(data.status == 406){
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
				url: "Salles",
				type: 'POST',
				data: { id: id, code: code, capacite: capacite, type: type, op: 'update' },
				success: function(data, textStatus, jqXHR) {
					$("#idSalle").val(0 + "");
					Swal.fire({
						position: 'center',
						icon: 'success',
						title: 'Salle modifie',
						showConfirmButton: false,
						timer: 1500
					})
					$("#code").val("");
					$("#capacite").val("");
					remplir(data);
					operation = "ajout";
				},
				error: function(data, textStatus, jqXHR) {
					if(data.status == 406){
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
	})

	$("#content").on("click", ".delete", function() {
		var id = $(this).attr("val");
		Swal.fire({
			title: 'Vous etes sur?',
			text: "Tu ne pourras pas recuperer la salle apres la suppression!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Oui, supprimer!'
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					url: 'Salles',
					type: 'POST',
					data: { id: id, op: "delete" },
					success: function(data, textStatus, jqXHR) {
						Swal.fire({
							position: 'center',
							icon: 'success',
							title: 'Salle supprimee',
							showConfirmButton: false,
							timer: 1500
						})
						remplir(data);
					},
					error: function(data, textStatus, jqXHR) {
						if (data.status == 408) {
							Swal.fire({
								position: 'center',
								icon: 'warning',
								title: 'Impossible de supprimer la salle',
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
		$("#idSalle").val($(this).attr("val"));
		$("#code").val($(this).closest('tr').find('td').eq(1).text());
		$("#capacite").val($(this).closest('tr').find('td').eq(3).text());
		operation = "modification";
	})

	function remplir(data) {
		var ligne = "";
		for (var i = 0; i < data.length; i++) {
			ligne += "<tr><td>" + data[i].id + "</td><td>" + data[i].code + "</td><td>" + data[i].type + "</td><td>" + data[i].capacite + "</td><td><button type='button' class='delete btn btn-custon-rounded-four btn-danger' val='" + data[i].id + "'>Supprimer</button><button type='button' class='update btn btn-custon-rounded-four btn-primary' val='" + data[i].id + "' style='margin-left: 10px;margin-right: 10px'  data-toggle='modal' data-target='#PrimaryModalalert'>Modifier</button></td></tr>";
		}
		$("#content").html(ligne);
	}
})