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
	
	public static Defi defiCardio(Groupe groupe) {
		return createDefi("Défi cardio", "Courir 5 fois dans la semaine ou une fois pendant 1h30", 150, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}
	
	public static Defi defiMuscu(Groupe groupe) {
		return createDefi("Défi muscu", "Faire 5 séries de 20 abdos, 10 pompes, 20 burpees et 30 secondes de gainage", 200, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}
	
	public static Defi defiBouffe(Groupe groupe) {
		return createDefi("Défi bouffe", "Manger sans matière grasse pendant une semaine entière", 250, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}
	
	public static Defi defiSouplesse(Groupe groupe) {
		return createDefi("Défi souplesse", "S'étirer pendant 20 minutes après chaque exercice pendant 2 semaines", 100, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}

}
