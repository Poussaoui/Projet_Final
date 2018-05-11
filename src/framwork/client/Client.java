package framwork.client;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import framwork.remote._ChatServer;

public class Client {

	private Remote remote;
	protected _ChatServer serveurChat;
	
	public Client() {

	}

	public void setRMIConnection(String host, String serviceName) {
		try {
			/*
			 * if (System.getSecurityManager() == null) {
			 * System.setSecurityManager(new RMISecurityManager()); }
			 */

			remote = Naming.lookup("rmi://" + host + "/" + serviceName);
			System.out.println(remote);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Remote getRemote() {
		return this.remote;
	}
	
}
