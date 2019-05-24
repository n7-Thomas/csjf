package modele;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Badge {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "badge_membre")
	private Collection<Membre> membres;

	private String description;

	private int niveau;
	private String nom;
	private String codepoint;

	public Badge() {
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param new_texte the new texte
	 */
	public void setDescription(String new_description) {
		this.description = new_description;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @param new_nom the new nom
	 */
	public void setNom(String new_nom) {
		this.nom = new_nom;
	}

	/**
	 * @return the membre
	 */
	public Collection<Membre> getMembre() {
		System.out.println("THIS MEMBRE " + this.membres);
		return this.membres;
	}


	public void addMembre(Membre membre) {
		membres.add(membre);
	}
	

	public int getId() {
		return this.id;
	}

	public void setNiveau(int valeur) {
		this.niveau = valeur;
	}

	public int getNiveau() {
		return this.niveau;
	}

	public String getCodepoint() {
		return codepoint;
	}

	public void setCodepoint(String codepoint) {
		this.codepoint = codepoint;
	}

}
