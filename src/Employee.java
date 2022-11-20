import java.util.UUID;
//Employee Class to create Employee objects
public class Employee {
    //class variables
    public String uniqueID;
    public String name;
    public String address;
    public String phone;
    public String title;
    public boolean isManager;
    public String birthDate;
    public Double salary;

    //default constructor
    public Employee(){
        this.name = "John Doe";
        this.address = "135 Default Ln. Wilmington, NC.";
        this.phone = "(910)-660-1677";
        this.title = "CEO";
        this.isManager = false;
        this.birthDate = "1995/06/16";
        this.salary = 1.0;
    }

    //constructor with parameters
    public Employee(String name, String address, String phone, String title, boolean isManager,
                    String birthDate, Double salary){
        this.uniqueID = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.title = title;
        this.isManager = isManager;
        this.birthDate = birthDate;
        this.salary = salary;
    }



}