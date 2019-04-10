package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Commentaire {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Publication publi;

	@OneToOne
	private Membre membre;
	
	private String date;
	
	public Commentaire(){}
	
	public Publication getPubli(){
		return this.publi;
	}
	
	public void setPubli(Publication publi){
		this.publi = publi;
	}
	
	public Membre getMembre(){
		return this.membre;
	}
	
	public int getId() {
		return this.id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
