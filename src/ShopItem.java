public class ShopItem {
    private String name, description;
    private int price, type;
    private boolean available = true;
    public static int MEDICINE = 0, FOOD = 1, BACKGROUND = 2, HAT = 3;

    public ShopItem(int type, String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
