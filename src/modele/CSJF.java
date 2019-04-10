package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CSJF {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private Membre membre;

	@OneToOne
	private Groupe groupe;

	private int nb_min;

	private int difficulte;

	private String sport;

	public CSJF(){}
	
	/**
	 * @return the nb_min
	 */
	public int getNb_min() {
		return nb_min;
	}

	/**
	 * @param nb_min the nb_min to set
	 */
	public void setNb_min(int nb_min) {
		this.nb_min = nb_min;
	}

	/**
	 * @return the difficulte
	 */
	public int getDifficulte() {
		return difficulte;
	}

	/**
	 * @param difficulte the difficulte to set
	 */
	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
	}

	/**
	 * @return the sport
	 */
	public String getSport() {
		return sport;
	}

	/**
	 * @param sport the sport to set
	 */
	public void setSport(String sport) {
		this.sport = sport;
	}

	/**
	 * @return the groupe
	 */
	public Groupe getGroupe() {
		return groupe;
	}

	/**
	 * @param groupe the groupe to set
	 */
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
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
	
	public int getId() {
		return this.id;
	}
}
