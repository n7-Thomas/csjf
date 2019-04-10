package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)


	private int id;

	private String message;

	@ManyToOne
	private Groupe groupe;

	public Notification(){}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Retourner l'identifiant.
	 * @return
	 */
	public int getId() {
		return this.id;
	}
}
