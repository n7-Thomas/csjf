package controleur;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			request.getRequestDispatcher("accueil.jsp").forward(request, response);
			return;
		}

		//AFFICHER LA PAGE D'ACCUEIL
		if(action.equals("afficher_pageAccueil")){
			request.setAttribute("groupes", facade.getGroupes());
			request.getRequestDispatcher("accueil.jsp").forward(request, response);
		}

		if(action.equals("deconnexion")) {
			session.invalidate();
			request.getRequestDispatcher("connexion.jsp").forward(request, response);
		}

		if (action.equals("modifier_profil")) {
			request.getRequestDispatcher("modifier_profil.jsp").forward(request, response);
		}

		if (action.equals("enregistrer_modif")) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String motdepasse = request.getParameter("motdepasse");
			Membre mb = ((Membre) session.getAttribute("user"));
			facade.modifierProfil(mb, nom, prenom, email, motdepasse);
			request.getRequestDispatcher("ServeurConnexion?action=afficher_profil").forward(request, response);
		}

	}

}
