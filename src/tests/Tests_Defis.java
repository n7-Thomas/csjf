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

	public static Defi defiCharite(Groupe groupe) {
		return createDefi("Défi charité", "Courir pour une noble cause", 300, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}

	public static Defi defiInsolite(Groupe groupe) {
		return createDefi("Défi sport insolite", "Tester pendant au moins 1h un nouveau sport", 100, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}
	
	public static Defi defiNatation(Groupe groupe) {
		return createDefi("Défi natation", "Nager 2km en une semaine", 200, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}
	
	public static Defi defiTest(Groupe groupe) {
		return createDefi("Défi test de salle", "Participer à une séance test de muscu où vous voulez", 100, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}
	
	public static Defi defiVelo(Groupe groupe) {
		return createDefi("Défi vélo", "Faire 3h de vélo en une semaine", 250, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
	}
	
	public static Defi defiCours(Groupe groupe) {
		return createDefi("Défi cours en cours!", "Aller en cours en courant pendant une semaine", 100, PrivateDate.getNow().toString(),
				PrivateDate.getNow().toString(), groupe);
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
