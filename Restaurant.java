import java.util.*;

public class Restaurant {
    private List<MenuItem> menu;
    private List<Order> orders;

    public Restaurant() {
        menu = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void showPendingOrders() {
        boolean found = false;
        for (Order o : orders) {
            if (!o.isComplete()) {
                System.out.println(o);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No pending orders.");
        }
    }

    public void completeOrder(int orderId) {
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                o.markComplete();
                System.out.println("Order #" + orderId + " marked as complete.");
                return;
            }
        }
        System.out.println("Order not found.");
    }
}
