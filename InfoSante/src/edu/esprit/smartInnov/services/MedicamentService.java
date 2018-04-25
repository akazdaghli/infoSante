package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Medicament;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.vues.VMedicament;

public class MedicamentService {

	Connection cnx;
	private static final Logger LOGGER = Logger.getLogger(MedicamentService.class.getName());

	public MedicamentService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}

	public void ajouter(Medicament m) {
		String addQuery = "INSERT INTO Medicament(libelle, idLaboratoire, notice) VALUES (?, ?, ?)";
		try {
			Long genKey = null;
			PreparedStatement ps = cnx.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getLibelle());
			ps.setLong(2, m.getLaboratoire().getId());
			ps.setString(3, m.getNotice());
			LOGGER.log(Level.INFO, ps.toString());
			ps.executeUpdate();
			ResultSet genKeys = ps.getGeneratedKeys();
			while (genKeys.next()) {
				genKey = genKeys.getLong(1);
			}
			ps.close();
			LOGGER.log(java.util.logging.Level.INFO, "Medicament ajouté");
			ajouterListMaladiesToMedicament(genKey, m.getMaladies());
		} catch (SQLException e) {
			LOGGER.log(java.util.logging.Level.INFO, "Medicament non ajouté");
			LOGGER.log(Level.SEVERE, e.getMessage());
		}

	}

	public void ajouterListMaladiesToMedicament(Long idMedicament, List<Maladie> maladies) {
		String addQuery = "INSERT INTO medicament_maladie (idMaladie, idMedicament) VALUES (?, ?)";
		for (Maladie m : maladies) {
			try {
				PreparedStatement ps = cnx.prepareStatement(addQuery);
				ps.setLong(2, idMedicament);
				ps.setLong(1, m.getId());
				LOGGER.log(Level.INFO, ps.toString());
				ps.executeUpdate();
				ps.close();
				LOGGER.log(java.util.logging.Level.INFO, "ligne maladie medicament ajoutée!");
			} catch (SQLException e) {
				LOGGER.log(java.util.logging.Level.INFO, "ligne maladie medicament non ajoutée!");
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
		}

	}

	public List<Medicament> getListMedicamentsByMaladie(Maladie m) {
		LaboratoireService ls = new LaboratoireService();
		List<Medicament> meds = new ArrayList<>();
		String searchQuery = "SELECT * FROM medicament WHERE id IN (SELECT idMedicament FROM medicament_maladie WHERE idMaladie = ?)";
		try {
			PreparedStatement ps = cnx.prepareStatement(searchQuery);
			ps.setLong(1, m.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Medicament med = new Medicament();
				med.setId(rs.getLong(1));
				med.setLibelle(rs.getString("libelle"));
				med.setNotice(rs.getString("notice"));
				med.setLaboratoire(ls.findById(rs.getLong("idLaboratoire")));
				med.setType(rs.getString("type"));
				med.setApplication(rs.getString("application"));
				meds.add(med);
			}
			LOGGER.log(java.util.logging.Level.INFO, meds.size() + " medicaments trouvés!!");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return meds;
	}

	public List<Medicament> getAll() {
		List<Medicament> medicaments = new ArrayList<>();
		LaboratoireService ls = new LaboratoireService();
		String searchQuery = "SELECT * FROM Medicament";
		LOGGER.log(java.util.logging.Level.INFO, searchQuery);
		try {
			Statement s = cnx.createStatement();
			ResultSet rs = s.executeQuery(searchQuery);
			while (rs.next()) {
				Medicament med = new Medicament();
				med.setId(rs.getLong(1));
				med.setLibelle(rs.getString("libelle"));
				med.setNotice(rs.getString("notice"));
				med.setLaboratoire(ls.findById(rs.getLong("idLaboratoire")));
				med.setType(rs.getString("type"));
				med.setApplication(rs.getString("application"));
				medicaments.add(med);
			}
			LOGGER.log(java.util.logging.Level.INFO, medicaments.size() + " medicaments trouvés!!");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return medicaments;
	}

	public List<VMedicament> getAllVMedicaments() {
		List<VMedicament> medicaments = new ArrayList<>();
		LaboratoireService ls = new LaboratoireService();
		String searchQuery = "SELECT * FROM V_Medicament";
		LOGGER.log(java.util.logging.Level.INFO, searchQuery);
		try {
			Statement s = cnx.createStatement();
			ResultSet rs = s.executeQuery(searchQuery);
			while (rs.next()) {
				VMedicament med = new VMedicament();
				med.setId(rs.getLong(1));
				med.setLibelle(rs.getString("libelle"));
				med.setNotice(rs.getString("notice"));
				med.setLabo(rs.getString("labo"));
				med.setType(rs.getString("type"));
				med.setApplication(rs.getString("application"));
				medicaments.add(med);
			}
			LOGGER.log(java.util.logging.Level.INFO, medicaments.size() + " vmedicaments trouvés");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return medicaments;
	}

	public List<VMedicament> getListVMedicamentsFiltred(VMedicament v) {
		StringBuilder searchQuery = new StringBuilder("SELECT * FROM v_medicament WHERE 1=1 ");
		List<VMedicament> medicaments = new ArrayList<>();
		if (v.getApplication() != null && !v.getApplication().isEmpty()) {
			searchQuery.append("AND application like '%" + v.getApplication() + "%'");
		}
		if (v.getLibelle() != null && !v.getLibelle().isEmpty()) {
			searchQuery.append("AND libelle like '" + v.getLibelle() + "'");
		}
		if (v.getType() != null && !v.getType().isEmpty()) {
			searchQuery.append("AND type like '" + v.getType() + "'");
		}
		if (v.getLabo() != null && !v.getLabo().isEmpty()) {
			searchQuery.append("AND labo like '" + v.getLabo() + "'");
		}
		LOGGER.log(java.util.logging.Level.INFO, searchQuery.toString());
		try (Statement st = cnx.createStatement()) {
			ResultSet rs = st.executeQuery(searchQuery.toString());
			while (rs.next()) {
				VMedicament med = new VMedicament();
				med.setId(rs.getLong(1));
				med.setLibelle(rs.getString("libelle"));
				med.setNotice(rs.getString("notice"));
				med.setLabo(rs.getString("labo"));
				med.setType(rs.getString("type"));
				med.setApplication(rs.getString("application"));
				medicaments.add(med);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return medicaments;
	}
	
	public void deleteMedicament(Long id) {
		String deleteQuery = "DELETE FROM Medicament_maladie WHERE idMedicament = ?";
		String deleteQuery2 = " DELETE FROM Medicament WHERE id = ?";
		try (PreparedStatement ps1 = cnx.prepareStatement(deleteQuery);
				PreparedStatement ps2 = cnx.prepareStatement(deleteQuery2);){
			ps1.setLong(1, id);
			ps2.setLong(1, id);
			LOGGER.log(java.util.logging.Level.INFO, deleteQuery.toString());
			LOGGER.log(java.util.logging.Level.INFO, deleteQuery2.toString());
			ps1.executeUpdate();
			ps2.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

}
