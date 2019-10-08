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

public class LoginActivity extends AppCompatActivity {
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
    private TextView tvShow,tvHide, forgotPass;
    private String username;
    private String password;
    private ProgressDialog pDialog;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if(session.isLoggedIn()){
            loadDashboard();
        }
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);

        tvShow = findViewById(R.id.login_showpass);
        tvHide = findViewById(R.id.login_hidepass);
        forgotPass = findViewById(R.id.forgot_pass);

        Button register = findViewById(R.id.btnLoginRegister);
        Button login = findViewById(R.id.btnLogin);

        etUsername.setTransformationMethod(null);

        tvHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePassword();
            }
        });

        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword();
            }
        });

        //Launch Registration screen when Register Button is clicked
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showPassword() {
        tvShow.setVisibility(View.GONE);
        etPassword.setTransformationMethod(null);
        tvHide.setVisibility(View.VISIBLE);
    }

    private void hidePassword() {
        tvHide.setVisibility(View.GONE);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        tvShow.setVisibility(View.VISIBLE);
    }

    /**
     * Launch Main Activity on Successful Login
     */
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void login() {
        HttpConfigs httpConfigs = new HttpConfigs();
        String login_url = httpConfigs.LoginAPI();

/*
        String base = httpConfigs.getBaseURL();
        String path = httpConfigs.getPath();
        String login_url = base+path+"login.php";
*/
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {
                                session.loginUser(username,response.getString(KEY_FULL_NAME)
                                        ,response.getString(KEY_ADDRESS)
                                        ,response.getString(KEY_EMAIL)
                                        ,response.getString(KEY_PHONE));
                                loadDashboard();

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
        if(KEY_EMPTY.equals(username)){
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}