package capstone.sda.com.literatures.Activity;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import capstone.sda.com.literatures.Adapter.Cartadapter;
import capstone.sda.com.literatures.Pojo.CartModel;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.HttpConfigs;
import capstone.sda.com.literatures.Utils.SessionHandler;

public class CartActivity extends AppCompatActivity {

    private List<CartModel> cartList;
    RecyclerView recycler_cart;
    Button btn_confirm;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tbTitle = toolbar.findViewById(R.id.toolbar_title3);
        tbTitle.setText("My Cart");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recycler_cart = findViewById(R.id.recycler_cart);
        btn_confirm = findViewById(R.id.btn_continue);
        cartList = new ArrayList<>();


        cartView();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recycler_cart.setLayoutManager(linearLayoutManager);


    }

    private void cartView() {

        SessionHandler session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        HttpConfigs httpConfigs = new HttpConfigs();
        final String URL = httpConfigs.viewCartAPI()+user.getUsername();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);
                        CartModel cartModel = new CartModel();
                        //      cartModel.setCart_id(jsonObject.getString("id"));
                        //       cartModel.setToken(jsonObject.getString("token"));
                        cartModel.setProduct_id("product_id");
                        cartModel.setProduct_name(jsonObject.getString("product_name"));
                        cartModel.setCart_imageurl(jsonObject.getString("product_image"));
                        cartModel.setProduct_price(jsonObject.getDouble("product_price"));
                        cartModel.setCart_qty(jsonObject.getString("qty"));
                        //       cartModel.setCart_date(jsonObject.getString("cart_date"));
                        cartList.add(cartModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Cartadapter cartadApter = new Cartadapter(getApplicationContext(), cartList, new Cartadapter.OnClick() {
                    @Override
                    public void onEvent(CartModel cartModel, int pos) {
                        position = pos;
                    }
                });
                recycler_cart.setAdapter(cartadApter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void confirmOrder() {
        SessionHandler session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();
        if(session.isLoggedIn()){

            //   final String URL = "http://192.168.8.102/Lebook/Admin/api/confirm_order.php?username="+user.getUsername();

            HttpConfigs httpConfigs = new HttpConfigs();
            final String URL = httpConfigs.confirmOrderAPI()+user.getUsername();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Intent i = new Intent(getApplicationContext(), OrderDetail.class);
                    i.putExtra("bno", response);
                    startActivity(i);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("username",user.getUsername());
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

        else {

            Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
        }
    }
}

