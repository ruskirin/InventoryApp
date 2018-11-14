package project.udacity.my.inventoryapp.objects;

public class EbayItem {

    private String name;
    private int quantity;
    //TODO: I want to store the time an item was bought, might have to add another variable here
    //       or might not, depending on how SQLite storage is handled
    //       Will use LocalDateTime class for this, supposedly simpler
    private double price;
    private String sellerName;
    private String sellerContact;
    private String thumbnail;

    public EbayItem() {
        name = "";
        quantity = 1;
        price = 0.0;
        sellerName = "";
        sellerContact = "";
        thumbnail = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
