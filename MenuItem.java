public class MenuItem {
    private int id;
    private String name;
    private double price;

    public MenuItem(int intId, String name, double price) {
        this.id = intId;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return id + ". " + name + " - $" + price;
    }
}
