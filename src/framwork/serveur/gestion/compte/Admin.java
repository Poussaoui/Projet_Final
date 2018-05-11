package framwork.serveur.gestion.compte;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import framwork.remote._Admin;
import framwork.serveur.base.db.connection.ConnexionSGBD;


public class Admin {
	// ************ ConnexionSGBD ***************
	private ConnexionSGBD dbOracle;
	private Connection conn;
	
	public Admin() {
		// ************ ConnexionSGBD ***************
		try {
			dbOracle = new ConnexionSGBD();
			conn = dbOracle.connexion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public String champToCondition(TreeMap<String, Object> c) {
		String s = "";
		for (Map.Entry<String, Object> entry : c.entrySet()) {

			if (entry.getKey() == c.lastKey()) {
				s = s + entry.getKey() + " = '" + entry.getValue().toString()
						+ "'";
			} else {
				s = s + entry.getKey() + " = '" + entry.getValue().toString()
						+ "' AND ";
			}
		}
		return s;
	}

	
	public void supprimerCompte(String table, TreeMap<String, Object> c)
			throws SQLException {

		Statement stmt = conn.createStatement();

		String condition = champToCondition(c);

		String req = "delete from " + table + " where " + condition;

		System.out.println(req);

		try {
			/** suppression du profil dans la BD **/
			stmt.executeUpdate(req);
			System.out.println("Profil supprimé");
		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}
		stmt.close();
	}

	
}
