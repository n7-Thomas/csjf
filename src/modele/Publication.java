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

	@OneToOne
	private Membre membre;

	@ManyToOne
	private Groupe groupe;

	private String contenu;

	@OneToMany(mappedBy="publication", fetch=FetchType.EAGER)
	private Collection<Reaction> reactions;

	public Publication(){}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	/**
	 * @return the publi
	 */
	public String getContenu() {
		return contenu;
	}

	public Collection<Reaction> getReactions() {
		return reactions;
	}
	

	public void setReactions(Collection<Reaction> reactions) {
		this.reactions = reactions;
	}

	public Reaction getReaction(Membre membre){

		for(Reaction r: this.reactions) {
			if(r.getMembre().getId()== membre.getId()) {
				return r;
			}
		}
		return new Reaction();

	}

	/**
	 * @param publi the publi to set
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
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

}
