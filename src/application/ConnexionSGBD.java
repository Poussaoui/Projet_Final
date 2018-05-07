package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class ConnexionSGBD {

    private static final String configurationFile = "BD.properties";

    public static void main(String args[]) throws Exception,SQLException {
        try {
            String jdbcDriver, dbUrl, username, password;
            DatabaseAccessProperties dap = new DatabaseAccessProperties(configurationFile);
            jdbcDriver = dap.getJdbcDriver();
            dbUrl = dap.getDatabaseUrl();
            username = dap.getUsername();
            password = dap.getPassword();
            // Load the database driver
            Class.forName(jdbcDriver);// Get a connection to the database
            try (Connection conn = DriverManager.getConnection(dbUrl, username, password)) {
            	//requetesbd.nbseminaire(conn);
            	
            	String table="profil";
            	Services s = new Services();
            	
            	ArrayList<Champ> champs = s.retourChamp(table);
          	
            	s.inscription(champs);
            	
            	Profil p = new Profil(champs);
            	
            	System.out.println("hoooo");
            	
            	s.inserer(table, champs);
            	
            	System.out.println("ça marche");
            	
            	ArrayList<Champ> c = new ArrayList<>();
            	
            	/** on veut modifier le champ PASS **/
            	c.add(new Champ("PASS", "yooo"));
            	
            	/** on modifie le mot de passe du profil créé **/
            	ArrayList<Champ> oldChamp = p.modifierProfil(c);
            	/** pour mettre à jour dans la table **/
            	s.updateProfil(table, p, oldChamp);
            	
            	System.out.println("hehehe");
            	
                // Print information about connection warnings
                SQLWarningsExceptions.printWarnings(conn);
            }
        } catch (SQLException se) {
            // Print information about SQL exceptions
            SQLWarningsExceptions.printExceptions(se);
        } catch (ClassNotFoundException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
