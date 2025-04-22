import java.util.*;
import java.io.FileWriter;
import java.io.IOException;


public class Order {
    private static int counter = 1;
    private int orderId;
    private List<OrderItem> items;
    private String orderType;
    private String paymentMethod;
    private String deliveryAddress;
    private boolean isComplete;

    public Order(String orderType) {
        this.orderId = counter++;
        this.orderType = orderType;
        this.items = new ArrayList<>();
        this.isComplete = false;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDeliveryAddress(String address) {
        this.deliveryAddress = address;
    }

    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public void markComplete() {
        isComplete = true;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public int getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId).append(" (").append(orderType).append(")\n");
        for (OrderItem item : items) {
            sb.append("  ").append(item.toString()).append("\n");
        }
        sb.append("Total: $").append(String.format("%.2f", getTotal())).append("\n");

        if ("Delivery".equalsIgnoreCase(orderType)) {
            sb.append("Delivery Address: ").append(deliveryAddress).append("\n");
        }

        sb.append("Payment: ").append(paymentMethod).append("\n");
        sb.append("Status: ").append(isComplete ? "Complete" : "Pending").append("\n");
        return sb.toString();

        
    }

    public void generateReceiptFile() {
        String fileName = "receipt_order_" + orderId + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Order #" + orderId + " (" + orderType + ")\n");
            writer.write("-----------------------------------\n");
            for (OrderItem item : items) {
                writer.write(item.toString() + "\n");
            }
            writer.write("-----------------------------------\n");
            writer.write(String.format("Total: $%.2f\n", getTotal()));
    
            if ("Delivery".equalsIgnoreCase(orderType)) {
                writer.write("Delivery Address: " + deliveryAddress + "\n");
            }
    
            writer.write("Payment Method: " + paymentMethod + "\n");
            writer.write("Status: " + (isComplete ? "Complete" : "Pending") + "\n");
            writer.write("-----------------------------------\n");
    
            System.out.println("📄 Receipt saved as: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Error writing receipt: " + e.getMessage());
        }
    }
    
}
