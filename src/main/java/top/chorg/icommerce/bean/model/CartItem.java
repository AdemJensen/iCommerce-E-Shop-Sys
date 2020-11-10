package top.chorg.icommerce.bean.model;

public class CartItem {
    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item item;
    public int quantity;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
