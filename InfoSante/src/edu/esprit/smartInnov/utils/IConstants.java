package edu.esprit.smartInnov.utils;

public interface IConstants {

	final String DATEPATTERN = "dd/MM/yyyy";
	final String DATETIMEPATTERN = "dd/MM/yyyy HH:mm:ss";
	
	public interface Connection{
		public static String urlDb="jdbc:mysql://localhost:3306/infosante";
		public static String userNameBd = "root";
		public static String pwdDb = "";
	}
	
	public interface Profils{
		public final static String MEDECIN = "Medecin";
		public final static String PATIENT = "Patient";
		public final static String ADMINISTRATUER = "Admin";
	}
	
	public interface DaysOfWeek{
		public final static int MONDAY = 1;
		public final static int TUESDAY = 2;
		public final static int WEDNESDAY = 3;
		public final static int THURSDAY = 4;
		public final static int FRIDAY = 5;
		public final static int SATURDAY = 6;
		public final static int SUNDAY = 7;
	}
	
	public interface TypeSecialites{
		public final static String MEDECINE = "Medecine";
		public final static String KINESIE = "Kinesie";
		public final static String RADIOLOGIE = "Radiologie";
	}
	
	public interface TypeLocals{
		public final static String CABINET = "Cabinet";
		public final static String CENTRE = "Centre";
		public final static String LABORATOIRE = "Laboratoire";
	}
	
	public final static String[] typesMedicament = {"Comprimé", "Sirop", "Suppo", "Injection","Pommade"};
	
	public final static String[] applicationMedicament = {"Voie sanguine", "Voie orale"};
	
	public final static String[] categorieProduit = {"Parfum", "Categorie2", "Categorie3", "Categorie4", "Categorie5"};
}
