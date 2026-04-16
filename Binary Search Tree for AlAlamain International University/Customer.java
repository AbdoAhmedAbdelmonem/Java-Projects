// Customer Entity Class
public class Customer implements Comparable<Customer> {
    private String customerID;
    private String name;
    private String phone;
    private String email;
    private String membershipType;
    
    public Customer(String customerID, String name, String phone, 
                   String email, String membershipType) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.membershipType = membershipType;
    }
    
    // Getters
    public String getCustomerID() { 
        return customerID; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public String getPhone() { 
        return phone; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public String getMembershipType() { 
        return membershipType; 
    }
    
    // Setters
    public void setName(String name) { 
        this.name = name; 
    }
    
    public void setPhone(String phone) { 
        this.phone = phone; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public void setMembershipType(String membershipType) { 
        this.membershipType = membershipType; 
    }
    
    @Override
    public int compareTo(Customer other) {
        return this.customerID.compareTo(other.customerID);
    }
    
    @Override
    public String toString() {
        return String.format("Customer[ID=%s, Name=%s, Phone=%s, Membership=%s]", 
                           customerID, name, phone, membershipType);
    }
}
