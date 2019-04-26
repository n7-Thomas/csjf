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
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}

		// ACTION AFFICHER PAGE GROUPE
		if (action.equals("pageGroupe")) {
			Groupe grp = (Groupe) session.getAttribute("groupe");
			request.setAttribute("groupe", grp);
			request.getRequestDispatcher("groupe.jsp").forward(request, response);
		}

		//AFFICHER LA PAGE D'ACCUEIL
		if(action.equals("afficher_pageAccueil")){
			Membre usr = (Membre) session.getAttribute("user");

			Collection<Groupe> groupes_appartenus = facade.getGroupesAppartenus(usr);
			Collection<Groupe> groupes_administres = facade.getGroupesAdmin(usr);

			request.setAttribute("groupesAppartenus", groupes_appartenus);
			request.setAttribute("groupesAdmin", groupes_administres);

			request.getRequestDispatcher("pageAccueil.jsp").forward(request, response);
		}



	}

}
