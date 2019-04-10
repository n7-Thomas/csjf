package modele;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Groupe {

	private String nom;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Membre admin;

	@ManyToMany(mappedBy="groupes_appartenus", fetch=FetchType.EAGER)
	private Collection<Membre> membres;

	@OneToMany(mappedBy="groupe", fetch=FetchType.EAGER)
	private Collection<Defi> defis;

	@OneToMany(mappedBy="groupe", fetch=FetchType.EAGER)
	private Collection<Publication> publis;

	@OneToMany(mappedBy="groupe", fetch=FetchType.EAGER)
	private Collection<Defi_A_Valider> defis_a_valider;
	
	@OneToMany(mappedBy="groupe", fetch=FetchType.EAGER)
	private Collection<Defi_Valide> defis_valides;
	
	public Groupe() {}
	
	public Collection<Defi_A_Valider> getDefis_a_valider() {
		return defis_a_valider;
	}

	public void setDefis_a_valider(Collection<Defi_A_Valider> defis_a_valider) {
		this.defis_a_valider = defis_a_valider;
	}

	public Collection<Defi_Valide> getDefis_valides() {
		return defis_valides;
	}

	public void setDefis_valides(Collection<Defi_Valide> defis_valides) {
		this.defis_valides = defis_valides;
	}

	/**
	 * Retourner le nom du groupe.
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Donner un nom au groupe.
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Donner un administrateur au groupe
	 * @param membre
	 */
	public void setAdministrateur(Membre membre) {
		this.admin = membre;		
	}
	
	/**
	 * Retourner l'admin du groupe.
	 * @return
	 */
	public Membre getAdministrateur() {
		return this.admin;
	}

	/**
	 * Retourner la liste des membres.
	 * @return
	 */
	public Collection<Membre> getMembres(){
		return this.membres;
	}
	
	/**
	 * Donner une liste de membre.
	 * @param membre
	 */
	public void setMembres(Collection<Membre> membres) {
		this.membres = membres;		
	}
	
	/**
	 * Retourner la liste des membres.
	 * @return
	 */
	public Collection<Defi> getDefis(){
		return this.defis;
	}
	
	/**
	 * Donner une liste de membre.
	 * @param membre
	 */
	public void setDefis(Collection<Defi> defis) {
		this.defis = defis;		
	}
		
	/**
	 * Retourner l'identifiant.
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	public void addMembre(Membre mb) {
		this.membres.add(mb);		
	}
}
