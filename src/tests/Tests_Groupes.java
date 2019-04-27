package tests;

import modele.Groupe;
import modele.Membre;

public class Tests_Groupes {

	
	private static Groupe createGroupe(String nom, Membre admin) {
		Groupe grp = new Groupe();
		grp.setAdmin(admin);
		grp.setNom(nom);
		return grp;
	}
	
	public static Groupe groupe1(Membre admin) {
		return createGroupe("Groupe 1", admin);
	}
}
