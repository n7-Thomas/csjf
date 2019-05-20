package modele;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Badge {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToMany
	private Collection<Membre> membres;

	@ManyToMany
	private Collection<Groupe> groupes;
	
	private String description;
	
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
	 * @return the groupe
	 */
	public Collection<Groupe> getGroupe() {
		return this.groupes;
	}

	/**
	 * @param groupe the groupe to set
	 */
	public void setGroupe(Collection<Groupe> groupe) {
		this.groupes = groupe;
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
