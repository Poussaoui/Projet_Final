package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.TreeMap;

public interface _Admin extends Remote {
	// **************************_Admin*****************************
	// public boolean ajouterAdmin(String loginChamp, String passChamp,
	// String tableLogin, String username, String password)
	// throws RemoteException;

	// ************ supprission a la base de donnees ************
	public void supprimerCompte(String table, TreeMap<String, Object> champs)
			throws RemoteException;

	//
	// public boolean bloqueCommpte(String loginChamp, String passChamp,
	// String tableLogin, String username, String password)
	// throws RemoteException;
	//
	// public boolean validerPublication(String loginChamp, String passChamp,
	// String tableLogin, String username, String password)
	// throws RemoteException;
	
	

}
