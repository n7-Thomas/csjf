package controleur;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import exceptions.ExceptionUserNonDefini;
import modele.Defi;
import modele.Defi_A_Valider;
import modele.Defi_Valide;
import modele.Demande_A_Rejoindre;
import modele.Groupe;
import modele.Membre;
import modele.Message;
import modele.PrivateDate;
import modele.Publication;
import modele.TypeDefi;
import tests.Tests_Defis;
import tests.Tests_Groupes;
import tests.Tests_Membres;

@Singleton
public class Facade {

	@PersistenceContext
	EntityManager em;

	public Facade() {
	}

	/**
	 * FROM PAGE CONNEXION
	 */
	public boolean checkPassword(String motDePasse, Membre m) {
		byte[] salt = m.getSalt();
		String motdepasseCryp = SHACrypt.get_SHA_256_SecurePassword(motDePasse, salt);
		boolean ok;
		if (m.getMotdepasse().equals(motdepasseCryp)) {
			ok = true;
		} else {
			ok = false;
		}
		return ok;
	}

	public Membre checkConnexion(String email, String motDePasse) {

		System.out.println("select * from Membre WHERE email='" + email + "'");
		Membre member;

		try {
			TypedQuery<Membre> req = em.createQuery("select m from Membre m WHERE email = '" + email + "'",
					Membre.class);
			Membre mb = req.getSingleResult();
			if (mb != null) {
				member = mb;
			} else {
				member = null;
			}
		} catch (Exception e) {
			System.out.println("Utilisateur n'existe pas");
			member = null;
		}
		return member;
	}

	public Membre inscriptionNewMember(String nom, String prenom, String email, String motdepasse, byte[] salt) {

		Membre member = null;
		try {
			TypedQuery<Membre> req = em.createQuery("select m from Membre m WHERE email = '" + email + "'",
					Membre.class);
			Membre mr = req.getSingleResult();
			if (mr.equals(null))
				member = null;
		} catch (Exception e) {
			System.out.println("Utilisateur n'existe pas");
			Membre mb = new Membre();
			mb.setCoeff_sportif(1);
			mb.setEmail(email);
			mb.setMotdepasse(motdepasse);
			mb.setNom(nom);
			mb.setPrenom(prenom);
			mb.setSalt(salt);
			em.persist(mb);
			member = mb;
		}
		return member;
	}

	/**
	 * Ajouter un défi dans le grp avec les caractéristiques.
	 *
	 * @param grp
	 * @param usr
	 * @param description
	 * @param nom
	 * @return
	 */
	public Defi ajouterDefi(String nom, String description, Groupe grp, int points, String dateDebut, String dateFin) {
		Defi defi = new Defi();
		defi.setDescription(description);
		defi.setGroupe(grp);
		defi.setNom(nom);

		defi.setPoints(points);

		defi.setType(TypeDefi.Sport);

		if (dateDebut.equals("")) {
			PrivateDate debutNow = PrivateDate.getNow();
			dateDebut = debutNow.toString();
		}
		if (dateFin.equals("")) {
			PrivateDate end1week = PrivateDate.getNow();
			end1week.setJour(end1week.getJour() + 7);
			dateFin = end1week.toString();
		}
		defi.setDate(dateDebut);
		defi.setEndDate(dateFin);
		em.persist(defi);

		return defi;
	}

	public void validerDefi(int id_dav) {
		Defi_A_Valider dav = em.find(Defi_A_Valider.class, id_dav);

		Defi_Valide dv = new Defi_Valide();
		em.persist(dv);
		dv.setDefi(dav.getDefi());
		dv.setGroupe(dav.getGroupe());
		dv.setMembre(dav.getMembre());

		em.remove(dav);
	}

	public void validerDemande(int id_dar) {
		Demande_A_Rejoindre dar = em.find(Demande_A_Rejoindre.class, id_dar);
		Membre mb = em.find(Membre.class, dar.getMembre().getId());
		Groupe gp = em.find(Groupe.class, dar.getGroupe().getId());
		mb.getGroupesAppartenus().add(gp);
		em.remove(dar);
	}

	public void supprimerGroupe(Groupe gp) {
		em.remove(em.find(Groupe.class, gp.getId()));
	}

	public Groupe changerNomGroupe(Groupe gp, String nom) {
		Groupe groupe = em.find(Groupe.class, gp.getId());
		groupe.setNom(nom);
		return groupe;
	}

	public boolean ajouterMembre(String email, Groupe grp) {
		TypedQuery<Membre> req = em.createQuery("select m from Membre m WHERE email='" + email + "'", Membre.class);
		if (req.getResultList() == null || req.getResultList().size() != 1) {
			System.out.println("Aucun résultat pour cette requête.");
			return false;
		}
		Membre mb = req.getSingleResult();
		if (mb == null)
			return false;

		if (mb.getGroupesAppartenus() != null && !mb.getGroupesAppartenus().contains(grp))
			mb.getGroupesAppartenus().add(grp);

		TypedQuery<Demande_A_Rejoindre> req2 = em.createQuery(
				"select d from Demande_A_Rejoindre d WHERE GROUPE_ID=" + grp.getId() + " AND MEMBRE_ID=" + mb.getId(),
				Demande_A_Rejoindre.class);
		if (req2.getResultList() != null && req2.getResultList().size() != 0) {
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
	public void ajouterDefiAValider(int idGroupe, int idDefiAValider, Membre membre) throws Exception {
		Defi defi1 = em.find(Defi.class, idDefiAValider);
		Groupe groupe = em.find(Groupe.class, idGroupe);
		Membre m = em.find(Membre.class, membre.getId());
		TypedQuery<Defi_A_Valider> req = em
				.createQuery("select d from Defi_A_Valider d WHERE DEFI_ID=" + idDefiAValider, Defi_A_Valider.class);
		if (req.getResultList().size() != 0) { // Alors le défi a déjà été demandé à être validé
			throw new Exception("Ce défi a déjà été envoyer pour être validé, vous ne pouvez pas le réenvoyer !");
		}
		if (defi1 != null && m != null && groupe != null) {
			Defi_A_Valider defi_a_valider = new Defi_A_Valider();
			defi_a_valider.setDefi(defi1);
			defi_a_valider.setMembre(m);
			defi_a_valider.setGroupe(groupe);
			em.persist(defi_a_valider);
		} else {
			throw new Exception("probbb facade ajouter defi a valider");
		}
	}

	/**
	 * FROM PAGE GROUPE, on demande les défis d'un groupe
	 */
	public Collection<Defi> getDefis(Groupe grp) {
		Groupe g = em.find(Groupe.class, grp.getId());
		return g.getDefis();
	}

	public void demandeValidationCSJF() {

	}

	public Publication creerPublication(Membre mbr, String contenu) {
		Publication publi = new Publication();
		// publi.setGroupe(grp);
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
	public boolean demanderRejoindreGroupe(Membre mb, Groupe gr) {
		if (mb.getGroupesAppartenus() == null || !mb.getGroupesAppartenus().contains(gr)) {
			Demande_A_Rejoindre dar = new Demande_A_Rejoindre();
			dar.setGroupe(gr);
			dar.setMembre(mb);
			em.persist(dar);
			return true;
		} else {
			return false;
		}
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
		g.setAdmin(usr);

		return g;
	}

	public Groupe creerGroupe(String nom, int usr) throws ExceptionUserNonDefini {
		return creerGroupe(nom, (em.find(Membre.class, usr)));
	}

	public boolean appartientGroupe(int id_mb, int id_grp) {
		Membre mb = em.find(Membre.class, id_mb);
		Groupe gp = em.find(Groupe.class, id_grp);

		return mb.getGroupesAppartenus().contains(gp);
	}

	public boolean administreGroupe(int id_mb, int id_grp) {
		Membre mb = em.find(Membre.class, id_mb);
		Groupe gp = em.find(Groupe.class, id_grp);

		return mb.getGroupesAdministres().contains(gp);
	}

	/**
	 * Récupérer le groupe dans la base de données avec son nom.
	 *
	 * @param nom
	 * @return
	 */
	public Groupe getGroupeFromNom(String nom) {
		Groupe grp = null;
		TypedQuery<Groupe> req = em.createQuery("select g from Groupe g WHERE nom='" + nom + "'", Groupe.class);
		if (req != null && req.getResultList().size() != 0) {
			grp = req.getSingleResult();
		}

		return grp;
	}

	/**
	 * Récupérer tous les groupes dans la base de données
	 */
	public Collection<Groupe> getGroupes() {
		TypedQuery<Groupe> req = em.createQuery("select g from Groupe g", Groupe.class);
		Collection<Groupe> groupes = req.getResultList();
		return groupes;
	}

	public Collection<Groupe> getGroupesAppartenus(Membre m) {
		Membre mb = em.find(Membre.class, m.getId());
		return mb.getGroupesAppartenus();
	}

	public Collection<Groupe> getGroupesAdministres(Membre m) {
		Membre mb = em.find(Membre.class, m.getId());
		return mb.getGroupesAdministres();
	}

	/**
	 * FROM PROFIL
	 */
	public void modifierProfil(Membre mb, String nom, String prenom, String email, String motdepasse) {
		mb.setNom(nom);
		mb.setPrenom(prenom);
		mb.setEmail(email);
		mb.setMotdepasse(motdepasse);
		em.merge(mb);
	}

	public Collection<Defi_A_Valider> getDefisAValider(Groupe grp) {
		TypedQuery<Defi_A_Valider> req = em.createQuery("select d from Defi_A_Valider d WHERE groupe=" + grp.getId(),
				Defi_A_Valider.class);
		return req.getResultList();
	}

	public Collection<Defi> getDefisEnCours(Groupe grp) {
		TypedQuery<Defi> req = em.createQuery("select d from Defi d WHERE groupe=" + grp.getId(), Defi.class);
		return req.getResultList();
	}

	public Collection<Demande_A_Rejoindre> getDemandeARejoindre(Groupe grp) {
		TypedQuery<Demande_A_Rejoindre> req = em.createQuery(
				"select d from Demande_A_Rejoindre d WHERE groupe=" + grp.getId(), Demande_A_Rejoindre.class);
		return req.getResultList();
	}

	public Collection<Membre> getMembres(Groupe grp) {
		Groupe g = em.find(Groupe.class, grp.getId());
		return g.getMembres();
	}

	public Groupe getGroupeFromId(int id) {
		return em.find(Groupe.class, id);
	}

	public Membre getMembreFromId(int id) {
		return em.find(Membre.class, id);
	}

	public void editerDefi(int id_defi, String nom, String description, int points, String dateDebut, String dateFin) {
		Defi defi = em.find(Defi.class, id_defi);

		if (!(nom.equals("")))
			defi.setNom(nom);

		if (!(description.equals("")))
			defi.setDescription(description);

		if (!(points == -1))
			defi.setPoints(points);

		if (!(dateDebut.equals("")))
			defi.setDate(dateDebut);

		if (!(dateFin.equals("")))
			defi.setEndDate(dateFin);

		System.out.print(
				"\n\n\nmodif defi: nom = " + nom + " description = " + description + " points = " + points + "\n\n\n");

	}

	public Collection<String> getClassement(Groupe grp) {
		Collection<String> resultat = new ArrayList<String>();

		Collection<Membre> membres = em.find(Groupe.class, grp.getId()).getMembres();
		System.out.println("\n\n MEMBRES " + membres + " \n\n");
		if (membres != null) {
			for (Membre mb : membres) {
				int somme = 0;
				TypedQuery<Defi_Valide> req = em.createQuery(
						"select dv from Defi_Valide dv where dv.membre=" + mb.getId() + " and dv.groupe=" + grp.getId(),
						Defi_Valide.class);
				if (req != null && req.getResultList().size() != 0) {
					Collection<Defi_Valide> dvs = req.getResultList();
					for (Defi_Valide dv : dvs) {
						somme += dv.getDefi().getPoints() * mb.getCoeff_sportif();
					}
				}
				resultat.add(mb.getPrenom() + ":" + somme);
			}
		} else {
			System.out.println("\n\n AUCUN MEMBRE \n\n");
		}
		System.out.println(resultat);
		return resultat;
	}

	public void enleverMembre(Groupe grp, int id_mbr) {
		Groupe gp = em.find(Groupe.class, grp.getId());
		Membre mb = em.find(Membre.class, id_mbr);
		mb.getGroupesAppartenus().remove(gp);
		em.merge(mb);
	}

	public void enleverDefi(int id_defi) {
		Defi defi = em.find(Defi.class, id_defi);
		em.remove(defi);
	}

	public void refuserDefi(int id_dav) {
		Defi_A_Valider dav = em.find(Defi_A_Valider.class, id_dav);
		em.remove(dav);
	}

	public Membre initialiserTest() {
		Membre mb = new Membre();
		mb.setCoeff_sportif(1);
		mb.setEmail("thomasdarget@hotmail.fr");
		mb.setMotdepasse("abc");
		mb.setNom("Darget");
		mb.setPrenom("Thomas");
		mb.setSalt(null);

		em.persist(mb);
		return mb;
	}

	public Membre initialiserTest2() throws Exception {

		Membre thomas = Tests_Membres.thomas();
		em.persist(thomas);

		Membre manu = Tests_Membres.manu();
		em.persist(manu);

		Membre charlotte = Tests_Membres.cha();
		em.persist(charlotte);

		Membre gregoire = Tests_Membres.gregoire();
		em.persist(gregoire);

		Membre celia = Tests_Membres.celia();
		em.persist(celia);

		Groupe gp = Tests_Groupes.groupe1(thomas);
		em.persist(gp);

		this.ajouterMembre("thomasdarget@hotmail.fr", gp);
		this.ajouterMembre("manugoncalves@gmail.com", gp);
		this.ajouterMembre("celia@gmail.com", gp);
		this.ajouterMembre("cha@sfr.fr", gp);

		Demande_A_Rejoindre dar = new Demande_A_Rejoindre();
		dar.setGroupe(gp);
		dar.setMembre(gregoire);
		em.persist(dar);

		Defi defi = Tests_Defis.defi1(gp);
		em.persist(defi);

		Defi_A_Valider defi_a_valider = new Defi_A_Valider();
		defi_a_valider.setDefi(defi);
		defi_a_valider.setGroupe(gp);
		defi_a_valider.setMembre(thomas);
		em.persist(defi_a_valider);

		this.validerDefi(defi_a_valider.getId());

		Defi_A_Valider defi_a_valider2 = new Defi_A_Valider();
		defi_a_valider2.setDefi(defi);
		defi_a_valider2.setGroupe(gp);
		defi_a_valider2.setMembre(manu);
		em.persist(defi_a_valider2);

		return thomas;
	}

	public Collection<Defi_Valide> getDefisValides(Groupe grp) {
		Groupe gp = em.find(Groupe.class, grp.getId());
		return gp.getDefis_valides();
	}

	public Collection<Message> getMessages(int id_usr, int id_conv) {
		String rq = "select m from Message m WHERE (m.sender=" + id_usr + " and m.receiver=" + id_conv
				+ ") OR (m.sender=" + id_conv + " and m.receiver=" + id_conv + ")";
		TypedQuery<Message> req = em.createQuery(rq, Message.class);

		if (req != null && req.getResultList().size() != 0) {
			return req.getResultList();
		} else {
			return null;
		}
	}

}
