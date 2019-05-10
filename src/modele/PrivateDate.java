package modele;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrivateDate {

	public static int[] nb_jours = { 31, 28, 31, 30, 31, 30, 31, 30, 31, 30, 31, 30 };

	public static String[] les_mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
	
	private int jour;

	private int mois;

	private int annee;

	public PrivateDate(String date) {
		System.out.println(date);
		this.setAnnee(Integer.parseInt(date.substring(0, 4)));
		this.setMois(Integer.parseInt(date.substring(4, 6)));
		this.setJour(Integer.parseInt(date.substring(6, 8)));
	}

	public String toString() {
		String date = "";
		date += String.valueOf(annee);
		if (this.mois < 10)
			date += "0";
		date += String.valueOf(mois);
		if (this.jour < 10)
			date += "0";
		date += String.valueOf(jour);

		return date;
	}

	public String toGoodString() {
		String date = "";
		date += String.valueOf(jour);
		date += " ";
		date += thisMoisString();
		date += " ";
		date += String.valueOf(annee);
	
		return date;
	}
	
	
	public static PrivateDate getNow() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		return new PrivateDate(dateFormat.format(date).toString());
	}

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		if (jour < 0) {
			this.setMois(this.getMois() - 1);
			this.jour = PrivateDate.nb_jours[this.getMois()] + jour;
		} else if (jour > PrivateDate.nb_jours[this.getMois()]) {
			this.jour = jour % PrivateDate.nb_jours[this.getMois()];
			this.setMois(this.getMois() + (jour / PrivateDate.nb_jours[this.getMois()]) + 1);
		} else {
			this.jour = jour;
		}
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		if (mois < 0) {
			this.mois = 12 + mois;
			this.setAnnee(this.getAnnee() - 1);
		} else if (mois > 12) {
			this.mois = mois % 12;
			this.setAnnee(this.getAnnee() + (mois / 12) + 1);
		} else {
			this.mois = mois;
		}

	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public boolean isBefore(PrivateDate date_prec) {
		
		boolean res = true;
		if (this.annee > date_prec.getAnnee())
			res = false;
		else if (this.annee < date_prec.getAnnee())
			res = true;
		else {
			if (this.mois > date_prec.getMois())
				res = false;
			else if (this.mois < date_prec.getMois())
				res = true;
			else {
				if (this.jour > date_prec.getJour())
					res = false;
				else if (this.jour < date_prec.getJour())
					res = true;
			}
		}
		
		System.out.println("\n\n" + this.toString() + " is before ? " + date_prec.toString() + " = " + res);
		return res;
	}

	public boolean isAfter(PrivateDate date_mois_1) {
		return(date_mois_1.isBefore(this));
	}
	
	public String thisMoisString() {
		return PrivateDate.les_mois[(this.getMois() - 1) % 12];
	}
	
}
