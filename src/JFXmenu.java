
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javax.swing.*;


public class JFXmenu extends Application {
    Stage stage = new Stage();
    Company business = Company.getInstanceOfCompany(); //create the single instance of the Company

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

        Scene main = new Scene(pane,500, 500);
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

        public void handle(ActionEvent e){
            viewEmps(stage);
        }
    }

    class viewCustHandler implements EventHandler<ActionEvent>{

        public void handle(ActionEvent e){
            viewCusts(stage);
        }
    }

    class addEmpHandler implements EventHandler<ActionEvent>{

        public void handle(ActionEvent e){
            addEmployee(stage);
        }
    }

    class addCustHandler implements EventHandler<ActionEvent>{

        public void handle(ActionEvent e){
            addCustomer(stage);
        }
    }




    public void viewEmps(Stage stage){

        FlowPane pane = new FlowPane();

        Scene viewEmps = new Scene(pane, 500, 500);

        Button toMain = new Button("MENU");
        TextArea listEmps = new TextArea();
        for(int i = 0; i < business.employees.size(); i++){
            Employee currEmployee = business.employees.get(i);
            listEmps.appendText(currEmployee.name + " " + currEmployee.address + " " + currEmployee.phone + " " +
                    currEmployee.birthDate + " " + currEmployee.title + " " + currEmployee.salary + " " + currEmployee.isManager);
        }
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

        Scene viewEmps = new Scene(pane, 500, 500);

        Button toMain = new Button("MENU");
        TextArea listCusts = new TextArea();

        for(int i = 0; i < business.customers.size(); i++){
            Customer currCustomer = business.customers.get(i);
            listCusts.appendText(currCustomer.name + " " + currCustomer.address + " " + currCustomer.phone + " " +
                    currCustomer.amountSpent);
        }

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        toMain.setOnAction(viewHomeHandle);

        pane.getChildren().add(toMain);
        pane.getChildren().add(listCusts);

        stage.setTitle("Customers");
        stage.setScene(viewEmps);
        stage.show();
    }

    public void addEmployee(Stage stage){

        GridPane pane = new GridPane();

        Scene addEmps = new Scene(pane, 500, 500);

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

        Label empPhonelbl = new Label("Phone (numbers only): ");
        TextArea empPhonetxt = new TextArea();
        empPhonetxt.setMaxHeight(12);
        empPhonetxt.setMaxWidth(100);

        Label empTitlelbl = new Label("Title: ");
        TextArea empTitletxt = new TextArea();
        empTitletxt.setMaxHeight(12);
        empTitletxt.setMaxWidth(100);

        final ToggleGroup managerial = new ToggleGroup();

        RadioButton empIsManagerBtn = new RadioButton("Manager");
        empIsManagerBtn.setToggleGroup(managerial);
        RadioButton empNonManagerBtn = new RadioButton("Non-manager");
        empNonManagerBtn.setToggleGroup(managerial);
        empNonManagerBtn.setSelected(true);

        Label empBirthDatelbl = new Label("Birth Date (YYYY/MM/DD): ");
        TextArea empBirthDatetxt = new TextArea();
        empBirthDatetxt.setMaxHeight(12);
        empBirthDatetxt.setMaxWidth(100);

        Label empSalarylbl = new Label("Salary (numbers only): ");
        TextArea empSalarytxt = new TextArea();
        empSalarytxt.setMaxHeight(12);
        empSalarytxt.setMaxWidth(100);

        Label errorLbl = new Label();


        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        toMain.setOnAction(viewHomeHandle);




        //EVENT HANDLER using arrow-like syntax will use this for creating customer objects too
        submit.setOnAction(e -> {
            String nameField = empNametxt.getText(),
                    addressField = empAddresstxt.getText(),
                    phoneField = empPhonetxt.getText(),
                    titleField = empTitletxt.getText(),
                    birthDateField = empBirthDatetxt.getText();
            Double salaryField;

            boolean validPhone = true;
            boolean validBirthDate = true;
            boolean validSalary = true;

            for(int i = 0; i < phoneField.length(); i++){
                if(!Character.isDigit(phoneField.charAt(i))){
                    validPhone = false;
                    errorLbl.setText("Invalid Phone number! Numbers only please.");
                }
            }

            try{
                Double.parseDouble(empSalarytxt.getText());
            }catch(NumberFormatException b){
                validSalary = false;
                errorLbl.setText("Invalid Salary! Numbers only please.");
            }

            if(birthDateField.length() != 10 || birthDateField.charAt(4) != '/' || birthDateField.charAt(7) != '/'){
                validBirthDate = false;
                errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
            }

            for(int i = 0; i < birthDateField.length(); i++){
                if(i <4 && !Character.isDigit(birthDateField.charAt(i))){
                    validBirthDate = false;
                    errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                } else if (i > 4 && i < 7 && !Character.isDigit(birthDateField.charAt(i))){
                    validBirthDate = false;
                    errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                } else if (i > 7 && !Character.isDigit(birthDateField.charAt(i))){
                    validBirthDate = false;
                    errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                }
            }

            if(validPhone && validBirthDate && validSalary) {
                salaryField = Double.parseDouble(empSalarytxt.getText());
                Employee newEmployee = new Employee(nameField, addressField,
                        phoneField, titleField, empIsManagerBtn.isSelected(),
                        birthDateField, salaryField);

                business.employees.add(newEmployee);
                start(stage);
            }

        });



        pane.add(toMain,0,0);
        pane.add(empNamelbl, 0, 1);
        pane.add(empNametxt, 1, 1);

        pane.add(empAddresslbl, 0, 2);
        pane.add(empAddresstxt, 1, 2);

        pane.add(empPhonelbl, 0, 3);
        pane.add(empPhonetxt, 1, 3);

        pane.add(empTitlelbl, 0, 4);
        pane.add(empTitletxt, 1, 4);

        pane.add(empIsManagerBtn, 0, 5);
        pane.add(empNonManagerBtn, 1, 5);

        pane.add(empBirthDatelbl, 0, 6);
        pane.add(empBirthDatetxt, 1, 6);

        pane.add(empSalarylbl, 0, 7);
        pane.add(empSalarytxt, 1, 7);

        pane.add(submit, 0, 8);

        pane.add(errorLbl, 0, 9);


        stage.setTitle("Add Employee");
        stage.setScene(addEmps);
        stage.show();
    }





    public void addCustomer(Stage stage){

        GridPane pane = new GridPane();

        Scene addCustomer = new Scene(pane, 500, 500);

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

        Label custPhonelbl = new Label("Phone (numbers only): ");
        TextArea custPhonetxt = new TextArea();
        custPhonetxt.setMaxHeight(12);
        custPhonetxt.setMaxWidth(100);

        Label custAmtlbl = new Label("Amount Spent (numbers only): ");
        TextArea custAmttxt = new TextArea();
        custAmttxt.setMaxHeight(12);
        custAmttxt.setMaxWidth(100);

        Label errorLabel = new Label();

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

        pane.add(errorLabel, 0, 6);


        //EVENT HANDLER using arrow-like syntax to add customer to customer array in business obj
        submit.setOnAction(e -> {

            String custName = custNametxt.getText(),
                    custAddress = custAddresstxt.getText(),
                    custPhone = custPhonetxt.getText(),
                    custAmountSpent = custAmttxt.getText();

            boolean validPhone = true;
            boolean validAmountSpent = true;

            for(int i = 0; i < custPhone.length(); i++){
                if(!Character.isDigit(custPhone.charAt(i))){
                    validPhone = false;
                    errorLabel.setText("Invalid Phone Number provided. Numbers only please.");
                }
            }

            for(int i=0; i < custAmountSpent.length(); i++){
                if(!Character.isDigit(custAmountSpent.charAt(i))){
                    validAmountSpent = false;
                    errorLabel.setText("Invalid Amount Spent provided. Numbers only please.");
                }
            }
            if(validPhone && validAmountSpent) {
                Customer newCustomer = new Customer(custName, custAddress,
                        custPhone, Double.parseDouble(custAmountSpent));

                business.customers.add(newCustomer);
                start(stage);
            }

        });



        stage.setTitle("Add Employee");
        stage.setScene(addCustomer);
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
