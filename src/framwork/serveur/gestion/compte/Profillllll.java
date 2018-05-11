package framwork.serveur.gestion.compte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import framwork.serveur.base.db.connection.ConnexionSGBD;

public class Profillllll {
	// ************ ConnexionSGBD ***************
	private ConnexionSGBD dbOracle;
	private Connection conn;
	// ************** Attributs *****************
	protected static TreeMap<String, Object> champs;
	// ***************** constricture par d�faut********************
	public Profillllll() {
		try {
			// ************ ConnexionSGBD ***************
			dbOracle = new ConnexionSGBD();
			conn = dbOracle.connexion();
			// **************** Initialisation ***************
			champs = new TreeMap<String, Object>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	// ***************** constricture param�tre ***********************
	public Profil(ArrayList<Champ> c) {
		try {
			// ************ ConnexionSGBD ***************
			dbOracle = new ConnexionSGBD();
			conn = dbOracle.connexion();
			// **************** Initialisation ***************
			champs = new ArrayList<Champ>();
			champs = c;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
//////////////////////////////////////////////////////////////////////////////////////
	// ***************** authentication from BD***********************
	public boolean authentication(String loginChamp, String passChamp,
			String tableLogin, String username, String password) {
		boolean exist = false;
		Object[] donnees = new Object[4];
		Statement stmt;
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT " + loginChamp + ","
					+ passChamp + " FROM " + tableLogin + " WHERE "
					+ loginChamp + "='" + username + "' AND " + passChamp
					+ "='" + password + "'");
			while (rs.next()) {
				donnees[0] = rs.getString(1);
				donnees[1] = rs.getString(2);
				exist = true;
			}
			rs.close();
		} catch (SQLException ex) {
			ex.getMessage();
		}
		return exist;
	}

	// *****************Select ProfilType type from BD***********************
	public String getProfilType(String loginChamp, String TypeChamp,
			String tableLogin, String username) {
		boolean exist = false;
		Statement stmt;
		String type = "";
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT " + TypeChamp + " FROM "
					+ tableLogin + " WHERE " + loginChamp + "='" + username
					+ "'");
			while (rs.next()) {
				type = rs.getString(1);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.getMessage();
		}
		return type;
	}

	// ***************** changePassword in BD***********************
	public int changePassword(String loginChamp, String passChamp,
			String tableLogin, String username, String oldPassword,
			String newPassword) {
		Statement st;
		int i = 0;
		try {
			st = conn.createStatement();
			i = st.executeUpdate("UPDATE " + tableLogin + " SET " + passChamp
					+ " ='" + newPassword + "' WHERE " + loginChamp + " ='"
					+ username + "' AND " + passChamp + " ='" + oldPassword
					+ "'");
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
////////////////////////////////////////////////////////////////////////////////////////////////
	// ***************** obtenir les champs @return les champs de la class
	public TreeMap<String, Object> getChamps() {
		return Profillllll.champs;
	}

	// ***************** modifierValeurChamp ********************
	public void modifierValeurChamp(String nom, Object val) {
		for (Map.Entry<String, Object> entry : champs.entrySet()) {
			if (entry.getKey().equals(nom)) {
				champs.put(nom, val);
			}
		}
	}

	// ************ retour Champ en ArrayList<Champ> ************
	public TreeMap<String, Object> retourChamp(String table) throws Exception,
			SQLException {

		Statement stmt = conn.createStatement();

		ArrayList<String> nomsChamps = new ArrayList<String>();
		TreeMap<String, Object> champs = new TreeMap<String, Object>();

		// récupère les champs de la table

		String sql = "SELECT * FROM " + table;

		ResultSet resultat = stmt.executeQuery(sql);

		System.out.println(sql);

		ResultSetMetaData metadata = resultat.getMetaData();

		// on récupère le noms des différents champs getColumnCount() donne
		// le nombre de colonnes
		for (int i = 1; i <= metadata.getColumnCount(); i++) {
			nomsChamps.add(metadata.getColumnName(i).toUpperCase());
		}

		// / on recupere les types java des différents champs

		for (int i = 1; i <= metadata.getColumnCount(); i++) {

			String typeJava = metadata.getColumnClassName(i);
			switch (typeJava) {
			case "java.math.BigDecimal":
				Integer d = Integer.MIN_VALUE;
				champs.put(nomsChamps.get(i - 1), d);
				break;
			case "java.lang.String":
				String s = "yes";
				champs.put(nomsChamps.get(i - 1), s);
				break;
			case "java.lang.Boolean":
				champs.put(nomsChamps.get(i - 1), true);
				break;
			case "java.sql.Timestamp":
				champs.put(nomsChamps.get(i - 1), new Date());
				break;
			default:
				break;
			}

		}
		stmt.close();
		resultat.close();
		return champs;
	}

 
	
		
	// ************ insertion a la base de donnees ************
	public void inserer(String table,TreeMap<String, Object> champs) // ArrayList<Champ> champs
			throws Exception, SQLException {

		Statement stmt = conn.createStatement();

		ArrayList<String> noms = new ArrayList<>();
		ArrayList<String> nomsChamps = new ArrayList<>();

		/**
		 * récupère le nom de tous les champs pour vérifier que la table a
		 * les mêmes
		 */
		for (Map.Entry<String, Object> entry : champs.entrySet()) {
			nomsChamps.add(entry.getKey().toUpperCase());
		}

		String sql = "SELECT * FROM " + table;

		ResultSet resultat = stmt.executeQuery(sql);

		ResultSetMetaData metadata = resultat.getMetaData();

		/**
		 * on récupère le noms des différents champs getColumnCount() donne
		 * le nombre de colonnes
		 **/
		for (int i = 1; i <= metadata.getColumnCount(); i++) {
			noms.add(metadata.getColumnName(i).toUpperCase());
		}

		/** on vérifie si les champs sont identiques avant de faire l'insertion **/
		for (int i = 0; i < noms.size(); i++) {
			if (!(noms.get(i).equals(nomsChamps.get(i))))
				throw new Exception(
						"Les champs donnés sont différents de ceux de la table");
		}

		/** on insère les valeurs dans la table pour chaque champ **/
		String s = "";
		for (Map.Entry<String, Object> entry : champs.entrySet()) {
			nomsChamps.add(entry.getKey().toUpperCase());
			if(entry.getKey() == champs.lastKey()){
				s = s + "'" + entry.getValue().toString() + "'";
			}else
			{
				s = s + "'" + entry.getValue().toString() + "'" + ",";
			}
		}
		String requete = "INSERT INTO " + table + " VALUES(" + s + ")";
		System.out.println(requete);
		try {
			stmt.executeUpdate(requete);
			System.out.println("Ajout réussi");
		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}

	}
	
}
