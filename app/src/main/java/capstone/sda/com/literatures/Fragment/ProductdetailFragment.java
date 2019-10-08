package capstone.sda.com.literatures.Fragment;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.HashMap;

import java.util.Map;

/*
import capstone.sda.com.literatures.Database.DbContract;
import capstone.sda.com.literatures.Database.DbHelper;
import capstone.sda.com.literatures.Database.LiteratureProvider;*/
import capstone.sda.com.literatures.MainActivity;
import capstone.sda.com.literatures.Pojo.CartModel;
import capstone.sda.com.literatures.Pojo.Product;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.HttpConfigs;
import capstone.sda.com.literatures.Utils.SessionHandler;

import static com.android.volley.VolleyLog.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductdetailFragment extends Fragment {


    ImageView image;
    TextView detail_pname,detail_price,detail_desc,p_id;
    Button add_cart,add_wishlist;
   // String URL = "https://capstone-sda-literatures.000webhostapp.com/api/api/cart.php";
   // private String URL = "http://192.168.100.22/loginapi/add_cart.php";
   // private String Buy_Now = "https://capstone-sda-literatures.000webhostapp.com/api/api/purchaseNew.php";
    TextView click_review;

    private static final String KEY_PRODUCTID = "product_id";
    private static final String KEY_USERNAME = "username";
    private SessionHandler session;

    public ProductdetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productdetail, container, false);

        p_id = view.findViewById(R.id.p_id);
        image = view.findViewById(R.id.image);
        detail_pname = view.findViewById(R.id.detail_pname);
        detail_price = view.findViewById(R.id.detail_price);
        detail_desc = view.findViewById(R.id.detail_desc);
        add_cart = view.findViewById(R.id.add_cart);
        add_wishlist = view.findViewById(R.id.buy);
        click_review = view.findViewById(R.id.click_review);

        session = new SessionHandler(getActivity());

        bindData();

        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        add_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToWishlist();
            }
        });

        click_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReviewsWatch();
            }
        });

        return view;

    }

   /* public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // define projection
        String projection[] = {
                DbContract.LiteraturesEntry.COLUMN_PRODUCT_NAME,
                DbContract.LiteraturesEntry.COLUMN_PRODUCT_DESCRIPTION,
                DbContract.LiteraturesEntry.COLUMN_PRODUCT_IMAGE,
                DbContract.LiteraturesEntry.COLUMN_PRODUCT_PRICE
        };
        // Loader will execute the Content Provider
        return new CursorLoader(getContext(),
                DbContract.LiteraturesEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data){

    }*/

    private void ReviewsWatch() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_main,new ViewReviewFragment());
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void addToWishlist() {
        if(session.isLoggedIn()){

            Bundle bundle = getArguments();
         String pid = bundle.getString("product_id");

            HttpConfigs httpConfigs = new HttpConfigs();
            String wishlist_url = httpConfigs.addWishlistAPI()+session.getUserDetails().getUsername()+"&pid="+pid;


            //    final String WL_URL = "http://192.168.8.102/Lebook/Admin/api/add_wishlist.php?username="
    //            +session.getUserDetails().getUsername()+"&pid="+pid;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, wishlist_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Intent in = new Intent(getActivity(), MainActivity.class);
                    startActivity(in);
                    Toast.makeText(getActivity(),"Successfully added to wishlist.", Toast.LENGTH_SHORT).show();


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
                  //  params.put("username", KEY_USERNAME);
                    params.put("user_id", String.valueOf(session.getUserDetails().getUID()));
                    Bundle bundle = getArguments();
                    String product_id = bundle.getString("product_id");
                    params.put("product_id",product_id);
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

    // add data on cart table user id get successfull but product id not get

    private void addToCart()  {
/*

        String productName;
        String imageUrl;
        int quantity;
        Double price;

        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        ContentValues productContentValues = new ContentValues();
        productContentValues.put(DbContract.LiteraturesEntry.COLUMN_CART_PRODUCT_NAME, productName);
        productContentValues.put(DbContract.LiteraturesEntry.COLUMN_CART_PRODUCT_IMG, imageUrl);
        productContentValues.put(DbContract.LiteraturesEntry.COLUMN_CART_PRODUCT_QUANTITY, quantity);
        productContentValues.put(DbContract.LiteraturesEntry.COLUMN_CART_PRODUCT_TOTAL_PRICE, price);

        db.insert(DbContract.LiteraturesEntry.TABLE_NAME, null, productContentValues);
        Log.d(TAG, "Inserted successfully." + productContentValues);
*/


        //if user id null then not add value on cart table automatic force close app

        if(session.isLoggedIn()){
           // bindData();
            Bundle bundle = getArguments();
            String pid = bundle.getString("product_id");
         //   final Product product = new Product();
        //    product.setProduct_id(product_id);

            HttpConfigs httpConfigs = new HttpConfigs();
            final String CART_URL = httpConfigs.addCartAPI()+session.getUserDetails().getUsername()+"&pid="+pid+"&qty=1";
        //    final String CART_URL = "http://192.168.8.102/Lebook/Admin/api/add_cart.php?username="
        //            +session.getUserDetails().getUsername()+"&pid="+pid+"&qty=1";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Intent in = new Intent(getActivity(), MainActivity.class);
                    startActivity(in);
                    Toast.makeText(getActivity(),"Successfully Added Into Cart",Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                 //   params.put("token", firebaseUser.getUid());  //Its a Reference Key Of User succesfull fetch
                    params.put("user_id", String.valueOf(session.getUserDetails().getUID()));
                    Bundle bundle = getArguments();
                    String product_id = bundle.getString("product_id");
                    params.put("product_id",product_id);

                    return params;
                    }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

        }
        else {
                Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
        }
    }

//get data FROM Productpage
private void bindData() {
    Bundle bundle = getArguments();
    String product_id =  bundle.getString("product_id");
    String name =  bundle.getString("product_name");
    Double price = bundle.getDouble("product_price");
    Double rating = bundle.getDouble("rating");
    String description = bundle.getString("product_desc");
    String imageurl = bundle.getString("product_image");


    p_id.setText(product_id);
    detail_pname.setText(name);
    detail_price.setText(String.valueOf(price)+"0");
    detail_desc.setText(description);
    Glide.with(this).load(imageurl).into(image);
}

}
