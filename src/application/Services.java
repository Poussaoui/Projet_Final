package application;

import java.util.*;

import application.connection.ConnexionSGBD;

import java.rmi.RemoteException;
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
	//************************************************Reaction************************************************************************
	
	/**
	 * 
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void stocker() throws RemoteException, SQLException {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("Insert into Admin values(511,'Lee','login','yee','yeelee.vip@gmail.com')");
			stmt.executeUpdate("Insert into Diffuseur values(611,'yee','lee','passdif','mail','doimaine','bloqué')");
			stmt.executeUpdate("Insert into Etudiant values(111,'LI','Yi','yee','12345','0651614','yeelee.vip@gmail.com','metier','CDI','bac+3','aucune','bloqué')");
			stmt.executeUpdate("Insert into Publication values(211,'titre','motcle','typePub','textSaisi','chemin',511,611)");			
		}catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
		}
	}
	/**
	 * ajouter une lingne dans Une relation
	 * @param relation  nom de la relation
	 * @param idPublication 
	 * @param idEtudiant
	 * @param idReaction
	 * @param rea reaction
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void reagit(String relation,  int idPublication, int idEtudiant,int idReaction, String rea) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
		//inserer une ligne dans bd
		try {
		        Statement stmt = conn.createStatement();
		        //String insertReaction = "Insert into "+ relation + " values(" + idPublication + "," + idEtudiant  + "," + idReaction + "," + "'" +rea+"'"+");";
		        //System.out.println(insertReaction);
		        stmt.executeUpdate("Insert into "+ relation + " values(" + idPublication + "," + idEtudiant  + "," + idReaction + "," + "'" +rea+"'"+")");
		        System.out.println("bon");
		} catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
		}
	}
	
	/**
	 * afficher les reactions par Id de la relation
	 * @param relation
	 * @param idReaction
	 * @return une liste qui comprend les attributs de charque reaction
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public ArrayList<String> afficherReactionById(String relation, int idReaction) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		//String re = " ";
		ArrayList<String> re = new ArrayList<String>();
		//select * form bd where idReaction = idReaction;
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + relation + " where idReaction =" + idReaction)){
		while (rs.next()) {
				//re = " " + rs.getString(4);
				re.add(String.valueOf(rs.getInt(1)));
				re.add(String.valueOf(rs.getInt(2)));
				re.add(String.valueOf(rs.getInt(3)));
				re.add(rs.getString(4));
			}
		} 
		
		return re;
	}

	/**
	 * afficher les reactions par Id de personne
	 * @param relation
	 * @param idEtd
	 * @return une liste qui comprend les attributs de  reaction
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public ArrayList<String> afficherReactionByIdPers(String relation, int idEtd) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		//String re = " ";
		ArrayList<String> re = new ArrayList<String>();
		//select * form bd where idReaction = idReaction;
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + relation + " where noEtd =" + idEtd)){
		while (rs.next()) {
				//re = " " + rs.getString(4);
				re.add(String.valueOf(rs.getInt(1)));
				re.add(String.valueOf(rs.getInt(2)));
				re.add(String.valueOf(rs.getInt(3)));
				re.add(rs.getString(4));
			}
		} 
		
		return re;
	}
	
	/**
	 * afficher les reaction par id de publication
	 * @param relation
	 * @param idPub
	 * @return une liste qui comprend les attributs de reaction
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public ArrayList<String> afficherReactionByIdPub(String relation, int idPub) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		//String re = " ";
		ArrayList<String> re = new ArrayList<String>();
		//select * form bd where idReaction = idReaction;
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + relation + " where idPub =" + idPub)){
		while (rs.next()) {
				//re = " " + rs.getString(4);
				re.add(String.valueOf(rs.getInt(1)));
				re.add(String.valueOf(rs.getInt(2)));
				re.add(String.valueOf(rs.getInt(3)));
				re.add(rs.getString(4));
			}
		} 
		
		return re;
	}
	
	/**
	 * modifier une reaction
	 * @param relation
	 * @param idEtudiant
	 * @param idPub
	 * @param rea reaction
	 * @param idReaction
	 * @throws RemoteException
	 */
	public void modifierReaction(String relation, int idEtudiant, int idPub, String rea, int idReaction) throws RemoteException {
		// TODO Auto-generated method stub
		//delete une ligne where id = idReaction
		//inserer une linge where id = idReaction
		try {
	        Statement stmt = conn.createStatement();
	        //String insertReaction = "insert into "+ relation + "values (" + idEtudiant + "," + idPub + "," + rea + ")";
	        String deleteReaction = "delete from " + relation + " where idReaction = " + idReaction;
	        stmt.executeQuery(deleteReaction);
	        this.reagit("Reaction", idPub, idEtudiant, idReaction, rea);
	        //stmt.executeUpdate(insertReaction);
		} catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
	}
	}

	/**
	 * supprime une reaction
	 * @param relation
	 * @param idReaction
	 * @throws RemoteException
	 */
	public void retirerReaction(String relation, int idReaction) throws RemoteException {
		// TODO Auto-generated method stub
		//delete une ligne where id = idReaction
		try {
	        Statement stmt = conn.createStatement();	       
	        String deleteReaction = "delete from " + relation + " where idReaction = " + idReaction;
	        stmt.executeQuery(deleteReaction);	      
		} catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
	}
	}

	/**
	 * nombre de reaction
	 * @param relation
	 * @return nombre de reaction
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public int nbReactions(String relation) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		// select count (*) from bd;
		int nbReactions = 1;
        try (// Get a statement from the connection
                Statement stmt = conn.createStatement(); // Execute the query

                ResultSet rc = stmt.executeQuery("select count(*) from " + relation)) {

            while (rc.next()) {
                nbReactions = rc.getInt(1);                   
                //System.out.println("nbParticipants: " + nbParticipants + " ");

            }
        }
        return nbReactions;		
       
	}
	
	//*************************************************Commentaire*****************************************************************
	/**
	 * ajouter une commentaire dans la relation Commentaire
	 * @param relation
	 * @param idPublication
	 * @param idEtudiant
	 * @param idCommentaire
	 * @param comm
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void commenter(String relation,  int idPublication, int idEtudiant,int idCommentaire, String comm) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
		//inserer une ligne dans bd
		try {
		        Statement stmt = conn.createStatement();
		        String insertReaction = "Insert into "+ relation + " values (" + idPublication + "," + idEtudiant  + "," + idCommentaire + ",'" + comm + "')";
		        System.out.println(insertReaction);
		        stmt.executeUpdate(insertReaction);
		} catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
		}
	}
	
	/**
	 * affichier les commentaires par id de commentaire
	 * @param relation
	 * @param idComm
	 * @throws RemoteException
	 */
	public void afficherCommentaireById(String relation, int idComm) throws RemoteException {
		// TODO Auto-generated method stub
		//select * form bd where idReaction = idReaction;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + relation + "where idReaction=" + idComm);
			while (rs.next()) {
				rs.getInt(3);
			}
		} catch (Exception e) {
	        SQLWarningsExceptions.printExceptions((SQLException) e);
	        System.err.println("Got an exception!"); 
	    }
	}
	/**
	 * supprimer une commentaire
	 * @param relation
	 * @param idCommentaire
	 * @throws RemoteException
	 */
	public void retirerCommentaire(String relation, int idCommentaire) throws RemoteException {
		// TODO Auto-generated method stub
		//delete une ligne where id = idReaction
		try {
	        Statement stmt = conn.createStatement();	       
	        String deleteReaction = "delete from " + relation + "where idRalation = " + idCommentaire;
	        stmt.executeQuery(deleteReaction);	      
		} catch (Exception e) {
			SQLWarningsExceptions.printExceptions((SQLException) e);
		    System.err.println("Got an exception!"); 
		}
	}
	/**
	 * nombre de commentaires
	 * @param relation
	 * @return la nombre de commentaire
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public int nbCommentaire(String relation) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		// select count (*) from bd;
		int nbReactions = 0;
        try (// Get a statement from the connection
                Statement stmt = conn.createStatement(); // Execute the query

                ResultSet rc = stmt.executeQuery("select count(*) from " + relation)) {

            while (rc.next()) {
                nbReactions = rc.getInt(1);                   
                //System.out.println("nbParticipants: " + nbParticipants + " ");

            }
        }
        return nbReactions;		
       
	}
	
	
}
        	
