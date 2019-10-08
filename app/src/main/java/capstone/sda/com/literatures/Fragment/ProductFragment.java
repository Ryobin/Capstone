package capstone.sda.com.literatures.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import capstone.sda.com.literatures.Adapter.CategoryRecyclerViewAdapter;
import capstone.sda.com.literatures.Pojo.CategoryModel;
import capstone.sda.com.literatures.Pojo.Product;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Adapter.Productadapter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    RecyclerView product_recyclerView, category_recyclerView;
    private ProgressBar progressBar;
    private Context context;
    private List<Product> productmodels;
  //  private List<CategoryModel> categoryModels;
    private String URL = "https://capstone-sda-literatures.000webhostapp.com/api/api/product.php";
    private String category_URL = "http://192.168.8.102/api/category.php";
   // private String URL = "http://192.168.8.107/Lebook/Admin/api/product.php";
   // private String URL = "http://192.168.8.111/Lebook/Admin/api/product.php";
   // String URL = "http://192.168.1.18/loginapi/product.php";

    private Realm realm;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        progressBar = view.findViewById(R.id.progressBar);
    //    category_recyclerView = view.findViewById(R.id.categoryRecyclerView);
        product_recyclerView = view.findViewById(R.id.product_recyclerView);
    //    categoryModels = new ArrayList<>();
        productmodels = new ArrayList<>();
      //  categoryBind();
        productBind();
        LinearLayoutManager lmanager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        product_recyclerView.setLayoutManager(lmanager);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
      //  category_recyclerView.setLayoutManager(lm);
        realm = Realm.getDefaultInstance();

        return view;
    }

    private void productBind() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;

                for (int i=0;i<response.length();i++){


             /*       *//* fetch data from server to local db *//*
                    realm.beginTransaction();
*/
                    try {
                        jsonObject = response.getJSONObject(i);
                        Product product = new Product();
                            product.setProduct_id(jsonObject.getString("product_id"));
                            product.setProduct_name(jsonObject.getString("product_name"));
                            product.setProduct_price(jsonObject.getDouble("product_price"));
                            product.setRating(jsonObject.getDouble("rating"));
                            product.setProduct_desc(jsonObject.getString("product_desc"));
                            product.setProduct_image(jsonObject.getString("product_image"));

                        productmodels.add(product);

               //      realm.commitTransaction();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            /*    realm.beginTransaction();
                final RealmResults<Product> results = realm.where(Product.class).findAll();
                realm.commitTransaction();
*/
                Productadapter productadapter = new Productadapter(getActivity(), productmodels);
                product_recyclerView.setAdapter(productadapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

    // Disable for now
    /*private void categoryBind(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(category_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               // progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;

                for(int i=0;i<response.length();i++){

                    try{
                        jsonObject = response.getJSONObject(i);
                        CategoryModel category = new CategoryModel();
                        category.setCat_id(jsonObject.getString("category_id"));
                        category.setCat_name(jsonObject.getString("name"));
                        category.setCat_description(jsonObject.getString("description"));

                        categoryModels.add(category);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(getActivity(), categoryModels);
                category_recyclerView.setAdapter(categoryRecyclerViewAdapter);

             }

            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }*/

}
