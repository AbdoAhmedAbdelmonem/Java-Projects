import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RestaurantController controller = new RestaurantController();
            controller.showMainWindow();
        });
    }
}
