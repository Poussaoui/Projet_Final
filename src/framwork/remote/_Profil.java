package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.TreeMap;

public interface _Profil extends Remote {
	// **************************_Profil*****************************
	public boolean authentication(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

	public int modifierPassword(String loginChamp, String passChamp,
			String tableLogin, String username, String oldPassword,
			String newPassword) throws RemoteException;

	public String getProfilType(String loginChamp, String TypeChamp,
			String tableLogin, String username) throws RemoteException;

	// ************ retour Champ en ArrayList<Champ> ************
	public TreeMap<String, Object> retourChamp(String table)
			throws RemoteException;

	// ************ insertion a la base de donnees ************
	public void inserer(String table, TreeMap<String, Object> champs)
			throws RemoteException;


	// ****************************n"est pas
	// faite********************************
	/*
	 * public String inScription(String loginChamp, String TypeChamp, String
	 * tableLogin, String username) throws RemoteException;
	 * 
	 * public String modifierProfil(String loginChamp, String TypeChamp, String
	 * modifierProfil, String username) throws RemoteException;
	 * 
	 * public String rechercherProfil(String loginChamp, String TypeChamp,
	 * String tableLogin, String username) throws RemoteException;
	 */

}
