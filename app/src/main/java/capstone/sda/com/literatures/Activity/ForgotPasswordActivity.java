package capstone.sda.com.literatures.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.HttpConfigs;
import capstone.sda.com.literatures.Utils.MySingleton;

public class ForgotPasswordActivity extends AppCompatActivity {

    private AppCompatEditText etUsername,etEmail;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        toolbarSetUp();
        initViews();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

    }

    private void initViews() {
        etUsername = findViewById(R.id.forgot_enter_username);
        etEmail = findViewById(R.id.forgot_enter_email);
        button = findViewById(R.id.btn_getcode);
    }

    private void toolbarSetUp() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tbTitle = toolbar.findViewById(R.id.toolbar_title_forgot);
        tbTitle.setText("Forgot Password");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void forgotPassword() {
        HttpConfigs httpConfigs = new HttpConfigs();
        String url = httpConfigs.forgotPasswordAPI();
        final String username, email;

        username = etUsername.getText().toString().toLowerCase();
        email = etEmail.getText().toString().toLowerCase();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("SUCCESS")) {
                    Toast.makeText(getApplicationContext(), "Email has been sent.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Failed, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPasswordActivity.this, "Please check your connection.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                        params.put("username",username);
                        params.put("email",email);
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
