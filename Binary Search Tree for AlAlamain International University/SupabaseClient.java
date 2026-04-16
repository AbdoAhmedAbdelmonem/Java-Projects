import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class SupabaseClient {
    private static final String SUPABASE_URL = "https://ikyudcmihpqjxbsaywpx.supabase.co";
    private static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlreXVkY21paHBxanhic2F5d3B4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQ2NjUxNzQsImV4cCI6MjA4MDI0MTE3NH0.EMXIVmFQpxdGbPRPEAbJ-Uc8PhYq6SvizOPLHDt-NSY";
    private static final String REST_API_URL = SUPABASE_URL + "/rest/v1";
    
    private HttpClient httpClient;
    
    public SupabaseClient() {
        this.httpClient = HttpClient.newHttpClient();
    }
    
    // Generic method to send GET request
    private String sendGetRequest(String endpoint) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(REST_API_URL + endpoint))
            .header("apikey", SUPABASE_KEY)
            .header("Authorization", "Bearer " + SUPABASE_KEY)
            .header("Content-Type", "application/json")
            .GET()
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() >= 400) {
            throw new Exception("HTTP " + response.statusCode() + ": " + response.body());
        }
        
        return response.body();
    }
    
    // Generic method to send POST request
    private String sendPostRequest(String endpoint, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(REST_API_URL + endpoint))
            .header("apikey", SUPABASE_KEY)
            .header("Authorization", "Bearer " + SUPABASE_KEY)
            .header("Content-Type", "application/json")
            .header("Prefer", "return=representation")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() >= 400) {
            throw new Exception("HTTP " + response.statusCode() + ": " + response.body());
        }
        
        return response.body();
    }
    
    // Generic method to send PATCH request
    private String sendPatchRequest(String endpoint, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(REST_API_URL + endpoint))
            .header("apikey", SUPABASE_KEY)
            .header("Authorization", "Bearer " + SUPABASE_KEY)
            .header("Content-Type", "application/json")
            .header("Prefer", "return=representation")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() >= 400) {
            throw new Exception("HTTP " + response.statusCode() + ": " + response.body());
        }
        
        return response.body();
    }
    
    // Generic method to send DELETE request
    private String sendDeleteRequest(String endpoint) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(REST_API_URL + endpoint))
            .header("apikey", SUPABASE_KEY)
            .header("Authorization", "Bearer " + SUPABASE_KEY)
            .DELETE()
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() >= 400) {
            throw new Exception("HTTP " + response.statusCode() + ": " + response.body());
        }
        
        return response.body();
    }
    
    // ==================== CUSTOMER OPERATIONS ====================
    
    public List<Customer> getAllCustomers() throws Exception {
        String response = sendGetRequest("/customers?select=*&order=customer_id.asc");
        JSONArray jsonArray = new JSONArray(response);
        List<Customer> customers = new ArrayList<>();
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            Customer customer = new Customer(
                json.getString("customer_id"),
                json.getString("name"),
                json.optString("phone", ""),
                json.optString("email", ""),
                json.optString("membership_type", "Standard")
            );
            customers.add(customer);
        }
        
        return customers;
    }
    
    public Customer getCustomerById(String customerId) throws Exception {
        String response = sendGetRequest("/customers?customer_id=eq." + customerId);
        JSONArray jsonArray = new JSONArray(response);
        
        if (jsonArray.length() == 0) return null;
        
        JSONObject json = jsonArray.getJSONObject(0);
        Customer customer = new Customer(
            json.getString("customer_id"),
            json.getString("name"),
            json.optString("phone", ""),
            json.optString("email", ""),
            json.optString("membership_type", "Standard")
        );
        return customer;
    }
    
    public boolean insertCustomer(Customer customer) throws Exception {
        JSONObject json = new JSONObject();
        json.put("customer_id", customer.getCustomerID());
        json.put("name", customer.getName());
        json.put("phone", customer.getPhone());
        json.put("email", customer.getEmail());
        json.put("membership_type", customer.getMembershipType());
        
        sendPostRequest("/customers", json.toString());
        return true;
    }
    
    public boolean updateCustomer(Customer customer) throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", customer.getName());
        json.put("phone", customer.getPhone());
        json.put("email", customer.getEmail());
        json.put("membership_type", customer.getMembershipType());
        
        sendPatchRequest("/customers?customer_id=eq." + customer.getCustomerID(), json.toString());
        return true;
    }
    
    public boolean deleteCustomer(String customerId) throws Exception {
        sendDeleteRequest("/customers?customer_id=eq." + customerId);
        return true;
    }
    
    // ==================== RESERVATION OPERATIONS ====================
    
    public List<Reservation> getAllReservations() throws Exception {
        String response = sendGetRequest("/reservations?select=*&order=reservation_date.desc");
        JSONArray jsonArray = new JSONArray(response);
        List<Reservation> reservations = new ArrayList<>();
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            
            // Parse ISO 8601 timestamp
            String dateStr = json.getString("reservation_date");
            java.util.Date date = parseISODate(dateStr);
            
            Reservation reservation = new Reservation(
                json.getString("reservation_id"),
                json.getString("customer_id"),
                json.getString("table_number"),
                date,
                json.getInt("number_of_guests")
            );
            reservation.setStatus(json.optString("status", "Confirmed"));
            reservations.add(reservation);
        }
        
        return reservations;
    }
    
    public Reservation getReservationById(String reservationId) throws Exception {
        String response = sendGetRequest("/reservations?reservation_id=eq." + reservationId);
        JSONArray jsonArray = new JSONArray(response);
        
        if (jsonArray.length() == 0) return null;
        
        JSONObject json = jsonArray.getJSONObject(0);
        String dateStr = json.getString("reservation_date");
        java.util.Date date = parseISODate(dateStr);
        
        Reservation reservation = new Reservation(
            json.getString("reservation_id"),
            json.getString("customer_id"),
            json.getString("table_number"),
            date,
            json.getInt("number_of_guests")
        );
        reservation.setStatus(json.optString("status", "Confirmed"));
        return reservation;
    }
    
    public boolean insertReservation(Reservation reservation) throws Exception {
        JSONObject json = new JSONObject();
        json.put("reservation_id", reservation.getReservationID());
        json.put("customer_id", reservation.getCustomerID());
        json.put("table_number", reservation.getTableNumber());
        
        // Format date as ISO 8601
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        json.put("reservation_date", sdf.format(reservation.getReservationDate()));
        
        json.put("number_of_guests", reservation.getNumberOfGuests());
        json.put("status", reservation.getStatus());
        
        sendPostRequest("/reservations", json.toString());
        return true;
    }
    
    public boolean updateReservation(Reservation reservation) throws Exception {
        JSONObject json = new JSONObject();
        json.put("customer_id", reservation.getCustomerID());
        json.put("table_number", reservation.getTableNumber());
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        json.put("reservation_date", sdf.format(reservation.getReservationDate()));
        
        json.put("number_of_guests", reservation.getNumberOfGuests());
        json.put("status", reservation.getStatus());
        
        sendPatchRequest("/reservations?reservation_id=eq." + reservation.getReservationID(), json.toString());
        return true;
    }
    
    public boolean deleteReservation(String reservationId) throws Exception {
        sendDeleteRequest("/reservations?reservation_id=eq." + reservationId);
        return true;
    }
    
    // Check if table is available for a specific date
    public boolean isTableAvailable(String tableNumber, java.util.Date date) throws Exception {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        
        String response = sendGetRequest(
            "/reservations?table_number=eq." + tableNumber + 
            "&reservation_date=gte." + dateStr + "T00:00:00" +
            "&reservation_date=lte." + dateStr + "T23:59:59" +
            "&status=neq.Cancelled"
        );
        
        JSONArray jsonArray = new JSONArray(response);
        return jsonArray.length() == 0;
    }
    
    // Helper method to parse ISO 8601 date string
    private java.util.Date parseISODate(String dateStr) {
        try {
            // Handle formats like: 2024-12-02T15:30:00 or 2024-12-02T15:30:00.000Z
            dateStr = dateStr.replace("Z", "+00:00");
            if (dateStr.contains(".")) {
                dateStr = dateStr.substring(0, dateStr.indexOf('.'));
            }
            if (dateStr.contains("+")) {
                dateStr = dateStr.substring(0, dateStr.indexOf('+'));
            }
            
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf.parse(dateStr);
        } catch (Exception e) {
            System.err.println("Failed to parse date: " + dateStr);
            return new java.util.Date();
        }
    }
}
