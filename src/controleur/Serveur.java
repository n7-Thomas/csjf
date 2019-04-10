package controleur;

import java.io.IOException;
import java.util.Collection;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.ExceptionUserNonDefini;
import modele.Defi;
import modele.Defi_A_Valider;
import modele.Demande_A_Rejoindre;
import modele.Groupe;
import modele.Membre;

/**
 * Servlet implementation class Serveur
 */
@WebServlet(description = "Serveur principal", urlPatterns = { "/Serveur" })
public class Serveur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private Facade facade;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Serveur() {
		super();
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


		// Si pas d'action on envoie sur l'index
		if (action == null) {
			request.getRequestDispatcher("index.html").forward(request, response);
			return;
		}

		// ACTION DEMANDE DE VALIDATION D'UN DEFI
		if (action.equals("formulaireValiderDefi")) {
			Defi defiAValider = (Defi) request.getAttribute("defiAValider");
			try {
				facade.demandeValidationDefi(defiAValider, (Membre) session.getAttribute("user"));
				request.setAttribute("succes", "Votre défi a été envoyé !");
				request.getRequestDispatcher("groupe.jsp").forward(request, response);
			} catch (Exception e) {
				request.setAttribute("erreur", e.getMessage());
				request.getRequestDispatcher("groupe.jsp").forward(request, response);
			}
		}

		if(action.equals("init1")) {
			Membre mb = facade.initialiserTest();
			session.setAttribute("user", mb);
			System.out.println("Thomas ajouté.");
			request.getRequestDispatcher("index.html").forward(request, response);
		}

		if(action.equals("init2")) {
			facade.initialiserTest2();
			System.out.println("Manu ajouté.");
			request.getRequestDispatcher("index.html").forward(request, response);
		}

		if(action.equals("init3")) {
			facade.initialiserTest3();
			System.out.println("Défi en demande de validation");
			request.getRequestDispatcher("index.html").forward(request, response);
		}

		// ACTION CREER DEFI
		if (action.equals("ajouterDefi")) {
			actionAjouterDefi(request, response, session);
		}

		// ACTION CREER GROUPE
		if (action.equals("creerGroupe")) {
			actionCreerGroupe(request, response, session);
		}

		// ACTION CREER GROUPE
		if (action.equals("ajouterMembre")) {
			actionAjouterMembre(request, response, session);
		}

		// ACTION CREER GROUPE
		if (action.equals("validerDefis")) {
			actionValiderDefis(request, response, session);
		}

		// ACTION CREER GROUPE
		if (action.equals("validerDemandesARejoindre")) {
			actionValiderDemandesARejoindre(request, response, session);
		}


		// ACTION AFFICHER PAGE GROUPE
		if (action.equals("pageGroupe")) {
			Groupe grp = (Groupe) session.getAttribute("groupe");
			request.setAttribute("groupe", grp);
			request.getRequestDispatcher("groupe.jsp").forward(request, response);
		}

		// ACTION CHECKER LA CONNEXION
		if (action.equals("testconnexion")) {
			String email = request.getParameter("email");
			String motdepasse = request.getParameter("motdepasse");
			Membre m = facade.checkConnexion(email, motdepasse);
			System.out.println("MEMBRE TROUVE : " + m);
			if (m == null) {
				request.setAttribute("erreur", "Pas de membre trouvé");
				request.getRequestDispatcher("connexion.jsp").forward(request, response);
			} else {
				session.setAttribute("user", m);
				request.getRequestDispatcher("profil.jsp").forward(request, response);
			}
		}

		if (action.equals("inscription")) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String motdepasse = request.getParameter("motdepasse");
			Membre m = facade.inscriptionNewMember(nom, prenom, email, motdepasse);
			System.out.println("MEMBRE TROUVE : " + m);
			session.setAttribute("user", m);
			request.getRequestDispatcher("profil.jsp").forward(request, response);
			}
		}

	/**
	 * Traiter la requête pour amener vers la page de validation des défis.
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
		Groupe grp = (Groupe) session.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Collection<Defi_A_Valider> defis = facade.getDefisAValider(grp);

		request.setAttribute("defis_a_valider", defis);
		request.getRequestDispatcher("valider_defis.jsp").forward(request, response);

	}

	/**
	 * Traiter la requête d'affichage des demandes à rejoindre.
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void actionValiderDemandesARejoindre(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		// Récupération du membre connecté
		Membre usr = (Membre) session.getAttribute("user");
		if (usr == null) {
			request.setAttribute("erreur", "Vous n'êtes pas connecté");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Récupération du groupe lié
		Groupe grp = (Groupe) session.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		Collection<Demande_A_Rejoindre> defis = facade.getDemandeARejoindre(grp);

		request.setAttribute("demandes_a_rejoindre", defis);
		request.getRequestDispatcher("valider_demande_a_rejoindre.jsp").forward(request, response);

	}


	/**
	 * Traiter la requête d'ajout d'un membre au groupe.
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
		Groupe grp = (Groupe) session.getAttribute("groupe");
		if (grp == null) {
			request.setAttribute("erreur", "Pas de groupe actif");
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		// Ajout du membre
		if(!facade.ajouterMembre(email, grp)) {
			request.setAttribute("erreur", "Nous n'avons pas trouvé cette personne");
			request.getRequestDispatcher("ajouter_membre.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("admin.jsp").forward(request, response);
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

		// Ajout du groupe sélectionné dans le groupe
		session.setAttribute("groupe", grp);

		// Redirection vers la page admin
		request.getRequestDispatcher("admin.jsp").forward(request, response);
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

		Groupe grp = (Groupe) session.getAttribute("groupe");
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
		int points = Integer.parseInt(str_points);

		Defi defi = null;
		try {
			defi = facade.ajouterDefi(nom, description, usr, grp, points);
		} catch (Exception e) {
			request.setAttribute("erreur", e.getStackTrace());
			request.getRequestDispatcher("erreur.jsp").forward(request, response);
			return;
		}

		request.setAttribute("defi", defi);
		request.getRequestDispatcher("admin.jsp").forward(request, response);

	}

}
