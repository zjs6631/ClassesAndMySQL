
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.*;


public class JFXmenu extends Application {
    Stage stage = new Stage();
    Company business = Company.getInstanceOfCompany(); //create the single instance of the Company

    String url = "jdbc:mysql://localhost:3306/company";
    String username = "zjs6631";
    String password = "mySQL4840";


    Connection connection;

    {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    Statement statement;

    {
        try {
            statement = connection.createStatement();
            String empQuery = "select * from employees";
            ResultSet empResults = statement.executeQuery(empQuery);
            while(empResults.next()) {
                String name = empResults.getString("name");
                String address = empResults.getString("address");
                String phone = empResults.getString("phone");
                String title = empResults.getString("title");
                Boolean isManager = empResults.getBoolean("isManager");
                String birthDate = empResults.getString("birthDate");
                Double salary = empResults.getDouble("salary");

                Employee currEmployee = new Employee(name, address, phone, title, isManager, birthDate, salary);

                business.employees.add(currEmployee);

            }
            empResults.close();

            String custQuery = "select * from customers";
            ResultSet custResults = statement.executeQuery(custQuery);
            while(custResults.next()){
                String name = custResults.getString("name");
                String address = custResults.getString("address");
                String phone = custResults.getString("phone");
                Double amntSpent = custResults.getDouble("amountSpent");

                Customer currCustomer = new Customer(name, address, phone, amntSpent);

                business.customers.add(currCustomer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







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
        Button updateEmpBtn = new Button("Update Employee");
        Button updateCustBtn = new Button("Update Customer");


        viewEmpBtn.setOnAction(e->{
            viewEmps(stage);
        });

        viewCustBtn.setOnAction(e->{
            viewCusts(stage);
        });

        addEmpBtn.setOnAction(e->{
            addEmployee(stage);
        });

        addCustBtn.setOnAction(e->{
            addCustomer(stage);
        });

        removeEmpBtn.setOnAction(e->{
            removeEmployee(stage);
        });

        removeCustBtn.setOnAction(e->{
            removeCustomer(stage);
        });

        updateEmpBtn.setOnAction(e->{
            updateEmployee(stage);
        });

        updateCustBtn.setOnAction(e->{
            updateCustomers(stage);
        });



        Scene main = new Scene(pane,500, 500);
        pane.add(title, 0, 0);
        pane.add(viewEmpBtn, 0, 1);
        pane.add(viewCustBtn, 0, 2);
        pane.add(addEmpBtn, 0, 3);
        pane.add(addCustBtn, 0, 4);
        pane.add(removeEmpBtn, 0, 5);
        pane.add(removeCustBtn, 0, 6);
        pane.add(updateEmpBtn, 0, 7);
        pane.add(updateCustBtn, 0, 8);


        stage.setTitle("Main Menu");
        stage.setScene(main);
        stage.show();


    }

    class viewHomeHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            start(stage);
        }

    }

    public void viewEmps(Stage stage){

        FlowPane pane = new FlowPane();

        Scene viewEmps = new Scene(pane, 500, 500);

        Button toMain = new Button("MENU");
        TextArea listEmps = new TextArea();
        listEmps.setWrapText(true);
        for(int i = 0; i < business.employees.size(); i++){
            Employee currEmployee = business.employees.get(i);
            listEmps.appendText(currEmployee.name + " " + currEmployee.address + " " + currEmployee.phone + " " +
                    currEmployee.birthDate + " " + currEmployee.title + " " + currEmployee.salary + " " + currEmployee.isManager + "\n");
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
                    currCustomer.amountSpent + "\n");
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
                try {
                    Statement addEmp = connection.createStatement();
                    String updateEmps = "INSERT INTO employees (name, address, phone, title, isManager, "
                            + "birthdate, salary) VALUES ('" + nameField +"', '" + addressField + "', '" + phoneField +
                            "', '" + titleField + "', " + empIsManagerBtn.isSelected() + ", '" + birthDateField + "', " +
                            salaryField + ")";
                    addEmp.executeUpdate(updateEmps);
                    addEmp.close();
                }catch(SQLException ex){

                    System.out.println(ex);

                    String updateEmps = "INSERT INTO employees (name, address, phone, title, isManager, "
                            + "birthdate, salary) VALUES ('" + nameField +"', '" + addressField + "', '" + phoneField +
                            "', '" + titleField + "', " + empIsManagerBtn.isSelected() + ", '" + birthDateField + "', " +
                            salaryField + ")";
                    System.out.println(updateEmps);
                }
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

                try {
                    Statement addCust = connection.createStatement();
                    String updateCusts = "INSERT INTO customers (name, address, phone, amountSpent) VALUES ('" + custName +"', '" + custAddress + "', '" + custPhone +
                            "', " + Double.parseDouble(custAmountSpent) + ")";
                    addCust.executeUpdate(updateCusts);
                    addCust.close();
                }catch(SQLException ex){

                    System.out.println(ex);

                    String updateCusts = "INSERT INTO employees (name, address, phone, title, isManager, "
                            + "birthdate, salary) VALUES ('" + custName +"', '" + custAddress + "', '" + custPhone +
                            "', '" + custAmountSpent + ")";
                    System.out.println(updateCusts);
                }

                start(stage);
            }

        });



        stage.setTitle("Add Employee");
        stage.setScene(addCustomer);
        stage.show();
    }

    public void removeEmployee(Stage stage){

        GridPane pane = new GridPane();
        Scene removeEmp = new Scene(pane, 500, 500);

        Button menu = new Button("MENU");

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        menu.setOnAction(viewHomeHandle);

        Label nameLbl = new Label("Name");
        TextArea nameTxt = new TextArea();

        nameTxt.setMaxWidth(100);
        nameTxt.setMaxHeight(12);

        Label phoneLbl = new Label("Phone");
        TextArea phoneTxt = new TextArea();
        phoneTxt.setMaxWidth(100);
        phoneTxt.setMaxHeight(12);

        Button submit = new Button("DELETE");

        submit.setOnAction(e->{
            String name = nameTxt.getText();
            String phone = phoneTxt.getText();

            try{
                Statement removeEmployee = connection.createStatement();

                int targEmployeeIndex = -1;

                for (Employee employee : business.employees) {
                    if(employee.name.equals(name) && employee.phone.equals(phone)){
                        targEmployeeIndex = business.employees.indexOf(employee);
                    }
                }
                if(targEmployeeIndex != -1){
                    business.employees.remove(targEmployeeIndex);
                    String updateEmployees = "DELETE FROM employees WHERE (name= '" + name + "' && phone= '"+phone+"')";
                    removeEmployee.executeUpdate(updateEmployees);
                    removeEmployee.close();
                } else {
                    System.out.println("Employee not found.");
                }



                start(stage);


            }catch(SQLException ex){
                System.out.println(ex);
            }

        });

        pane.add(menu, 0, 0);
        pane.add(nameLbl, 0, 1);
        pane.add(nameTxt, 1, 1);
        pane.add(phoneLbl, 0, 2);
        pane.add(phoneTxt, 1, 2);
        pane.add(submit, 0, 3);

        stage.setTitle("Remove Employee");
        stage.setScene(removeEmp);
        stage.show();


    }

    public void removeCustomer(Stage stage){

        GridPane pane = new GridPane();
        Scene removeCust = new Scene(pane, 500, 500);

        Button menu = new Button("MENU");

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        menu.setOnAction(viewHomeHandle);

        Label nameLbl = new Label("Name");
        TextArea nameTxt = new TextArea();

        nameTxt.setMaxHeight(12);
        nameTxt.setMaxWidth(100);

        Label phoneLbl = new Label("Phone");
        TextArea phoneTxt = new TextArea();

        phoneTxt.setMaxWidth(100);
        phoneTxt.setMaxHeight(12);

        Button submit = new Button("DELETE");

        submit.setOnAction(e-> {
                    String name = nameTxt.getText();
                    String phone = phoneTxt.getText();

                    try {
                        Statement removeCustomer = connection.createStatement();

                        int targIndex = -1;
                        for (Customer customer : business.customers) {
                            if (customer.name.equals(name) && customer.phone.equals(phone)) {
                                targIndex = business.customers.indexOf(customer);
                            }
                        }
                        if(targIndex != -1) {
                            business.customers.remove(targIndex);
                            String updateCustomers = "DELETE FROM customers WHERE (name= '" + name + "' && phone= '" + phone + "')";
                            removeCustomer.executeUpdate(updateCustomers);
                            removeCustomer.close();
                        } else {
                            System.out.println("Customer not found.");
                        }

                        start(stage);

                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                });

        pane.add(menu, 0, 0);
        pane.add(nameLbl, 0, 1);
        pane.add(nameTxt, 1, 1);
        pane.add(phoneLbl, 0, 2);
        pane.add(phoneTxt, 1, 2);
        pane.add(submit, 0, 3);

        stage.setTitle("Remove Customer");
        stage.setScene(removeCust);
        stage.show();


    }

    public void updateCustomers(Stage stage){
        GridPane pane = new GridPane();
        Scene updateCust = new Scene(pane, 500, 500);

        Button menu = new Button("MENU");

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        menu.setOnAction(viewHomeHandle);

        Label nameLbl = new Label("Name");
        TextArea nameTxt = new TextArea();

        nameTxt.setMaxHeight(12);
        nameTxt.setMaxWidth(100);

        Label phoneLbl = new Label("Phone");
        TextArea phoneTxt = new TextArea();

        phoneTxt.setMaxHeight(12);
        phoneTxt.setMaxWidth(100);

        Button search = new Button("SEARCH");

        search.setOnAction(e->{

            String targName = nameTxt.getText();
            String targPhone = phoneTxt.getText();

            int targCustIndex = -1;

            for(Customer customer : business.customers){
                if(customer.name.equals(targName) && customer.phone.equals(targPhone)){
                    targCustIndex = business.customers.indexOf(customer);
                }
            }

            if(targCustIndex != -1){

                int finalTargCustIndex = targCustIndex;
                Customer targetCustomer = business.customers.get(finalTargCustIndex);

                Label newNameLbl = new Label("Name: ");
                TextArea newNameTxt = new TextArea(targetCustomer.name);

                newNameTxt.setMaxWidth(100);
                newNameTxt.setMaxHeight(12);

                Label newAddressLbl = new Label("Address: ");
                TextArea newAddressTxt = new TextArea(targetCustomer.address);

                newAddressTxt.setMaxHeight(12);
                newAddressTxt.setMaxWidth(100);

                Label newPhoneLbl = new Label("Phone: ");
                TextArea newPhoneTxt = new TextArea(targetCustomer.phone);

                newPhoneTxt.setMaxWidth(100);
                newPhoneTxt.setMaxHeight(12);

                Label newAmountSpentLbl = new Label("Title: ");
                TextArea newAmountSpentTxt = new TextArea(targetCustomer.amountSpent.toString());

                newAmountSpentTxt.setMaxHeight(12);
                newAmountSpentTxt.setMaxWidth(100);


                Button submit = new Button("SUBMIT UPDATES");

                Label errorLbl = new Label("");


                submit.setOnAction(a->{
                    boolean validPhone = true;
                    boolean validSalary = true;

                    for(int i = 0; i < newPhoneTxt.getText().length(); i++){
                        if(!Character.isDigit(newPhoneTxt.getText().charAt(i))){
                            validPhone = false;
                            errorLbl.setText("Invalid Phone number! Numbers only please.");
                        }
                    }

                    try{
                        Double.parseDouble(newAmountSpentTxt.getText());
                    }catch(NumberFormatException b){
                        validSalary = false;
                        errorLbl.setText("Invalid Salary! Numbers only please.");
                    }


                    if(validPhone && validSalary){
                        Customer targ = business.customers.get(finalTargCustIndex);
                        targ.name = newNameTxt.getText();
                        targ.address = newAddressTxt.getText();
                        targ.phone = newPhoneTxt.getText();
                        targ.amountSpent = Double.parseDouble(newAmountSpentTxt.getText());

                        try {
                            Statement updateCustomer = connection.createStatement();

                            String updateCustomers = "UPDATE customers SET name = '"+ newNameTxt.getText() +
                                    "', address='" + newAddressTxt.getText() + "', phone='" + newPhoneTxt.getText() + "', amountSpent=" +
                            Double.parseDouble(newAmountSpentTxt.getText()) + " WHERE (name= '" + targName + "' && phone= '" + targPhone + "')";
                            updateCustomer.executeUpdate(updateCustomers);
                            updateCustomer.close();

                            start(stage);
                        } catch (SQLException ex) {
                            ex.printStackTrace();

                            String updateCustomers = "UPDATE customers SET name = '"+ newNameTxt.getText() +
                                    "', address='" + newAddressTxt.getText() + "', phone='" + newPhoneTxt.getText() + "', amountSpent=" +
                                    Double.parseDouble(newAmountSpentTxt.getText()) + " WHERE (name= '" + targName + "' && phone= '" + targPhone + "')";

                            System.out.println(updateCustomers);
                        }


                    }
                });

                pane.add(newNameLbl, 0, 5);
                pane.add(newNameTxt, 1, 5);
                pane.add(newAddressLbl, 0, 6);
                pane.add(newAddressTxt, 1, 6);
                pane.add(newPhoneLbl, 0, 7);
                pane.add(newPhoneTxt, 1, 7);
                pane.add(newAmountSpentLbl, 0, 8);
                pane.add(newAmountSpentTxt, 1, 8);

                pane.add(submit, 0, 10);
                pane.add(errorLbl, 0, 11);
            }
        });
         pane.add(menu, 0, 0);
         pane.add(nameLbl, 0, 1);
         pane.add(nameTxt, 1, 1);
         pane.add(phoneLbl, 0, 2);
         pane.add(phoneTxt, 1, 2);
         pane.add(search, 0, 3);

        stage.setTitle("Update Customers");
        stage.setScene(updateCust);
        stage.show();
    };

    public void updateEmployee(Stage stage){
        GridPane pane = new GridPane();
        Scene updateEmp = new Scene(pane, 500, 500);

        Button menu = new Button("MENU");

        viewHomeHandler viewHomeHandle = new viewHomeHandler();
        menu.setOnAction(viewHomeHandle);

        Label nameLbl = new Label("Name");
        TextArea nameTxt = new TextArea();

        nameTxt.setMaxHeight(12);
        nameTxt.setMaxWidth(100);

        Label phoneLbl = new Label("Phone");
        TextArea phoneTxt = new TextArea();

        phoneTxt.setMaxHeight(12);
        phoneTxt.setMaxWidth(100);

        Button search = new Button("SEARCH");

        search.setOnAction(e->{

            String targName = nameTxt.getText();
            String targPhone = phoneTxt.getText();

            int targEmpIndex = -1;

            for(Employee employee : business.employees){
                if(employee.name.equals(targName) && employee.phone.equals(targPhone)){
                    targEmpIndex = business.employees.indexOf(employee);
                }
            }

            if(targEmpIndex != -1){

                int finalTargEmpIndex = targEmpIndex;
                Employee targetEmployee = business.employees.get(finalTargEmpIndex);

                Label newNameLbl = new Label("Name: ");
                TextArea newNameTxt = new TextArea(targetEmployee.name);

                newNameTxt.setMaxWidth(100);
                newNameTxt.setMaxHeight(12);

                Label newAddressLbl = new Label("Address: ");
                TextArea newAddressTxt = new TextArea(targetEmployee.address);

                newAddressTxt.setMaxHeight(12);
                newAddressTxt.setMaxWidth(100);

                Label newPhoneLbl = new Label("Phone: ");
                TextArea newPhoneTxt = new TextArea(targetEmployee.phone);

                newPhoneTxt.setMaxWidth(100);
                newPhoneTxt.setMaxHeight(12);

                Label newTitleLbl = new Label("Title: ");
                TextArea newTitleTxt = new TextArea(targetEmployee.title);

                newTitleTxt.setMaxHeight(12);
                newTitleTxt.setMaxWidth(100);

                final ToggleGroup managerial = new ToggleGroup();

                RadioButton empIsManagerBtn = new RadioButton("Manager");
                empIsManagerBtn.setToggleGroup(managerial);
                RadioButton empNonManagerBtn = new RadioButton("Non-manager");
                empNonManagerBtn.setToggleGroup(managerial);
                empNonManagerBtn.setSelected(true);

                Label newBirthDateLbl = new Label("Birth Date: ");
                TextArea newBirthDateTxt = new TextArea(targetEmployee.birthDate);

                newBirthDateTxt.setMaxHeight(12);
                newBirthDateTxt.setMaxWidth(100);

                Label newSalaryLbl = new Label("Salary: ");
                TextArea newSalaryTxt = new TextArea(targetEmployee.salary.toString());

                newSalaryTxt.setMaxHeight(12);
                newSalaryTxt.setMaxWidth(100);

                Button submit = new Button("SUBMIT UPDATES");

                Label errorLbl = new Label("");


                submit.setOnAction(a->{
                    boolean validPhone = true;
                    boolean validBirthDate = true;
                    boolean validSalary = true;

                    for(int i = 0; i < newPhoneTxt.getText().length(); i++){
                        if(!Character.isDigit(newPhoneTxt.getText().charAt(i))){
                            validPhone = false;
                            errorLbl.setText("Invalid Phone number! Numbers only please.");
                        }
                    }

                    try{
                        Double.parseDouble(newSalaryTxt.getText());
                    }catch(NumberFormatException b){
                        validSalary = false;
                        errorLbl.setText("Invalid Salary! Numbers only please.");
                    }

                    if(newBirthDateTxt.getText().length() != 10 || newBirthDateTxt.getText().charAt(4) != '/' || newBirthDateTxt.getText().charAt(7) != '/'){
                        validBirthDate = false;
                        errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                    }

                    for(int i = 0; i < newBirthDateTxt.getText().length(); i++){
                        if(i <4 && !Character.isDigit(newBirthDateTxt.getText().charAt(i))){
                            validBirthDate = false;
                            errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                        } else if (i > 4 && i < 7 && !Character.isDigit(newBirthDateTxt.getText().charAt(i))){
                            validBirthDate = false;
                            errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                        } else if (i > 7 && !Character.isDigit(newBirthDateTxt.getText().charAt(i))){
                            validBirthDate = false;
                            errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                        }
                    }

                    if(validPhone && validBirthDate && validSalary){
                        Employee targ = business.employees.get(finalTargEmpIndex);
                        targ.name = newNameTxt.getText();
                        targ.address = newAddressTxt.getText();
                        targ.phone = newPhoneTxt.getText();
                        targ.title = newTitleTxt.getText();
                        targ.isManager = empIsManagerBtn.isSelected();
                        targ.birthDate = newBirthDateTxt.getText();
                        targ.salary = Double.parseDouble(newSalaryTxt.getText());

                        try {
                            Statement updateEmployee = connection.createStatement();

                            String updateCustomers = "UPDATE employees SET name = '"+ newNameTxt.getText() +
                                    "', address='" + newAddressTxt.getText() + "', phone='" + newPhoneTxt.getText() + "', title='" +
                                    newTitleTxt.getText() + "', isManager=" + empIsManagerBtn.isSelected() + ", birthDate='" + newBirthDateTxt.getText() +
                                    "', salary=" + Double.parseDouble(newSalaryTxt.getText()) + " WHERE (name= '" + targName + "' && phone= '" + targPhone + "')";
                            updateEmployee.executeUpdate(updateCustomers);
                            updateEmployee.close();

                            start(stage);
                        } catch (SQLException ex) {
                            ex.printStackTrace();

                            String updateCustomers = "UPDATE employees SET name = '"+ newNameTxt.getText() +
                                    "', address='" + newAddressTxt.getText() + "', phone='" + newPhoneTxt.getText() + "', title='" +
                                    newTitleTxt.getText() + "', isManager=" + empIsManagerBtn.isSelected() + ", birthDate='" + newBirthDateTxt.getText() +
                                    "', salary=" + Double.parseDouble(newSalaryTxt.getText()) + " WHERE (name= '" + targName + "' && phone= '" + targPhone + "')";
                        }


                    }
                });

                pane.add(newNameLbl, 0, 5);
                pane.add(newNameTxt, 1, 5);
                pane.add(newAddressLbl, 0, 6);
                pane.add(newAddressTxt, 1, 6);
                pane.add(newPhoneLbl, 0, 7);
                pane.add(newPhoneTxt, 1, 7);
                pane.add(newTitleLbl, 0, 8);
                pane.add(newTitleTxt, 1, 8);
                pane.add(empIsManagerBtn, 0, 9);
                pane.add(empNonManagerBtn, 1, 9);
                pane.add(newBirthDateLbl, 0, 10);
                pane.add(newBirthDateTxt, 1, 10);
                pane.add(newSalaryLbl, 0, 11);
                pane.add(newSalaryTxt, 1, 11);
                pane.add(submit, 0, 12);
                pane.add(errorLbl, 0, 13);
            }
        });
        pane.add(menu, 0, 0);
        pane.add(nameLbl, 0, 1);
        pane.add(nameTxt, 1, 1);
        pane.add(phoneLbl, 0, 2);
        pane.add(phoneTxt, 1, 2);
        pane.add(search, 0, 3);

        stage.setTitle("Update Employee");
        stage.setScene(updateEmp);
        stage.show();
    };



    public static void main(String[] args) {

        Application.launch(args);
    }
}
