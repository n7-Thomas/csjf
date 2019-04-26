package modele;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private Membre sender;

	@OneToOne
	private Membre receiver;

	private String contenu;

	private PrivateDate date;

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public PrivateDate getDate() {
		return date;
	}

	public void setDate(PrivateDate date) {
		this.date = date;
	}

	public Membre getReceiver() {
		return receiver;
	}

	public void setReceiver(Membre receiver) {
		this.receiver = receiver;
	}

	public Membre getSender() {
		return sender;
	}

	public void setSender(Membre sender) {
		this.sender = sender;
	}
	
	/**
	 * Retourner l'identifiant.
	 * @return
	 */
	public int getId() {
		return this.id;
	}
}
