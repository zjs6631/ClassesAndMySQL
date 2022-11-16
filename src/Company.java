//this is the Company class used to create a Singleton class of
//the company used in this project


import java.lang.reflect.Array;
import java.util.ArrayList;

public class Company {

    public static Company company = null; //create a variable to hold the single instance

    public ArrayList<Employee> employees = new ArrayList<>();
    public ArrayList<Customer> customers = new ArrayList<>();

    private Company() {
        //make a private constructor so that there is no external access to construction of
        //this object
    };

    public static Company getInstanceOfCompany(){
         if(company == null){ //if the variable is null then we create a new instance
             company = new Company();
         }

        return company;
    }

    public void addEmp(Employee employee){
        employees.add(employee);
    }


}
