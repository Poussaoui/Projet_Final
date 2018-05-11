package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _Reaction extends Remote {
	// **************************_Reaction*****************************
	public boolean reagir(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

	public boolean affichierToutesLesReaction(String loginChamp,
			String passChamp, String tableLogin, String username,
			String password) throws RemoteException;

	public boolean affichierReactionParIdPers(String loginChamp,
			String passChamp, String tableLogin, String username,
			String password) throws RemoteException;

	public boolean affichierReactionParIdPub(String loginChamp,
			String passChamp, String tableLogin, String username,
			String password) throws RemoteException;

	public boolean modifierReaction(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

	public boolean retirerReaction(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

	public boolean nbReaction(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

}
