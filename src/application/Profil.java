package application;

import java.sql.SQLException;

import java.util.ArrayList;



public class Profil {
	protected static ArrayList<Champ> champs; 
	
	/**
	 * 
	 * constructeur de la classe Profil
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws Exception 
	 */
	public Profil(ArrayList<Champ> c) {
		champs = new ArrayList<Champ>();
		champs = c;
	}
		
	
	/**
	 * obtenir les champs
	 * @return les champs de la class Profil
	 */
	public ArrayList<Champ> getChamps() {return Profil.champs;}
		

	/**
	 * @param nomChamp
	 * @param val
	 */
	public void modifierValeurChamp(String nom, Object val) {	
		for(int i=0;i<champs.size();i++) {
			if(champs.get(i).getNameChamp().equals(nom)) {
				champs.get(i).modifierValeur(val);
			}
		}
	}
	

}
