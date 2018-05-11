package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _Utilisateur extends Remote {
	// **************************_Utilisateur*****************************
	public boolean signaler(String loginChamp, String passChamp,
			String tableLogin, String username, String password)
			throws RemoteException;

}
