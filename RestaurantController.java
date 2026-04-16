import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestaurantController {
    private BinarySearchTree<Customer> customers;
    private ArrayList<Reservation> reservations;
    private int customerCounter = 1000;
    private int reservationCounter = 5000;
    private RestaurantView view;
    private SupabaseClient supabase;
    private boolean useDatabase = true;
    
    public RestaurantController() {
        this.customers = new BinarySearchTree<>();
        this.reservations = new ArrayList<>();
        this.supabase = new SupabaseClient();
        
        if (useDatabase) {
            loadDataFromDatabase();
        }
    }
    
    private void loadDataFromDatabase() {
        try {
            System.out.println("🔄 Connecting to Supabase database...");
            List<Customer> dbCustomers = supabase.getAllCustomers();
            for (Customer c : dbCustomers) {
                customers.insert(c);
                String idNum = c.getCustomerID().substring(1);
                int num = Integer.parseInt(idNum);
                if (num >= customerCounter) customerCounter = num + 1;
            }
            
            reservations = new ArrayList<>(supabase.getAllReservations());
            for (Reservation r : reservations) {
                String idNum = r.getReservationID().substring(1);
                int num = Integer.parseInt(idNum);
                if (num >= reservationCounter) reservationCounter = num + 1;
            }
            
            System.out.println("✅ Loaded: " + dbCustomers.size() + " customers, " + reservations.size() + " reservations");
        } catch (Exception e) {
            System.err.println("⚠️ Database connection failed: " + e.getMessage());
            e.printStackTrace();
            useDatabase = false;
        }
    }
    
    public void showMainWindow() {
        this.view = new RestaurantView(this);
        view.setVisible(true);
    }
    
    public String addNewCustomer(String name, String phone, String email, String membershipType) {
        String id = "C" + (customerCounter++);
        Customer customer = new Customer(id, name, phone, email, membershipType);
        customers.insert(customer);
        
        if (useDatabase) {
            try {
                supabase.insertCustomer(customer);
            } catch (Exception e) {
                System.err.println("❌ Failed to save customer to database: " + e.getMessage());
            }
        }
        
        return id;
    }
    
    public List<Customer> getCustomerList() {
        return customers.getInorderTraversal();
    }
    
    public Customer searchCustomer(String customerID) {
        Customer searchKey = new Customer(customerID, "", "", "", "");
        return customers.search(searchKey);
    }
    
    public boolean deleteCustomer(String customerID) {
        Customer customer = searchCustomer(customerID);
        if (customer != null) {
            customers.delete(customer);
            
            if (useDatabase) {
                try {
                    supabase.deleteCustomer(customerID);
                } catch (Exception e) {
                    System.err.println("❌ Failed to delete customer from database: " + e.getMessage());
                }
            }
            
            return true;
        }
        return false;
    }
    
    public String makeNewReservation(String customerID, String tableNumber, int numberOfGuests) {
        Customer customer = searchCustomer(customerID);
        if (customer == null) {
            return null;
        }
        
        // Check if table is already reserved (only consider confirmed/completed reservations)
        Date newDate = new Date();
        for (Reservation r : reservations) {
            if (r.getTableNumber().equalsIgnoreCase(tableNumber) && 
                !r.getStatus().equals("Cancelled") &&
                isSameDay(r.getReservationDate(), newDate)) {
                return "TABLE_OCCUPIED"; // Special code to indicate table is occupied
            }
        }
        
        String id = "R" + (reservationCounter++);
        Reservation reservation = new Reservation(id, customerID, tableNumber, new Date(), numberOfGuests);
        reservations.add(reservation);
        
        if (useDatabase) {
            try {
                supabase.insertReservation(reservation);
            } catch (Exception e) {
                System.err.println("❌ Failed to save reservation to database: " + e.getMessage());
            }
        }
        
        return id;
    }
    
    // Helper method to check if two dates are on the same day
    private boolean isSameDay(Date date1, Date date2) {
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR) &&
               cal1.get(java.util.Calendar.DAY_OF_YEAR) == cal2.get(java.util.Calendar.DAY_OF_YEAR);
    }
    
    // Method to cancel a reservation and make the table available
    public boolean cancelReservation(String reservationID) {
        Reservation res = searchReservation(reservationID);
        if (res != null) {
            res.cancel();
            
            if (useDatabase) {
                try {
                    supabase.updateReservation(res);
                } catch (Exception e) {
                    System.err.println("❌ Failed to update reservation in database: " + e.getMessage());
                }
            }
            
            return true;
        }
        return false;
    }
    
    public List<Reservation> getReservationList() {
        return new ArrayList<>(reservations);
    }
    
    public List<Reservation> getReservationsSortedByDate() {
        List<Reservation> sorted = new ArrayList<>(reservations);
        mergeSortReservations(sorted, 0, sorted.size() - 1);
        return sorted;
    }
    
    public List<Customer> getCustomersSortedByName() {
        List<Customer> sorted = new ArrayList<>(getCustomerList());
        mergeSortCustomers(sorted, 0, sorted.size() - 1);
        return sorted;
    }
    
    private void mergeSortReservations(List<Reservation> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortReservations(list, left, mid);
            mergeSortReservations(list, mid + 1, right);
            mergeReservations(list, left, mid, right);
        }
    }
    
    private void mergeReservations(List<Reservation> list, int left, int mid, int right) {
        List<Reservation> temp = new ArrayList<>();
        int i = left, j = mid + 1;
        
        while (i <= mid && j <= right) {
            if (list.get(i).getReservationDate().compareTo(list.get(j).getReservationDate()) <= 0) {
                temp.add(list.get(i++));
            } else {
                temp.add(list.get(j++));
            }
        }
        
        while (i <= mid) temp.add(list.get(i++));
        while (j <= right) temp.add(list.get(j++));
        
        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }
    
    private void mergeSortCustomers(List<Customer> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortCustomers(list, left, mid);
            mergeSortCustomers(list, mid + 1, right);
            mergeCustomers(list, left, mid, right);
        }
    }
    
    private void mergeCustomers(List<Customer> list, int left, int mid, int right) {
        List<Customer> temp = new ArrayList<>();
        int i = left, j = mid + 1;
        
        while (i <= mid && j <= right) {
            if (list.get(i).getName().compareToIgnoreCase(list.get(j).getName()) <= 0) {
                temp.add(list.get(i++));
            } else {
                temp.add(list.get(j++));
            }
        }
        
        while (i <= mid) temp.add(list.get(i++));
        while (j <= right) temp.add(list.get(j++));
        
        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }
    
    public Reservation searchReservation(String reservationID) {
        for (Reservation res : reservations) {
            if (res.getReservationID().equals(reservationID)) {
                return res;
            }
        }
        return null;
    }
    
    public boolean deleteReservation(String reservationID) {
        Reservation res = searchReservation(reservationID);
        if (res != null) {
            reservations.remove(res);
            
            if (useDatabase) {
                try {
                    supabase.deleteReservation(reservationID);
                } catch (Exception e) {
                    System.err.println("❌ Failed to delete reservation from database: " + e.getMessage());
                }
            }
            
            return true;
        }
        return false;
    }
    
    public int getCustomerCount() {
        return getCustomerList().size();
    }
    
    public int getReservationCount() {
        return reservations.size();
    }
    
    public ArrayList<Integer> getSystemStatistics() {
        ArrayList<Integer> stats = new ArrayList<>();
        
        // Index 0: Total Customers
        stats.add(getCustomerList().size());
        
        // Index 1: Total Reservations  
        stats.add(reservations.size());
        
        // Count reservation statuses
        int confirmed = 0, completed = 0, cancelled = 0;
        for (Reservation r : reservations) {
            if (r.getStatus().equals("Confirmed")) confirmed++;
            else if (r.getStatus().equals("Completed")) completed++;
            else if (r.getStatus().equals("Cancelled")) cancelled++;
        }
        
        // Index 2: Confirmed Reservations
        stats.add(confirmed);
        
        // Index 3: Completed Reservations  
        stats.add(completed);
        
        // Index 4: Cancelled Reservations
        stats.add(cancelled);
        
        return stats;
    }
}
