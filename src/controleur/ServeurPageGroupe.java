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

		// Si pas d'action on envoie sur l'accueil
		if (action == null) {
			request.getRequestDispatcher("accueil.jsp").forward(request, response);
			return;
		}



		// ACTION Afficher le fil d'actualité
		if (action.equals("afficher_filActu")) {

			// Récupération du groupe lié
			String str_id_grp = request.getParameter("id_grp");
			if (str_id_grp == null) {

				request.setAttribute("erreur", "Pas de groupe selectionné dans actionAfficherGroupe");
				request.getRequestDispatcher("erreur.jsp").forward(request, response);
				return;
			}
			int id_grp = Integer.parseInt(str_id_grp);

			try {
				request.setAttribute("user", session.getAttribute("user"));
				request.setAttribute("id_grp", id_grp);
			    request.setAttribute("listePublications", facade.getPublications(id_grp));
				request.getRequestDispatcher("aff_filActu.jsp").forward(request, response);

			} catch (Exception e) {
				request.setAttribute("erreur", e.getStackTrace());
				request.getRequestDispatcher("erreur.jsp").forward(request, response);
			}

		}

		// Publier une Publication
		if (action.equals("publier")){
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
			request.getRequestDispatcher("ServeurPageGroupe?action=afficher_filActu").forward(request, response);

		}
	}

}
