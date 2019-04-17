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
import modele.Groupe;
import modele.Membre;
import modele.Publication;
import modele.TypeDefi;

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


		System.out.println("select * from Membre WHERE email='" + email + "'");
		Membre member;

		try {
			TypedQuery<Membre> req = em.createQuery("select m from Membre m WHERE email = '" + email + "'", Membre.class);
			Membre mb = req.getSingleResult();
			if (mb != null && mb.getMotdepasse().equals(motDePasse)) {
				member = mb;;
			} else {
				member = null;
			}
		} catch (Exception e) {
			System.out.println("Utilisateur n'existe pas");
			member = null;
		}
		return member;
	}

	public Membre inscriptionNewMember(String nom, String prenom, String email, String motdepasse) {
		Membre mb = new Membre();
		mb.setCoeff_sportif(1);
		mb.setEmail(email);
		mb.setMotdepasse(motdepasse);
		mb.setNom(nom);
		mb.setPrenom(prenom);
		em.persist(mb);
		return mb;
	}

	/**
	 * Ajouter un défi dans le grp avec les caractéristiques.
	 * @param grp
	 * @param usr
	 * @param description
	 * @param nom
	 * @return
	 */
	public Defi ajouterDefi(String nom, String description, Membre usr, Groupe grp, int points) {
		Defi defi = new Defi();
		defi.setDescription(description);
		//defi.setGroupe(grp);
		defi.setNom(nom);
		defi.setPoints(points);
		defi.setType(TypeDefi.Sport);

		em.persist(defi);

		Groupe g = em.find(Groupe.class, grp.getId());
		g.addDefi(defi);

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
		}
		Membre mb = req.getSingleResult();
		mb.getGroupesAppartenus().add(grp);

		TypedQuery<Demande_A_Rejoindre> req2 = em.createQuery("select d from Demande_A_Rejoindre d WHERE GROUPE_ID=" + grp.getId() + " AND MEMBRE_ID=" + mb.getId(), Demande_A_Rejoindre.class);
		if(req2.getResultList() != null) {
			Demande_A_Rejoindre dar = req2.getSingleResult();
			em.remove(em.find(Demande_A_Rejoindre.class, dar.getId()));
		}

		return true;
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
		if (req.getResultList().size() == 0) { // alors le défi est déjà en cours de validation ou rien n'a été sélectionné
			Defi_A_Valider defi_a_valider = new Defi_A_Valider();
			defi_a_valider.setDefi(defi);
			defi_a_valider.setMembre(membre);
		} else {
			throw new Exception("Ce défi est déjà en cours de validation !");
		}
	}

	public void demandeValidationCSJF() {

	}
	/*

	public FilActu getFilActu(Groupe grp) {

			TypedQuery<Groupe> req = em.createQuery("select * from Groupe WHERE grp='" + grp + "'", Groupe.class);
			grp = req.getSingleResult();
			FilActu f = grp.getFilActuB();

			if (f != null) {
				return f;

			} else {
				return null;
			}
	}
	*/

	public Publication creerPublication( Membre mbr, String contenu) {
		Publication publi = new Publication();
		//publi.setGroupe(grp);
		publi.setMembre(mbr);
		publi.setContenu(contenu);

		em.persist(publi);

		return publi;
	}

	public void commenter() {

	}

	/**
	 * FROM PAGE ACCUEIL
	 */
	public void demanderRejoindreGroupe(Membre mb, Groupe gr) {
		Demande_A_Rejoindre dar = new Demande_A_Rejoindre();
		dar.setGroupe(gr);
		dar.setMembre(mb);
		em.persist(dar);
	}

	/**
	 * Créer un groupe.
	 *
	 * @param nom
	 * @throws ExceptionUserNonDefini
	 */
	public Groupe creerGroupe(String nom, Membre usr) throws ExceptionUserNonDefini {
		Groupe g = new Groupe();
		em.persist(g);
		g.setNom(nom);
		g.setAdministrateur(usr);

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
	 * Récupérer tous les groupes dans la base de données
	 */
	public Collection<Groupe> getGroupes() {
		TypedQuery<Groupe> req = (TypedQuery<Groupe>) em.createQuery("select g from Groupe g");
		Collection<Groupe> groupes = req.getResultList();
		return groupes;

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
		TypedQuery<Defi_A_Valider> req = em.createQuery("select d from Defi_A_Valider d WHERE groupe=" + grp.getId(), Defi_A_Valider.class);
		return req.getResultList();
	}

	public Collection<Demande_A_Rejoindre> getDemandeARejoindre(Groupe grp) {
		TypedQuery<Demande_A_Rejoindre> req = em.createQuery("select d from Demande_A_Rejoindre d WHERE groupe=" + grp.getId(), Demande_A_Rejoindre.class);
		return req.getResultList();
	}



	public Collection<Membre> getMembres(Groupe grp) {
		Groupe g = em.find(Groupe.class, grp.getId());
		return g.getMembres();
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
	public void initialiserTest3() {
		Membre mb = em.find(Membre.class, 2);
		Groupe gp = em.find(Groupe.class, 1);
		Defi defi = em.find(Defi.class, 1);

		Defi_A_Valider dav = new Defi_A_Valider();
		dav.setDefi(defi);
		dav.setMembre(mb);
		dav.setGroupe(gp);

		em.persist(dav);
	}
	public Groupe initialiserTest4() {
		Membre mb = new Membre();
		mb.setCoeff_sportif(1);
		mb.setEmail("thomasdarget@hotmail.fr");
		mb.setMotdepasse("abc");
		mb.setNom("Darget");
		mb.setPrenom("Thomas");
		em.persist(mb);

		Membre mb2 = new Membre();
		mb2.setCoeff_sportif(1);
		mb2.setEmail("manugoncalves@gmail.com");
		mb2.setMotdepasse("abc");
		mb2.setNom("Goncalves");
		mb2.setPrenom("Manu");
		em.persist(mb2);

		Groupe g = new Groupe();
		g.setAdministrateur(mb);
		g.setNom("Groupe1");

		em.persist(g);


		System.out.println("Groupe : " + g);
		System.out.println("Membres : " + g.getMembres());

		g.getMembres().add(mb2);

		System.out.println("Membre.getGroupes " + mb2.getGroupesAppartenus());
		System.out.println("Admin.getGroupesAdmin " + mb.getGroupesAdministres());
		System.out.println("Groupe by em : " + em.find(Groupe.class, g.getId()));



		//mb2.getGroupesAppartenus().add(g);
		//g.getMembres().add(mb2);


		Defi d = new Defi();
		d.setDescription("Description");
		d.setNom("Defi test");
		d.setPoints(10);
		d.setType(TypeDefi.Sport);
		d.setGroupe(g);
		em.persist(d);

		Defi_A_Valider dav = new Defi_A_Valider();
		dav.setDefi(d);
		dav.setMembre(mb2);
		dav.setGroupe(g);
		em.persist(dav);

		Membre mb3 = new Membre();
		mb3.setCoeff_sportif(1);
		mb3.setEmail("cha@sfr.fr");
		mb3.setMotdepasse("abc");
		mb3.setNom("De Foucaud");
		mb3.setPrenom("Charlotte");
		em.persist(mb3);


		Demande_A_Rejoindre dar = new Demande_A_Rejoindre();
		dar.setGroupe(g);
		dar.setMembre(mb3);
		em.persist(dar);

		return g;
	}

}
