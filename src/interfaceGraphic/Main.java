package interfaceGraphic;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
		
	public void start(Stage primaryStage) {
		try {
			/*
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			*/
			 // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass().getResource("/interfaceGraphic/MyScene.fxml"));

            primaryStage.setTitle("Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);		
		/*
		ArrayList<Champ>  schema = new ArrayList<>();
		Champ champ1 = new Champ("nom","varchar(6)");
		Profil profil = new Profil("Student");
		profil.ajouterChamp(champ1);
		//Base base = new Base();
		//base.creerRelation(schema);
		Base base = new Base();
		try {
			System.out.println(base.creerProfilSQL(profil));
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			System.out.println("erreur");
			e.printStackTrace();
		}*/
	}
	
}
