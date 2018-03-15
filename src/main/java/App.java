import controllers.UiController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.UI;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UI view = new UI();
        UiController controller = new UiController(view);

        Scene scene = new Scene(view.getContent());
        primaryStage.setTitle("Grid Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
