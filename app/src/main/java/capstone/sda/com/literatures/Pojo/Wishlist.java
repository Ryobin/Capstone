package capstone.sda.com.literatures.Pojo;

import java.util.Date;

public class Wishlist {

    private String product_id;
    private String product_name;
    private String wishlist_imageurl;
    private Double rating;
    private Date date;

    public Wishlist(){}

    public Wishlist(String product_id, String product_name, String wishlist_imageurl, Date date) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.wishlist_imageurl = wishlist_imageurl;
        this.date = date;
    }

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

    public String getWishlist_imageurl() {
        return wishlist_imageurl;
    }

    public void setWishlist_imageurl(String wishlist_imageurl) {
        this.wishlist_imageurl = wishlist_imageurl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
