package framwork.serveur.communication;

import framwork.remote._ChatClient;


public class Chatter {

	public String name;
	public _ChatClient client;
	
	//constructor
	public Chatter(String name, _ChatClient client){
		this.name = name;
		this.client = client;
	}

	//getters and setters
	public String getName(){
		return name;
	}
	public _ChatClient getClient(){
		return client;
	}
	
	
}
