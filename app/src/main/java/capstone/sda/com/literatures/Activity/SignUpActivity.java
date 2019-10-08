package capstone.sda.com.literatures.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import capstone.sda.com.literatures.MainActivity;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.SessionHandler;
import io.realm.Realm;
import io.realm.RealmResults;

public class SignUpActivity extends AppCompatActivity {


    private EditText etFullName;
   // private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etAddress;
    private EditText etEmail;
    private EditText etPhone;

    private Button btnSignup;
    private Button btnBackToLogin;

    private String username;
    private String password;
    private String confirmPassword;
    private String fullname;
    private String address;
    private String email;
    private String phone;

    private static final String KEY_EMPTY = "";
    private static final String KEY_EMAIL = "email";

    private Realm realm;
    private int uid;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFullName = findViewById(R.id.signup_et_fullname);
      //  etUsername = findViewById(R.id.signup_et_username);
        etPassword = findViewById(R.id.signup_et_password);
        etConfirmPassword = findViewById(R.id.signup_et_confirmpassword);
        etAddress = findViewById(R.id.signup_et_address);
        etEmail = findViewById(R.id.signup_et_email);
        etPhone = findViewById(R.id.signup_et_phone);
        btnSignup = findViewById(R.id.signup_btn);
        btnBackToLogin = findViewById(R.id.signup_btn_back_to_login);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
                finish();
            }
        });

      //  Realm.init(this);
        realm = Realm.getDefaultInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       username = etUsername.getText().toString().toLowerCase();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                fullname = etFullName.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                email = etEmail.getText().toString().toLowerCase().trim();
                phone = etPhone.getText().toString().trim();
                if(!validateInputs()) {
                  Log.e("input valid:", "false");

                }else{
                    if(isUsernameExist(etEmail.getText().toString().toLowerCase().trim())){
                        Toast.makeText(SignUpActivity.this, email + " is already taken.", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("input valid:", "true");
                        RealmResults<User> resultUser = realm.where(User.class).findAll();
                        //check if any user already registered
                        if(resultUser.size()>0){
                            //found users, get last userID
                            User exist = resultUser.get(resultUser.size()-1);
                            uid = exist.getUID()+1;
                            Log.d("UID",String.valueOf(uid));
                            registerProccess(uid);
                        }else{
                            //empty users
                            uid = 1;
                            Log.d("UID",String.valueOf(uid));
                            registerProccess(uid);
                            }
                        }
                    }
                }});
        }

/*
        //check if any user already registered
        if(resultUser.size()>0){
            //found users, get last userID
            User exist = resultUser.get(resultUser.size()-1);
            uid = exist.getUID()+1;
            Log.d("UID",String.valueOf(uid));
            registerProccess(uid);
        }else{
            //empty users
            uid = 1;
            Log.d("UID",String.valueOf(uid));
            registerProccess(uid);
        }
*/

    /*private boolean isUsernameExist(String username){
        RealmResults listUsername = realm
                .where(User.class)
                .equalTo("username",username).findAll();
        if(listUsername.size()>1){
            return true;
        }else{
            return false;
        }
    }*/

    private void registerProccess(int id){
        realm.beginTransaction();
        User user = realm.createObject(User.class);
        user.setUID(id);
        user.setFullName(fullname);
    //    user.setUsername(etUsername.getText().toString().toLowerCase().trim());
        user.setPassword(etPassword.getText().toString().trim());
        user.setAddress(etAddress.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setPhone(etPhone.getText().toString());
        realm.commitTransaction();
        /* Handle session */
        SessionHandler session = new SessionHandler(getApplicationContext());
        session.loginUser(user.getUsername(),
                          user.getFullName(),
                          user.getAddress(),
                          user.getEmail(),
                          user.getPhone());
  //      displayLoader();
        Log.d("USER: ",user.getFullName()+" has successfully registered -- password is "
                +user.getPassword()+" and email is -- "+user.getEmail());
        Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_SHORT).show();
        SignUpActivity.this.finish();
        Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(i);
    }

    private void displayLoader() {
        ProgressDialog pDialog = new ProgressDialog(getApplicationContext());
        pDialog.setMessage("Signing up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    private boolean validateInputs(){
        if(KEY_EMPTY.equals(fullname)){
            etFullName.setError("Full Name cannot be empty");
            etFullName.requestFocus();
            return false;
    ///    }if(KEY_EMPTY.equals(username)) {
    ////        etUsername.setError("Username cannot be empty");
    ///        etUsername.requestFocus();
    ///        return false;
        }if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }if(KEY_EMPTY.equals(confirmPassword)){
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }if(!password.equals(confirmPassword)){
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }if(KEY_EMPTY.equals(address)){
            etAddress.setError("Address cannot be empty");
            etAddress.requestFocus();
            return false;
        }if(KEY_EMPTY.equals(email)){
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
            return false;
        }if(KEY_EMPTY.equals(phone)){
            etPhone.setError("Phone number cannot be empty");
            etPhone.requestFocus();
            return false;
        }
        return true;
      }

    private boolean isUsernameExist(String un){
        RealmResults listUsername = realm
                .where(User.class)
                .equalTo(KEY_EMAIL,un).findAll();
        if(listUsername.size()>1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}