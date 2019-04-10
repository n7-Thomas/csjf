package modele;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

//Faire une entity?
public class FilActu {
	
	//Faudrat que tu mettes ça @OneToOne(mappedBy="filactu", fetch=FetchType.EAGER)
	private Groupe groupe;

	// P-e faire une classe abstraite pour Publication et Notification

	@OneToMany(mappedBy="groupe", fetch=FetchType.EAGER)
	private List<Publication> publications;


	@OneToMany(mappedBy="groupe", fetch=FetchType.EAGER)
	private List<Notification> notifications;


	public FilActu(Groupe g){
		this.setGroupe(g);
	}

	public void addNotif(Notification notif){
		notifications.add(notif);
	}

	public List<Notification> getNotification(){
		return this.notifications;
	}


	public void addPublication(Publication publi){
		publications.add(publi);
	}

	public List<Publication> getPublication(){
		return this.publications;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

}
