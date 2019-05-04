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
	
	private String texte;
	
	private int points;
	
	private Etats etat;
	
	private String dateValidation;

	public CSJF() {
		this.etat = Etats.EnCoursDeValidation;
	}
	
	/**
	 * @return the texte
	 */
	public String getTexte() {
		return this.texte;
	}
	
	/**
	 * @param new_texte the new texte
	 */
	public void setTexte(String new_texte) {
		this.texte = new_texte;
	}
	
	/**
	 * @return the etat
	 */
	public Etats getEtat() {
		return this.etat;
	}
	
	/**
	 * @param new_etat the new etat
	 */
	public void setEtat(Etats new_etat) {
		this.etat = new_etat;
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

	public void setPoints(int valeur) {
		this.points = valeur;
	}
	
	public int getPoints() {
		return this.points;
	}

	public String getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(String dateValidation) {
		this.dateValidation = dateValidation;
	}
}
