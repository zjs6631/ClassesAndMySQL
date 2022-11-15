//this is the Company class used to create a Singleton class of
//the company used in this project


import java.util.ArrayList;

public class Company {

    public static Company company; //create a variable to hold the single instance

    private Company() {
        //make a private constructor so that there is no external access to construction of
        //this object
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
    };

    public static Company getInstanceOfCompany(){
         if(company == null){ //if the variable is null then we create a new instance
             company = new Company();
         }

        return company;
    }


}
