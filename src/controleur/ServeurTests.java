package controleur;

import java.io.IOException;

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
 * Servlet implementation class ServeurTests
 */
@WebServlet("/ServeurTests")
public class ServeurTests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private Facade facade;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServeurTests() {
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
		
		if (action.equals("init1")) {
			Membre mb = facade.initialiserTest();
			session.setAttribute("user", mb);
			System.out.println("Thomas ajouté.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}

		if (action.equals("init2")) {
			facade.initialiserTest2();
			System.out.println("Manu ajouté.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}

		if (action.equals("init3")) {
			facade.initialiserTest3();
			System.out.println("Défi en demande de validation");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}

		if (action.equals("init4")) {
			Groupe g = facade.initialiserTest4();
			session.setAttribute("groupe", g);
			System.out.println("Initialisation bdd");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
		
		
	}

}
