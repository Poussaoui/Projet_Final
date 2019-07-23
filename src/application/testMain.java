package application;


import java.sql.SQLException;
import java.util.ArrayList;

public class testMain {


    public static void main(String args[]) throws Exception,SQLException {
     
		String table="profil";
		Services s = new Services();
		/*
		System.out.println(s.nbReactions("Reaction"));
		s.afficherReactionById("Reaction", 0);
		s.afficherReactionByIdPers("Reaction", 115);
		s.afficherReactionByIdPub("Reaction", 117);
		s.reagit("Reaction", 111, 211, 311,"favoris");
		s.modifierReaction("Reaction", 111, 211, "favoris", 311);
		*/
		//s.stocker();
		//#########################################TESTER################################################
		System.out.println(s.nbReactions("Reaction"));
		/** Tester Reactions
		 * s.reagit("Reaction", 211, 111, 818,"favoris"); ---bon
		 * System.out.println(s.afficherReactionById("Reaction", 818).get(3)); ---bon 
		 * System.out.println(s.afficherReactionByIdPers("Reaction", 111).get(3)); ---bon
		 * System.out.println(s.afficherReactionByIdPub("Reaction", 211).get(3)); ---bon
		 * s.modifierReaction("Reaction", 111, 211, "jaime", 818); ---bon
		 * s.retirerReaction("Reaction", 818); ---bon
		 */
		
		
		/*
		ArrayList<Champ> champs1 = s.retourChamp(table);
		
		ArrayList<Champ> champs2 = new ArrayList<>();
		
		s.inscription(champs1);
		
		Profil p1 = new Profil(champs1);
		
		s.inserer(table, champs1);
		
		champs2 = s.retourChamp(table);
		s.inscription(champs2);
		
		Profil p2 = new Profil(champs2);
		
		s.inserer(table, champs2);
		
		ArrayList<Champ> c = new ArrayList<>();
		
		/** on veut modifier le champ PASS 
		c.add(new Champ("PASS", "yooo"));
		
		
		String condition = p2.champToCondition();
		
		/** dans le profil p2, on modifie le mot de passe 
		p2.modifierProfil(c);
		
		/** pour mettre à jour la modification dan la base de données 
		s.updateProfil(table, p2, condition);
	
		
		/** suppression d'un profil
		s.supprimerProfil(table, p2);
		
		ArrayList<Champ> rech = new ArrayList<>();
		rech.add(new Champ("login", "lol"));
		rech.add(new Champ("pass", "lol"));
		
		ArrayList<Profil> find = new ArrayList<>();
		find =s.recherche(table, rech);
		
		System.out.println("profil trouvé "+find.get(0).champToString());
		*/
    }    	
}
