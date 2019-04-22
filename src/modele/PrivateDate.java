package modele;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrivateDate {

	private int jour;

	private int mois;

	private int annee;

	/*
	 * private int heure;
	 * 
	 * private int minute;
	 * 
	 * private int seconde;
	 */
	public PrivateDate(String date) {
		this.setAnnee(Integer.parseInt(date.substring(0, 4)));
		// tiret
		this.setMois(Integer.parseInt(date.substring(5, 7)));
		// tiret
		this.setJour(Integer.parseInt(date.substring(8, 10)));
		/*
		 * this.setHeure(Integer.parseInt(date.substring(8,10)));
		 * this.setMinute(Integer.parseInt(date.substring(10,12)));
		 * this.setSeconde(Integer.parseInt(date.substring(12,14)));
		 */
	}

	public String toString() {
		String date = "";
		date += String.valueOf(annee);
		date += "-";
		if (this.mois < 10)
			date += "0";
		date += String.valueOf(mois);
		date += "-";
		if (this.jour < 10)
			date += "0";
		date += String.valueOf(jour);

		/*
		 * if(this.heure < 10) date += "0"; date += String.valueOf(heure);
		 * 
		 * if(this.minute < 10) date += "0"; date += String.valueOf(minute);
		 * 
		 * if(this.seconde < 10) date += "0"; date += String.valueOf(seconde);
		 */
		return date;
	}

	public static PrivateDate getNow() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return new PrivateDate(dateFormat.format(date).toString());
	}

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}
/*
	public int getHeure() {
		return heure;
	}

	public void setHeure(int heure) {
		this.heure = heure;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSeconde() {
		return seconde;
	}

	public void setSeconde(int seconde) {
		this.seconde = seconde;
	}
	*/
}
