public class MenuItem {
    private int id;
    private String name;
    private double price;
    private String category;

    public MenuItem(int intId, String name, double price, String category) {
        this.id = intId;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return id + ". " + name + " - $" + price + " [" + category + "]";
    }
}
