package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Publication {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;


	@OneToOne
	private Membre membre;

	private String contenu;

	//private Date date;

//	@OneToMany(mappedBy="publi", fetch= FetchType.EAGER)
//	private Collection<Commentaire> commentaires;

	//@ManyToOne
	//private Groupe groupe;

	@ManyToOne
	private FilActu filActu;


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
	 * Retourner l'identifiant.
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	public void setFilActu(FilActu filActu){
		this.filActu = filActu;
	}

	public FilActu getFilActu(){
		return this.filActu;
	}
}
