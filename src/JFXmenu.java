
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.*;


public class JFXmenu extends Application {
    //variable to create singleton instance of the business the database is for.
    Company business = Company.getInstanceOfCompany(); //create the single instance of the Company
    //information to connect to the mySQL database
    String url = "jdbc:mysql://localhost:3306/company";
    String username = "zjs6631";
    String password = "mySQL4840";
    Connection connection;
    {
        try {
            connection = DriverManager.getConnection(url, username, password); //using DriverManager to get connected
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    Statement statement; //statement variable used to make query to get existing information out of database
    {
        try {
            statement = connection.createStatement();
            String empQuery = "select * from employees"; //get all employees
            ResultSet empResults = statement.executeQuery(empQuery);
            while(empResults.next()) { //while loop to process query
                String name = empResults.getString("name");
                String address = empResults.getString("address");
                String phone = empResults.getString("phone");
                String title = empResults.getString("title");
                Boolean isManager = empResults.getBoolean("isManager");
                String birthDate = empResults.getString("birthDate");
                Double salary = empResults.getDouble("salary");
                Employee currEmployee = new Employee(name, address, phone, title, isManager, birthDate, salary); //create new Employee objects
                business.employees.add(currEmployee);//add new Employees to the businesses ArrayList
            }
            empResults.close();//close our ResultSet once we are done

            //repeat for the Customers held in the database
            String customerQuery = "select * from customers";
            ResultSet customerResults = statement.executeQuery(customerQuery);
            while(customerResults.next()){
                String name = customerResults.getString("name");
                String address = customerResults.getString("address");
                String phone = customerResults.getString("phone");
                Double amntSpent = customerResults.getDouble("amountSpent");
                Customer currCustomer = new Customer(name, address, phone, amntSpent);
                business.customers.add(currCustomer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//start method used in javaFX app as the main menu
    public void start(Stage stage){
    //using GridPane to easily handle layout
        //1. create a pane to hold all the items you want displayed.
        //2. add items to the pane
        //3. create a scene using the pane and desired dimensions
        //4. set the stage using the created scene.
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        Label title = new Label("MENU");
        Scene main = new Scene(pane,500, 500);
        Button viewEmpBtn = new Button("View Employees");
        Button viewCustBtn = new Button("View Customers");
        Button addEmpBtn = new Button("Add Employee");
        Button addCustBtn = new Button("Add Customer");
        Button removeEmpBtn = new Button("Remove Employee");
        Button removeCustBtn = new Button("Remove Customer");
        Button updateEmpBtn = new Button("Update Employee");
        Button updateCustBtn = new Button("Update Customer");
        //lambda functions to handle changing the displayed scene
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
        //place all items on the pane
        pane.add(title, 0, 0);
        pane.add(viewEmpBtn, 0, 1);
        pane.add(viewCustBtn, 0, 2);
        pane.add(addEmpBtn, 0, 3);
        pane.add(addCustBtn, 0, 4);
        pane.add(removeEmpBtn, 0, 5);
        pane.add(removeCustBtn, 0, 6);
        pane.add(updateEmpBtn, 0, 7);
        pane.add(updateCustBtn, 0, 8);
        //set the padding for elements on the pane
        pane.setHgap(10);
        pane.setVgap(10);

        stage.setTitle("Main Menu");
        stage.setScene(main);
        stage.show();
    }

    public void viewEmps(Stage stage){
    //used FlowPane for this one to display the large textArea.
        FlowPane pane = new FlowPane();
        Scene viewEmps = new Scene(pane, 500, 500);
        Button toMain = new Button("MENU");
        TextArea listEmps = new TextArea();
        listEmps.setWrapText(true);
        //loop over the Business object's employee ArrayList and display the Employee information
        for(int i = 0; i < business.employees.size(); i++){
            Employee currEmployee = business.employees.get(i);
            listEmps.appendText("Name: " +currEmployee.name + "\n" +
                    "Address: " + currEmployee.address + "\n" +
                    "Phone: " + currEmployee.phone + "\n" +
                    "Birth Date: " + currEmployee.birthDate + "\n" +
                    "Job Title: " + currEmployee.title + "\n" +
                    "Salary: $" + String.format("%.2f", currEmployee.salary) + "\n" +
                    "Manager: " + currEmployee.isManager + "\n\n");
        }
        //lambda to return home
        toMain.setOnAction(e->{
            start(stage);
        });
        //add elements to pane and set the padding and alignment.
        pane.getChildren().add(toMain);
        pane.getChildren().add(listEmps);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(30);
        stage.setTitle("Employees");
        stage.setScene(viewEmps);
        stage.show();
    }

    public void viewCusts(Stage stage){
    //same implementation as the viewEmps function
        FlowPane pane = new FlowPane();
        Scene viewEmps = new Scene(pane, 500, 500);
        Button toMain = new Button("MENU");
        TextArea listCusts = new TextArea();
    //loop over and display each customer held in the Business ArrayList.
        for(int i = 0; i < business.customers.size(); i++){
            Customer currCustomer = business.customers.get(i);
            listCusts.appendText("Name: " + currCustomer.name + "\n" +
                    "Address: " + currCustomer.address + "\n" +
                    "Phone: " + currCustomer.phone + "\n" +
                    "Amount Spent: $" + String.format("%.2f", currCustomer.amountSpent) + "\n\n");
        }
        toMain.setOnAction(e->{
            start(stage);
        });
    //format the pane layout
        pane.getChildren().add(toMain);
        pane.getChildren().add(listCusts);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(30);
        stage.setTitle("Customers");
        stage.setScene(viewEmps);
        stage.show();
    }

    public void addEmployee(Stage stage){
    //GridPane to add new employees
        GridPane pane = new GridPane();
        Scene addEmps = new Scene(pane, 500, 500);
        //create all the form elements needed to take in form information
        Button toMain = new Button("MENU");
        Button submit = new Button("Submit");
        Label empNamelbl = new Label("Name: ");
        TextArea empNametxt = new TextArea();
        empNametxt.setMaxHeight(8);
        empNametxt.setMaxWidth(100);
        empNamelbl.setMaxWidth(150);
        Label empAddresslbl = new Label("Address: ");
        TextArea empAddresstxt = new TextArea();
        empAddresstxt.setMaxHeight(8);
        empAddresstxt.setMaxWidth(100);
        empAddresslbl.setMaxWidth(150);
        Label empPhonelbl = new Label("Phone (numbers only): ");
        TextArea empPhonetxt = new TextArea();
        empPhonetxt.setMaxHeight(8);
        empPhonetxt.setMaxWidth(100);
        empPhonelbl.setMaxWidth(150);
        Label empTitlelbl = new Label("Title: ");
        TextArea empTitletxt = new TextArea();
        empTitletxt.setMaxHeight(8);
        empTitletxt.setMaxWidth(100);
        empTitlelbl.setMaxWidth(150);
        //radio buttons for boolean to show if employee is a manager
        final ToggleGroup managerial = new ToggleGroup();
        RadioButton empIsManagerBtn = new RadioButton("Manager");
        empIsManagerBtn.setToggleGroup(managerial);
        RadioButton empNonManagerBtn = new RadioButton("Non-manager");
        empNonManagerBtn.setToggleGroup(managerial);
        empNonManagerBtn.setSelected(true);

        Label empBirthDatelbl = new Label("Birth Date (YYYY/MM/DD): ");
        TextArea empBirthDatetxt = new TextArea();
        empBirthDatetxt.setMaxHeight(8);
        empBirthDatetxt.setMaxWidth(100);
        empBirthDatelbl.setMaxWidth(150);
        Label empSalarylbl = new Label("Salary (numbers only): ");
        TextArea empSalarytxt = new TextArea();
        empSalarytxt.setMaxHeight(8);
        empSalarytxt.setMaxWidth(100);
        empSalarylbl.setMaxWidth(150);
        //error label to display errors in form
        Label errorLbl = new Label();

        toMain.setOnAction(e->{
            start(stage);
        });

        //EVENT HANDLER using arrow-like syntax will use this for creating customer objects too
        submit.setOnAction(e -> {
            String nameField = empNametxt.getText(),
                    addressField = empAddresstxt.getText(),
                    phoneField = empPhonetxt.getText(),
                    titleField = empTitletxt.getText(),
                    birthDateField = empBirthDatetxt.getText();
            Double salaryField;
        //booleans used as flag variables to check if valid information is provided
            boolean validPhone = true;
            boolean validBirthDate = true;
            boolean validSalary = true;
            //loop over phone and make sure it is all numbers
            for(int i = 0; i < phoneField.length(); i++){
                if(!Character.isDigit(phoneField.charAt(i))){
                    validPhone = false;
                    errorLbl.setText("Invalid Phone number! Numbers only please.");
                    errorLbl.setTextFill(Color.web("#ff0000"));
                }
            }
            //try/catch to parse the empSalary to test if it is numerical
            try{
                Double.parseDouble(empSalarytxt.getText());
            }catch(NumberFormatException b){
                validSalary = false;
                errorLbl.setText("Invalid Salary! Numbers only please.");
                errorLbl.setTextFill(Color.web("#ff0000"));
            }
            //test the birthdate to make sure it is valid
            if(birthDateField.length() != 10 || birthDateField.charAt(4) != '/'|| birthDateField.charAt(7) != '/'){
                validBirthDate = false;
                errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                errorLbl.setTextFill(Color.web("#ff0000"));
            }
            //loop over birthDate to make sure the necessary places are numerical
            for(int i = 0; i < birthDateField.length(); i++){
                if(i <4 && !Character.isDigit(birthDateField.charAt(i))){
                    validBirthDate = false;
                    errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                    errorLbl.setTextFill(Color.web("#ff0000"));
                } else if (i > 4 && i < 7 && !Character.isDigit(birthDateField.charAt(i))){
                    validBirthDate = false;
                    errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                    errorLbl.setTextFill(Color.web("#ff0000"));
                } else if (i > 7 && !Character.isDigit(birthDateField.charAt(i))){
                    validBirthDate = false;
                    errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                    errorLbl.setTextFill(Color.web("#ff0000"));
                }
            }
            //if all the input fields are valid then process the data
            if(validPhone && validBirthDate && validSalary) {
                salaryField = Double.parseDouble(empSalarytxt.getText());
                //create a new employee with the information
                Employee newEmployee = new Employee(nameField, addressField,
                        phoneField, titleField, empIsManagerBtn.isSelected(),
                        birthDateField, salaryField);

                business.employees.add(newEmployee); //add them to the ArrayList
                try {
                    //create a connection and execute an update on the mySQL database
                    Statement addEmp = connection.createStatement();
                    String updateEmps = "INSERT INTO employees (name, address, phone, title, isManager, "
                            + "birthdate, salary) VALUES ('" + nameField +"', '" + addressField + "', '" + phoneField +
                            "', '" + titleField + "', " + empIsManagerBtn.isSelected() + ", '" + birthDateField + "', " +
                            salaryField + ")";
                    addEmp.executeUpdate(updateEmps);
                    addEmp.close();
                }catch(SQLException ex){
                    System.out.println(ex);
                }
                start(stage);
            }
        });
        //add all elements to the pane and set the stage using the created scene
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
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
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
        custNametxt.setMaxHeight(8);
        custNametxt.setMaxWidth(100);
        custNamelbl.setMaxWidth(150);
        Label custAddresslbl = new Label("Address: ");
        TextArea custAddresstxt = new TextArea();
        custAddresstxt.setMaxHeight(8);
        custAddresstxt.setMaxWidth(100);
        custAddresslbl.setMaxWidth(150);
        Label custPhonelbl = new Label("Phone (numbers only): ");
        TextArea custPhonetxt = new TextArea();
        custPhonetxt.setMaxHeight(8);
        custPhonetxt.setMaxWidth(100);
        custPhonelbl.setMaxWidth(150);
        Label custAmtlbl = new Label("Amount Spent (numbers only): ");
        TextArea custAmttxt = new TextArea();
        custAmttxt.setMaxHeight(8);
        custAmttxt.setMaxWidth(100);
        custAmtlbl.setMaxWidth(200);
        Label errorLabel = new Label(); //error label for the input fields

        toMain.setOnAction(e->{
            start(stage);
        });
        //add all elements to the pane and the proper formatting
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
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        //EVENT HANDLER using arrow-like syntax to add customer to customer array in business obj
        submit.setOnAction(e -> {
        //when the submit button is click we test to make sure provided information is valid
            String custName = custNametxt.getText(),
                    custAddress = custAddresstxt.getText(),
                    custPhone = custPhonetxt.getText(),
                    custAmountSpent = custAmttxt.getText();

            boolean validPhone = true;
            boolean validAmountSpent = true;
        //make sure the phone number is entirely numeric
            for(int i = 0; i < custPhone.length(); i++){
                if(!Character.isDigit(custPhone.charAt(i))){
                    validPhone = false;
                    errorLabel.setText("Invalid Phone Number provided. Numbers only please.");
                    errorLabel.setTextFill(Color.web("#ff0000"));
                }
            }
        //make sure the amount spent is also entirely numeric
            for(int i=0; i < custAmountSpent.length(); i++){
                if(!Character.isDigit(custAmountSpent.charAt(i))){
                    validAmountSpent = false;
                    errorLabel.setText("Invalid Amount Spent provided. Numbers only please.");
                    errorLabel.setTextFill(Color.web("#ff0000"));
                }
            }
            //if the information is valid then create a new customer, add them to the ArrayList, and execute an update on the mySQL database
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

        menu.setOnAction(e->{
            start(stage);
        });

        Label nameLbl = new Label("Name: ");
        TextArea nameTxt = new TextArea();
        nameTxt.setMaxWidth(100);
        nameTxt.setMaxHeight(8);
        nameLbl.setMaxWidth(150);
        Label phoneLbl = new Label("Phone");
        TextArea phoneTxt = new TextArea();
        phoneTxt.setMaxWidth(100);
        phoneTxt.setMaxHeight(8);
        phoneLbl.setMaxWidth(150);
        Button submit = new Button("DELETE");
        Label errorLabel = new Label(); //error label to show if employee not found

        submit.setOnAction(e->{
            String name = nameTxt.getText();
            String phone = phoneTxt.getText();

            try{
                Statement removeEmployee = connection.createStatement();

                int targEmployeeIndex = -1;
            //search through the list and find the employee
                for (Employee employee : business.employees) {
                    if(employee.name.equals(name) && employee.phone.equals(phone)){
                        targEmployeeIndex = business.employees.indexOf(employee);
                    }
                }
                if(targEmployeeIndex != -1){ //if the index is not -1 then we have found the employee, so remove them from the ArrayList and
                    //execute an update on the mySQL database
                    business.employees.remove(targEmployeeIndex);
                    String updateEmployees = "DELETE FROM employees WHERE (name= '" + name + "' && phone= '"+phone+"')";
                    removeEmployee.executeUpdate(updateEmployees);
                    removeEmployee.close();
                    start(stage);
                } else {
                    errorLabel.setText("Employee not found.");
                    errorLabel.setTextFill(Color.web("#ff0000"));

                }
            }catch(SQLException ex){
                System.out.println(ex);
            }

        });
        //add all elements to the pane and add formatting
        pane.add(menu, 0, 0);
        pane.add(nameLbl, 0, 1);
        pane.add(nameTxt, 1, 1);
        pane.add(phoneLbl, 0, 2);
        pane.add(phoneTxt, 1, 2);
        pane.add(submit, 0, 3);
        pane.add(errorLabel, 0, 4);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        stage.setTitle("Remove Employee");
        stage.setScene(removeEmp);
        stage.show();
    }

    public void removeCustomer(Stage stage){
        //works the same a removeEmployee
        GridPane pane = new GridPane();
        Scene removeCust = new Scene(pane, 500, 500);
        Button menu = new Button("MENU");

        menu.setOnAction(e->{
            start(stage);
        });

        Label nameLbl = new Label("Name");
        TextArea nameTxt = new TextArea();
        nameTxt.setMaxHeight(8);
        nameTxt.setMaxWidth(100);
        nameLbl.setMaxWidth(150);

        Label phoneLbl = new Label("Phone");
        TextArea phoneTxt = new TextArea();
        phoneTxt.setMaxWidth(100);
        phoneTxt.setMaxHeight(8);
        phoneLbl.setMaxWidth(150);

        Button submit = new Button("DELETE");
        Label errorLabel = new Label(); //error label to show if employee not found
        //once we submit our input
        submit.setOnAction(e-> {
                    String name = nameTxt.getText();
                    String phone = phoneTxt.getText();

                    try {
                        Statement removeCustomer = connection.createStatement();

                        int targIndex = -1;
                        for (Customer customer : business.customers) { //search the customers ArrayList to try to find a match
                            if (customer.name.equals(name) && customer.phone.equals(phone)) {
                                targIndex = business.customers.indexOf(customer);
                            }
                        }
                        if(targIndex != -1) { //if the index is not -1 then we found a match
                            //so remove the customer from the ArrayList and execute an update on the mySQL database
                            business.customers.remove(targIndex);
                            String updateCustomers = "DELETE FROM customers WHERE (name= '" + name + "' && phone= '" + phone + "')";
                            removeCustomer.executeUpdate(updateCustomers);
                            removeCustomer.close();
                            start(stage);
                        } else {
                            errorLabel.setText("Customer not found.");
                            errorLabel.setTextFill(Color.web("#ff0000"));
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                });
    //add all elements to the pane and set the scene using created scene
        pane.add(menu, 0, 0);
        pane.add(nameLbl, 0, 1);
        pane.add(nameTxt, 1, 1);
        pane.add(phoneLbl, 0, 2);
        pane.add(phoneTxt, 1, 2);
        pane.add(submit, 0, 3);
        pane.add(errorLabel, 0, 4);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        stage.setTitle("Remove Customer");
        stage.setScene(removeCust);
        stage.show();
    }

    public void updateCustomers(Stage stage){
        GridPane pane = new GridPane();
        Scene updateCust = new Scene(pane, 500, 500);

        Button menu = new Button("MENU");

        menu.setOnAction(e->{
            start(stage);
        });

        Label nameLbl = new Label("Name");
        TextArea nameTxt = new TextArea();
        nameTxt.setMaxHeight(8);
        nameTxt.setMaxWidth(100);
        nameLbl.setMaxWidth(150);

        Label phoneLbl = new Label("Phone");
        TextArea phoneTxt = new TextArea();
        phoneTxt.setMaxHeight(8);
        phoneTxt.setMaxWidth(100);
        phoneLbl.setMaxWidth(150);

        Button search = new Button("SEARCH");

        Label errorLabel = new Label(); //displays if no customer is found that matches

        search.setOnAction(e->{ //when we do our search

            String targName = nameTxt.getText();
            String targPhone = phoneTxt.getText();

            int targCustIndex = -1;

            for(Customer customer : business.customers){ //try to find a matching customer and return their index
                if(customer.name.equals(targName) && customer.phone.equals(targPhone)){
                    targCustIndex = business.customers.indexOf(customer);
                }
            }

            if(targCustIndex != -1){ //if index != -1 then we found the customer

                int finalTargCustIndex = targCustIndex;
                Customer targetCustomer = business.customers.get(finalTargCustIndex);
                //create a copy of that customer and show a containing their information that can be edited
                Label newNameLbl = new Label("Name: ");
                TextArea newNameTxt = new TextArea(targetCustomer.name);
                newNameTxt.setMaxWidth(100);
                newNameTxt.setMaxHeight(8);
                newNameLbl.setMaxWidth(150);

                Label newAddressLbl = new Label("Address: ");
                TextArea newAddressTxt = new TextArea(targetCustomer.address);
                newAddressTxt.setMaxHeight(8);
                newAddressTxt.setMaxWidth(100);
                newAddressLbl.setMaxWidth(150);

                Label newPhoneLbl = new Label("Phone: ");
                TextArea newPhoneTxt = new TextArea(targetCustomer.phone);
                newPhoneTxt.setMaxWidth(100);
                newPhoneTxt.setMaxHeight(8);
                newPhoneLbl.setMaxWidth(150);

                Label newAmountSpentLbl = new Label("Title: ");
                TextArea newAmountSpentTxt = new TextArea(targetCustomer.amountSpent.toString());
                newAmountSpentTxt.setMaxHeight(8);
                newAmountSpentTxt.setMaxWidth(100);
                newAmountSpentLbl.setMaxWidth(200);

                Button submit = new Button("SUBMIT UPDATES");

                Label errorLbl = new Label(); //error label for if invalid information is put into form


                submit.setOnAction(a->{ //when we submit new information
                    boolean validPhone = true;
                    boolean validSalary = true;
                    //make sure phone is completely numeric
                    for(int i = 0; i < newPhoneTxt.getText().length(); i++){
                        if(!Character.isDigit(newPhoneTxt.getText().charAt(i))){
                            validPhone = false;
                            errorLbl.setText("Invalid Phone number! Numbers only please.");
                            errorLbl.setTextFill(Color.web("#ff0000"));
                        }
                    }
                    //make sure amount spent is numeric
                    try{
                        Double.parseDouble(newAmountSpentTxt.getText());
                    }catch(NumberFormatException b){
                        validSalary = false;
                        errorLbl.setText("Invalid Salary! Numbers only please.");
                        errorLbl.setTextFill(Color.web("#ff0000"));
                    }

                    //if they are both valid then set the customers information to be the new values
                    if(validPhone && validSalary){
                        Customer targ = business.customers.get(finalTargCustIndex);
                        targ.name = newNameTxt.getText();
                        targ.address = newAddressTxt.getText();
                        targ.phone = newPhoneTxt.getText();
                        targ.amountSpent = Double.parseDouble(newAmountSpentTxt.getText());

                        try {
                            //execute an update to set the new values into the mySQL database
                            Statement updateCustomer = connection.createStatement();
                            String updateCustomers = "UPDATE customers SET name = '"+ newNameTxt.getText() +
                                    "', address='" + newAddressTxt.getText() + "', phone='" + newPhoneTxt.getText() + "', amountSpent=" +
                            Double.parseDouble(newAmountSpentTxt.getText()) + " WHERE (name= '" + targName + "' && phone= '" + targPhone + "')";
                            updateCustomer.executeUpdate(updateCustomers);
                            updateCustomer.close();
                            start(stage); //return home if the update was successful
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                //set the pane with the form elements for the new information
                pane.add(newNameLbl, 0, 6);
                pane.add(newNameTxt, 1, 6);
                pane.add(newAddressLbl, 0, 7);
                pane.add(newAddressTxt, 1, 7);
                pane.add(newPhoneLbl, 0, 8);
                pane.add(newPhoneTxt, 1, 8);
                pane.add(newAmountSpentLbl, 0, 9);
                pane.add(newAmountSpentTxt, 1, 9);
                pane.add(submit, 0, 11);
                pane.add(errorLbl, 0, 12);
            }else {
                errorLabel.setText("Customer not found.");
                errorLabel.setTextFill(Color.web("#ff0000"));
            }
        });
        //add elements to the pane
         pane.add(menu, 0, 0);
         pane.add(nameLbl, 0, 1);
         pane.add(nameTxt, 1, 1);
         pane.add(phoneLbl, 0, 2);
         pane.add(phoneTxt, 1, 2);
         pane.add(search, 0, 3);
         pane.add(errorLabel, 0, 5);
         pane.setAlignment(Pos.CENTER);
         pane.setVgap(10);
        stage.setTitle("Update Customers");
        stage.setScene(updateCust);
        stage.show();
    };

    public void updateEmployee(Stage stage){
        //works the same as updateCustomers
        GridPane pane = new GridPane();
        Scene updateEmp = new Scene(pane, 500, 500);

        Button menu = new Button("MENU");

        menu.setOnAction(e->{
            start(stage);
        });

        Label nameLbl = new Label("Name");
        TextArea nameTxt = new TextArea();
        nameTxt.setMaxHeight(8);
        nameTxt.setMaxWidth(100);
        nameLbl.setMaxWidth(150);

        Label phoneLbl = new Label("Phone");
        TextArea phoneTxt = new TextArea();
        phoneTxt.setMaxHeight(8);
        phoneTxt.setMaxWidth(100);
        phoneLbl.setMaxWidth(150);

        Button search = new Button("SEARCH");

        Label errorLabel = new Label(); //error label for if employee is not found

        search.setOnAction(e->{ //when we submit our search

            String targName = nameTxt.getText();
            String targPhone = phoneTxt.getText();

            int targEmpIndex = -1;

            for(Employee employee : business.employees){ //search the ArrayList for the employee
                if(employee.name.equals(targName) && employee.phone.equals(targPhone)){
                    targEmpIndex = business.employees.indexOf(employee); //set the index if we find employee
                }
            }

            if(targEmpIndex != -1){ //if the index is no longer -1 then we found the employee
                int finalTargEmpIndex = targEmpIndex;
                Employee targetEmployee = business.employees.get(finalTargEmpIndex);

                Label newNameLbl = new Label("Name: ");
                TextArea newNameTxt = new TextArea(targetEmployee.name);
                newNameTxt.setMaxWidth(100);
                newNameTxt.setMaxHeight(8);
                newNameLbl.setMaxWidth(150);

                Label newAddressLbl = new Label("Address: ");
                TextArea newAddressTxt = new TextArea(targetEmployee.address);
                newAddressTxt.setMaxHeight(8);
                newAddressTxt.setMaxWidth(100);
                newAddressLbl.setMaxWidth(150);

                Label newPhoneLbl = new Label("Phone: ");
                TextArea newPhoneTxt = new TextArea(targetEmployee.phone);
                newPhoneTxt.setMaxWidth(100);
                newPhoneTxt.setMaxHeight(8);
                newPhoneLbl.setMaxWidth(150);

                Label newTitleLbl = new Label("Title: ");
                TextArea newTitleTxt = new TextArea(targetEmployee.title);
                newTitleTxt.setMaxHeight(8);
                newTitleTxt.setMaxWidth(100);
                newTitleLbl.setMaxWidth(150);

                final ToggleGroup managerial = new ToggleGroup();
                RadioButton empIsManagerBtn = new RadioButton("Manager");
                empIsManagerBtn.setToggleGroup(managerial);
                RadioButton empNonManagerBtn = new RadioButton("Non-manager");
                empNonManagerBtn.setToggleGroup(managerial);
                empNonManagerBtn.setSelected(true);

                Label newBirthDateLbl = new Label("Birth Date: ");
                TextArea newBirthDateTxt = new TextArea(targetEmployee.birthDate);
                newBirthDateTxt.setMaxHeight(8);
                newBirthDateTxt.setMaxWidth(100);
                newBirthDateLbl.setMaxWidth(150);

                Label newSalaryLbl = new Label("Salary: ");
                TextArea newSalaryTxt = new TextArea(targetEmployee.salary.toString());
                newSalaryTxt.setMaxHeight(8);
                newSalaryTxt.setMaxWidth(100);
                newSalaryLbl.setMaxWidth(150);

                Button submit = new Button("SUBMIT UPDATES");

                Label errorLbl = new Label(); //error label for if invalid info is provided in form


                submit.setOnAction(a->{ //when we submit new employee info
                    boolean validPhone = true;
                    boolean validBirthDate = true;
                    boolean validSalary = true;
                //make sure phone number is numeric
                    for(int i = 0; i < newPhoneTxt.getText().length(); i++){
                        if(!Character.isDigit(newPhoneTxt.getText().charAt(i))){
                            validPhone = false;
                            errorLbl.setText("Invalid Phone number! Numbers only please.");
                            errorLbl.setTextFill(Color.web("#ff0000"));
                        }
                    }
                //make sure salary is numeric
                    try{
                        Double.parseDouble(newSalaryTxt.getText());
                    }catch(NumberFormatException b){
                        validSalary = false;
                        errorLbl.setText("Invalid Salary! Numbers only please.");
                        errorLbl.setTextFill(Color.web("ff0000"));
                    }
            //make sure birthday is correct length and test characters at the separation points
                    if(newBirthDateTxt.getText().length() != 10 || newBirthDateTxt.getText().charAt(4) != '/' ||
                            newBirthDateTxt.getText().charAt(7) != '/'){
                        validBirthDate = false;
                        errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                        errorLbl.setTextFill(Color.web("#ff0000"));
                    }

                    for(int i = 0; i < newBirthDateTxt.getText().length(); i++){
                        if(i <4 && !Character.isDigit(newBirthDateTxt.getText().charAt(i))){
                            validBirthDate = false;
                            errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                            errorLbl.setTextFill(Color.web("#ff0000"));
                        } else if (i > 4 && i < 7 && !Character.isDigit(newBirthDateTxt.getText().charAt(i))){
                            validBirthDate = false;
                            errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                            errorLbl.setTextFill(Color.web("#ff0000"));
                        } else if (i > 7 && !Character.isDigit(newBirthDateTxt.getText().charAt(i))){
                            validBirthDate = false;
                            errorLbl.setText("Invalid Birth Date provided! Make sure it is formatted properly.");
                            errorLbl.setTextFill(Color.web("#ff0000"));
                        }
                    }
                //once we have valid information set the employee's information to the provided info
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
                            //execute an update on the mySQL database
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
                        }
                    }
                });
            //add the form for new information to the pane
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
            } else {
                errorLabel.setText("Employee not found.");
                errorLabel.setTextFill(Color.web("#ff0000"));
            }
        });
        //add the initial search form to the pane
        pane.add(menu, 0, 0);
        pane.add(nameLbl, 0, 1);
        pane.add(nameTxt, 1, 1);
        pane.add(phoneLbl, 0, 2);
        pane.add(phoneTxt, 1, 2);
        pane.add(search, 0, 3);
        pane.add(errorLabel, 0, 4);
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        stage.setTitle("Update Employee");
        stage.setScene(updateEmp);
        stage.show();
    };

    public static void main(String[] args) {
        //launch the application
        Application.launch(args);
    }
}
