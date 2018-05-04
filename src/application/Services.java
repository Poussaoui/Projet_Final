package application;

import java.util.*;

import application.connection.ConnexionSGBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Services {
	private ConnexionSGBD dbOracle;
    private Connection conn;
    

	public Services() throws ClassNotFoundException, SQLException {		
		dbOracle = new ConnexionSGBD();
        conn = dbOracle.connexion();
	}


	public void inserer(String table,ArrayList<Champ> champs) throws Exception, SQLException   {

        Statement stmt = conn.createStatement();
        
        ArrayList<String> noms = new ArrayList<>();
        ArrayList<String> nomsChamps = new ArrayList<>();
        
        /** récupère le nom de tous les champs pour vérifier que la table a les mêmes*/
        for(int i=0;i<champs.size();i++) {
        	nomsChamps.add(champs.get(i).getNameChamp().toUpperCase());
        }
        
        
        String sql = "SELECT * FROM "+table; 
		
		ResultSet resultat = stmt.executeQuery(sql);
		
		ResultSetMetaData metadata = resultat.getMetaData(); 
		
		/** on récupère le noms des différents champs
	   	 * getColumnCount() donne le nombre de colonnes **/
	   	for(int i=1;i<=metadata.getColumnCount();i++) {
	   		noms.add(metadata.getColumnName(i).toUpperCase());
	   	}
        
        /** on vérifie si les champs sont identiques avant de faire l'insertion **/
        for(int i=0; i < noms.size();i++) {
        	if(!(noms.get(i).equals(nomsChamps.get(i)))) throw new 
        		Exception("Les champs donnés sont différents de ceux de la table");
        }
            
        /** on insère les valeurs dans la table pour chaque champ **/
        String s="";	
        for(int i=0; i<champs.size() ; i++) {        		
        	if(i==champs.size()-1) {
        		 s=s +"'"+champs.get(i).getValeurChamp().toString()+"'";
        	}else {
        		 s=s +"'"+champs.get(i).getValeurChamp().toString()+"'"+",";
        	} 
        }
		String requete = "INSERT INTO "+table+" VALUES("+s+")";
		System.out.println(requete);    
		try {
			stmt.executeUpdate(requete);
		    System.out.println("Ajout réussi");
		    } catch (Exception e) {
		        System.out.println("ERROR " + e.getMessage());
		}
      
    }
	
public ArrayList<Champ> retourChamp(String table) throws Exception,SQLException{
		
		Statement stmt = conn.createStatement();
		
		ArrayList<String> nomsChamps = new ArrayList<>();
		ArrayList<Champ> champ = new ArrayList<>();
		
		/** récupère les champs de la table **/
		
		String sql = "SELECT * FROM "+table; 
		
		ResultSet resultat = stmt.executeQuery(sql); 
		
		System.out.println(sql);
		
	    ResultSetMetaData metadata = resultat.getMetaData(); 
	    
	   	/** on récupère le noms des différents champs
	   	 * getColumnCount() donne le nombre de colonnes **/
	   	for(int i=1;i<=metadata.getColumnCount();i++) {
	   		nomsChamps.add(metadata.getColumnName(i).toUpperCase());
	   	}
		
	    /** on recupere les types java des différents champs **/
	    
	    for(int i = 1; i<= metadata.getColumnCount(); i++){ 
     
	    	String typeJava = metadata.getColumnClassName(i); 
	    	switch (typeJava) {
				case "java.math.BigDecimal":
					Integer d = Integer.MIN_VALUE;
					champ.add(new Champ(nomsChamps.get(i-1),d));
					break;
				case "java.lang.String":
					String s ="yes";
					champ.add(new Champ(nomsChamps.get(i-1),s));				
					break;
				case "java.lang.Boolean":					
					champ.add(new Champ(nomsChamps.get(i-1),true));
					break;
				case "java.sql.Timestamp":
					champ.add(new Champ(nomsChamps.get(i-1),new Date()));					
					break;				
				default:
					break;
			}
	  	       
	    }
	    stmt.close();
	    resultat.close();
		return champ;				
	}
	
	public void inscription(ArrayList<Champ> ch) {
		Scanner sc;
		for(int i = 0; i<ch.size() ; i++) {
			System.out.println("Donnez une valeur pour "+ch.get(i).getNameChamp());
			sc = new Scanner(System.in);
			/** Si le type est une String **/
			if (ch.get(i).getValeurChamp() instanceof String){
				
				ch.get(i).modifierValeur(sc.nextLine());
				
			}/** Si le type est un Double **/
			else if(ch.get(i).getValeurChamp() instanceof Integer) {
				
				ch.get(i).modifierValeur(sc.nextInt());
				
			}/** Si le type est un booléen **/
			else if(ch.get(i).getValeurChamp() instanceof Boolean) {	
				
				ch.get(i).modifierValeur(sc.nextBoolean());
			
			}/** Si le type est une date **/
			else {
		        String date = sc.nextLine();
		        DateFormat df = new SimpleDateFormat("dd-MM-yy");
		        @SuppressWarnings("unused")
				Date d = null;
		        try {
		            d = df.parse(date);
		        } catch (ParseException e) {
		            System.out.println("Impossible de parser "
		                    + date
		                    + ". Veuillez entrer la date au format JJ-MM-AA");
		            System.out.println("message" + e.getMessage());
		        }
		        ch.get(i).modifierValeur(date);		        
		        
			}		
		}
		
		
		
	}
	
	
}
        	
