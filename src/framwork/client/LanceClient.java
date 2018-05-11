package framwork.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import framwork.remote._Admin;
import framwork.remote._Profil;

public class LanceClient {

	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws RemoteException {
		System.out.println("Lancement du client");
		
		Client clint = new Client();
		clint.setRMIConnection("147.171.175.111", "ReseauSocial");
		
		authenticationTest(clint.getRemote());

		TreeMap<String, Object> tmap = new TreeMap<String, Object>();
	     /*Adding elements to TreeMap*/
	     tmap.put("LOGIN", "yoyo");
	     tmap.put("PASS", "yoyo");
		//supprissionTest(clint.getRemote(), tmap);
		

//		try {
//
//			Remote r = Naming.lookup("rmi://147.171.175.89/ReseauSocial");
//			System.out.println(r);
//
//			runClientCode(r);
//			
//			if (r instanceof _Profil) {
//				//String s = ((_Profil) r).getProfilType("LOGIN", "TYPE","PROFIL", "med");
//				//inscriptionTest((_Profil) r);
//				//System.out.println("chaine renvoyee = " + s);
//				runClientCode((_Profil) r);
//			}

			// if (r instanceof _EnvoiFichierServeur) {
			// receveFile((_EnvoiFichierServeur) r);
			// System.out.println(" Transfert Done ! ");
			// }

//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		System.out.println("Fin du client");
	}
	

	// TreeMap<String, Object> champs = new TreeMap<String, Object>();

	// ************ demande a l'utilisateur de remplir les champs ************
	public static TreeMap<String, Object> inscriptionFormulConsol(
			TreeMap<String, Object> champs) { // ArrayList<Champ> ch
		Scanner sc;
		for (Map.Entry<String, Object> entry : champs.entrySet()) {
			System.out.println("Donnez une valeur pour " + entry.getKey());
			sc = new Scanner(System.in);
			/** Si le type est une String **/
			if (entry.getValue() instanceof String) {
				entry.setValue(sc.nextLine());
			}/** Si le type est un Double **/
			else if (entry.getValue() instanceof Integer) {
				entry.setValue(sc.nextInt());

			}/** Si le type est un booléen **/
			else if (entry.getValue() instanceof Boolean) {
				entry.setValue(sc.nextBoolean());

			}/** Si le type est une date **/
			else {
				String date = sc.nextLine();
				DateFormat df = new SimpleDateFormat("dd-MM-yy");
				@SuppressWarnings("unused")
				Date d = null;
				try {
					d = df.parse(date);
				} catch (ParseException e) {
					System.out.println("Impossible de parser " + date
							+ ". Veuillez entrer la date au format JJ-MM-AA");
					System.out.println("message" + e.getMessage());
				}
				entry.setValue(date);
			}
		}
		return champs;

	}

	public static void inscriptionTest(_Profil myServer)
			throws RemoteException {
		try {
			String table = "profil";

			TreeMap<String, Object> champs = new TreeMap<String, Object>();
			champs = myServer.retourChamp(table);
			myServer.inserer(table, inscriptionFormulConsol(champs));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void authenticationTest(Remote r)
			throws RemoteException {
		String username;
		String password;
		boolean isAdmin;
		int option;

		System.out.println("Welcome! Please enter your username and password!");
		System.out.print("Username: ");
		username = input.next();
		System.out.print("Password: ");
		password = input.next();

		if (!((_Profil) r).authentication("login", "pass", "profil", username,password)) {
			System.out
					.println("\nLogin unsuccessful! Your credentials are invalid.\n");
			return;
		}// if

//		isAdmin = true; // myServer.isAdmin(username);
		String typeUser = ((_Profil) r).getProfilType("login", "typep", "profil", username);
		String welcome = "\nWelcome ";
//		welcome += (isAdmin) ? "Administrator!" : "User!";
		System.out.println(welcome + " : " + typeUser);
	}
	
	public static void supprissionTest(Remote r,TreeMap<String, Object> champs) throws RemoteException {
		try {
			String table = "profil";

			((_Admin) r).supprimerCompte(table, champs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
