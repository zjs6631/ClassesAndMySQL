
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;



public class JFXmenu extends Application {
    Stage stage = new Stage();
    public void start(Stage stage){

        Button viewEmpBtn = new Button("View all Emps");
        Scene main = new Scene(viewEmpBtn, 200, 200);
        stage.setTitle("Main Menu");
        stage.setScene(main);
        stage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
