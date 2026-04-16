import java.util.Date;

// Reservation Entity Class
public class Reservation {
    private String reservationID;
    private String customerID;
    private String tableNumber;
    private Date reservationDate;
    private int numberOfGuests;
    private String status;
    
    public Reservation(String reservationID, String customerID, String tableNumber, 
                      Date reservationDate, int numberOfGuests) {
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.tableNumber = tableNumber;
        this.reservationDate = reservationDate;
        this.numberOfGuests = numberOfGuests;
        this.status = "Confirmed";
    }
    
    // Getters
    public String getReservationID() { 
        return reservationID; 
    }
    
    public String getCustomerID() { 
        return customerID; 
    }
    
    public String getTableNumber() { 
        return tableNumber; 
    }
    
    public Date getReservationDate() { 
        return reservationDate; 
    }
    
    public int getNumberOfGuests() { 
        return numberOfGuests; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    // Setters
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public void setReservationDate(Date date) { 
        this.reservationDate = date; 
    }
    
    public void setTableNumber(String tableNumber) { 
        this.tableNumber = tableNumber; 
    }
    
    public void setNumberOfGuests(int numberOfGuests) { 
        this.numberOfGuests = numberOfGuests; 
    }
    
    // Business methods
    public void cancel() {
        this.status = "Cancelled";
    }
    
    public void complete() {
        this.status = "Completed";
    }
    
    public void noShow() {
        this.status = "No-Show";
    }
    
    @Override
    public String toString() {
        return String.format("Reservation[ID=%s, Customer=%s, Table=%s, Guests=%d, Status=%s]", 
                           reservationID, customerID, tableNumber, numberOfGuests, status);
    }
}
