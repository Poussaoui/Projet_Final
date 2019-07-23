package rmi.remoteInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteInterface extends Remote {
	
	public boolean authenticateLogin (String username, String password) throws RemoteException;
	
	public boolean createUser (String username, String password) throws RemoteException;
	
	public boolean changePassword (String username, String password) throws RemoteException;
	
	public boolean isAdmin (String username) throws RemoteException;
	
	public void reagit(int idEtudiant, int idPublication, String rea)throws RemoteException;
	
	public void afficherReactionById(int idReaction)throws RemoteException;
	
	public void afficherReactionByIdPers(int idReaction)throws RemoteException;
	
	public void afficherReactionByIdPub(int idReaction)throws RemoteException;
	
	public void modifierReaction(int idReaction)throws RemoteException;
	
	public void retirerReaction(int idReaction)throws RemoteException;
	
	public int nbReactions()throws RemoteException;
}
