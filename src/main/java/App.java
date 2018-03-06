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
        UI view = new UI();
        Parent parent = view.createContent();
        UiController controller = new UiController(view);


        Scene scene = new Scene(parent);
        primaryStage.setTitle("A*");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
