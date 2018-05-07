package application;

import java.sql.SQLException;

import java.util.ArrayList;



public class Profil {
	protected ArrayList<Champ> champs; 
	
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
	 * 
	 * @return les champs de la class Profil
	 */
	public ArrayList<Champ> getChamps() {return champs;}
	
	/**
	 * 
	 * @return
	 */
	public String champToString() {
		
		/** on met les noms et les valeurs des champs du profil 
		 * dans une chaine de caracteres **/
        String s="";	
        for(int i=0; i<this.getChamps().size() ; i++) {        		
        	if(i==this.getChamps().size()-1) {
        		 s = s + this.getChamps().get(i).getNameChamp()+" = '"+this.getChamps().
        				 get(i).getValeurChamp().toString()+"'";
        	}else {
        		 s = s + this.getChamps().get(i).getNameChamp()+" = '"+this.getChamps().
        				 get(i).getValeurChamp().toString()+"' , ";
        	} 
        }
		return s;
	}
	
	/**
	 * 
	 * @return
	 */
	public String champToCondition() {
		
		/** on met les noms et les valeurs des champs du profil dans une chaine de 
		 * caracteres pour gérer la condition **/
        String s="";	
        for(int i=0; i<this.getChamps().size() ; i++) {        		
        	if(i==this.getChamps().size()-1) {
        		 s = s + this.getChamps().get(i).getNameChamp()+" = '"+this.getChamps().
        				 get(i).getValeurChamp().toString()+"'";
        	}else {
        		 s = s + this.getChamps().get(i).getNameChamp()+" = '"+this.getChamps().
        				 get(i).getValeurChamp().toString()+"' and ";
        	} 
        }
		return s;
	}
		
	/**
	 * 
	 * @param c
	 * @return 
	 * @throws Exception 
	 */
	public void modifierProfil(ArrayList<Champ> c) throws Exception {
		
		for(int i=0 ; i<c.size() ; i++) {
			for(int j=0 ; j<champs.size() ; j++) {
				if(c.get(i).getNameChamp().toUpperCase().equals(champs.get(j).getNameChamp().toUpperCase())) {
					champs.get(j).modifierValeur(c.get(i).getValeurChamp());
				}
			}
		}
	}
	
	

	
	
	

}
