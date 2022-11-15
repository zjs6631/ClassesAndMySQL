
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;



public class JFXmenu extends Application {
    Stage stage = new Stage();
    Company company = Company.getInstanceOfCompany(); //create the single instance of the Company

    public void start(Stage stage){

        GridPane pane = new GridPane();

        pane.setAlignment(Pos.CENTER);

        Label title = new Label("MENU");

        Button viewEmpBtn = new Button("View Employees");
        Button viewCustBtn = new Button("View Customers");
        Button addEmpBtn = new Button("Add Employee");
        Button addCustBtn = new Button("Add Customer");
        Button removeEmpBtn = new Button("Remove Employee");
        Button removeCustBtn = new Button("Remove Customer");

        viewEmpHandler viewEmpHndl = new viewEmpHandler();
        viewEmpBtn.setOnAction(viewEmpHndl);

        viewCustHandler viewCustHndl = new viewCustHandler();
        viewCustBtn.setOnAction(viewCustHndl);

        addEmpHandler addEmpHndl = new addEmpHandler();
        addEmpBtn.setOnAction(addEmpHndl);

        addCustHandler addCustHndl = new addCustHandler();
        addCustBtn.setOnAction(addCustHndl);

        Scene main = new Scene(pane,200, 200);
        pane.add(title, 0, 0);
        pane.add(viewEmpBtn, 0, 1);
        pane.add(viewCustBtn, 0, 2);
        pane.add(addEmpBtn, 0, 3);
        pane.add(addCustBtn, 0, 4);
        pane.add(removeEmpBtn, 0, 5);
        pane.add(removeCustBtn, 0, 6);

        stage.setTitle("Main Menu");
        stage.setScene(main);
        stage.show();


    }

    class viewHomeHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            start(stage);
        }

    }


    class viewEmpHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e){
            viewEmps(stage);
        }
    }

    class viewCustHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            viewCusts(stage);
        }
    }

    class addEmpHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            addEmployee(stage);
        }
    }

    class addCustHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            addCustomer(stage);
        }
    }

    public void viewEmps(Stage stage){

        FlowPane pane = new FlowPane();

        Scene viewEmps = new Scene(pane, 200, 200);

        Button toMain = new Button("MENU");
        Label listEmps = new Label("Here's the list");

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        toMain.setOnAction(viewHomeHandle);

        pane.getChildren().add(toMain);
        pane.getChildren().add(listEmps);

        stage.setTitle("Employees");
        stage.setScene(viewEmps);
        stage.show();
    }

    public void viewCusts(Stage stage){

        FlowPane pane = new FlowPane();

        Scene viewEmps = new Scene(pane, 200, 200);

        Button toMain = new Button("MENU");
        Label listEmps = new Label("Here's the list");

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        toMain.setOnAction(viewHomeHandle);

        pane.getChildren().add(toMain);
        pane.getChildren().add(listEmps);

        stage.setTitle("Customers");
        stage.setScene(viewEmps);
        stage.show();
    }

    public void addEmployee(Stage stage){

        GridPane pane = new GridPane();

        Scene addEmps = new Scene(pane, 200, 200);

        Button toMain = new Button("MENU");
        Button submit = new Button("Submit");

        Label empNamelbl = new Label("Name: ");
        TextArea empNametxt = new TextArea();
        empNametxt.setMaxHeight(12);
        empNametxt.setMaxWidth(100);

        Label empAddresslbl = new Label("Address: ");
        TextArea empAddresstxt = new TextArea();
        empAddresstxt.setMaxHeight(12);
        empAddresstxt.setMaxWidth(100);

        Label empPhonelbl = new Label("Phone: ");
        TextArea empPhonetxt = new TextArea();
        empPhonetxt.setMaxHeight(12);
        empPhonetxt.setMaxWidth(100);

        Label empTitlelbl = new Label("Title: ");
        TextArea empTitletxt = new TextArea();
        empTitletxt.setMaxHeight(12);
        empTitletxt.setMaxWidth(100);

        Label empIsManagerlbl = new Label("Manager: ");
        TextArea empIsManagertxt = new TextArea();
        empIsManagertxt.setMaxHeight(12);
        empIsManagertxt.setMaxWidth(100);

        Label empBirthDatelbl = new Label("Birth Date: ");
        TextArea empBirthDatetxt = new TextArea();
        empBirthDatetxt.setMaxHeight(12);
        empBirthDatetxt.setMaxWidth(100);

        Label empSalarylbl = new Label("Salary: ");
        TextArea empSalarytxt = new TextArea();
        empSalarytxt.setMaxHeight(12);
        empSalarytxt.setMaxWidth(100);

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        toMain.setOnAction(viewHomeHandle);

        pane.add(toMain,0,0);
        pane.add(empNamelbl, 0, 1);
        pane.add(empNametxt, 1, 1);

        pane.add(empAddresslbl, 0, 2);
        pane.add(empAddresstxt, 1, 2);

        pane.add(empPhonelbl, 0, 3);
        pane.add(empPhonetxt, 1, 3);

        pane.add(empTitlelbl, 0, 4);
        pane.add(empTitletxt, 1, 4);

        pane.add(empIsManagerlbl, 0, 5);
        pane.add(empIsManagertxt, 1, 5);

        pane.add(empBirthDatelbl, 0, 6);
        pane.add(empBirthDatetxt, 1, 6);

        pane.add(empSalarylbl, 0, 7);
        pane.add(empSalarytxt, 1, 7);

        pane.add(submit, 0, 8);




        stage.setTitle("Add Employee");
        stage.setScene(addEmps);
        stage.show();
    }



    public void addCustomer(Stage stage){

        GridPane pane = new GridPane();

        Scene addCustomer = new Scene(pane, 200, 200);

        Button toMain = new Button("MENU");
        Button submit = new Button("Submit");

        Label custNamelbl = new Label("Name: ");
        TextArea custNametxt = new TextArea();
        custNametxt.setMaxHeight(12);
        custNametxt.setMaxWidth(100);

        Label custAddresslbl = new Label("Address: ");
        TextArea custAddresstxt = new TextArea();
        custAddresstxt.setMaxHeight(12);
        custAddresstxt.setMaxWidth(100);

        Label custPhonelbl = new Label("Phone: ");
        TextArea custPhonetxt = new TextArea();
        custPhonetxt.setMaxHeight(12);
        custPhonetxt.setMaxWidth(100);

        Label custAmtlbl = new Label("Amount Spent: ");
        TextArea custAmttxt = new TextArea();
        custAmttxt.setMaxHeight(12);
        custAmttxt.setMaxWidth(100);

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        toMain.setOnAction(viewHomeHandle);

        pane.add(toMain,0,0);
        pane.add(custNamelbl, 0, 1);
        pane.add(custNametxt, 1, 1);

        pane.add(custAddresslbl, 0, 2);
        pane.add(custAddresstxt, 1, 2);

        pane.add(custPhonelbl, 0, 3);
        pane.add(custPhonetxt, 1, 3);

        pane.add(custAmtlbl, 0, 4);
        pane.add(custAmttxt, 1, 4);

        pane.add(submit, 0, 5);






        stage.setTitle("Add Employee");
        stage.setScene(addCustomer);
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
