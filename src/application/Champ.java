package application;



public class Champ {
	 private String nom;
	 private Object valeur;
	    
	 /**
	  * 
	  * @param name
	  * @param v
	  * @param opt
	  * @throws Exception 
	  * @require val not null : opt && val.equals(null)
	  */
	 public Champ(String name, Object v) throws Exception{
	   
	    	this.nom = name;
		    this.valeur = v;
	    
	 }
	 
	    
	 /**
	  * 
	  * @return nom
	  */
	 public String getNameChamp(){return this.nom;}
	    
	 /**
	  * 
	  * @return valeur
	  */
	 public Object getValeurChamp() {return this.valeur;}
	
	 
	 /**
	  * modifie la valeur d'un champ
	  * @param val
	  */
	 public void modifierValeur(Object val) {valeur=val;}
	 
}
