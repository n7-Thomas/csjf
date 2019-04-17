package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class FilActu {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	
	@OneToOne
	private Groupe groupe;

	/*
	@OneToMany(mappedBy="groupe", fetch=FetchType.EAGER)
	private Collection<Publication> publications;


	@OneToMany(mappedBy="groupe", fetch=FetchType.EAGER)
	private Collection<Notification> notifications;

*/
	public FilActu(){}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

}
