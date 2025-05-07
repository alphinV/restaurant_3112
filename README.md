# Java Restaurant Ordering System

This is a simple Java terminal app for managing a restaurant. Customers can place orders, and staff can manage the menu, view orders, and generate sales reports.

---

# Features

#For Customers
- View menu items (with category filter like Drinks, Sides, etc.)
- Place Takeout or Delivery orders
- Add multiple items with quantity
- Choose payment method (Cash or Card)
- Get a receipt saved as a text file

# For Staff
- Login with username and password, Can be updated in StaffAuth.java, the default is "admin" and "pass123"
- View menu items
- Add new menu items
- Update or remove menu items
- View pending orders
- Mark orders as complete
- Generate sales report showing:
  - Total orders and revenue
  - Cash vs Card breakdown
  - Creates a `sales_report.csv` file

---

# How to Run

1. Compile the code:
 - The main class is `OrderSystem.java`
