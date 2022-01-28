package entities;


public class SalleCreneau {
	private int id;
	private Salle salle;
	private Creneau creneau;

	public SalleCreneau(int id, Salle salle, Creneau creneau) {
		this.id = id;
		this.salle = salle;
		this.creneau = creneau;
	}

	public SalleCreneau(Salle salle, Creneau creneau) {
		this.salle = salle;
		this.creneau = creneau;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Creneau getCreneau() {
		return creneau;
	}

	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}
}
