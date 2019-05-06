package tests;

import modele.Defi;
import modele.Groupe;
import modele.PrivateDate;
import modele.TypeDefi;

public class Tests_Defis {

	private static Defi createDefi(String nom, String description, int points, String date, String endDate,
			Groupe groupe) {
		Defi defi = new Defi();
		defi.setDescription(description);
		defi.setNom(nom);
		defi.setPoints(points);
		defi.setDate(date);
		defi.setEndDate(endDate);
		defi.setGroupe(groupe);
		defi.setType(TypeDefi.Sport);
		return defi;
	}

	public static Defi defi1(Groupe groupe) {
		return createDefi("Défi 1", "Ceci est un défi test", 100, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}

	public static Defi defi2(Groupe groupe) {
		return createDefi("Défi 2", "Ceci est un autre défi test", 10, "",
				"", groupe);
	}

}
