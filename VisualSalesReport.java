import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class VisualSalesReport {

    public static void generate(List<Order> orders) {
        Map<String, Integer> itemCounts = new HashMap<>();
        Map<String, Double> revenueMap = new HashMap<>();

        for (Order order : orders) {
            if (order.isComplete()) {
                for (OrderItem item : order.getItems()) {  // FIXED: Properly typed
                    String name = item.getItem().getName();  // FIXED: getItem().getName()
                    itemCounts.put(name, itemCounts.getOrDefault(name, 0) + item.getQuantity());
                    revenueMap.put(name, revenueMap.getOrDefault(name, 0.0) + item.getTotalPrice());
                }
            }
        }

        try (FileWriter writer = new FileWriter("sales_report.csv")) {
            writer.write("Item,Quantity Sold,Revenue\n");
            for (String item : itemCounts.keySet()) {
                writer.write(item + "," + itemCounts.get(item) + "," + String.format("%.2f", revenueMap.get(item)) + "\n");
            }
            System.out.println("📊 Visual sales data exported to sales_report.csv");
        } catch (IOException e) {
            System.out.println("❌ Error writing sales report: " + e.getMessage());
        }
    }
}
