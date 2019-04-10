package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Defi_Valide {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private Defi defi;

	@OneToOne
	private Membre membre;

	@ManyToOne
	private Groupe groupe;
	
	public Defi_Valide(){}
	
	/**
	 * @return the defi
	 */
	public Defi getDefi() {
		return defi;
	}

	/**
	 * @param defi the defi to set
	 */
	public void setDefi(Defi defi) {
		this.defi = defi;
	}

	/**
	 * @return the membre
	 */
	public Membre getMembre() {
		return membre;
	}

	/**
	 * @param membre the membre to set
	 */
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	
	/**
	 * Retourner l'identifiant.
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
}
