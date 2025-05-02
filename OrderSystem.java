import java.util.*;

public class OrderSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Restaurant restaurant = new Restaurant();

        // Sample menu items with categories
        restaurant.addMenuItem(new MenuItem(1, "Burger", 5.99, "Main Course"));
        restaurant.addMenuItem(new MenuItem(2, "Pizza", 8.99, "Main Course"));
        restaurant.addMenuItem(new MenuItem(3, "Fries", 2.99, "Sides"));
        restaurant.addMenuItem(new MenuItem(4, "Soda", 1.99, "Drinks"));

        while (true) {
            System.out.println("\n--- Restaurant System ---");
            System.out.println("1. Customer Order");
            System.out.println("2. View Pending Orders (Read-Only)");
            System.out.println("3. Staff Login");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = safeIntInput(scanner);

            if (choice == 1) {
                scanner.nextLine(); // clear buffer

                // Validate order type
                String type = "";
                while (true) {
                    System.out.print("Takeout or Delivery? ");
                    type = scanner.nextLine().trim().toLowerCase();
                    if (type.equals("takeout") || type.equals("delivery")) {
                        type = Character.toUpperCase(type.charAt(0)) + type.substring(1);
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'Takeout' or 'Delivery'.");
                    }
                }

                Order order = new Order(type);

                // === MULTI-CATEGORY ORDER LOOP ===
                boolean ordering = true;
                while (ordering) {
                    System.out.println("\nAvailable Categories:");
                    List<String> categoryList = new ArrayList<>(restaurant.getCategories());
                    for (int i = 0; i < categoryList.size(); i++) {
                        System.out.println((i + 1) + ". " + categoryList.get(i));
                    }
                    System.out.println("0. Finish ordering");

                    System.out.print("Choose a category number to view items: ");
                    String input = scanner.nextLine().trim();

                    if (input.equals("0")) {
                        ordering = false;
                        break;
                    }

                    List<MenuItem> filteredMenu = new ArrayList<>();
                    try {
                        int index = Integer.parseInt(input);
                        if (index >= 1 && index <= categoryList.size()) {
                            String selectedCategory = categoryList.get(index - 1);
                            filteredMenu = restaurant.getMenuByCategory(selectedCategory);
                        } else {
                            System.out.println("Invalid category number.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                        continue;
                    }

                    // Show items in selected category
                    System.out.println("\nItems in " + categoryList.get(Integer.parseInt(input) - 1) + ":");
                    for (MenuItem item : filteredMenu) {
                        System.out.println(item);
                    }

                    // Ordering items
                    while (true) {
                        System.out.print("Enter item ID to order (0 to go back to category list): ");
                        int id = safeIntInput(scanner);
                        if (id == 0) break;

                        MenuItem selectedItem = null;
                        for (MenuItem m : restaurant.getMenu()) {
                            if (m.getId() == id) {
                                selectedItem = m;
                                break;
                            }
                        }

                        if (selectedItem != null) {
                            System.out.print("Quantity: ");
                            int qty = safeIntInput(scanner);
                            if (qty > 0) {
                                order.addItem(new OrderItem(selectedItem, qty));
                            } else {
                                System.out.println("Quantity must be greater than 0.");
                            }
                        } else {
                            System.out.println("Invalid item ID.");
                        }
                    }
                }

                scanner.nextLine(); // clear buffer

                if ("Delivery".equalsIgnoreCase(type)) {
                    System.out.print("Enter delivery address: ");
                    String address = scanner.nextLine();
                    order.setDeliveryAddress(address);
                }

                String payment = "";
                while (true) {
                    System.out.print("Enter payment method (Cash/Card): ");
                    payment = scanner.nextLine().trim().toLowerCase();
                    if (payment.equals("cash") || payment.equals("card")) {
                        payment = Character.toUpperCase(payment.charAt(0)) + payment.substring(1);
                        break;
                    } else {
                        System.out.println("Invalid payment method. Please enter 'Cash' or 'Card'.");
                    }
                }

                order.setPaymentMethod(payment);
                restaurant.addOrder(order);

                System.out.println("\nReceipt:");
                System.out.println(order);
                order.generateReceiptFile();

            } else if (choice == 2) {
                restaurant.showPendingOrders();

            } else if (choice == 3) {
                scanner.nextLine(); // clear buffer
                if (StaffAuth.login(scanner)) {
                    System.out.println("✅ Logged in successfully!");
                    boolean loggedIn = true;
                    while (loggedIn) {
                        System.out.println("\n--- Staff Menu ---");
                        System.out.println("1. Add Menu Item");
                        System.out.println("2. View Pending Orders");
                        System.out.println("3. Complete Order");
                        System.out.println("4. Modify Menu Item");
                        System.out.println("5. Generate Sales Report");
                        System.out.println("6. Log Out");

                        System.out.print("Choose option: ");
                        int staffChoice = safeIntInput(scanner);
                        scanner.nextLine(); // clear buffer

                        switch (staffChoice) {
                            case 1:
                                System.out.print("Enter item name: ");
                                String name = scanner.nextLine();
                                System.out.print("Enter item price: ");
                                double price = safeDoubleInput(scanner);
                                System.out.print("Enter item category: ");
                                String category = scanner.nextLine();
                                int newId = restaurant.getMenu().size() + 1;
                                restaurant.addMenuItem(new MenuItem(newId, name, price, category));
                                System.out.println("✅ Menu item added.");
                                break;

                            case 2:
                                restaurant.showPendingOrders();
                                break;

                            case 3:
                                System.out.print("Enter order ID to complete: ");
                                int id = safeIntInput(scanner);
                                restaurant.completeOrder(id);
                                break;

                            case 4:
                                restaurant.modifyMenuItem(scanner);
                                break;

                            case 5:
                                restaurant.generateSalesReport();
                                break;

                            case 6:
                                loggedIn = false;
                                System.out.println("Logged out.");
                                break;

                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                } else {
                    System.out.println("❌ Invalid credentials.");
                }

            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    public static int safeIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // clear invalid input
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    public static double safeDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
