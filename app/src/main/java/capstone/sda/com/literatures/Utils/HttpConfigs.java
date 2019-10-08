package capstone.sda.com.literatures.Utils;

public class HttpConfigs {

    private String BaseURL = "http://192.168.100.22/";
    private String path = "Lebook/Admin/api/";

    // Routes php files.
    private String loginPath = "login.php";
    private String registerPath = "register.php";
    private String forgotPassword = "retrieve_password.php";
    private String profilePath = "profile.php";
    private String addCartPath = "add_cart.php?username=";
    private String addWishlistPath = "add_wishlist.php?username=";
    private String viewCartPath = "view_cart.php?username=";
    private String viewOrderDetailsPath = "view_order_detail.php";
    private String viewWishlistPath = "view_wishlist.php?username=";
    private String confirmOrderPath = "confirm_order.php?username=";
    private String placeOrderPath = "place_order.php?username=";

    private String cancelCartPath = "cancel_cart.php";
    private String removeCartPath = "remove_cart.php";
    private String emptyWishlistPath = "empty_wishlist.php";
    private String getTotalPath = "get_total.php";
    private String getSubtotalPath = "get_subtotal.php";


    public HttpConfigs() {
    }

    public String getBaseURL() {
        return BaseURL;
    }

    public void setBaseURL(String baseURL) {
        BaseURL = baseURL;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLoginPath() {
        return loginPath;
    }

    public void setLoginPath(String loginPath) {
        this.loginPath = loginPath;
    }

    public String getRegisterPath() {
        return registerPath;
    }

    public void setRegisterPath(String registerPath) {
        this.registerPath = registerPath;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getAddCartPath() {
        return addCartPath;
    }

    public void setAddCartPath(String addCartPath) {
        this.addCartPath = addCartPath;
    }

    public String getAddWishlistPath() {
        return addWishlistPath;
    }

    public void setAddWishlistPath(String addWishlistPath) {
        this.addWishlistPath = addWishlistPath;
    }

    public String getViewCartPath() {
        return viewCartPath;
    }

    public void setViewCartPath(String viewCartPath) {
        this.viewCartPath = viewCartPath;
    }

    public String getViewOrderDetailsPath() {
        return viewOrderDetailsPath;
    }

    public void setViewOrderDetailsPath(String viewOrderDetailsPath) {
        this.viewOrderDetailsPath = viewOrderDetailsPath;
    }

    public String getViewWishlistPath() {
        return viewWishlistPath;
    }

    public void setViewWishlistPath(String viewWishlistPath) {
        this.viewWishlistPath = viewWishlistPath;
    }

    public String getPlaceOrderPath() {
        return placeOrderPath;
    }

    public void setPlaceOrderPath(String placeOrderPath) {
        this.placeOrderPath = placeOrderPath;
    }

    public String getConfirmOrderPath() {
        return confirmOrderPath;
    }

    public void setConfirmOrderPath(String confirmOrderPath) {
        this.confirmOrderPath = confirmOrderPath;
    }

    public String getCancelCartPath() {
        return cancelCartPath;
    }

    public void setCancelCartPath(String cancelCartPath) {
        this.cancelCartPath = cancelCartPath;
    }

    public String getRemoveCartPath() {
        return removeCartPath;
    }

    public void setRemoveCartPath(String removeCartPath) {
        this.removeCartPath = removeCartPath;
    }

    public String getEmptyWishlistPath() {
        return emptyWishlistPath;
    }

    public void setEmptyWishlistPath(String emptyWishlistPath) {
        this.emptyWishlistPath = emptyWishlistPath;
    }

    public String getGetTotalPath() {
        return getTotalPath;
    }

    public void setGetTotalPath(String getTotalPath) {
        this.getTotalPath = getTotalPath;
    }

    public String getGetSubtotalPath() {
        return getSubtotalPath;
    }

    public void setGetSubtotalPath(String getSubtotalPath) {
        this.getSubtotalPath = getSubtotalPath;
    }

    public String getForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(String forgotPassword) {
        this.forgotPassword = forgotPassword;
    }

    // set up the whole configs here.

    public String RegisterAPI(){

        String base = getBaseURL();
        String p = getPath();
        String r = getRegisterPath();
        String registerURL = base+p+r;

        return registerURL;
    }

    public String LoginAPI(){

        String base = getBaseURL();
        String p = getPath();
        String l = getLoginPath();
        String loginURL = base+p+l;

        return loginURL;
    }

    public String viewProfileAPI(){

        String base = getBaseURL();
        String p = getPath();
        String profile = getProfilePath();
        String profileURL = base+p+profile;

        return profileURL;
    }

    public String addCartAPI(){

        String base = getBaseURL();
        String p = getPath();
        String f = getAddCartPath();
        String url = base+p+f;

        return url;
    }

    public String viewCartAPI(){

        String base = getBaseURL();
        String p = getPath();
        String l = getViewCartPath();
        String v = base+p+l;

        return v;
    }

    public String addWishlistAPI(){

        String base = getBaseURL();
        String p = getPath();
        String f = getAddWishlistPath();
        String url = base+p+f;

        return url;
    }

    public String viewWishlistAPI(){

        String base = getBaseURL();
        String p = getPath();
        String f = getViewWishlistPath();
        String url = base+p+f;

        return url;
    }

    public String confirmOrderAPI(){

        String base = getBaseURL();
        String p = getPath();
        String f = getConfirmOrderPath();
        String url = base+p+f;

        return url;
    }

    public String placeOrderAPI(){

        String base = getBaseURL();
        String p = getPath();
        String f = getPlaceOrderPath();
        String url = base+p+f;

        return url;
    }

    public String forgotPasswordAPI(){

        String base = getBaseURL();
        String p = getPath();
        String f = getForgotPassword();
        String url = base+p+f;

        return url;
    }

}
