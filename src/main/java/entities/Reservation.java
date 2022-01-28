package entities;

import java.util.Date;

public class Reservation {
	private int id;
	private SalleCreneau salleCreneau;
	private Client client;
	private Date dateReservation;
	private String etatReservation;
	
	public Reservation(int id, SalleCreneau salleCreneau, Client client, Date dateReservation, String etatReservation) {
		this.id = id;
		this.salleCreneau = salleCreneau;
		this.client = client;
		this.dateReservation = dateReservation;
		this.etatReservation = etatReservation;
	}
	
	public Reservation(SalleCreneau salleCreneau, Client client, Date dateReservation, String etatReservation) {
		this.salleCreneau = salleCreneau;
		this.client = client;
		this.dateReservation = dateReservation;
		this.etatReservation = etatReservation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SalleCreneau getSalleCreneau() {
		return salleCreneau;
	}

	public void setSalleCreneau(SalleCreneau salleCreneau) {
		this.salleCreneau = salleCreneau;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getDateReservation() {
		return dateReservation;
	}

	public void setDateReservation(Date dateReservation) {
		this.dateReservation = dateReservation;
	}

	public String getEtatReservation() {
		return etatReservation;
	}

	public void setEtatReservation(String etatReservation) {
		this.etatReservation = etatReservation;
	}

	
}
