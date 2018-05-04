package application;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class requetesbd {

    public static void nbseminaire(Connection conn) throws
            SQLException {

      
/* Execute the query
        ResultSet rs = stmt.executeQuery("SELECT idSem FROM inscription ");
        while (rs.next()) {
            System.out.println("Nombre de inscription : "
                    + rs.getInt(1));
        }
 Close the result set, statement and theconnection 
        rs.close();
        stmt.close();
    }*/

   /* String sql = "SELECT * FROM prestataire"; 
 // Get a statement from the connection
    Statement statement = conn.createStatement(); 
    ResultSet resultat = statement.executeQuery(sql); 
    ResultSetMetaData metadata = resultat.getMetaData(); 
    for(int i = 0; i< metadata.getColumnCount(); i++){ 
    int index = i+1; 
    int typeSQL = metadata.getColumnType(index);  
    String nomTypeSQL = metadata.getColumnTypeName(index);  
    String typeJava = metadata.getColumnClassName(index); 
       System.out.println("INFORMATIONS SUR LA COLONNE D'INDEXE "+index); 
       System.out.println("Type SQL dans java.sql.Types : "+typeSQL); 
       System.out.println("Nom du type SQL : "+nomTypeSQL); 
       System.out.println("Classe java correspondante : "+typeJava); 
    }
    	
    resultat.close();
    statement.close();*/
    	
    	
    	 // Get a statement from the connection
        Statement stmt = conn.createStatement(); 
    	String req="select * from profil";
    	 
    	 
    	 ResultSet rs = stmt.executeQuery(req);
    	 ResultSetMetaData rsmd = rs.getMetaData();
    	 System.out.println("No. of columns : " + rsmd.getColumnCount());
    	 for(int i=1;i<=rsmd.getColumnCount();i++) {
    		 System.out.println("Column name of 1st column : " + rsmd.getColumnName(i));
        	 System.out.println("Column type of 1st column : " + rsmd.getColumnTypeName(i));
        	 System.out.println("Column type java of 1st column : " + rsmd.getColumnClassName(i));
    	 }
    	 
    	 
    	 rs.close();
    	 stmt.close();

    }
}
