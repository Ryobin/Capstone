package capstone.sda.com.literatures.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Adapter.Categoryadaptor;
import capstone.sda.com.literatures.Pojo.CategoryModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String URL = "https://capstone-sda-literatures.000webhostapp.com/api/api/category.php";
    private RecyclerView category_recycler;
    private ProgressBar progressBar;
    private List<CategoryModel> categoryModels;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        categoryModels = new ArrayList<>();
        category_recycler = view.findViewById(R.id.category_recycler);
        progressBar = view.findViewById(R.id.progressBar);
        CategoryData();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        category_recycler.setLayoutManager(manager);

        return view;
    }

    private void CategoryData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = null;
                for (int i=0;i<response.length();i++){

                    try {
                        jsonObject = response.getJSONObject(i);
                        CategoryModel model= new CategoryModel();
                        model.setCat_image(jsonObject.getString("cat_image"));
                        model.setCat_name(jsonObject.getString("cat_name"));
                        model.setCat_id(jsonObject.getString("cat_id"));
                        categoryModels.add(model);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Categoryadaptor categoryadaptor = new Categoryadaptor(getActivity(), categoryModels);
                category_recycler.setAdapter(categoryadaptor);
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


}
