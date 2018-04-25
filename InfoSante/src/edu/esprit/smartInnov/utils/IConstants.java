package edu.esprit.smartInnov.utils;

public interface IConstants {

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
