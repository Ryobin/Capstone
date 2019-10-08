package capstone.sda.com.literatures.Pojo;

import java.util.Date;

import io.realm.RealmObject;

public class User extends RealmObject {

    private int uid;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Date sessionExpiryDate;

    public int getUID() {
        return uid;
    }

    public void setUID(int UID) {
        this.uid = UID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "UID=" + uid +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", sessionExpiryDate=" + sessionExpiryDate +
                '}';
    }
}
