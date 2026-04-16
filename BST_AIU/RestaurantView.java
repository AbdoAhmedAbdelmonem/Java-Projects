import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RestaurantView extends JFrame {
    private RestaurantController controller;
    private DefaultTableModel customerModel, reservationModel;
    
    private Color PRIMARY_LIGHT = new Color(52, 152, 219);
    private Color SUCCESS_LIGHT = new Color(46, 204, 113);
    private Color DANGER_LIGHT = new Color(231, 76, 60);
    private Color WARNING_LIGHT = new Color(241, 196, 15);
    private Color BG_LIGHT = new Color(255, 255, 255);
    private Color PANEL_LIGHT = new Color(245, 245, 245);
    private Color TEXT_LIGHT = new Color(33, 33, 33);
    private Color SIDEBAR_LIGHT = new Color(52, 73, 94);
    
    // Dark Mode Colors
    private Color PRIMARY_DARK = new Color(41, 128, 185);
    private Color SUCCESS_DARK = new Color(39, 174, 96);
    private Color DANGER_DARK = new Color(192, 57, 43);
    private Color WARNING_DARK = new Color(243, 156, 18);
    private Color BG_DARK = new Color(30, 30, 30);
    private Color PANEL_DARK = new Color(45, 45, 45);
    private Color TEXT_DARK = new Color(220, 220, 220);
    private Color SIDEBAR_DARK = new Color(20, 20, 20);
    
    private Color PRIMARY, SUCCESS, DANGER, WARNING, BG_COLOR, PANEL_COLOR, TEXT_COLOR, SIDEBAR_COLOR;
    private boolean isDarkMode = false;
    
    private JPanel mainPanel;
    private JPanel contentPanel;
    private String currentView = "dashboard";
    
    public RestaurantView(RestaurantController controller) {
        this.controller = controller;
        applyLightMode();
        
        setTitle("Restaurant Management System - CSE 111");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        
        JPanel header = createHeader();
        mainPanel.add(header, BorderLayout.NORTH);
        
        JPanel sidebar = createSidebar();
        mainPanel.add(sidebar, BorderLayout.WEST);
        
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BG_COLOR);
        contentPanel.add(createDashboard());
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private void applyLightMode() {
        isDarkMode = false;
        PRIMARY = PRIMARY_LIGHT;
        SUCCESS = SUCCESS_LIGHT;
        DANGER = DANGER_LIGHT;
        WARNING = WARNING_LIGHT;
        BG_COLOR = BG_LIGHT;
        PANEL_COLOR = PANEL_LIGHT;
        TEXT_COLOR = TEXT_LIGHT;
        SIDEBAR_COLOR = SIDEBAR_LIGHT;
    }
    
    private void applyDarkMode() {
        isDarkMode = true;
        PRIMARY = PRIMARY_DARK;
        SUCCESS = SUCCESS_DARK;
        DANGER = DANGER_DARK;
        WARNING = WARNING_DARK;
        BG_COLOR = BG_DARK;
        PANEL_COLOR = PANEL_DARK;
        TEXT_COLOR = TEXT_DARK;
        SIDEBAR_COLOR = SIDEBAR_DARK;
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("+ Restaurant Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);
        
        JLabel timeLabel = new JLabel(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timeLabel.setForeground(Color.WHITE);
        
        JToggleButton darkModeToggle = new JToggleButton(isDarkMode ? "[Light Mode]" : "[Dark Mode]");
        darkModeToggle.setFont(new Font("Arial", Font.BOLD, 12));
        darkModeToggle.setFocusPainted(false);
        darkModeToggle.setBackground(Color.WHITE);
        darkModeToggle.setForeground(PRIMARY);
        darkModeToggle.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        darkModeToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        darkModeToggle.addActionListener(e -> toggleDarkMode(darkModeToggle));
        
        rightPanel.add(timeLabel);
        rightPanel.add(darkModeToggle);
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private void toggleDarkMode(JToggleButton toggle) {
        if (isDarkMode) {
            applyLightMode();
            toggle.setText("[Dark Mode]");
        } else {
            applyDarkMode();
            toggle.setText("[Light Mode]");
        }
        
        mainPanel.removeAll();
        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createSidebar(), BorderLayout.WEST);
        
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BG_COLOR);
        
        switch(currentView) {
            case "dashboard": contentPanel.add(createDashboard()); break;
            case "customers": contentPanel.add(createCustomers()); break;
            case "reservations": contentPanel.add(createReservations()); break;
            case "reports": contentPanel.add(createReportsPanel()); break;
        }
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(SIDEBAR_COLOR);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 30, 15));
        logoPanel.setMaximumSize(new Dimension(220, 80));
        
        JLabel logoLabel = new JLabel("<html><div style='text-align:center;'><b>RESTAURANT<br>SYSTEM</b></div></html>");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel);
        
        sidebar.add(logoPanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        
        sidebar.add(createSidebarButton("Dashboard", "dashboard", PRIMARY));
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(createSidebarButton("Customers", "customers", SUCCESS));
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(createSidebarButton("Reservations", "reservations", WARNING));
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(createSidebarButton("Reports", "reports", DANGER));
        sidebar.add(Box.createVerticalGlue());
        
        return sidebar;
    }
    
    private JButton createSidebarButton(String text, String view, Color accentColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (currentView.equals(view)) {
            btn.setBackground(accentColor);
        } else {
            btn.setBackground(SIDEBAR_COLOR);
        }
        
        btn.addActionListener(e -> switchView(view));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!currentView.equals(view)) {
                    btn.setBackground(accentColor.darker());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!currentView.equals(view)) {
                    btn.setBackground(SIDEBAR_COLOR);
                }
            }
        });
        
        return btn;
    }
    
    private void switchView(String view) {
        currentView = view;
        contentPanel.removeAll();
        
        switch(view) {
            case "dashboard": contentPanel.add(createDashboard()); break;
            case "customers": contentPanel.add(createCustomers()); break;
            case "reservations": contentPanel.add(createReservations()); break;
            case "reports": contentPanel.add(createReportsPanel()); break;
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
        
        mainPanel.remove(1);
        mainPanel.add(createSidebar(), BorderLayout.WEST, 1);
        mainPanel.revalidate();
    }
    
    private JPanel createDashboard() {
        JPanel mainDash = new JPanel(new BorderLayout());
        mainDash.setBackground(BG_COLOR);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 25, 25));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));
        statsPanel.setBackground(BG_COLOR);
        
        ArrayList<Integer> stats = controller.getSystemStatistics();
        statsPanel.add(statCard("Total Customers", stats.get(0), PRIMARY, "[C]"));
        statsPanel.add(statCard("Total Reservations", stats.get(1), SUCCESS, "[R]"));
        statsPanel.add(statCard("Confirmed Reservations", stats.get(2), DANGER, "[S]"));
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        infoPanel.setBackground(BG_COLOR);
        
        infoPanel.add(createInfoCard("Quick Stats", 
            "Active Customers: " + stats.get(0) + "\n" +
            "Today's Reservations: " + stats.get(2), 
            PRIMARY));
        infoPanel.add(createInfoCard("System Status", 
            "[OK] All Systems Operational\n" +
            "[OK] Database Connected", 
            SUCCESS));
        infoPanel.add(createInfoCard("Recent Activity", 
            "Last Customer: " + (stats.get(0) > 0 ? "C" + (999 + stats.get(0)) : "None") + "\n" +
            "Last Reservation: " + (stats.get(1) > 0 ? "R" + (4999 + stats.get(1)) : "None"), 
            WARNING));
        infoPanel.add(createInfoCard("Quick Actions", 
            "> Add New Customer\n" +
            "> Make Reservation\n" +
            "> View Reports", 
            PRIMARY));
        
        mainDash.add(statsPanel, BorderLayout.NORTH);
        mainDash.add(infoPanel, BorderLayout.CENTER);
        
        return mainDash;
    }
    
    private JPanel createInfoCard(String title, String content, Color color) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(PANEL_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(color);
        
        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 13));
        contentArea.setForeground(TEXT_COLOR);
        contentArea.setBackground(PANEL_COLOR);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(contentArea, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel statCard(String label, int value, Color color, String icon) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(icon, SwingConstants.LEFT);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 32));
        iconLabel.setForeground(Color.WHITE);
        
        JLabel lbl = new JLabel(label, SwingConstants.RIGHT);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        
        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(lbl, BorderLayout.EAST);
        
        JLabel val = new JLabel(String.valueOf(value), SwingConstants.CENTER);
        val.setForeground(Color.WHITE);
        val.setFont(new Font("Arial", Font.BOLD, 56));
        
        card.add(topPanel, BorderLayout.NORTH);
        card.add(val, BorderLayout.CENTER);
        return card;
    }
    
    private JPanel createCustomers() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(BG_COLOR);
        top.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        
        JLabel title = new JLabel("Customer Management");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(TEXT_COLOR);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnPanel.setBackground(BG_COLOR);
        
        JButton refreshBtn = createStyledButton("Refresh", PRIMARY, e -> refreshCustomers());
        JButton searchBtn = createStyledButton("Search", PRIMARY, e -> searchCustomer());
        JButton deleteBtn = createStyledButton("Delete", DANGER, e -> deleteCustomer());
        JButton addBtn = createStyledButton("+ Add Customer", SUCCESS, e -> addCustomer());
        
        btnPanel.add(refreshBtn);
        btnPanel.add(searchBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(addBtn);
        
        top.add(title, BorderLayout.WEST);
        top.add(btnPanel, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);
        
        String[] cols = {"ID", "Name", "Phone", "Email", "Membership Type"};
        customerModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable table = new JTable(customerModel);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(PANEL_COLOR);
        table.setForeground(TEXT_COLOR);
        table.setSelectionBackground(PRIMARY.brighter());
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(isDarkMode ? new Color(60, 60, 60) : new Color(220, 220, 220));
        table.setShowGrid(true);
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        scrollPane.getViewport().setBackground(PANEL_COLOR);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        refreshCustomers();
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(listener);
        
        return btn;
    }
    
    private JPanel createReservations() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(BG_COLOR);
        top.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        
        JLabel title = new JLabel("Reservation Management");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(TEXT_COLOR);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnPanel.setBackground(BG_COLOR);
        
        JButton refreshBtn = createStyledButton("Refresh", PRIMARY, e -> refreshReservations());
        JButton searchBtn = createStyledButton("Search", PRIMARY, e -> searchReservation());
        JButton cancelBtn = createStyledButton("Cancel Reservation", WARNING, e -> cancelReservationDialog());
        JButton deleteBtn = createStyledButton("Delete", DANGER, e -> deleteReservation());
        JButton addBtn = createStyledButton("+ Make Reservation", SUCCESS, e -> addReservation());
        
        btnPanel.add(refreshBtn);
        btnPanel.add(searchBtn);
        btnPanel.add(cancelBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(addBtn);
        
        top.add(title, BorderLayout.WEST);
        top.add(btnPanel, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);
        
        String[] cols = {"ID", "Customer ID", "Table Number", "Date", "Number of Guests", "Status"};
        reservationModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable table = new JTable(reservationModel);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(PANEL_COLOR);
        table.setForeground(TEXT_COLOR);
        table.setSelectionBackground(SUCCESS.brighter());
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(isDarkMode ? new Color(60, 60, 60) : new Color(220, 220, 220));
        table.setShowGrid(true);
        table.getTableHeader().setBackground(SUCCESS);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(SUCCESS, 2));
        scrollPane.getViewport().setBackground(PANEL_COLOR);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        refreshReservations();
        return panel;
    }
    
    private void addCustomer() {
        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField email = new JTextField();
        JComboBox<String> membership = new JComboBox<>(new String[]{"Standard", "Silver", "Gold", "Platinum"});
        
        Object[] fields = {
            "Name:", name,
            "Phone:", phone,
            "Email:", email,
            "Membership Type:", membership
        };
        
        int result = JOptionPane.showConfirmDialog(this, fields, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && !name.getText().isEmpty()) {
            String id = controller.addNewCustomer(
                name.getText(),
                phone.getText(),
                email.getText(),
                (String) membership.getSelectedItem()
            );
            JOptionPane.showMessageDialog(this, "Customer added successfully!\nID: " + id);
            refreshCustomers();
            updateDashboard();
        }
    }
    
    private void addReservation() {
        JTextField customerID = new JTextField();
        JTextField tableNumber = new JTextField();
        JTextField numberOfGuests = new JTextField();
        
        Object[] fields = {
            "Customer ID:", customerID,
            "Table Number:", tableNumber,
            "Number of Guests:", numberOfGuests
        };
        
        int result = JOptionPane.showConfirmDialog(this, fields, "Make Reservation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && !customerID.getText().isEmpty()) {
            try {
                int guests = Integer.parseInt(numberOfGuests.getText().trim());
                String id = controller.makeNewReservation(
                    customerID.getText().trim(),
                    tableNumber.getText().trim(),
                    guests
                );
                
                if (id == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Customer ID '" + customerID.getText().trim() + "' does not exist!\nPlease register the customer first.", 
                        "Invalid Customer ID", 
                        JOptionPane.ERROR_MESSAGE);
                } else if (id.equals("TABLE_OCCUPIED")) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Table '" + tableNumber.getText().trim() + "' is already reserved for today!\n" +
                        "Please choose another table or cancel the existing reservation.", 
                        "Table Not Available", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Reservation made successfully!\nID: " + id);
                    refreshReservations();
                    updateDashboard();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for guests!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void refreshCustomers() {
        customerModel.setRowCount(0);
        for (Customer c : controller.getCustomerList()) {
            customerModel.addRow(new Object[]{
                c.getCustomerID(),
                c.getName(),
                c.getPhone(),
                c.getEmail(),
                c.getMembershipType()
            });
        }
    }
    
    private void refreshReservations() {
        reservationModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Reservation r : controller.getReservationList()) {
            reservationModel.addRow(new Object[]{
                r.getReservationID(),
                r.getCustomerID(),
                r.getTableNumber(),
                sdf.format(r.getReservationDate()),
                r.getNumberOfGuests(),
                r.getStatus()
            });
        }
    }
    
    private void updateDashboard() {
        if (currentView.equals("dashboard")) {
            contentPanel.removeAll();
            contentPanel.add(createDashboard());
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }
    
    private void searchCustomer() {
        String id = JOptionPane.showInputDialog(this, "Enter Customer ID:", "Search Customer", JOptionPane.QUESTION_MESSAGE);
        if (id != null && !id.trim().isEmpty()) {
            Customer customer = controller.searchCustomer(id.trim());
            if (customer != null) {
                String info = String.format(
                    "Customer Found!\n\nID: %s\nName: %s\nPhone: %s\nEmail: %s\nMembership Type: %s",
                    customer.getCustomerID(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getMembershipType()
                );
                JOptionPane.showMessageDialog(this, info, "Customer Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteCustomer() {
        String id = JOptionPane.showInputDialog(this, "Enter Customer ID to delete:", "Delete Customer", JOptionPane.WARNING_MESSAGE);
        if (id != null && !id.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete customer " + id + "?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = controller.deleteCustomer(id.trim());
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Customer deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshCustomers();
                    updateDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Customer not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void searchReservation() {
        String id = JOptionPane.showInputDialog(this, "Enter Reservation ID:", "Search Reservation", JOptionPane.QUESTION_MESSAGE);
        if (id != null && !id.trim().isEmpty()) {
            Reservation res = controller.searchReservation(id.trim());
            if (res != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String info = String.format(
                    "Reservation Found!\n\nID: %s\nCustomer ID: %s\nTable Number: %s\nDate: %s\nNumber of Guests: %s\nStatus: %s",
                    res.getReservationID(),
                    res.getCustomerID(),
                    res.getTableNumber(),
                    sdf.format(res.getReservationDate()),
                    res.getNumberOfGuests(),
                    res.getStatus()
                );
                JOptionPane.showMessageDialog(this, info, "Reservation Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Reservation not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelReservationDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Reservation ID to cancel:", "Cancel Reservation", JOptionPane.WARNING_MESSAGE);
        if (id != null && !id.trim().isEmpty()) {
            Reservation res = controller.searchReservation(id.trim());
            if (res == null) {
                JOptionPane.showMessageDialog(this, "Reservation not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (res.getStatus().equals("Cancelled")) {
                JOptionPane.showMessageDialog(this, "This reservation is already cancelled!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Cancel Reservation Details:\n\n" +
                "ID: " + res.getReservationID() + "\n" +
                "Customer: " + res.getCustomerID() + "\n" +
                "Table: " + res.getTableNumber() + "\n" +
                "Status: " + res.getStatus() + "\n\n" +
                "Are you sure you want to cancel this reservation?\n" +
                "(This will make the table available for other customers)", 
                "Confirm Cancellation", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean cancelled = controller.cancelReservation(id.trim());
                if (cancelled) {
                    JOptionPane.showMessageDialog(this, 
                        "Reservation cancelled successfully!\n" +
                        "Table " + res.getTableNumber() + " is now available.", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    refreshReservations();
                    updateDashboard();
                }
            }
        }
    }
    
    private void deleteReservation() {
        String id = JOptionPane.showInputDialog(this, "Enter Reservation ID to delete:", "Delete Reservation", JOptionPane.WARNING_MESSAGE);
        if (id != null && !id.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete reservation " + id + "?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = controller.deleteReservation(id.trim());
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Reservation deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshReservations();
                    updateDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Reservation not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(BG_COLOR);
        top.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        
        JLabel title = new JLabel("Report Generator");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(TEXT_COLOR);
        
        top.add(title, BorderLayout.WEST);
        panel.add(top, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        
        JPanel customerReportCard = createReportCard(
            "Customer Report", 
            "Generate a detailed report of all registered customers\nIncludes: ID, Name, Phone, Email, Membership Type",
            PRIMARY,
            e -> generateCustomerReport()
        );
        
        JPanel reservationReportCard = createReportCard(
            "Reservation Report", 
            "Generate a detailed report of all reservations\nIncludes: ID, Customer ID, Table, Date, Guests, Status",
            SUCCESS,
            e -> generateReservationReport()
        );
        
        JPanel summaryReportCard = createReportCard(
            "Summary Report", 
            "Generate a comprehensive summary of the restaurant system\nIncludes: Statistics, Customer count, Reservation count",
            WARNING,
            e -> generateSummaryReport()
        );
        
        centerPanel.add(customerReportCard);
        centerPanel.add(reservationReportCard);
        centerPanel.add(summaryReportCard);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createReportCard(String title, String description, Color color, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(PANEL_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(color);
        
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descArea.setForeground(TEXT_COLOR);
        descArea.setBackground(PANEL_COLOR);
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        
        leftPanel.add(titleLabel, BorderLayout.NORTH);
        leftPanel.add(descArea, BorderLayout.CENTER);
        
        JButton printBtn = createStyledButton("Print Report", color, action);
        printBtn.setPreferredSize(new Dimension(150, 45));
        
        card.add(leftPanel, BorderLayout.CENTER);
        card.add(printBtn, BorderLayout.EAST);
        
        return card;
    }
    
    private void generateCustomerReport() {
        String[] options = {"Sorted by Name (Merge Sort)", "Sorted by ID (BST)", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
            "Choose report type:",
            "Customer Report Options",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (choice == 2) return; // Cancel
        
        StringBuilder report = new StringBuilder();
        report.append("========================================\n");
        report.append("       CUSTOMER REPORT\n");
        
        java.util.List<Customer> customers;
        if (choice == 0) {
            customers = controller.getCustomersSortedByName();
            report.append("   (Sorted by Name - Merge Sort)\n");
        } else {
            customers = controller.getCustomerList();
            report.append("   (Sorted by ID - BST Inorder)\n");
        }
        
        report.append("========================================\n");
        report.append("Date: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n\n");
        report.append("Total Customers: ").append(customers.size()).append("\n\n");
        
        report.append(String.format("%-10s %-20s %-15s %-25s %-15s\n", 
            "ID", "Name", "Phone", "Email", "Membership"));
        report.append("----------------------------------------------------------------------------------------\n");
        
        for (Customer c : customers) {
            report.append(String.format("%-10s %-20s %-15s %-25s %-15s\n",
                c.getCustomerID(),
                c.getName(),
                c.getPhone(),
                c.getEmail(),
                c.getMembershipType()
            ));
        }
        
        report.append("\n========================================\n");
        report.append("End of Report\n");
        report.append("========================================\n");
        
        showReportDialog("Customer Report", report.toString());
    }
    
    private void generateReservationReport() {
        String[] options = {"Sorted by Date (Merge Sort)", "Unsorted", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
            "Choose report type:",
            "Reservation Report Options",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (choice == 2) return;
        
        StringBuilder report = new StringBuilder();
        report.append("========================================\n");
        report.append("     RESERVATION REPORT\n");
        
        java.util.List<Reservation> reservations;
        if (choice == 0) {
            reservations = controller.getReservationsSortedByDate();
            report.append("   (Sorted by Date - Merge Sort)\n");
        } else {
            reservations = controller.getReservationList();
            report.append("        (Unsorted)\n");
        }
        
        report.append("========================================\n");
        report.append("Date: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n\n");
        report.append("Total Reservations: ").append(reservations.size()).append("\n\n");
        
        report.append(String.format("%-10s %-12s %-12s %-18s %-15s %-12s\n", 
            "ID", "Customer ID", "Table #", "Date", "Guests", "Status"));
        report.append("--------------------------------------------------------------------------------\n");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Reservation r : reservations) {
            report.append(String.format("%-10s %-12s %-12s %-18s %-15s %-12s\n",
                r.getReservationID(),
                r.getCustomerID(),
                r.getTableNumber(),
                sdf.format(r.getReservationDate()),
                r.getNumberOfGuests(),
                r.getStatus()
            ));
        }
        
        report.append("\n========================================\n");
        report.append("End of Report\n");
        report.append("========================================\n");
        
        showReportDialog("Reservation Report", report.toString());
    }
    
    private void generateSummaryReport() {
        StringBuilder report = new StringBuilder();
        report.append("========================================\n");
        report.append("      RESTAURANT SUMMARY REPORT\n");
        report.append("========================================\n");
        report.append("Date: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n\n");
        
        ArrayList<Integer> stats = controller.getSystemStatistics();
        
        report.append("STATISTICS\n");
        report.append("----------\n");
        report.append("Total Customers:           ").append(stats.get(0)).append("\n");
        report.append("Total Reservations:        ").append(stats.get(1)).append("\n");
        report.append("Confirmed Reservations:    ").append(stats.get(2)).append("\n");
        report.append("Completed Reservations:    ").append(stats.get(3)).append("\n");
        report.append("Cancelled Reservations:    ").append(stats.get(4)).append("\n\n");
        
        report.append("SYSTEM STATUS\n");
        report.append("-------------\n");
        report.append("Database:        Connected\n");
        report.append("System Status:   Operational\n\n");
        
        report.append("========================================\n");
        report.append("End of Report\n");
        report.append("========================================\n");
        
        showReportDialog("Summary Report", report.toString());
    }
    
    private void showReportDialog(String title, String content) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(700, 600);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextArea textArea = new JTextArea(content);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(PANEL_COLOR);
        textArea.setForeground(TEXT_COLOR);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY, 2));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BG_COLOR);
        
        JButton printBtn = createStyledButton("Print", PRIMARY, e -> {
            try {
                textArea.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error printing: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton saveBtn = createStyledButton("Save to File", SUCCESS, e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Report");
            fileChooser.setSelectedFile(new java.io.File(title.replace(" ", "_") + ".txt"));
            
            int result = fileChooser.showSaveDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile());
                    writer.write(content);
                    writer.close();
                    JOptionPane.showMessageDialog(dialog, "Report saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton closeBtn = createStyledButton("Close", DANGER, e -> dialog.dispose());
        
        buttonPanel.add(printBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(closeBtn);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}
