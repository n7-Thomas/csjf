package modele;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Membre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nom;

	private String prenom;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "membre_groupe")
	private Collection<Groupe> groupes_appartenus;

	@OneToMany(mappedBy = "admin", fetch = FetchType.EAGER)
	private Collection<Groupe> groupes_administres;

	private int coeff_sportif;

	@OneToMany(mappedBy = "membre", fetch = FetchType.EAGER)
	private Collection<Defi_Valide> defis_valides;

	private String email;

	private String motdepasse;

	public Membre() {
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCoeff_sportif() {
		return coeff_sportif;
	}

	public void setCoeff_sportif(int coeff_sportif) {
		this.coeff_sportif = coeff_sportif;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Collection<Groupe> getGroupesAppartenus() {
		return groupes_appartenus;
	}

	public void setGroupesAppartenus(Collection<Groupe> id_groupes_app) {
		this.groupes_appartenus = id_groupes_app;
	}

	public Collection<Groupe> getGroupesAdministres() {
		return groupes_administres;
	}

	public void setGroupesAdministres(Collection<Groupe> id_groupes_admin) {
		this.groupes_administres = id_groupes_admin;
	}

	public Collection<Defi_Valide> getDefis_valides() {
		return defis_valides;
	}

	public void setDefis_valides(Collection<Defi_Valide> defis_valides) {
		this.defis_valides = defis_valides;
	}

	/**
	 * Retourner l'identifiant.
	 * @return
	 */
	public int getId() {
		return this.id;
	}

}
