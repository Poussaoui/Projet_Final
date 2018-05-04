package rmi.server;
import java.util.ArrayList;
import java.util.TreeMap;

import application.Champ;
import application.Services;


public class ServerDatabase {
	private TreeMap<String, String> loginInfo;
	private TreeMap<String, Object> admin;
	
	public ServerDatabase ()
	{
		loginInfo = new TreeMap<String, String>();
		loginInfo.put("admin", "admin");
		admin = new TreeMap<String, Object>();
		admin.put("admin", null);
	}//constructor
	
	public boolean authenticateLogin (String username, String password)
	{
		if (!loginInfo.containsKey(username))
			return false;
		else 
		{
			String correctPassword = loginInfo.get(username);
			if (correctPassword.equals(password))
				return true;
			else
				return false;
		}//else
	}//authenticateLogin
	
	public boolean createUser (String username, String password)
	{
		
		try {
		Champ champUsername = new Champ("username", username);
		Champ champPass = new Champ("password", password);
		
		ArrayList<Champ> champs = new ArrayList<Champ>();
		champs.add(champUsername);
		champs.add(champPass);
		new Services().inserer("Profil",champs);
		}
		catch (Exception e){
			e.getMessage();
		}
//		if (loginInfo.containsKey(username))
//			return false;
//		else 
//		{
//			loginInfo.put(username, password);
//			return true;
//		}//else	
		return false;
	}//createUser
	
	public boolean changePassword (String username, String password)
	{
		if (!loginInfo.containsKey(username))
			return false;
		else 
		{
			loginInfo.put(username, password);
			return true;
		}//else
	}//changePassword

	public boolean isAdmin (String username)
	{
		return admin.containsKey(username);
	}//isAdmin
	
}//class
