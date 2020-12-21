package top.chorg.icommerce.bean.model;

public class CartItem {
    public ItemModel getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemModel item;
    public int quantity;

    public CartItem(ItemModel item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
