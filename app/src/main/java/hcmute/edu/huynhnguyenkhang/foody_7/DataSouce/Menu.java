package hcmute.edu.huynhnguyenkhang.foody_7.DataSouce;
public class Menu {
    String storeName;
    String name;
    String price;
    String description;
    String photoUrl;

    public Menu(String storeName, String name, String price, String description, String photoUrl) {
        this.storeName = storeName;
        this.name = name;
        this.price = price;
        this.description = description;
        this.photoUrl = photoUrl;
    }


    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
