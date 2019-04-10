package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Defi_A_Valider {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private Defi defi;

	@OneToOne
	private Membre membre;
	
	@ManyToOne
	private Groupe groupe;

	public Defi_A_Valider(){}
	
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
	
	public int getId() {
		return this.id;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	
}
