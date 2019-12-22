package top.chorg.iCommerce.api;

public class Shipping {
    public int id;
    public String name;
    public String phone;
    public String address;
    public String mailCode;
    public int userId;

    public Shipping(int id, String name, String phone, String address, String mailCode, int userId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.mailCode = mailCode;
        this.userId = userId;
    }

    public String getFormatOutput() {
        return address + " | " + mailCode + " | " + name + " | " + phone;
    }

}
