package tests;

import modele.Membre;

public class Tests_Membres {
	
	private static Membre createMembre(String prenom, String nom, String email, String motdepasse, int cs) {
		Membre mb = new Membre();
		mb.setCoeff_sportif(cs);
		mb.setEmail(email);
		mb.setNom(nom);
		mb.setPrenom(prenom);
		mb.setMotdepasse(motdepasse);
		return mb;
	}
	
	public static Membre thomas() {
		return createMembre("Thomas", "Darget", "thomasdarget@hotmail.fr", "abc", 1);
	}
	
	public static Membre manu() {
		return createMembre("Manu", "Goncalves", "manugoncalves@gmail.com", "abc", 2);
	}
	
	public static Membre gregoire() {
		return createMembre("Grégoire", "Méné", "greg@wanadoo.fr", "abc", 2);
	}
	
	public static Membre cha() {
		return createMembre("Charlotte", "de Foucaud", "cha@sfr.fr", "abc", 2);
	}
	
	public static Membre celia() {
		return createMembre("Célia", "Lacoste", "celia@gmail.com", "abc", 2);
	}
}
