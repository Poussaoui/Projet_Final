package rmi.server;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import application.Champ;
import application.ConnexionSGBD;
import application.SQLWarningsExceptions;
import application.Services;
import java.sql.Connection;

public class ServerDatabase {
	private TreeMap<String, String> loginInfo;
	private TreeMap<String, Object> admin;
	private ConnexionSGBD dbOracle;
    private Connection conn;
	public ServerDatabase () throws ClassNotFoundException, SQLException
	{
		loginInfo = new TreeMap<String, String>();
		loginInfo.put("admin", "admin");
		admin = new TreeMap<String, Object>();
		admin.put("admin", null);
		dbOracle = new ConnexionSGBD();
        conn = dbOracle.connexion();
	}//constructor
	
	public boolean authenticateLogin (String username, String password)
	{
		if (!loginInfo.containsKey(username))
			return false;
		else 
		{
			String correctPassword = loginInfo.get(username);
			if (correctPassword.equals(password))
				return true;
			else
				return false;
		}//else
	}//authenticateLogin
	
	public boolean createUser (String username, String password)
	{
		
		try {
		Champ champUsername = new Champ("username", username);
		Champ champPass = new Champ("password", password);
		
		ArrayList<Champ> champs = new ArrayList<Champ>();
		champs.add(champUsername);
		champs.add(champPass);
		new Services().inserer("Profil",champs);
		}
		catch (Exception e){
			e.getMessage();
		}
//		if (loginInfo.containsKey(username))
//			return false;
//		else 
//		{
//			loginInfo.put(username, password);
//			return true;
//		}//else	
		return false;
	}//createUser
	
	public boolean changePassword (String username, String password)
	{
		if (!loginInfo.containsKey(username))
			return false;
		else 
		{
			loginInfo.put(username, password);
			return true;
		}//else
	}//changePassword

	public boolean isAdmin (String username)
	{
		return admin.containsKey(username);
	}//isAdmin
	
	public void reagit(Connection conn, String relation, int idEtudiant, int idPublication, String rea) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
		//inserer une ligne dans bd
		try {
		        Statement stmt = conn.createStatement();
		        String insertReaction = "insert into "+ relation + "values (" + idEtudiant + "," + idPublication + "," + rea + ")";
		        stmt.executeUpdate(insertReaction);
		} catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
		}
	}
	
	public void afficherReactionById(Connection conn, String relation, int idReaction) throws RemoteException {
		// TODO Auto-generated method stub
		//select * form bd where idReaction = idReaction;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + relation + "where idReaction=" + idReaction);
			while (rs.next()) {
				rs.getInt(3);
			}
		} catch (Exception e) {
	        SQLWarningsExceptions.printExceptions((SQLException) e);
	        System.err.println("Got an exception!"); 
	    }
	}


	public void afficherReactionByIdPers(Connection conn,String relation, int idPers) throws RemoteException {
		// TODO Auto-generated method stub
		
		//select reaction from bd where id = idPers
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + relation + "where idPers=" + idPers);
			while (rs.next()) {
				rs.getInt(3);
			}
		} catch (Exception e) {
	        SQLWarningsExceptions.printExceptions((SQLException) e);
	        System.err.println("Got an exception!"); 
	    }
	}


	public void afficherReactionByIdPub(Connection conn, String relation, int idPub) throws RemoteException {
		// TODO Auto-generated method stub
		
		//select reaction from bd where id=IdPub
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + relation + "where idReaction=" + idPub);
			
			while (rs.next()) {
				rs.getInt(3);
			}
		} catch (Exception e) {
	        SQLWarningsExceptions.printExceptions((SQLException) e);
	        System.err.println("Got an exception!"); 
	    }
		
	}


	public void modifierReaction(Connection conn, String relation, int idEtudiant, int idPub, String rea, int idReaction) throws RemoteException {
		// TODO Auto-generated method stub
		//delete une ligne where id = idReaction
		//inserer une linge where id = idReaction
		try {
	        Statement stmt = conn.createStatement();
	        String insertReaction = "insert into "+ relation + "values (" + idEtudiant + "," + idPub + "," + rea + ")";
	        String deleteReaction = "delete from " + relation + "where idRalation = " + idReaction;
	        stmt.executeQuery(deleteReaction);
	        stmt.executeUpdate(insertReaction);
		} catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
	}
	}


	public void retirerReaction(Connection conn, String relation, int idReaction) throws RemoteException {
		// TODO Auto-generated method stub
		//delete une ligne where id = idReaction
		try {
	        Statement stmt = conn.createStatement();	       
	        String deleteReaction = "delete from " + relation + "where idRalation = " + idReaction;
	        stmt.executeQuery(deleteReaction);	      
		} catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
	}
	}


	public int nbReactions(Connection conn, String relation) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		// select count (*) from bd;
		int nbReactions = 0;
        try (// Get a statement from the connection
                Statement stmt = conn.createStatement(); // Execute the query

                ResultSet rc = stmt.executeQuery("select count(*) from" + relation)) {

            while (rc.next()) {
                nbReactions = rc.getInt(1);                   
                //System.out.println("nbParticipants: " + nbParticipants + " ");

            }
        }
        return nbReactions;		
	}
}
