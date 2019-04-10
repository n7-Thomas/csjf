package controleur;

import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import exceptions.ExceptionUserNonDefini;
import modele.Defi;
import modele.Defi_A_Valider;
import modele.Demande_A_Rejoindre;
import modele.FilActu;
import modele.Groupe;
import modele.Membre;

@Singleton
public class Facade {

	@PersistenceContext
	EntityManager em;

	public Facade() {
	}

	/**
	 * FROM PAGE CONNEXION
	 */
	public Membre checkConnexion(String email, String motDePasse) {
		System.out.println("select m from Membre m WHERE email='" + email + "'");
		TypedQuery<Membre> req = em.createQuery("select m from Membre m WHERE email='" + email + "'", Membre.class);
		Membre mb = req.getSingleResult();
		System.out.println("AH : " + mb);
		if (mb != null && mb.getMotdepasse() == motDePasse) {
			return mb;
		} else {
			return null;
		}
	}

	/**
	 * FROM PAGE ADMIN
	 * @param grp 
	 * @param usr 
	 * @param description 
	 * @param nom 
	 * @return 
	 */
	public Defi ajouterDefi(String nom, String description, Membre usr, Groupe grp, int points) {
		Defi defi = new Defi();
		defi.setDescription(description);
		defi.setGroupe(grp);
		defi.setNom(nom);
		defi.setPoints(points);
		
		em.persist(defi);
		
		return defi;
	}

	public void validerDefi() {

	}

	public void modifierDefi() {

	}

	public void supprimerGroupe() {

	}

	public boolean ajouterMembre(String email, Groupe grp) {
		TypedQuery<Membre> req = em.createQuery("select m from Membre m WHERE email='" + email + "'", Membre.class);
		if (req.getResultList() == null) {
			System.out.println("Aucun résultat pour cette requête.");
			return false;
		}else {
			Membre mb = (Membre) req.getSingleResult();
			mb.addGroupeAppartenus(grp);
			return true;			
		}
		
	}

	public void supprimerMembre() {

	}

	public void editerCoefMembre() {

	}

	/**
	 * FROM PAGE GROUPE, on demande à valider un défi
	 */
	public void demandeValidationDefi(Defi defi, Membre membre) throws Exception {
		TypedQuery<Defi_A_Valider> req = em.createQuery("select d from Defi_A_Valider d WHERE defi='" + defi + "' && membre='" + membre + "'", Defi_A_Valider.class);
		if (req.getResultList().size() == 0) { // alors le défi à valider n'existe pas déjà
			Defi_A_Valider defi_a_valider = new Defi_A_Valider();
			defi_a_valider.setDefi(defi);
			defi_a_valider.setMembre(membre);
		} else {
			throw new Exception("Ce défi est déjà en cours de validation !");
		}
	}

	public void demandeValidationCSJF() {

	}

	public FilActu getFilActu(int id_groupe) {
		// Faire la requete vers l'em
		
		// Renvoie
		
		
		return null;
	}

	public void publier() {

	}

	public void commenter() {

	}

	/**
	 * FROM PAGE ACCUEIL
	 */
	public void rejoindreGroupe() {

	}

	/**
	 * Créer un groupe.
	 * 
	 * @param nom
	 * @throws ExceptionUserNonDefini
	 */
	public Groupe creerGroupe(String nom, Membre usr) throws ExceptionUserNonDefini {
		Groupe g = new Groupe();
		g.setNom(nom);
		g.setAdministrateur(usr);
		em.persist(g);

		return g;
	}

	public Groupe creerGroupe(String nom, int usr) throws ExceptionUserNonDefini {
		return creerGroupe(nom, (em.find(Membre.class, usr)));
	}

	/**
	 * Récupérer le groupe dans la base de données avec son nom.
	 * 
	 * @param nom
	 * @return
	 */
	public Groupe getGroupeFromNom(String nom) {
		TypedQuery<Groupe> req = em.createQuery("select g from Groupe g WHERE nom='" + nom + "'", Groupe.class);
		Groupe grp = req.getSingleResult();
		return grp;
	}

	/**
	 * FROM INSCRIPTION
	 */
	public void creerProfil() {

	}

	/**
	 * FROM PROFIL
	 */
	public void modifierProfil() {

	}

	public Collection<Defi_A_Valider> getDefisAValider(Groupe grp) {
		TypedQuery<Defi_A_Valider> req = em.createQuery("select d from Defi_A_Valider d WHERE groupe=??", Defi_A_Valider.class);
		return req.getResultList();
	}

	public Collection<Demande_A_Rejoindre> getDemandeARejoindre(Groupe grp) {
		TypedQuery<Demande_A_Rejoindre> req = em.createQuery("select d from Demande_A_Rejoindre d WHERE groupe=" + grp.getId(), Demande_A_Rejoindre.class);
		return req.getResultList();
	}

	public Membre initialiserTest() {
		Membre mb = new Membre();
		mb.setCoeff_sportif(1);
		mb.setEmail("thomasdarget@hotmail.fr");
		mb.setMotdepasse("abc");
		mb.setNom("Darget");
		mb.setPrenom("Thomas");
		em.persist(mb);
		return mb;
	}

	public Membre initialiserTest2() {
		Membre mb = new Membre();
		mb.setCoeff_sportif(1);
		mb.setEmail("manugoncalves@gmail.com");
		mb.setMotdepasse("abc");
		mb.setNom("Goncalves");
		mb.setPrenom("Manu");
		em.persist(mb);	
		return mb;
	}
	
	// A UTILISER QU'APRES 1 et 2
	public void initialiserTest3() {
		Membre mb = em.find(Membre.class, 2);
		Groupe gp = em.find(Groupe.class, 1);
		Defi defi = em.find(Defi.class, 1);
		
		Defi_A_Valider dav = new Defi_A_Valider();
		dav.setDefi(defi);
		dav.setMembre(mb);
		
	}
	

}
