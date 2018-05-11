package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _Evenement extends Remote{

	// **************************_ChatClient*****************************
	
	public void messageFromServer(String message) throws RemoteException;

}
