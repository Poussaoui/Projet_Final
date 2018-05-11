package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _ChatClient extends Remote{

	// **************************_ChatClient*****************************
	
	public void messageFromServer(String message) throws RemoteException;

	public void updateUserList(String[] currentUsers) throws RemoteException;
	
}