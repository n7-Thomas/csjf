package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Reaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Membre membre;

	@ManyToOne
	private Publication publication;

	public enum Type{cool, pasCool, surpris};

	private Type type;

	public Reaction(){
	}

	public Type getType() {
		return type;
	}


	public int getId() {
		return id;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public boolean isCool(){
		return type == Type.cool;
	}

	public boolean isPasCool(){
		return type == Type.pasCool;
	}

	public boolean isSurpris(){
		return type == Type.surpris;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
