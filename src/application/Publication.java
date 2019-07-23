package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Publication {
	private int id_pub;
	private ArrayList<Champ> listeChamps;
	private boolean valide;
	private boolean signale;
	// Créer une publication avec tous les champs, et la renvoyer ua serveur qui la range 
	// Une méthode qui renvoie l'indice de la publication
	// Faire un iterator pour le parcour de ArrayList
	public Publication(ArrayList<Champ> c) {
		this.valide = false;
		this.signale = false;
		this.listeChamps = c;
		// TODO Auto-generated constructor stub
	}
	public boolean getValide(){return this.valide;}
	public boolean getSignale(){return this.signale;}

}

//
