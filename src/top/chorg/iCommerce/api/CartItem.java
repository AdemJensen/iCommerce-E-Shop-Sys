package top.chorg.iCommerce.api;

public class CartItem {
    public Item item;
    public int quantity;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
