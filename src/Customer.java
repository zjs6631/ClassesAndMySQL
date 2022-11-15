public class Customer {
    private String name;
    private String address;
    private String phone;
    private Double amountSpent;

    public Customer(){
        this.name = "John Doe";
        this.address = "135 Default Ln. Wilmington, NC.";
        this.phone = "(910)-660-1677";
        this.amountSpent = 0.0;
    }

    public Customer(String name, String address, String phone, Double amountSpent){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.amountSpent = amountSpent;
    }

}
