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

	private String contenu;

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
	 * Retourner l'identifiant.
	 * @return
	 */
	public int getId() {
		return this.id;
	}
}
