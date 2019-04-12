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
 * Servlet implementation class ServeurPageGroupe
 */
@WebServlet("/ServeurPageGroupe")
public class ServeurPageGroupe extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private Facade facade;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServeurPageGroupe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Récupération de l'action
		String action = request.getParameter("action");
		HttpSession session = request.getSession();

		// Si pas d'action on envoie sur l'index
		if (action == null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}

		// ACTION Afficher le fil d'actualité
		if (action.equals("afficher_filActu")) {
			Groupe grp =  (Groupe) session.getAttribute("grp");
			try {
				//request.setAttribute("listeNotif", facade.getFilActu(grp).getNotifications());
			//	request.setAttribute("listePubli", facade.getFilActu(grp).getPublications());
				request.getRequestDispatcher("aff_filActu.jsp").forward(request, response);
			} catch (Exception e) {
				request.setAttribute("erreur", e.getStackTrace());
				request.getRequestDispatcher("erreur.jsp").forward(request, response);
			}


		}

		// Publier une Publication
		if (action.equals("publier")){
			String contenu = request.getParameter("contenu");
			Membre mbr = (Membre) session.getAttribute("usr");

			try {
				facade.creerPublication( mbr, contenu);
			}catch (Exception e) {
				request.setAttribute("erreur", e.getStackTrace());
				request.getRequestDispatcher("erreur.jsp").forward(request, response);
			}
			request.getRequestDispatcher("aff_filActu.jsp").forward(request, response);

		}
	}

}
