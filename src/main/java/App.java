import controllers.UiController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.UI;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UiController controller = new UiController();
        UI view = new UI(controller);
        Parent p = view.createContent();
        Scene scene = new Scene(p);
        controller.setView(view);
        controller.addListener();
        primaryStage.setTitle("A*");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
