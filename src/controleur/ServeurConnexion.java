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
 * Servlet implementation class ServeurConnexion
 */
@WebServlet("/ServeurConnexion")
public class ServeurConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private Facade facade;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServeurConnexion() {
		super();
		// TODO Auto-generated constructor stub
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
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
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
			request.getRequestDispatcher("Serveur?action=afficher_pageAccueil").forward(request, response);
		}

	}

}
