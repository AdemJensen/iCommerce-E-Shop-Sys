package top.chorg.iCommerce.api;

import java.util.Date;

public class Item {
    public int id;
    public String name;
    public String detailPage;
    public double oldPrice;
    public double newPrice;
    public int cover;
    public int quantity;
    public int deals;
    public Date releaseDate;

    public Item(int id, String name, String detailPage, double oldPrice, double newPrice, int cover, int quantity, int deals, Date releaseDate) {
        this.id = id;
        this.name = name;
        this.detailPage = detailPage;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.cover = cover;
        this.quantity = quantity;
        this.deals = deals;
        this.releaseDate = releaseDate;
    }
}
