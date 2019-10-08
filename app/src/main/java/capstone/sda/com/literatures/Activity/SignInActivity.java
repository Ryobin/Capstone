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

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.sda.com.literatures.MainActivity;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.SessionHandler;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.login_et_username) EditText username;
    @BindView(R.id.login_et_password) EditText password;
    @BindView(R.id.login_btn) Button login;
    @BindView(R.id.login_btn_go_to_register) Button toRegister;

    private static final String KEY_UID = "UID";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private final String KEY_EMPTY = "";
    private final String TAG = "Login";
    private String uname;
    private String pass;
    private String fn;
    private String em;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(reg);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                em = username.getText().toString().toLowerCase().trim();
                pass  = password.getText().toString().trim();
                if(validateInputs()){
                   // displayLoader();
                    validateLogin(em,pass);
                    Log.d("Login Credential", "onClick: "+em);
                  //  SessionHandler session = new SessionHandler(getApplicationContext());
                  //  User user = session.getUserDetails();
                  //  session.loginUser(uname
                  //                  ,user.getFullName()
                  //                  ,user.getAddress()
                  //                  ,user.getEmail()
                  //                  ,user.getPhone());
                   // User user = new User();
                }
       //         Log.e(TAG, "User class -- Name: "+user.getFullName()+" Email: "+user.getEmail());

            }

            private void validateLogin(String user, String pass) {
                Realm realm = Realm.getDefaultInstance();
                //check username
                RealmResults listUsername = realm
                        .where(User.class)
                        .equalTo(KEY_EMAIL,user)
                        .findAll();
                checkUser(user,pass);
                if(listUsername.size()>0){
                    //check password
                    RealmResults listPass = realm
                            .where(User.class)
                            .equalTo(KEY_EMAIL,user)
                            .equalTo(KEY_PASSWORD,pass)
                            .findAll();
                    if(listPass.size()>0){
                       // User u = new User();
                        Intent main = new Intent(SignInActivity.this, MainActivity.class);
                        main.putExtra(KEY_EMAIL,user);
                      //  main.putExtra(KEY_FULL_NAME,fullname);
                     //   main.putExtra(KEY_EMAIL,email);
                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(main);
                        Toast.makeText(getApplicationContext(), "Logged in as "+user, Toast.LENGTH_SHORT).show();
                        SignInActivity.this.finish();

                    }else{
                        Toast.makeText(SignInActivity.this, "Username and password not match.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignInActivity.this, "Username not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


   private boolean checkUser(String u, String p) {
       RealmResults<User> newUser = realm.where(User.class).findAll();
       for (User user : newUser) {
           if (u.equals(user.getEmail()) && p.equals(user.getPassword())) {
               Log.d(TAG, user.getEmail());
               return true;
           }
       }
       Log.d(TAG, String.valueOf(realm.where(User.class).contains(KEY_EMAIL, u)));
       return false;
   }

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(em)){
            username.setError("Username cannot be empty");
            username.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(pass)){
            password.setError("Password cannot be empty");
            password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
