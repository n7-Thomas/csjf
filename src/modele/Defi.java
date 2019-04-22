package modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Defi {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Groupe groupe;

	private int points;

	private TypeDefi type;
	
	private String description;
	
	private String nom;

	private String date;
	
	private String endDate;
	
	public Defi() {
		this.setDescription("");
		this.setNom("");
		this.setDate("");
		this.setEndDate("");
		this.setPoints(0);
	}
	
	/**
	 * Retourner la description du defi.
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Fixer la description du defi.
	 * @return
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	/**
	 * Retourner le nom du defi.
	 * @return
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Fixer le nom du defi.
	 * @return
	 */
	public void setNom(String n) {
		this.nom = n;
	}
	
	/**
	 * Retourner le nombre de points.
	 * @return
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Fixer le nombre de points.
	 * @param points
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Retourner le type du defi.
	 * @return
	 */
	public TypeDefi getType() {
		return type;
	}

	/**
	 * Fixer le type du défi.
	 * @param type
	 */
	public void setType(TypeDefi type) {
		this.type = type;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
