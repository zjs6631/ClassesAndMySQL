import java.util.UUID;
//customer class to create customer objects
public class Customer {
    public String uniqueID;
    public String name;
    public String address;
    public String phone;
    public Double amountSpent;

    public Customer(){
        this.name = "John Doe";
        this.address = "135 Default Ln. Wilmington, NC.";
        this.phone = "(910)-660-1677";
        this.amountSpent = 0.0;
    }

    public Customer(String name, String address, String phone, Double amountSpent){
        this.uniqueID = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.amountSpent = amountSpent;
    }

}
