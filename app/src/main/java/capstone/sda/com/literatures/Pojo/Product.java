package capstone.sda.com.literatures.Pojo;

import android.database.Cursor;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*import capstone.sda.com.literatures.Database.DbContract;*/

public class Product extends RealmObject {

    @PrimaryKey
    private String product_id;
    private String product_name;
    private Double product_price;
    private Double rating;
    private String product_desc;
    private String product_image;
    private String cat_id;

    public Product() {
    }
/*
    public Product(Cursor cursor){
        this.product_name = cursor.getString(cursor.getColumnIndex(DbContract.LiteraturesEntry.COLUMN_PRODUCT_NAME));
        this.product_image = cursor.getString(cursor.getColumnIndex(DbContract.LiteraturesEntry.COLUMN_PRODUCT_IMAGE));
        this.product_price = cursor.getString(cursor.getColumnIndex(DbContract.LiteraturesEntry.COLUMN_PRODUCT_PRICE));
        this.product_desc = cursor.getString(cursor.getColumnIndex(DbContract.LiteraturesEntry.COLUMN_PRODUCT_DESCRIPTION));
    }*/

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

}
