package controleur;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import exceptions.ExceptionUserNonDefini;
import modele.Badge;
import modele.CSJF;
import modele.Defi;
import modele.Defi_A_Valider;
import modele.Defi_Valide;
import modele.Demande_A_Rejoindre;
import modele.Etats;
import modele.Groupe;
import modele.Membre;
import modele.Message;
import modele.PrivateDate;
import modele.Publication;
import modele.Reaction;
import modele.Reaction.Type;
import modele.TypeDefi;
import tests.Tests_Defis;
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

		return m.getMotdepasse().equals(motdepasseCryp);
	}

	public Membre checkConnexion(String email, String motDePasse) {

		Membre member = null;

		TypedQuery<Membre> req = em.createQuery("select m from Membre m WHERE email = '" + email + "'", Membre.class);

		if (req == null || req.getResultList().size() != 1) {
			System.out.println("Utilisateur n'existe pas");
			member = null;
		} else {
			member = req.getSingleResult();
		}
		return member;
	}

	public Membre inscriptionNewMember(String nom, String prenom, String email, String motdepasse, byte[] salt) {

		Membre member = null;
		TypedQuery<Membre> req = em.createQuery("select m from Membre m WHERE email = '" + email + "'", Membre.class);
		if (req == null || req.getResultList().size() > 0) {
			member = null;
		} else {
			System.out.println("Nouvel utilisateur");
			member = new Membre();
			member.setCoeff_sportif(1);
			member.setEmail(email);
			member.setMotdepasse(motdepasse);
			member.setNom(nom);
			member.setPrenom(prenom);
			member.setSalt(salt);
			em.persist(member);
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

		String date_debut = formatDate(dateDebut);
		String date_fin = formatDate(dateFin);

		defi.setDate(date_debut);
		defi.setEndDate(date_fin);
		em.persist(defi);

		return defi;
	}

	public String formatDate(String date) {
		String date_bien = date.replaceAll("\\W", "");
		System.out.println(date);
		return date_bien;
	}

	/**
	 * @param id_dav
	 */
	public void validerDefi(int id_dav) {
		Defi_A_Valider dav = em.find(Defi_A_Valider.class, id_dav);
		Defi_Valide dv = new Defi_Valide();
		dv.setDefi(dav.getDefi());
		dv.setGroupe(dav.getGroupe());
		dv.setMembre(dav.getMembre());

		dv.setDateValidation(PrivateDate.getNow().toString());

		creerNotification(dav.getGroupe().getId(), dav.getMembre().getPrenom() + " vient de valider le défi: " + dav.getDefi().getDescription());



		/*Badge b1 = em.find(Badge.class, 1);
		Membre m = dav.getMembre();
		if(!(m.getBadges().contains(b1)) ) {

			b1.addMembre(m);
			dav.getMembre().getBadges().add(b1);
		}*/

		em.persist(dv);
		em.remove(dav);

	}


	public void validerDemande(int id_dar) {
		Demande_A_Rejoindre dar = em.find(Demande_A_Rejoindre.class, id_dar);
		Membre mb = em.find(Membre.class, dar.getMembre().getId());
		Groupe gp = em.find(Groupe.class, dar.getGroupe().getId());
		mb.getGroupesAppartenus().add(gp);
		em.remove(dar);

		creerNotification(id_dar, mb.getPrenom() + " " + mb.getNom() + " vient de rejoindre le groupe!");

	}

	public void supprimerGroupe(Groupe gp) {
		em.remove(em.find(Groupe.class, gp.getId()));
	}

	public Groupe changerNomGroupe(Groupe gp, String nom) {
		Groupe groupe = em.find(Groupe.class, gp.getId());
		groupe.setNom(nom);
		creerNotification(gp.getId(), gp.getAdmin().getPrenom() + " à remplacé le nom du groupe par " + nom);
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

		Collection<Groupe> groupes_app = mb.getGroupesAppartenus();

		if (mb.getGroupesAppartenus() != null) {
			for (Groupe g : groupes_app) {
				if (g.getId() == grp.getId()) {
					return false;
				}
			}
			mb.getGroupesAppartenus().add(grp);
		}

		TypedQuery<Demande_A_Rejoindre> req2 = em.createQuery(
				"select d from Demande_A_Rejoindre d WHERE GROUPE_ID=" + grp.getId() + " AND MEMBRE_ID=" + mb.getId(),
				Demande_A_Rejoindre.class);
		if (req2.getResultList() != null && req2.getResultList().size() != 0) {
			Demande_A_Rejoindre dar = req2.getSingleResult();
			em.remove(em.find(Demande_A_Rejoindre.class, dar.getId()));
		}

		creerNotification(grp.getId(), mb.getPrenom() + " " + mb.getNom() + " vient de rejoindre le groupe!");
		return true;
	}

	public void supprimerMembre() {

	}

	public void editerCoefMembre() {

	}

	/**
	 * FROM PAGE GROUPE, on demande à valider un défi
	 */
	public void ajouterDefiAValider(Groupe groupe, int idDefiAValider, Membre membre) throws Exception {
		Defi defi1 = em.find(Defi.class, idDefiAValider);
		Membre m = em.find(Membre.class, membre.getId());
		TypedQuery<Defi_A_Valider> req = em
				.createQuery("select d from Defi_A_Valider d WHERE d.defi=" + idDefiAValider + " and d.membre=" + membre.getId(), Defi_A_Valider.class);

		if (req.getResultList().size() != 0) { // Alors le défi a déjà été demandé à être validé
			throw new Exception("Ce défi a déjà été envoyé pour être validé, vous ne pouvez pas le renvoyer !");
		}

		TypedQuery<Defi_Valide> req2 = em.createQuery(
				"select d from Defi_Valide d WHERE d.defi=" + idDefiAValider + " and d.membre=" + membre.getId(),
				Defi_Valide.class);

		if (req2.getResultList().size() != 0) {
			throw new Exception("Ce défi a déjà été validé !");
		}

		if (defi1 != null && m != null && groupe != null) {
			Defi_A_Valider defi_a_valider = new Defi_A_Valider();
			defi_a_valider.setDefi(defi1);
			defi_a_valider.setMembre(m);
			defi_a_valider.setGroupe(groupe);
			em.persist(defi_a_valider);
		} else {
			throw new Exception("Problème facade ajouter defi a valider");
		}
	}

	/**
	 * FROM PAGE GROUPE, on crée un CSJF pour le membre concerné
	 */
	public void envoyerCSJF(String texte, Groupe groupe, Membre membre) throws Exception {
		Groupe g = em.find(Groupe.class, groupe.getId());
		Membre m = em.find(Membre.class, membre.getId());
		TypedQuery<CSJF> req = em
				.createQuery("select c from CSJF c WHERE MEMBRE_ID=" + membre.getId() + " and c.etat=2", CSJF.class);
		if (req.getResultList().size() != 0) { // Alors le défi a déjà été demandé à être validé
			throw new Exception(
					"Vous avez déjà envoyé un CSJF, vous ne pouvez pas en renvoyer un autre pour l'instant");
		}
		if (m != null && groupe != null) {
			CSJF csjf = new CSJF();
			csjf.setGroupe(g);
			csjf.setMembre(m);
			csjf.setTexte(texte);
			em.persist(csjf);
		} else {
			throw new Exception("probbb facade créer CSJF");
		}
	}

	/**
	 * FROM PAGE GROUPE, on demande les défis d'un groupe
	 */
	public Collection<Defi> getDefis(Groupe grp) {
		Groupe g = em.find(Groupe.class, grp.getId());
		return g.getDefis();
	}

	public Publication creerPublication(Membre mbr, String contenu) {
		Publication publi = new Publication();
		// publi.setGroupe(grp);
		publi.setMembre(mbr);
		publi.setContenu(contenu);

		em.persist(publi);

		return publi;
	}

	/**
	 * FROM PAGE GROUPE, on demande les défis d'un membres, ie. tous les défis mais
	 * aussi les défis validés et à valider
	 */
	public ArrayList<Object> getDefisMembre(Membre membre, Groupe grp) {
		Groupe g = em.find(Groupe.class, grp.getId());
		Membre m = em.find(Membre.class, membre.getId());
		ArrayList<Object> l = new ArrayList<Object>();
		Collection<Defi> defis = g.getDefis();
		l.addAll(defis);
		for (Defi_Valide d : m.getDefis_valides()) {
			if (defis.contains(d.getDefi())) {
				l.remove(d.getDefi());
				l.add(d);
			} else {
				l.add(d);
			}
		}
		TypedQuery<Defi_A_Valider> req = em.createQuery("select d from Defi_A_Valider d WHERE MEMBRE_ID=" + m.getId(),
				Defi_A_Valider.class);
		for (Defi_A_Valider d : req.getResultList()) {
			if (defis.contains(d.getDefi())) {
				l.remove(d.getDefi());
				l.add(d);
			} else {
				l.add(d);
			}
		}
		return l;
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

		g.setNom(nom);
		g.setAdmin(usr);
		em.persist(g);
		init();

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

	/**
	 * Récuperer la liste des publications d'un groupe
	 */
	public Collection<Publication> getPublications(int id_grp) {
		Groupe g = em.find(Groupe.class, id_grp);
		return g.getPublications();

	}

	/***
	 * Creer une nouvelle publication
	 */
	public Publication creerPublication(int id_groupe, Membre membre, String contenu) {
		Publication publication = new Publication();
		publication.setContenu(contenu);
		publication.setMembre(membre);

		Groupe gp = em.find(Groupe.class, id_groupe);
		publication.setGroupe(gp);

		em.persist(publication);

		return publication;
	}

	/***
	 * Creer une nouvelle notification
	 */
	public void creerNotification(int id_groupe, String contenu){
		Publication notification = new Publication();
		notification.setContenu(contenu);

		Groupe gp = em.find(Groupe.class, id_groupe);
		notification.setGroupe(gp);

		em.persist(notification);
	}

	/**
	 * Creation d'une réaction
	 */
	public Reaction creerReaction(Membre membre, int id_publication, String type){

		Publication publication = em.find(Publication.class, id_publication);

		Reaction reaction = publication.getReaction(membre);

		//Si la réaction n'a pas de membre: C'est une première réaction
		if (reaction.getMembre()==null){

			reaction.setPublication(publication);
			reaction.setMembre(membre);

			if(type.equals("cool")){
				reaction.setType(Type.cool);
			}
			else if(type.equals("pasCool")){
				reaction.setType(Type.pasCool);
			}
			else if(type.equals("surpris")){
				reaction.setType(Type.surpris);
			}
			em.persist(reaction);

		// Si la Réaction à déjà à un membre: c'est un changement d'avis
		}else {
			//Si le type est le même que l'ancien: onle supprime
			if(reaction.getType().toString().equals(type)){
				publication.getReactions().remove(reaction);
				em.remove(reaction);
			}
			//Sinon on le change
			else {
				if(type.equals("cool")){
					reaction.setType(Type.cool);
				}
				else if(type.equals("pasCool")){
					reaction.setType(Type.pasCool);
				}
				else if(type.equals("surpris")){
					reaction.setType(Type.surpris);
				}
			}
		}

		return reaction;
	}

	/**
	 * FROM INSCRIPTION
	 */
	public void creerProfil() {

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
		Membre mbr = em.find(Membre.class, mb.getId());
		mbr.setNom(nom);
		mbr.setPrenom(prenom);
		mbr.setEmail(email);
		mbr.setMotdepasse(motdepasse);
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

		if (!(dateDebut.equals(""))) {
			String date_debut = formatDate(dateDebut);
			defi.setDate(date_debut);
		}

		if (!(dateFin.equals(""))) {
			String date_fin = formatDate(dateFin);
			defi.setEndDate(date_fin);
		}

		System.out.print(
				"\n\n\nmodif defi: nom = " + nom + " description = " + description + " points = " + points + "\n\n\n");

	}

	public Collection<String> getClassement(Groupe grp) {
		Collection<String> resultat = new ArrayList<String>();

		Collection<Membre> membres = em.find(Groupe.class, grp.getId()).getMembres();
		System.out.println("\n\n MEMBRES " + membres + " \n\n");
		if (membres != null) {
			for (Membre mb : membres) {
				int somme_defi_total = 0;
				int somme_csjf_total = 0;
				int somme_defi_cette_semaine = 0;
				int somme_csjf_cette_semaine = 0;
				TypedQuery<Defi_Valide> req = em.createQuery(
						"select dv from Defi_Valide dv where dv.membre=" + mb.getId() + " and dv.groupe=" + grp.getId(),
						Defi_Valide.class);
				TypedQuery<CSJF> req2 = em.createQuery("select c from CSJF c where c.membre=" + mb.getId()
						+ " and c.groupe=" + grp.getId() + " and c.etat=0", CSJF.class);


				PrivateDate date_prec = PrivateDate.getNow();
				date_prec.setJour(date_prec.getJour() - 7);


				if (req != null && req.getResultList().size() != 0) {
					Collection<Defi_Valide> dvs = req.getResultList();
					for (Defi_Valide dv : dvs) {
						PrivateDate date = new PrivateDate(dv.getDateValidation());
						somme_defi_total += dv.getDefi().getPoints();
						if(!date.isBefore(date_prec))
							somme_defi_cette_semaine += dv.getDefi().getPoints();
					}
				}
				if (req2 != null && req2.getResultList().size() != 0) {
					Collection<CSJF> csjfs = req2.getResultList();
					for (CSJF csjf : csjfs) {
						PrivateDate date = new PrivateDate(csjf.getDateValidation());
						somme_csjf_total += csjf.getPoints();

						if(!date.isBefore(date_prec))
							somme_csjf_cette_semaine += csjf.getPoints();

					}
				}

				resultat.add(mb.getPrenom() + ":" + somme_defi_total + ":" + somme_defi_cette_semaine + ":"
						+ somme_csjf_total + ":" + somme_csjf_cette_semaine);
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

		TypedQuery<Defi_Valide> req = em.createQuery("select dv from Defi_Valide dv where groupe_id=" + grp.getId() + "and membre_id=" + id_mbr, Defi_Valide.class);
		Collection<Defi_Valide> dvs = req.getResultList();

		for (Defi_Valide defi_v : dvs) {

			Defi_Valide defi_valide = em.find(Defi_Valide.class, defi_v.getId());

			em.remove(defi_valide);
		}

		TypedQuery<Defi_A_Valider> req2 = em.createQuery("select dav from Defi_A_Valider dav where groupe_id=" + grp.getId() + "and membre_id=" + id_mbr, Defi_A_Valider.class);
		Collection<Defi_A_Valider> davs = req2.getResultList();

		for (Defi_A_Valider defi_av : davs) {

			Defi_A_Valider defi_aval = em.find(Defi_A_Valider.class, defi_av.getId());

			em.remove(defi_aval);
		}

		mb.getGroupesAppartenus().remove(gp);
	}

	public void enleverDefi(int id_defi) {

		// SUPPRIMER LE DEFI DANS BDD DEFI
		Defi defi = em.find(Defi.class, id_defi);
		em.remove(defi);



		// SUPPRIMER LE DEFI DANS BDD DEFI_VALIDE
		TypedQuery<Defi_Valide> req = em.createQuery(
				"select dv from Defi_Valide dv where defi_id=" + id_defi,
				Defi_Valide.class);
		Collection<Defi_Valide> defi_a_supp = req.getResultList();

		for (Defi_Valide defi_v : defi_a_supp) {

			Defi_Valide defi_valide = em.find(Defi_Valide.class, defi_v.getId());

			em.remove(defi_valide);
		}




		// SUPPRIMER LE DEFI DANS BDD DEFI_A_VALIDER
		TypedQuery<Defi_A_Valider> req2= em.createQuery(
				"select dav from Defi_A_Valider dav where defi_id=" + id_defi,
				Defi_A_Valider.class);
		Collection<Defi_A_Valider> defi_a_supp2 = req2.getResultList();

		for (Defi_A_Valider defi_av : defi_a_supp2) {

			Defi_A_Valider defi_a_valider = em.find(Defi_A_Valider.class, defi_av.getId());

			em.remove(defi_a_valider);
		}

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

		Groupe gp = creerGroupe("Objectif Summer Body", thomas);
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


		/*Defi_A_Valider defi_a_valider = new Defi_A_Valider();
		defi_a_valider.setDefi(defi);
		defi_a_valider.setGroupe(gp);
		defi_a_valider.setMembre(thomas);
		em.persist(defi_a_valider);

		this.validerDefi(defi_a_valider.getId());*/

		/*
		 * Defi_A_Valider defi_a_valider = new Defi_A_Valider();
		 * defi_a_valider.setDefi(defi); defi_a_valider.setGroupe(gp);
		 * defi_a_valider.setMembre(thomas); em.persist(defi_a_valider);
		 *
		 * this.validerDefi(defi_a_valider.getId());
		 */


		Defi_A_Valider defi_a_valider2 = new Defi_A_Valider();
		defi_a_valider2.setDefi(defi);
		defi_a_valider2.setGroupe(gp);
		defi_a_valider2.setMembre(manu);
		em.persist(defi_a_valider2);

		CSJF csjf = new CSJF();
		csjf.setEtat(Etats.EnCoursDeValidation);
		csjf.setGroupe(gp);
		csjf.setMembre(celia);
		csjf.setTexte("J'ai fait des pâtes");
		em.persist(csjf);

		CSJF csjf_deja_valide = new CSJF();
		csjf_deja_valide.setEtat(Etats.Valide);
		csjf_deja_valide.setGroupe(gp);
		csjf_deja_valide.setMembre(thomas);
		csjf_deja_valide.setTexte("Je suis né");
		csjf_deja_valide.setDateValidation("19970723");
		csjf_deja_valide.setPoints(100);
		em.persist(csjf_deja_valide);

		CSJF csjf_deja_valide2 = new CSJF();
		csjf_deja_valide2.setEtat(Etats.Valide);
		csjf_deja_valide2.setGroupe(gp);
		csjf_deja_valide2.setMembre(thomas);
		csjf_deja_valide2.setTexte("Je suis");
		csjf_deja_valide2.setDateValidation("20190404");
		csjf_deja_valide2.setPoints(200);
		em.persist(csjf_deja_valide2);


		CSJF csjf_deja_valide3 = new CSJF();
		csjf_deja_valide3.setEtat(Etats.Valide);
		csjf_deja_valide3.setGroupe(gp);
		csjf_deja_valide3.setMembre(thomas);
		csjf_deja_valide3.setTexte("Yo");
		csjf_deja_valide3.setDateValidation("20190508");
		csjf_deja_valide3.setPoints(300);
		em.persist(csjf_deja_valide3);

		/*
		Badge badge = new Badge();
		badge.setNom("Il faut un début à tout !");
		badge.setDescription("Vous avez validé votre premier défi !");
		badge.setNiveau(1);
		em.persist(badge);

		Badge badge1 = new Badge();
		badge.setNom("La chasse au points commence !");
		badge.setDescription("Vous avez validé plus de 500 points !");
		badge.setNiveau(1);
		em.persist(badge1);

		Badge badge2 = new Badge();
		badge.setNom("Beau parleur");
		badge.setDescription("Vous avez publiez plus de 10 fois !");
		badge.setNiveau(1);
		em.persist(badge2);
		*/

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

	public void validerCSJF(int id_csjf, int valeur) {
		CSJF csjf = em.find(CSJF.class, id_csjf);
		csjf.setEtat(Etats.Valide);
		csjf.setPoints(valeur);
		csjf.setDateValidation(PrivateDate.getNow().toString());

		creerNotification(csjf.getGroupe().getId(), csjf.getMembre().getPrenom() + " vient de " + csjf.getTexte() + " pour " + valeur + " points.");
	}

	public void refuserCSJF(int id_csjf) {
		CSJF csjf = em.find(CSJF.class, id_csjf);
		csjf.setEtat(Etats.NonValide);
		csjf.setDateValidation(PrivateDate.getNow().toString());
	}

	public Collection<CSJF> getCSJFAValider(Groupe gp){
		Collection<CSJF> csjf_a_valider = null;
		String rq = "select c from CSJF c where c.groupe=" + gp.getId() + " and c.etat=2"; // 2 pour EnCoursDeValidation
		TypedQuery<CSJF> req = em.createQuery(rq, CSJF.class);


		if(req != null && req.getResultList().size() != 0)
			csjf_a_valider = req.getResultList();

		return csjf_a_valider;
	}


	public Collection<String> getStatistiques(Membre usr) {
		Collection<String> resultat = new ArrayList<String>();

		int nb_mois = 4;

		if (usr != null) {


			int[] somme_csjfs_mois = new int[nb_mois];
			int[] somme_defis_mois = new int[nb_mois];



			TypedQuery<Defi_Valide> req = em.createQuery(
						"select dv from Defi_Valide dv where dv.membre=" + usr.getId(),
						Defi_Valide.class);
			TypedQuery<CSJF> req2 = em.createQuery("select c from CSJF c where c.membre=" + usr.getId() + " and c.etat=0", CSJF.class);


			PrivateDate[] date_mois = new PrivateDate[nb_mois];
			for(int i=0; i < nb_mois; i++) {
				date_mois[i] = PrivateDate.getNow();
				date_mois[i].setMois(date_mois[i].getMois() - i);
			}


			if (req != null && req.getResultList().size() != 0) {
				Collection<Defi_Valide> dvs = req.getResultList();
				for (Defi_Valide dv : dvs) {
					PrivateDate date = new PrivateDate(dv.getDateValidation());

					for(int i=0; i < nb_mois - 1; i++) {
						if(date.isBefore(date_mois[i]) && date.isAfter(date_mois[i+1]))
							somme_csjfs_mois[i] += dv.getDefi().getPoints();
					}
				}
			}

			if (req2 != null && req2.getResultList().size() != 0) {
				Collection<CSJF> csjfs = req2.getResultList();
				for (CSJF csjf : csjfs) {
					PrivateDate date = new PrivateDate(csjf.getDateValidation());


					for(int i=0; i < nb_mois - 1; i++) {
						if(date.isBefore(date_mois[i]) && date.isAfter(date_mois[i+1]))
							somme_csjfs_mois[i] += csjf.getPoints();
					}

				}


			}

			for(int i=nb_mois - 1; i >= 0; i--) {
				resultat.add(date_mois[i].thisMoisString() + ":" + somme_defis_mois[i] + ":" + somme_csjfs_mois[i]);
			}


		}

		System.out.println(resultat);
		return resultat;
	}


	public Collection<Badge> getBadges(Membre m){
		Membre membre = em.find(Membre.class, m.getId());

		// Mise à jour du badge défi
		int nbDefis = membre.getDefis_valides().size();

		// Si le membre à effectué un défis et qu'il n'avait pas le badge avant: Obtention badge premier défi!
		Badge b1 = em.find(Badge.class,1);
		if(nbDefis>0 && !membre.getBadges().contains(b1)) {
			b1.addMembre(membre);
			membre.getBadges().add(b1);
		}
		Badge b2 = em.find(Badge.class,2);
		if(nbDefis>30 && !membre.getBadges().contains(b2)) {
			b2.addMembre(membre);
			membre.getBadges().add(b2);
		}

		Badge b3 = em.find(Badge.class,3);
		if(nbDefis>30 && !membre.getBadges().contains(b3)) {
			b3.addMembre(membre);
			membre.getBadges().add(b3);
		}



		return membre.getBadges();
	}

	public void init() {
		Badge badge1 = new Badge();
		badge1.setDescription("Vous avez validé votre premier défi!");
		badge1.setNom("défis");
		badge1.setNiveau(1);


		Badge badge2 = new Badge();
		badge2.setDescription("Vous avez validé 10 défis!");
		badge2.setNom("défis");
		badge2.setNiveau(2);

		Badge badge3 = new Badge();
		badge3.setDescription("Vous avez validé 100 défis!");
		badge3.setNom("défis");
		badge3.setNiveau(3);


		em.persist(badge1);
		em.persist(badge2);
		em.persist(badge3);

	}

}


