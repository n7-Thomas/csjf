package modele;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Publication {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Groupe groupe;

	@OneToOne
	private Membre membre;

	private String contenu;

	//private Date date;

	@OneToMany(mappedBy="publi", fetch= FetchType.EAGER)
	private Collection<Commentaire> commentaires;

	/**
	 * @return the publi
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * @param publi the publi to set
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	/**
	 * @return the date
	 
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 
	public void setDate(Date date) {
		this.date = date;
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
	 * Retourner l'identifiant.
	 * @return
	 */
	public int getId() {
		return this.id;
	}
}
