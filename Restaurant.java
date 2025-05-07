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

    public List<MenuItem> getMenuByCategory(String category) {
        List<MenuItem> filtered = new ArrayList<>();
        for (MenuItem item : menu) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    public Set<String> getCategories() {
        Set<String> categories = new TreeSet<>();
        for (MenuItem item : menu) {
            categories.add(item.getCategory());
        }
        return categories;
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

    public void generateSalesReport() {
        double totalSales = 0;
        int orderCount = 0;
        int cashCount = 0;
        int cardCount = 0;

        for (Order o : orders) {
            if (o.isComplete()) {
                totalSales += o.getTotal();
                orderCount++;
                if (o.toString().toLowerCase().contains("payment: cash")) {
                    cashCount++;
                } else {
                    cardCount++;
                }
            }
        }

        System.out.println("\n--- Sales Report ---");
        System.out.printf("Total Completed Orders: %d%n", orderCount);
        System.out.printf("Total Sales: $%.2f%n", totalSales);
        System.out.printf("Average Order Value: $%.2f%n", orderCount == 0 ? 0 : totalSales / orderCount);
        System.out.printf("Payment Breakdown: Cash - %d | Card - %d%n", cashCount, cardCount);
        System.out.println("----------------------");

        VisualSalesReport.generate(orders);
    }

    public void modifyMenuItem(Scanner scanner) {
        if (menu.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        System.out.println("Current Menu:");
        for (MenuItem item : menu) {
            System.out.println(item);
        }

        System.out.print("Enter item ID to update or remove: ");
        int id = OrderSystem.safeIntInput(scanner);
        MenuItem target = null;
        for (MenuItem m : menu) {
            if (m.getId() == id) {
                target = m;
                break;
            }
        }

        if (target == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.println("1. Update name");
        System.out.println("2. Update price");
        System.out.println("3. Remove item");
        System.out.print("Choose option: ");
        int opt = OrderSystem.safeIntInput(scanner);
        scanner.nextLine(); 

        switch (opt) {
            case 1:
                System.out.print("New name: ");
                String newName = scanner.nextLine();
                menu.set(menu.indexOf(target), new MenuItem(target.getId(), newName, target.getPrice(), target.getCategory()));
                System.out.println("✅ Name updated.");
                break;
            case 2:
                System.out.print("New price: ");
                double newPrice = OrderSystem.safeDoubleInput(scanner);
                menu.set(menu.indexOf(target), new MenuItem(target.getId(), target.getName(), newPrice, target.getCategory()));
                System.out.println("✅ Price updated.");
                break;
            case 3:
                menu.remove(target);
                System.out.println("❌ Item removed.");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}
