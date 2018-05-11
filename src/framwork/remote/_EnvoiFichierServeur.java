package framwork.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _EnvoiFichierServeur extends Remote {

	//********************************** _EnvoiFichierServeur *******************************
		public boolean login(_EnvoiFichierClient c) throws RemoteException;
}