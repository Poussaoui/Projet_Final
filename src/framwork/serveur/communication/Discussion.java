package framwork.serveur.communication;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.util.Date;
import java.util.Vector;

import framwork.remote._ChatClient;

public class Discussion {
	String line = "---------------------------------------------\n";
	private Vector<Chatter> chatters;

	public Discussion() {
		chatters = new Vector<Chatter>(10, 1);
	}

	/**
	 * Return a message to client
	 */
	public String sayHello(String ClientName) throws RemoteException {
		System.out.println(ClientName + " sent a message");
		return "Hello " + ClientName + " from group chat server";
	}

	/**
	 * Send a string ( the latest post, mostly ) to all connected clients
	 */
	public void updateChat(String name, String nextPost) throws RemoteException {
		String message = name + " : " + nextPost + "\n";
		sendToAll(message);
	}

	/**
	 * Receive a new client remote reference
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 */

	public void passIDentity(RemoteRef ref) throws RemoteException {
		// System.out.println("\n" + ref.remoteToString() + "\n");
		try {
			System.out.println(line + ref.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end passIDentity

	/**
	 * Receive a new client and display details to the console send on to
	 * register method
	 */

	public void registerListener(String[] details) throws RemoteException {
		System.out.println(new Date(System.currentTimeMillis()));
		System.out.println(details[0] + " has joined the chat session");
		System.out.println(details[0] + "'s hostname : " + details[1]);
		System.out.println(details[0] + "'sRMI service : " + details[2]);
		registerChatter(details);
	}

	/**
	 * stocker register de client en référence pour des messages futurs à être envoyé,
	 *  C'EST-À-DIRE les messages de la session de chat d'autres membres. 
	 	puis envoyer un message de test pour la confirmation/la connexion de test
	 * 
	 * @param details
	 */
	private void registerChatter(String[] details) {
		try {

			_ChatClient nextClient = (_ChatClient) Naming.lookup("rmi://"+ details[1] + "/" + details[2]);

			chatters.addElement(new Chatter(details[0], nextClient));

			nextClient.messageFromServer("[Server] : Bonjour !, " + details[0]
					+ " vous êtes maintenant libre de discuter.\n");

			sendToAll("[Server] : " + details[0] + " vient de rejoindre Messenger !.\n");

			updateUserList();
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metter à jour tous les clients en invoquant à distance leur updateUserList rmi methode
	 */
	private void updateUserList() {
		String[] currentUsers = getUserList();
		for (Chatter c : chatters) {
			try {
				c.getClient().updateUserList(currentUsers);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Produire un tableau des utilisateurs actuels
	 * 
	 * @return
	 */
	private String[] getUserList() {
		String[] allUsers = new String[chatters.size()];
		for (int i = 0; i < allUsers.length; i++) {
			allUsers[i] = chatters.elementAt(i).getName();
		}
		return allUsers;
	}

	/**
	 * Envoyer message a tous les utilisateurs
	 * 
	 * @param newMessage
	 */
	public void sendToAll(String newMessage) {
		for (Chatter c : chatters) {
			try {
				c.getClient().messageFromServer(newMessage);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Enlever un client de la liste, et notifier tout les clients
	 */
	public void leaveChat(String userName) throws RemoteException {

		for (Chatter c : chatters) {
			if (c.getName().equals(userName)) {
				System.out.println(line + userName + " left the chat session");
				System.out.println(new Date(System.currentTimeMillis()));
				chatters.remove(c);
				break;
			}
		}
		if (!chatters.isEmpty()) {
			updateUserList();
		}
	}

	/**
	 * Une méthode d'envoyer un message privé aux clients choisis le tableau
	 * d'entier tient les index(indices) des clients envoyer et message
	 */
	public void sendPM(int[] privateGroup, String privateMessage)
			throws RemoteException {
		Chatter pc;
		for (int i : privateGroup) {
			pc = chatters.elementAt(i);
			pc.getClient().messageFromServer(privateMessage);

		}
	}
}
