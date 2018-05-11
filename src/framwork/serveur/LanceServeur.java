package framwork.serveur;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import framwork.remote.*;
import framwork.serveur.base.serveurImpl.ServeurImpl;

public class LanceServeur {

  public static void main(String[] args) {
    try {
      System.setProperty("java.rmi.server.hostname","147.171.175.111");
      LocateRegistry.createRegistry(Constant.portNumber);
/*
      System.out.println("Mise en place du Security Manager ...");
      if (System.getSecurityManager() == null) {
        System.setSecurityManager(new RMISecurityManager());
      }
*/
      ServeurImpl myServeur = new ServeurImpl();
      //myServeur.setFile("src//Bum_Tam.mp3");      
      //String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/TestRMI";

      String url = "rmi://147.171.175.111/ReseauSocial";
      System.out.println("Enregistrement de l'objet avec l'url : " + url);
      Naming.rebind(url, myServeur);

      System.out.println("Serveur lance");
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } 
//    catch (UnknownHostException e) {
//      e.printStackTrace();
//    }
  }
}
