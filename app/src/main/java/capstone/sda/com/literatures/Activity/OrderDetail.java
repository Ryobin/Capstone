package capstone.sda.com.literatures.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import capstone.sda.com.literatures.Adapter.OrderDetailAdapter;
import capstone.sda.com.literatures.MainActivity;
import capstone.sda.com.literatures.Pojo.OrderDetailsModel;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.HttpConfigs;
import capstone.sda.com.literatures.Utils.MySingleton;
import capstone.sda.com.literatures.Utils.SessionHandler;


public class OrderDetail extends AppCompatActivity {

    private TextView fullName;
    private TextView email;
    private TextView phone;
    private TextView address;
    private TextView subTotal;
    private TextView shippingFee;
    private TextView total;
    private Button placeOrder;
    private RecyclerView odRecyclerV;
    private List<User> userModel;
    private List<OrderDetailsModel> orderDetailsModelList;

    SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
       // toolbar.setTitle("Order Details");
        setSupportActionBar(toolbar);
        TextView tbTitle = toolbar.findViewById(R.id.toolbar_title2);
        tbTitle.setText("Order Details");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        odRecyclerV = findViewById(R.id.recycler_order_detail);
        orderDetailsModelList = new ArrayList<>();

         UIViews();
         userProfileAPI();
         showItems();
         showSubTotal();
         showTotal();

        session = new SessionHandler(getApplicationContext());

        User user = session.getUserDetails();
        fullName.setText(user.getFullName());
        address.setText(user.getAddress());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        odRecyclerV.setLayoutManager(linearLayoutManager);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderPlaced();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void orderPlaced() {
        User user = session.getUserDetails();

        HttpConfigs httpConfigs = new HttpConfigs();
        String URL = httpConfigs.placeOrderAPI()+user.getUsername();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Order has been placed. Thank you!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void showItems() {

        Intent i = getIntent();
        String user_order_no = i.getStringExtra("bno");
        final String URL ="http://192.168.8.102/lebook/Admin/api/view_order_detail.php?user_order_no="+user_order_no;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);
                        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                        orderDetailsModel.setProduct_id("product_id");
                        orderDetailsModel.setProduct_name(jsonObject.getString("product_name"));
                        orderDetailsModel.setOrd_imageurl(jsonObject.getString("product_image"));
                        orderDetailsModel.setProduct_price(jsonObject.getDouble("product_price"));
                        orderDetailsModel.setQuantity(jsonObject.getInt("qty"));
                        orderDetailsModelList.add(orderDetailsModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(getApplicationContext(), orderDetailsModelList);
                odRecyclerV.setAdapter(orderDetailAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);


    }

    private void showSubTotal() {
        subTotal = findViewById(R.id.subtotal_price);
        shippingFee = findViewById(R.id.shipping_fee);

        Intent i = getIntent();
        String user_order_no = i.getStringExtra("bno");
        String URL = "http://192.168.8.102/Lebook/Admin/api/get_subtotal.php?user_order_no="+user_order_no;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        subTotal.setText(getApplicationContext().getString(R.string.Php)+" "+response+".00");
                        shippingFee.setText(getApplicationContext().getString(R.string.Php)+" "+"120.00");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void showTotal() {
        total = findViewById(R.id.total_price);

        Intent i = getIntent();
        String user_order_no = i.getStringExtra("bno");
        JSONObject request = new JSONObject();
        String URL = "http://192.168.8.102/Lebook/Admin/api/get_total.php?user_order_no="+user_order_no;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        total.setText(getApplicationContext().getString(R.string.Php)+" "+response+".00");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void UIViews() {
        fullName = findViewById(R.id.ord_detail_name);
        email = findViewById(R.id.ord_detail_email);
        phone = findViewById(R.id.ord_detail_phone);
        address = findViewById(R.id.ord_detail_address);
        placeOrder = findViewById(R.id.btn_place_order);
    }

    private void userProfileAPI() {

        String URL = "http://192.168.8.102/loginapi/profile.php";
        final Context context = getApplicationContext();
        final SessionHandler session;

        session = new SessionHandler(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);

                        session.getUserDetails();
                        User user = new User();
                        user.setFullName(jsonObject.getString("full_name"));
                        user.setAddress(jsonObject.getString("address"));
                        user.setEmail(jsonObject.getString("email"));
                        user.setPhone(jsonObject.getString("phone"));
                        userModel.add(user);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }
}
