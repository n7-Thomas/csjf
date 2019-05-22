package modele;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Badge {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToMany
	private Collection<Membre> membres;
	
	private String description;
	
	private String nom;
	
	private int niveau;
	
	public Badge() {}
	
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
		return this.membres;
	}

	/**
	 * @param membre the membre to set
	 */
	public void setMembre(Collection<Membre> membre) {
		this.membres = membre;
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
}
