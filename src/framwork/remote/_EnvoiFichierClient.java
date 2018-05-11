package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _EnvoiFichierClient extends Remote {

	public boolean sendData(String filename, byte[] data, int len) throws RemoteException;
}