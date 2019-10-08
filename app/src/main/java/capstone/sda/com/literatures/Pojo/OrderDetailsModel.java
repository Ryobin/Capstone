package capstone.sda.com.literatures.Pojo;

public class OrderDetailsModel {

    private String product_id;
    private String product_name;
    private Double product_price;
    private int quantity;
    private String ord_imageurl;
    private int subtotal;
    private int shippingFee;
    private int total;

    public OrderDetailsModel(){}

    public OrderDetailsModel(String product_id, String product_name, Double product_price, String ord_imageurl, int subtotal, int shippingFee, int total, int quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.ord_imageurl = ord_imageurl;
        this.subtotal = subtotal;
        this.shippingFee = shippingFee;
        this.total = total;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public String getOrd_imageurl() {
        return ord_imageurl;
    }

    public void setOrd_imageurl(String ord_imageurl) {
        this.ord_imageurl = ord_imageurl;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
