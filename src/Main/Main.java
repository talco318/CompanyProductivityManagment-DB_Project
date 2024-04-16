package Main;



import Controller.Controller;
import Model.Company;
import Model.CompanyUI;
import View.ViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) throws Exception {
//		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		
		ViewModel view = new ViewModel(primaryStage);
		
		CompanyUI model = new Company();
		Controller controller = new Controller(view, model);
	
	}

}
