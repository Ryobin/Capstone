package capstone.sda.com.literatures.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import capstone.sda.com.literatures.Adapter.DashboardAdaptor;
import capstone.sda.com.literatures.Pojo.Dashboardmodel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    TextView brand_label,result;
    RecyclerView brand_dashboard;
    List<Dashboardmodel> dashboardmodels;
    String Dashboard_url = "https://capstone-sda-literatures.000webhostapp.com/api/api/brand.php";
    //String Search_Url = "http://192.168.0.103/shopping/brandSearch.php?brand_name="+search+"";
   // String Dashboard_url = "http://192.168.100.22:80/";



    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        brand_label = view.findViewById(R.id.brand_label);
        brand_dashboard = view.findViewById(R.id.brand_dashboard);
        dashboardmodels = new ArrayList<>();
        view_banner();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
       brand_dashboard.setLayoutManager(manager);


        return view;
    }



    private void view_banner() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Dashboard_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i=0;i<response.length();i++){

                    try {
                        jsonObject = response.getJSONObject(i);
                        Dashboardmodel dashboardmodel = new Dashboardmodel();
                        dashboardmodel.setName(jsonObject.getString("brand_name"));
                        dashboardmodel.setImage(jsonObject.getString("brand_image"));

                        dashboardmodels.add(dashboardmodel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                DashboardAdaptor dashboard_adaptor = new DashboardAdaptor(getActivity(),dashboardmodels);
                brand_dashboard.setAdapter(dashboard_adaptor);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }

}
