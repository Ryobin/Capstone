package capstone.sda.com.literatures.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import capstone.sda.com.literatures.MainActivity;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.HttpConfigs;
import capstone.sda.com.literatures.Utils.MySingleton;
import capstone.sda.com.literatures.Utils.SessionHandler;

public class RegisterActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMPTY = "";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etFullName;
    private EditText etAddress;
    private EditText etEmail;
    private EditText etPhone;
    private TextView tvShow;
    private TextView tvHide;
    private TextView tvShowC;
    private TextView tvHideC;
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String address;
    private String email;
    private String phone;

    private boolean isPasswordShown = true;

    private ProgressDialog pDialog;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName = findViewById(R.id.etFullName);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        tvShow = findViewById(R.id.pass_showhidepass);
        tvHide = findViewById(R.id.pass_hidepass);
        tvShowC = findViewById(R.id.cpass_showhidepass);
        tvHideC = findViewById(R.id.cpass_hidepass);

        Button login = findViewById(R.id.btnRegisterLogin);
        Button register = findViewById(R.id.btnRegister);

        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPass();
            }
        });

        tvHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePass();
            }
        });

        tvShowC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmPass();
            }
        });

        tvHideC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideConfirmPass();
            }
        });

        //Launch Login screen when Login Button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                fullName = etFullName.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    private void hideConfirmPass() {
        if (tvHideC.isShown()){
            tvHideC.setVisibility(View.GONE);
            etConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            tvShowC.setVisibility(View.VISIBLE);
        }else{
            etConfirmPassword.setTransformationMethod(null);
        }
    }

    private void showConfirmPass() {
        if(tvShowC.isShown()){
            tvShowC.setVisibility(View.GONE);
            etConfirmPassword.setTransformationMethod(null);
            tvHideC.setVisibility(View.VISIBLE);
        }else{
            etConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            tvHideC.setVisibility(View.VISIBLE);
        }
    }

    private void hidePass() {
        if (tvHide.isShown()){
            tvHide.setVisibility(View.GONE);
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            tvShow.setVisibility(View.VISIBLE);
        }else{
            etPassword.setTransformationMethod(null);
        }
    }

    private void showPass() {
        if (tvShow.isShown()){
            tvShow.setVisibility(View.GONE);
            etPassword.setTransformationMethod(null);
            tvHide.setVisibility(View.VISIBLE);
        }else{
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            tvHide.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        session.logoutUser();
        startActivity(i);
        finish();

    }

    private void registerUser() {

        HttpConfigs httpConfigs = new HttpConfigs();
        String register_url = httpConfigs.RegisterAPI();


        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_FULL_NAME, fullName);
            request.put(KEY_ADDRESS, address);
            request.put(KEY_EMAIL, email);
            request.put(KEY_PHONE, phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                session.loginUser(username,fullName,address,email,phone);
                                Toast.makeText(getApplicationContext(), "Account registration successful.", Toast.LENGTH_SHORT).show();
                                loadDashboard();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                etUsername.setError("Username already taken!");
                                etUsername.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(fullName)) {
            etFullName.setError("Full Name cannot be empty");
            etFullName.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(username)) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}