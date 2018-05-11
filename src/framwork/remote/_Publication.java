package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _Publication extends Remote {
	// **************************_Utilisateur*****************************
	public boolean commenter(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

	public boolean affichierCommentaire(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

	public boolean affichierTousLesCommentaire(String loginChamp,
			String passChamp, String tableLogin, String username,
			String password) throws RemoteException;

	public boolean supprimerCommentaire(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

	public boolean modifierCommentaire(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

	public boolean nbCommentaire(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

}
