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
	 * @throws Exception 
	 */
	public void inscription(ArrayList<Champ> ch) throws Exception {
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
	
	/****
	 * 
	 * @param table
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Profil> recherche(String table, ArrayList<Champ> c) throws Exception{
		Profil p = new Profil(c);
		ArrayList<Profil> profilsTrouvés = new ArrayList<>();
		
		ArrayList<Champ> ch = new ArrayList<>();
		
		String condition = p.champToCondition();
		String req = "select * from "+table+" where "+condition;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(req);
		
		ResultSetMetaData metadata = rs.getMetaData();
		
		while(rs.next()) {
			/** on ajoute dans la liste des champs les valeurs qui seront trouvés **/
			for(int j=1;j<=metadata.getColumnCount();j++) {
				ch.add(new Champ(metadata.getColumnName(j), rs.getObject(j)));
			}
			
			profilsTrouvés.add(new Profil(ch));
			/** on réinitilise la variable ch pour ajouter un autre profil
			 * qui sera trouvé avec la condition de recherche**/
			ch = new ArrayList<>();
		}
		
		stmt.close();
		rs.close();
		return profilsTrouvés;
		
	}
	
	/**
	 * 
	 * @param table
	 * @param p
	 * @throws SQLException 
	 */
	public void updateProfil(String table, Profil p, String condition) throws SQLException {
		Statement stmt = conn.createStatement();
		String newChamp=p.champToString();
        String requete = "UPDATE "+table+ " SET " + newChamp + " WHERE "+condition;
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
	
	
	public void supprimerProfil(String table, Profil p) throws SQLException {
		
		Statement stmt = conn.createStatement();
		
		String condition = p.champToCondition();
		
		String req = "delete from "+table+" where "+condition;
		
		System.out.println(req);
		
		try {
			/** suppression du profil dans la BD **/
			stmt.executeUpdate(req);
		    System.out.println("Profil supprimé");
		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}
		stmt.close();		
	}
	
	
}
        	
