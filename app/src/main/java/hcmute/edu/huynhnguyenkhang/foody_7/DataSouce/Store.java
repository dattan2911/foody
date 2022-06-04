package hcmute.edu.huynhnguyenkhang.foody_7.DataSouce;

public class Store {
    String name;
    String address;
    String photoUrl;
    String idStore;

    public Store(String id,String title, String address,String photoUrl) {
        this.address = address;
        this.name = title;
        this.photoUrl = photoUrl;
        this.idStore=id;
    }
    public String getName(){
        return name;
    }
    public String getPhotoUrl(){
        return photoUrl;
    }
    public String getStoreId(){
        return idStore;
    }
    public String getAddress(){return address;}
}
