package controleur;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.ExceptionUserNonDefini;
import modele.Defi;
import modele.Groupe;
import modele.Membre;

/**
 * Servlet implementation class ServeurGroupe
 */
@WebServlet("/ServeurGroupe")
public class ServeurGroupe extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private Facade facade;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServeurGroupe() {
		super();
		//facade.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session == null) {
			request.setAttribute("erreur", "Pas de session");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération de l'action
		String action = request.getParameter("action");

		System.out.println("Serveur action : " + action);

		// Si pas d'action on envoie sur l'accueil
		if (action == null) {
			request.getRequestDispatcher("Serveur?action=afficher_pageAccueil").forward(request, response);
			return;
		}

		String str_id_groupe_selectionne = request.getParameter("id_grp");
		if (str_id_groupe_selectionne != null) {
			Groupe g = facade.getGroupeFromId(Integer.parseInt(str_id_groupe_selectionne));
			request.setAttribute("groupe", g);
		}

		// ACTION CREER DEFI
		if (action.equals("ajouterDefi")) {
			actionAjouterDefi(request, response, session);
		}
		// ACTION EDITER DEFI
		if (action.equals("editDefi")) {
			actionEditerDefi(request, response, session);
		}
		// ACTION AJOUTER DEFI AUX DEFI A VALIDER
		if (action.equals("ajouterDefiAValider")) {
			actionAjouterDefiAValider(request, response, session);
		}

		// ACTION CREER GROUPE
		if (action.equals("creerGroupe")) {
			actionCreerGroupe(request, response, session);
		}

		// ACTION AJOUTER MEMBRE AU GROUPE
		if (action.equals("ajouterMembre")) {
			actionAjouterMembre(request, response, session);
		}

		// ACTION VALIDER DEFIS
		if (action.equals("validerDefis")) {
			actionValiderDefis(request, response, session);
		}

		// ACTION VALIDER DEMANDES A REJOINDRE
		if (action.equals("validerDemandes")) {
			actionValiderDemandesARejoindre(request, response, session);
		}

		// ACTION CREER GROUPE
		if (action.equals("demanderRejoindreGroupe")) {
			actionDemanderRejoindreGroupe(request, response, session);
		}

		// ACTION AFFICHER ADMIN
		if (action.equals("admin")) {
			actionAfficherAdmin(request, response, session);
		}

		// ACTION AFFICHER PAGE GROUPE
		if (action.equals("pageGroupe")) {
			actionAfficherGroupe(request, response, session);
		}

		// ACTION AFFICHER CLASSEMENT
		if (action.equals("histogramme")) {
			actionAfficherHistogramme(request, response, session);
		}

		// ACTION SUPPRIMER MEMBRE
		if (action.equals("del_membre")) {
			actionEnleverMembre(request, response, session);
		}

		// ACTION SUPPRIMER DEFI
		if (action.equals("del_defi")) {
			actionSupprimerDefi(request, response, session);
		}

		// ACTION SUPPRIMER GROUPE
		if (action.equals("supprimerGroupe")) {
			actionSupprimerGroupe(request, response, session);
		}

		// ACTION EDIT GROUPE NOM
		if (action.equals("editNameGroupe")) {
			actionEditerNomGroupe(request, response, session);
		}

		// ACTION ENVOYER CSJF
		if (action.equals("envoyerCSJF")) {
			actionEnvoyerCSJF(request, response, session);
		}

		// ACTION VALIDER CSJF
		if (action.equals("validerCSJF")) {
			actionValiderCSJF(request, response, session);
		}


		// Publier une Publication
		if (action.equals("publier")){
			actionPublier(request, response, session);
		}

		// Réagir à une Publication
		if (action.equals("reagir")){
			actionReagir(request, response, session);
		}



	}

	private void actionReagir(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		String str_id_grp = request.getParameter("id_grp");

		if(str_id_grp == null){
			request.setAttribute("erreur", "pas de groupe");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
		}
		int id_grp = Integer.parseInt(str_id_grp);

		Membre mbr = (Membre) session.getAttribute("user");
		String str_id_publication = request.getParameter("id_publication");

		if(str_id_publication == null){
			request.setAttribute("erreur", "pas de publication");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
		}

		int id_publication = Integer.parseInt(str_id_publication);
		String type = request.getParameter("type");


		try {
			facade.creerReaction(mbr, id_publication, type);
		}catch (Exception e) {
			request.setAttribute("erreur", e.getStackTrace());
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
		}

		actionAfficherGroupe(request, response, session);
	}

	private void actionPublier(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		String str_id_grp = request.getParameter("id_grp");

		if(str_id_grp == null){
			request.setAttribute("erreur", "pas de groupe");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
		}
		int id_grp = Integer.parseInt(str_id_grp);

		Membre mbr = (Membre) session.getAttribute("user");
		String contenu = request.getParameter("contenu");

		if(contenu == null){
			contenu = " ";
		}

		try {
			facade.creerPublication(id_grp, mbr, contenu);
		}catch (Exception e) {
			request.setAttribute("erreur", e.getStackTrace());
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
		}

		actionAfficherGroupe(request, response, session);
	}

	/**
	 * Ajouter un défi à valider à la liste pour le faire valider par l'admin
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionEnvoyerCSJF(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {

		Membre usr = (Membre) session.getAttribute("user");
		Groupe groupe = (Groupe) request.getAttribute("groupe");
		String texte = request.getParameter("csjf");

		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		if (groupe == null) {
			request.setAttribute("erreur", "Aucun groupe présent");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		if (texte == null) {
			request.setAttribute("status", "Vous n'avez pas saisi de csjf !");
			actionAfficherGroupe(request, response, session);
		}

		try {
			facade.envoyerCSJF(texte, groupe, usr);
			request.setAttribute("status", "Votre CSJF a été envoyé !");
			actionAfficherGroupe(request, response, session);

		} catch (Exception e) {
			request.setAttribute("status", e.getMessage());
			actionAfficherGroupe(request, response, session);
		}

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionSupprimerGroupe(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		facade.supprimerGroupe(grp);

		request.setAttribute("status", "Vous avez bien supprimé ce groupe");
		request.getRequestDispatcher("Serveur?action=afficher_pageAccueil").forward(request, response);

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionEditerNomGroupe(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String nom = request.getParameter("nouveau_nom_groupe");
		if(nom == null){
			request.setAttribute("erreur", "Pas de nom récupéré");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}


		request.setAttribute("groupe", facade.changerNomGroupe(grp, nom));


		actionAfficherAdmin(request, response, session);

	}


	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionSupprimerDefi(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String str_id_defi = request.getParameter("id_defi");
		if(str_id_defi == null){
			request.setAttribute("erreur", "Pas de défi sélectionné");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}
		int id_defi = Integer.parseInt(str_id_defi);

		facade.enleverDefi(id_defi);

		actionAfficherAdmin(request, response, session);
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionEnleverMembre(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String str_id_mbr = request.getParameter("id_mbr");
		if(str_id_mbr == null){
			request.setAttribute("erreur", "Pas de membre sélectionné");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}
		int id_mbr = Integer.parseInt(str_id_mbr);

		facade.enleverMembre(grp, id_mbr);

		actionAfficherAdmin(request, response, session);
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionAfficherHistogramme(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {

		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Collection<String> classement = facade.getClassement(grp);
		if (classement == null) {
			request.setAttribute("erreur", "récupération des points");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}
		request.setAttribute("groupe", grp);
		request.setAttribute("classement", classement);
		request.getRequestDispatcher("histogramme.jsp").forward(request, response);


	}

	/**

	 * Traiter une requête pour éditer un défi.
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionEditerDefi(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String nom = request.getParameter("nom");
		if (nom == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de nom");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String description = request.getParameter("description");
		if (description == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de description");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String str_points = request.getParameter("points");
		if (str_points == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de points");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		int points = -1;
		if (!(str_points.equals("")))
			points = Integer.parseInt(str_points);

		String str_id_defi = request.getParameter("id_defi");
		if (str_id_defi == null) {
			request.setAttribute("erreur", "On a pas pu récupéré l'id du défi");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}
		int id_defi = Integer.parseInt(str_id_defi);

		String dateDebut = request.getParameter("dateDebut");
		if (dateDebut == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de date de début");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String dateFin = request.getParameter("dateFin");
		if (dateFin == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de date de fin");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		try {
			facade.editerDefi(id_defi, nom, description, points, dateDebut, dateFin);
		} catch (Exception e) {
			request.setAttribute("erreur", e.getStackTrace());
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		request.setAttribute("status", "Le défi " + nom + " a été modifié.");
		request.getRequestDispatcher("ServeurGroupe?action=admin&id_grp=" + grp.getId()).forward(request, response);

	}

	/**
	 * Ajouter un défi à valider à la liste pour le faire valider par l'admin
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionAjouterDefiAValider(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {


		String defiAValider = request.getParameter("id_defi");
		//String groupe = request.getParameter("id_groupe");
		Membre usr = (Membre) session.getAttribute("user");
		Groupe groupe = (Groupe) request.getAttribute("groupe");

		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		if (defiAValider == null) {
			request.setAttribute("erreur", "Aucun défi séléctionné");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		if (groupe == null) {
			request.setAttribute("erreur", "Aucun groupe sélectionné dans AjouterDefiAValider");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		int idDefiAValider = Integer.parseInt(defiAValider);

		try {
			facade.ajouterDefiAValider(groupe, idDefiAValider, usr);
			request.setAttribute("status", "Votre défi a été envoyé !");
			actionAfficherGroupe(request, response, session);

		} catch (Exception e) {
			request.setAttribute("status", e.getMessage());
			actionAfficherGroupe(request, response, session);
		}
	}

	/**
	 * Traiter une requête de demande à rejoindre le groupe.
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionDemanderRejoindreGroupe(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {

		// Récupération du membre connecté
		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String nom_groupe = request.getParameter("nom");
		if (nom_groupe == null) {
			request.setAttribute("erreur", "Vous n'avez pas spécifié de nom de groupe");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Groupe gp = facade.getGroupeFromNom(nom_groupe);
		if (gp == null) {
			request.setAttribute("erreur", "Ce groupe est introuvable");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		if (facade.demanderRejoindreGroupe(usr, gp)) {
			request.setAttribute("status", "La demande a été envoyé");
			request.getRequestDispatcher("Serveur?action=afficher_pageAccueil").forward(request, response);
		} else {
			request.setAttribute("status", "Vous faites déjà parti de ce groupe");
			request.getRequestDispatcher("Serveur?action=afficher_pageAccueil").forward(request, response);
		}
	}

	/**
	 * Traiter la requête pour amener vers la page de validation des défis.
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionValiderDefis(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		// Récupération du membre connecté
		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du groupe lié
		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}


		boolean valider = true;
		if (request.getParameter("refuser") != null)
			valider = false;


		Map<String, String[]> hm = request.getParameterMap();
		Iterator<Entry<String, String[]>> iter = hm.entrySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().getKey();


			if (key.contains("defi_")) {

				int id_dav = Integer.parseInt(key.substring(5));
				if (valider)
					facade.validerDefi(id_dav);
				else
					facade.refuserDefi(id_dav);

			}
		}

		actionAfficherAdmin(request, response, session);
	}

	private void actionValiderCSJF(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		// Récupération du membre connecté
		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du groupe lié
		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Map<String, String[]> hm = request.getParameterMap();
		Iterator<Entry<String, String[]>> iter = hm.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String[]> next = iter.next();
			String key = next.getKey();

			if (key.contains("csjf_")) {
				String value[] = next.getValue();
				if(value != null && value.length == 1) {
					if(!value[0].equals("")) {
						int valeur = Integer.parseInt(value[0]);
						int id_csjf = Integer.parseInt(key.substring(5));
						facade.validerCSJF(id_csjf, valeur);
					}
				}
			}
		}

		actionAfficherAdmin(request, response, session);
	}

	private void actionAfficherAdmin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		// Récupération du membre connecté
		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du groupe lié
		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		if (!facade.administreGroupe(usr.getId(), grp.getId())) {
			request.setAttribute("erreur", "Vous n'êtes pas administrateur de ce groupe");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du membre connecté pour la topbar
		request.setAttribute("groupes_appartenus", facade.getGroupesAppartenus(usr));
		request.setAttribute("groupes_admins", facade.getGroupesAdministres(usr));


		request.setAttribute("csjf_a_valider", facade.getCSJFAValider(grp));
		request.setAttribute("defis_valides", facade.getDefisValides(grp));
		request.setAttribute("defis_en_cours", facade.getDefisEnCours(grp));
		request.setAttribute("demandes_a_rejoindre", facade.getDemandeARejoindre(grp));
		request.setAttribute("defis_a_valider", facade.getDefisAValider(grp));
		request.setAttribute("membres",  facade.getMembres(grp));

		request.getRequestDispatcher("admin.jsp").forward(request, response);
	}

	private void actionAfficherGroupe(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		// Récupération du membre connecté
		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du groupe lié
		String id_grp = request.getParameter("id_grp");
		if (id_grp == null) {
			request.setAttribute("erreur", "Pas de groupe selectionné dans actionAfficherGroupe");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}
		int id_grp1 = Integer.parseInt(id_grp);

		Groupe grp = facade.getGroupeFromId(id_grp1);
		if (grp == null) {
			request.setAttribute("erreur", "Le groupe n'a pas été trouvé");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		request.setAttribute("classement", facade.getClassement(grp));
		request.setAttribute("groupe", grp);
		request.setAttribute("id_grp", id_grp);
		request.setAttribute("defis", facade.getDefisMembre(usr, grp));
		request.setAttribute("membres", facade.getMembres(grp));
		request.setAttribute("listePublications", facade.getPublications(grp.getId()));
		request.setAttribute("groupes_appartenus", facade.getGroupesAppartenus(usr));
		request.setAttribute("groupes_admins", facade.getGroupesAdministres(usr));

		request.getRequestDispatcher("groupe.jsp").forward(request, response);
	}

	/**
	 * Traiter la requête d'affichage des demandes à rejoindre.
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionValiderDemandesARejoindre(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {

		// Récupération du membre connecté
		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du groupe lié
		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Map<String, String[]> hm = request.getParameterMap();
		Iterator<Entry<String, String[]>> iter = hm.entrySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().getKey();

			if (key.contains("dar_")) {
				int id_dar = Integer.parseInt(key.substring(4));
				facade.validerDemande(id_dar);

			}
		}

		actionAfficherAdmin(request, response, session);
	}

	/**
	 * Traiter la requête d'ajout d'un membre au groupe.
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionAjouterMembre(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		// Vérification du email de groupe donné
		String email = request.getParameter("email");
		if (email == null) {
			request.setAttribute("erreur", "Pas de email rentré");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du groupe courant
		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Ajout du membre
		if (!facade.ajouterMembre(email, grp)) {
			request.setAttribute("erreur", "Nous n'avons pas trouvé cette personne");
			request.getRequestDispatcher("ajouter_membre.jsp").forward(request, response);
			return;
		}

		request.setAttribute("status", "Le membre " + email + " a été ajouté au groupe " + grp.getNom());
		request.getRequestDispatcher("ServeurGroupe?action=admin&id_grp=" + grp.getId()).forward(request, response);
	}

	/**
	 * Traiter la requête de création d'un nouveau groupe.
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionCreerGroupe(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		// Vérification du nom de groupe donné
		String nom = request.getParameter("nom");
		if (nom == null) {
			request.setAttribute("erreur", "Pas de nom rentré");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du membre connecté qui deviendra admin
		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Création du groupe avec la facade
		Groupe grp = null;
		try {
			grp = facade.creerGroupe(nom, usr);

		} catch (ExceptionUserNonDefini e) {
			request.setAttribute("erreur", "Pas de groupe créé : " + e.getStackTrace());
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
		}


		request.setAttribute("status", "Le groupe " + grp.getNom() + " a été crée.");

		// Redirection vers la page admin
		request.getRequestDispatcher("ServeurGroupe?action=admin&id_grp=" + grp.getId()).forward(request, response);
	}

	/**
	 * Traiter la requete d'ajout d'un défi au groupe correspondant.
	 *
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionAjouterDefi(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Groupe grp = (Groupe) request.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String nom = request.getParameter("nom");
		if (nom == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de nom");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String description = request.getParameter("description");
		if (description == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de description");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String str_points = request.getParameter("points");
		if (str_points == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de nom");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}
		int points = -1;
		if (!(str_points.equals("")))
			points = Integer.parseInt(str_points);

		String dateDebut = request.getParameter("dateDebut");
		if (dateDebut == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de date de début");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		String dateFin = request.getParameter("dateFin");
		if (dateFin == null) {
			request.setAttribute("erreur", "Vous n'avez pas donné de date de fin");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Defi defi = null;
		try {
			defi = facade.ajouterDefi(nom, description, grp, points, dateDebut, dateFin);
		} catch (Exception e) {
			request.setAttribute("erreur", e.getStackTrace());
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		request.setAttribute("status", "Le défi " + nom + " a été ajouté au groupe " + grp.getNom());
		request.setAttribute("defi", defi);
		request.getRequestDispatcher("ServeurGroupe?action=admin&id_grp=" + grp.getId()).forward(request, response);

	}
}
