package capstone.sda.com.literatures.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import capstone.sda.com.literatures.Pojo.User;

import static android.accounts.AccountManager.KEY_PASSWORD;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMPTY = "";
    private static final String KEY_ISLOGGEDIN = "isLoggedIn";

    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param username
     * @param fullName
     * @param address
     * @param email
     * @param phone
     *
     */
    public void loginUser(String username, String fullName, String address, String email, String phone) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_FULL_NAME, fullName);
        mEditor.putString(KEY_ADDRESS, address);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_PHONE, phone);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }
    /* SPF for now while fixing some stuffs for sql. */
    /*public void userRegistration(String fullName, String username, String password, String address, String email, String phone){
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_FULL_NAME, fullName);
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.putString(KEY_ADDRESS, address);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_PHONE, phone);

        mEditor.commit();
    }*/

    public void loginCredenetials(String fullname, String username, String address, String email, String phone){
        mEditor.putString(KEY_FULL_NAME,fullname);
        mEditor.putString(KEY_USERNAME,username);
        mEditor.putString(KEY_ADDRESS,address);
        mEditor.putString(KEY_EMAIL,email);
        mEditor.putString(KEY_PHONE,phone);
        mEditor.putBoolean(KEY_ISLOGGEDIN,true);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public User getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        User user = new User();
        user.setUsername(mPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        user.setFullName(mPreferences.getString(KEY_FULL_NAME, KEY_EMPTY));
        user.setAddress(mPreferences.getString(KEY_ADDRESS, KEY_EMPTY));
        user.setEmail(mPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        user.setPhone(mPreferences.getString(KEY_PHONE, KEY_EMPTY));
        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));

        return user;
    }

    public void saveLoginCredenetials(String username){
        mEditor.putString(KEY_USERNAME,username);
        mEditor.putBoolean(KEY_ISLOGGEDIN,true);
        mEditor.commit();
    }

    public boolean isCredentialsOkay(){
        return mPreferences.getBoolean(KEY_ISLOGGEDIN,false);
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}