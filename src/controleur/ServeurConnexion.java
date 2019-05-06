package controleur;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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

		// Si pas d'action on envoie sur l'accueil
		if (action == null) {
			request.getRequestDispatcher("Serveur?action=afficher_pageAccueil").forward(request, response);
			return;
		}

		// ACTION CHECKER LA CONNEXION
		if (action.equals("testconnexion")) {
			String email = request.getParameter("email");
			String motdepasse = request.getParameter("motdepasse");
			Membre m = facade.checkConnexion(email, motdepasse);
			System.out.println("MEMBRE TROUVE : " + m);
			if (m == null) {
				boolean connecte = false;
				String msg = "L'email entré ne correspond à aucun compte.";
				request.setAttribute("connecte", connecte);
				request.setAttribute("email_faux", email);
				request.setAttribute("message", msg);
				request.setAttribute("erreur", "");
				request.getRequestDispatcher("connexion.jsp").forward(request, response);
			} else {
				request.setAttribute("message","");
				if (facade.checkPassword(motdepasse,m) == true) {
					session.setAttribute("user", m);
					actionAfficherGroupe(request,response,session);
				} else {
					boolean connecte = false;
					request.setAttribute("erreur", "Mot de passe incorrect");
					request.setAttribute("connecte", connecte);
					request.setAttribute("email_faux", email);
					request.getRequestDispatcher("connexion.jsp").forward(request, response);
				}

			}
		}

		if (action.equals("inscription")) {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String motdepasse = request.getParameter("motdepasse");

			String motdepasseCrypte;
			byte[] salt;
			try {
				salt = SHACrypt.getSalt();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
				salt = null;
			}
			motdepasseCrypte = SHACrypt.get_SHA_256_SecurePassword(motdepasse, salt);
			Membre m = facade.inscriptionNewMember(nom, prenom, email, motdepasseCrypte,salt);
			System.out.println("MEMBRE TROUVE : " + m);
			if (m != null) {
				session.setAttribute("user", m);
				request.getRequestDispatcher("Serveur?action=afficher_pageAccueil").forward(request, response);
			} else {
				String erreur = "Un membre utilise déjà cette email.";
				boolean existe = true;
				request.setAttribute("warning", erreur);
				request.setAttribute("existe", existe);
				request.setAttribute("nom_user", nom);
				request.setAttribute("prenom_user", prenom);
				request.setAttribute("email_user", email);
				request.getRequestDispatcher("inscription.jsp").forward(request, response);
			}

		}

		if(action.equals("afficher_profil")){
			actionAfficherGroupe(request,response,session);
		}

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
		
		request.setAttribute("groupes_appartenus", facade.getGroupesAppartenus(usr));
		request.setAttribute("groupes_admins", facade.getGroupesAdministres(usr));
		request.getRequestDispatcher("profil.jsp").forward(request, response);
	}

}
