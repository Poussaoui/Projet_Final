package framwork.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import javax.swing.JOptionPane;

import application.ClientRMIGUI;

import framwork.remote.Constant;
import framwork.remote._ChatClient;
import framwork.remote._ChatServer;
import framwork.remote._DiffusionDonnee;

public class Client extends UnicastRemoteObject implements _ChatClient {
	// **********************************************
	private static final long serialVersionUID = 7468891722773409712L;
	ClientRMIGUI chatGUI;
	public String hostName = "147.171.175.115:" + Constant.portNumber;
	public String serviceName = Constant.RMIName;// "GroupChatService";
	public String clientServiceName;
	public String name;
	// protected _ChatServerInterface serverIF;
	// public _ChatServer serverIF;
	public boolean connectionProblem = false;
	// **********************************************
	private Remote remote;
	protected _ChatServer serveurChat;

	public Client() throws RemoteException {
		super();

		// **********************************************
		// **********************************************
	}

	public void setRMIConnection(String host, String serviceName) throws RemoteException {
		// String[] details = { name, hostName, clientServiceName };
		try {
			/*
			 * if (System.getSecurityManager() == null) {
			 * System.setSecurityManager(new RMISecurityManager()); }
			 */
			System.setProperty("java.rmi.server.hostname", "147.171.175.115");
			Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);

			remote = Naming.lookup("rmi://" + host + "/" + serviceName);

			System.out.println(remote);
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(chatGUI.frame,
					"The server seems to be unavailable\nPlease try later",
					"Connection problem", JOptionPane.ERROR_MESSAGE);
			connectionProblem = true;
			e.printStackTrace();
		} catch (NotBoundException | MalformedURLException me) {
			connectionProblem = true;
			me.printStackTrace();
		}
		// if (!connectionProblem) {
		// registerWithServer(details);
		// }
		System.out.println("Client Listen RMI Server is running...\n");
	}

	public Remote getRemote() {
		return this.remote;
	}

	// **********************************************

	public void addClient() {
		String[] details = { name, hostName, clientServiceName };
		if (!connectionProblem) {
			registerWithServer(details);
		}
	}

	/**
	 * class constructor, note may also use an overloaded constructor with a
	 * port no passed in argument to super
	 * 
	 * @throws RemoteException
	 */
	public Client(ClientRMIGUI aChatGUI, String userName)
			throws RemoteException {
		super();
		this.chatGUI = aChatGUI;
		this.name = userName;
		this.clientServiceName = "ClientListenService_" + userName;
	}

	// /**
	// * Register our own listening service/interface lookup the server RMI
	// * interface, then send our details
	// *
	// * @throws RemoteException
	// */
	// public void startClient() throws RemoteException {
	// String[] details = { name, hostName, clientServiceName };
	//
	// try {
	// // System.setProperty("java.rmi.server.hostname", hostName);
	// Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
	// // serverIF = ( _ChatServerInterface )Naming.lookup("rmi://" +
	// // hostName + "/" + serviceName);
	//
	// serverIF = (_ChatServer) Naming.lookup("rmi://" + hostName + "/"
	// + serviceName);
	// } catch (ConnectException e) {
	// JOptionPane.showMessageDialog(chatGUI.frame,
	// "The server seems to be unavailable\nPlease try later",
	// "Connection problem", JOptionPane.ERROR_MESSAGE);
	// connectionProblem = true;
	// e.printStackTrace();
	// } catch (NotBoundException | MalformedURLException me) {
	// connectionProblem = true;
	// me.printStackTrace();
	// }
	// if (!connectionProblem) {
	// registerWithServer(details);
	// }
	// System.out.println("Client Listen RMI Server is running...\n");
	// }

	/**
	 * pass our username, hostname and RMI service name to the server to
	 * register out interest in joining the chat
	 * 
	 * @param details
	 */
	public void registerWithServer(String[] details) {
		try {
			((_ChatServer) this.remote).passIDentity(this.ref);// now redundant
																// ??
			((_ChatServer) this.remote).registerListener(details);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// =====================================================================
	/**
	 * Receive a string from the chat server this is the clients RMI method,
	 * which will be used by the server to send messages to us
	 */
	@Override
	public void messageFromServer(String message) throws RemoteException {
		System.out.println(message);
		chatGUI.textArea.append(message);
		// make the gui display the last appended text, ie scroll to bottom
		chatGUI.textArea.setCaretPosition(chatGUI.textArea.getDocument()
				.getLength());
	}

	/**
	 * A method to update the display of users currently connected to the server
	 */

	@Override
	public void updateUserList(String[] currentUsers) throws RemoteException {
		if (currentUsers.length < 2) {
			chatGUI.privateMsgButton.setEnabled(false);
		}
		chatGUI.userPanel.remove(chatGUI.clientPanel);
		chatGUI.setClientPanel(currentUsers);
		chatGUI.clientPanel.repaint();
		chatGUI.clientPanel.revalidate();
		System.out.println(Arrays.toString(currentUsers));
	}

	// **********************************************

	// to upload a file
	public void uploadFile(String clientpath, String serverpath) throws Exception {
		// String clientpath= "C:\\files\\client\\cli.txt";
		// String serverpath = "C:\\files\\server\\cli.txt";

		File clientpathfile = new File(clientpath);
		byte[] mydata = new byte[(int) clientpathfile.length()];
		FileInputStream in = new FileInputStream(clientpathfile);
		System.out.println("uploading to server...");
		in.read(mydata, 0, mydata.length);
		((_DiffusionDonnee) remote).uploadFileToServer(mydata, serverpath, (int) clientpathfile.length());

		in.close();
	}

	//
	// //to download a file
	public void downloadFile(String serverpath, String clientpath) throws Exception {
		
		 byte [] mydata = ((_DiffusionDonnee) remote).downloadFileFromServer(serverpath);
		 System.out.println("downloading...");
		 File clientpathfile = new File(clientpath);
		 FileOutputStream out=new FileOutputStream(clientpathfile);
		 out.write(mydata);
		 out.flush();
		 out.close();
	}

	// //to list all the files in a directory   pour diffusion de tous les publication
	public void listFileDir(String serverpath) throws Exception {
		serverpath = "C:\\files\\server";
		String[] filelist = ((_DiffusionDonnee) remote).listFiles(serverpath);
		for (String i : filelist)
		{
			System.out.println(i);
		}
	}

	// //to create a new directory
	public void createDir(String serverpath) throws Exception {
		boolean bool = ((_DiffusionDonnee) remote).createDirectory(serverpath);
		System.out.println("directory created :" + bool);
	}

	// //to remove/delete a directory
	public void deleteDir(String serverpath) throws Exception {
		boolean bool = ((_DiffusionDonnee) remote).removeDirectoryOrFile(serverpath);
		System.out.println("directory deleted :" + bool);
	}
}
