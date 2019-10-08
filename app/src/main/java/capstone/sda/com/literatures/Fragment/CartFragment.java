package capstone.sda.com.literatures.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import capstone.sda.com.literatures.Database.DbContract;
import capstone.sda.com.literatures.Database.DbHelper;*/
import capstone.sda.com.literatures.Activity.OrderDetail;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Adapter.Cartadapter;
import capstone.sda.com.literatures.Pojo.CartModel;
import capstone.sda.com.literatures.Utils.HttpConfigs;
import capstone.sda.com.literatures.Utils.SessionHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment{

   // String URL = "https://capstone-sda-literatures.000webhostapp.com/api/api/viewCart.php";
   // String qty_cartURL = "https://capstone-sda-literatures.000webhostapp.com/api/api/cart.php";
    private List<CartModel> cartList;
    RecyclerView recycler_cart;
    TextView total_price;
    Button btn_confirm;
    int position;

    private static final String KEY_STATUS = "status";

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_cart, container, false);
        recycler_cart = view.findViewById(R.id.recycler_cart);
      //  total_price = view.findViewById(R.id.subtotal_price);

        btn_confirm = view.findViewById(R.id.btn_continue);
        cartList = new ArrayList<>();


        cartView();

       /* show editetxt dialog on button click event*/

       /* btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.edittext_dialog,null);
                final EditText edit_dialog = view1.findViewById(R.id.edit_dialog);
                Button dialog_btn = view1.findViewById(R.id.dialog_btn);

                dialog_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!edit_dialog.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(),"Edit Success",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(),"Please fill it",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setView(view1);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }); */

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recycler_cart.setLayoutManager(linearLayoutManager);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });

        return view;
    }

    /*private void showPrice() {

        SessionHandler session = new SessionHandler(getActivity());
        final User user = session.getUserDetails();
        if(session.isLoggedIn()){
            final String URL = "http://192.168.100.22/Lebook/Admin/api/get_total.php?user_order_no=";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("username",user.getUsername());
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

        else {

            Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
        }

    }*/

    private void confirmOrder() {
        SessionHandler session = new SessionHandler(getActivity());
        final User user = session.getUserDetails();
        if(session.isLoggedIn()){

     //   final String URL = "http://192.168.8.102/Lebook/Admin/api/confirm_order.php?username="+user.getUsername();

        HttpConfigs httpConfigs = new HttpConfigs();
        final String URL = httpConfigs.confirmOrderAPI()+user.getUsername();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Intent i = new Intent(getActivity(), OrderDetail.class);
                i.putExtra("bno", response);
                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params = new HashMap<>();
                params.put("username",user.getUsername());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

        else {

        Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
    }
    }

   /* private void readFromLocalStorage(){

        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readFromLocalCart(database);

        while (cursor.moveToNext()){

            String token = cursor.getString(cursor.getColumnIndex(DbContract.TOKEN));
            String product_name = cursor.getString(cursor.getColumnIndex(DbContract.PRODUCT_NAME));
            String product_image = cursor.getString(cursor.getColumnIndex(DbContract.PRODUCT_IMG));
            double product_price = cursor.getDouble(cursor.getColumnIndex(DbContract.PRODUCT_PRICE));
            int sync_status = cursor.getInt(cursor.getColumnIndex(DbContract.SYNC_STATUS));
            cartList.add(new CartModel(token, product_name, product_image, product_price, sync_status));


        }
    }*/



    private void cartView() {
        SessionHandler session = new SessionHandler(getActivity());
        User user = session.getUserDetails();

        HttpConfigs httpConfigs = new HttpConfigs();
        final String URL = httpConfigs.viewCartAPI()+user.getUsername();

     //   final String URL ="http://192.168.8.102/Lebook/Admin/api/view_cart.php?username="+user.getUsername();


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

                    Cartadapter cartadApter = new Cartadapter(getActivity(), cartList, new Cartadapter.OnClick() {
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

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonArrayRequest);

        }

        private void removeFromCart(){

        }

       /* public void removeCart(int position){
            Cartadapter cartAdapter = new Cartadapter(getActivity(), cartList);
            cartList.remove(position);
            cartAdapter.notifyItemRemoved(position);
        }*/

     public boolean checkNetworkConnection(){

         ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                 .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
         return (networkInfo !=null && networkInfo.isConnected());

    }
}
