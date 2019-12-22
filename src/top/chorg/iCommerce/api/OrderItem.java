package top.chorg.iCommerce.api;

public class OrderItem {
    public int orderId;
    public int productId;
    public int amount;
    public double price;
    public String historyPage;
    public int historyCover;
    public String historyName;

    public OrderItem(int orderId, int productId, int amount, double price, String historyPage, int historyCover, String historyName) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.price = price;
        this.historyPage = historyPage;
        this.historyCover = historyCover;
        this.historyName = historyName;
    }
}
