package application;


public class Champ {
	 private String nom;
	 private Object valeur;
	    
	 /**
	  * 
	  * @param name
	  * @param v
	  * @throws Exception 
	  * @require val_not_null : val.equals(null)
	  * @ensure nom_valeur : this.nom == name && this.valeur == v
	  */
	 public Champ(String name, Object v) throws Exception{
		 	if( name.equals(null) )
		 		{ throw new Exception("val_not_null");}
		 	
	    	this.nom = name;
		    this.valeur = v;
		    
		    if( !(this.nom == name && this.valeur == v) )
		    	{ throw new Exception("nom_valeur");}

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
	  * @ensure valCorrect : valeur ==  val
	  */
	 public void modifierValeur(Object val) throws Exception{
		 	valeur=val;
		 	if(!(valeur ==  val))
		 		{ throw new Exception("nom_valeur");}
	 }
	 
}
