package modele;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class FilActu {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;


	//@OneToOne
	//private Groupe groupe;

	// P-e faire une classe abstraite pour Publication et Notification


	//@OneToMany(mappedBy="filActu", fetch = FetchType.EAGER)
	//private Collection< Notification> notifications;


	@OneToMany(mappedBy="filActu", fetch = FetchType.LAZY)
	private Collection<Publication> publications;


	public FilActu(){
	}

/*
	public void addNotification(Notification notif){
		notifications.add(notif);

	}

	public  Collection<Notification> getNotifications(){
		return this.notifications;
	}
*/

	public void addPublication(Publication publi){
		publications.add(publi);
	}

	public Collection<Publication> getPublications(){
		return this.publications;
	}
/*
	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	*/

}
