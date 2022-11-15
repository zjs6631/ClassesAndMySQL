import java.util.ArrayList;

public class Manager extends Employee {
    ArrayList<Employee> managedEmployees = new ArrayList<>();

    public void addEmployee(ArrayList<Employee> managedEmployees, Employee newEmployee){
        managedEmployees.add(newEmployee);
    }

    public void removeEmployee(ArrayList<Employee> managedEmployees, Employee targEmployee){
        managedEmployees.remove(targEmployee);
    }

}
