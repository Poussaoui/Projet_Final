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
    

    /**
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public Services() throws ClassNotFoundException, SQLException {		
		dbOracle = new ConnexionSGBD();
        conn = dbOracle.connexion();
	}

	/**
	 * 
	 * @param table
	 * @param champs
	 * @throws Exception
	 * @throws SQLException
	 */
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
            
        /** on met les valeurs des champs dans une chaine de caracteres **/
        String s="";	
        for(int i=0; i<champs.size() ; i++) {        		
        	if(i==champs.size()-1) {
        		 s=s +"'"+champs.get(i).getValeurChamp().toString()+"'";
        	}else {
        		 s=s +"'"+champs.get(i).getValeurChamp().toString()+"'"+",";
        	} 
        }
        /** on insere les valeurs dans la table sachant qu'elles sont dans la chaine s **/
		String requete = "INSERT INTO "+table+" VALUES("+s+")";
		System.out.println(requete);    
		try {
			stmt.executeUpdate(requete);
		    System.out.println("Ajout réussi");
		    } catch (Exception e) {
		        System.out.println("ERROR " + e.getMessage());
		}
		stmt.close();
		resultat.close();
    }
	
	/**
	 * 
	 * @param table
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public ArrayList<Champ> retourChamp(String table) throws Exception,SQLException{
			
		Statement stmt = conn.createStatement();
		/** une variable pour contenir le nom des colonnes de la table **/ 	
		ArrayList<String> nomsChamps = new ArrayList<>();
		/** une variable pour contenir les champs qui seront créés 
		 * avec des valeurs par défaut **/
		ArrayList<Champ> champ = new ArrayList<>();			
		
		String sql = "select * from " + table; 
		
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
	    	/** getColumnClassName() renvoie le nom de la classe java **/
	    	String typeJava = metadata.getColumnClassName(i); 
	    	switch (typeJava) {
				case "java.math.BigDecimal":					
					Integer d = Integer.MIN_VALUE;
					/** la variable d est celle par défaut du champ correspondant **/
					champ.add(new Champ(nomsChamps.get(i-1),d));
					break;
				case "java.lang.String":
					String s ="yes";
					/** la variable s est celle par défaut du champ correspondant **/
					champ.add(new Champ(nomsChamps.get(i-1),s));				
					break;
				case "java.lang.Boolean":		
					/** si le champ est un booleen, alors la valeur par défaut est true **/
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
	
	/**
	 * 
	 * @param ch
	 */
	public void inscription(ArrayList<Champ> ch) {
		Scanner sc;
		for(int i = 0; i<ch.size() ; i++) {
			System.out.println("Donnez une valeur pour "+ch.get(i).getNameChamp());
			sc = new Scanner(System.in);
			/** Si le type est une String **/
			if (ch.get(i).getValeurChamp() instanceof String){

				
				try {
					ch.get(i).modifierValeur(sc.nextLine());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}/** Si le type est un Double **/
			else if(ch.get(i).getValeurChamp() instanceof Integer) {
				
				try {
					ch.get(i).modifierValeur(sc.nextInt());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}/** Si le type est un booléen **/
			else if(ch.get(i).getValeurChamp() instanceof Boolean) {	
				
				try {
					ch.get(i).modifierValeur(sc.nextBoolean());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
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

		        try {
					ch.get(i).modifierValeur(date);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		        
		        
			}		
		}	
	}
	
	/** pas terminé
	 * public ArrayList<Profil> recherche(String table, Champ c) throws SQLException{
		ArrayList<Profil> p = new ArrayList<>();
		String req = "select * from "+table+" where "+c.getNameChamp()+" = "+c.getValeurChamp();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(req);
		
		ResultSetMetaData metadata = rs.getMetaData();
		while(rs.next()) {
			for(int j=1;j<=metadata.getColumnCount();j++) {
				
			}
		}
		
		stmt.close();
		rs.close();
		return null;
		
	}**/
	
	/**
	 * 
	 * @param table
	 * @param p
	 * @throws SQLException 
	 */
	public void updateProfil(String table, Profil p, ArrayList<Champ> oldChampProfil) throws SQLException {
		Statement stmt = conn.createStatement();
		/** on déclare une variable pour contenir les champs du profil p **/
		ArrayList<Champ> c = new ArrayList<>();
		c=p.getChamps();
		
		/** on met les noms et les valeurs des champs dans une chaine de 
		 * caracteres pour faciliter la requete sql **/
        String s1="";	
        for(int i=0; i<c.size() ; i++) {        		
        	if(i==c.size()-1) {
        		 s1 = s1 + c.get(i).getNameChamp()+" = '"+c.get(i).getValeurChamp().toString()+"'";
        	}else {
        		 s1 = s1 + c.get(i).getNameChamp()+" = '"+c.get(i).getValeurChamp().toString()+"',";
        	} 
        }
        
        /** on met les noms et les valeurs des anciens champs du profil dans une chaine de 
		 * caracteres pour gérer la condition pour la mise à jour **/
        String s2="";	
        for(int i=0; i<c.size() ; i++) {        		
        	if(i==c.size()-1) {
        		 s2 = s2 + c.get(i).getNameChamp()+" = '"+c.get(i).getValeurChamp().toString()+"'";
        	}else {
        		 s2 = s2 + c.get(i).getNameChamp()+" = '"+c.get(i).getValeurChamp().toString()+"' and ";
        	} 
        }
        
        String requete = "UPDATE "+table+ " SET "+s1+" WHERE "+s2;
        /** affichage de la requete **/
        System.out.println(requete);
        try {
        	/** mise à jour dans la table **/
			stmt.executeUpdate(requete);
		    System.out.println("Modification réussie");
		    } catch (Exception e) {
		        System.out.println("ERROR " + e.getMessage());
		}
        stmt.close();
	}
	
	
	
}
        	
