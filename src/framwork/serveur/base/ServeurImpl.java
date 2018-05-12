package framwork.serveur.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

import framwork.remote.*;
import framwork.serveur.base.db.connection.ConnexionSGBD;

import framwork.serveur.gestion.compte.Admin;
import framwork.serveur.gestion.compte.Profil;
import framwork.serveur.gestion.document.DiffusionDonnee;
import framwork.serveur.communication.*;

public class ServeurImpl extends UnicastRemoteObject implements _Profil, _Admin, _ChatServer, _DiffusionDonnee, Serializable {

	private static final long serialVersionUID = 2674880711467464646L;
	Profil profil;
	Admin admin;
	Discussion discussion;
	DiffusionDonnee diffusionD;

	public ServeurImpl(String storageDir) throws RemoteException {
		super();
		profil = new Profil();
		admin = new Admin();
		discussion = new Discussion();
		diffusionD = new DiffusionDonnee(storageDir);
	}

	public String getInformation() throws RemoteException {
		System.out.println("Invocation de la méthode getInformation()");
		return "bonjour";
	}

	// ****************************** Profil *****************************

	// ----------------------- authentication from BD******-****************
	@Override
	public boolean authentication(String loginChamp, String passChamp, String tableLogin, String username, String password) {
		return profil.authentication(loginChamp, passChamp, tableLogin, username, password);
	}

	// *****************Select ProfilType type from BD***********************
	@Override
	public String getProfilType(String loginChamp, String TypeChamp, String tableLogin, String username) {
		return profil.getProfilType(loginChamp, TypeChamp, tableLogin, username);
	}

	// ***************** changePassword in BD***********************
	@Override
	public int modifierPassword(String loginChamp, String passChamp, String tableLogin, String username, String oldPassword, String newPassword) {
		return profil.modifierPassword(loginChamp, passChamp, tableLogin, username, oldPassword, newPassword);
	}

	// ***************************** retourChamp
	// *********************************
	@Override
	public TreeMap<String, Object> retourChamp(String table) {
		try {
			return profil.retourChamp(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ***************************** inserer *********************************
	@Override
	public void inserer(String table, TreeMap<String, Object> champs) throws RemoteException {
		try {
			profil.inserer(table, champs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ****************************** Admin *****************************

	// ***************************** supprimerCompte
	// *********************************
	@Override
	public void supprimerCompte(String table, TreeMap<String, Object> champs) throws RemoteException {
		try {
			admin.supprimerCompte(table, champs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ****************************** Chat *****************************

	@Override
	public void updateChat(String userName, String chatMessage) throws RemoteException {
		discussion.updateChat(userName, chatMessage);

	}

	@Override
	public void passIDentity(RemoteRef ref) throws RemoteException {
		discussion.passIDentity(ref);

	}

	@Override
	public void registerListener(String[] details) throws RemoteException {
		discussion.registerListener(details);

	}

	@Override
	public void leaveChat(String userName) throws RemoteException {
		discussion.leaveChat(userName);

	}

	@Override
	public void sendPM(int[] privateGroup, String privateMessage) throws RemoteException {
		discussion.sendPM(privateGroup, privateMessage);

	}

	// ///////////////////////////////////////////////////////////////////////

	@Override
	public void uploadFileToServer(byte[] mybyte, String serverpath, int length) throws RemoteException {
		diffusionD.uploadFileToServer(mybyte, serverpath, length);

	}

	@Override
	public byte[] downloadFileFromServer(String serverpath) throws RemoteException {
		return diffusionD.downloadFileFromServer(serverpath);
	}

	@Override
	public String[] listFiles(String serverpath) throws RemoteException {
		return diffusionD.listFiles(serverpath);
	}

	@Override
	public boolean createDirectory(String serverpath) throws RemoteException {
		return diffusionD.createDirectory(serverpath);
	}

	@Override
	public boolean removeDirectoryOrFile(String serverpath) throws RemoteException {
		return diffusionD.removeDirectoryOrFile(serverpath);
	}

}