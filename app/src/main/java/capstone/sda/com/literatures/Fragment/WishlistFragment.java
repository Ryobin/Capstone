package capstone.sda.com.literatures.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import capstone.sda.com.literatures.Adapter.WishlistAdapter;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.Pojo.Wishlist;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.HttpConfigs;
import capstone.sda.com.literatures.Utils.MySingleton;
import capstone.sda.com.literatures.Utils.SessionHandler;

public class WishlistFragment extends Fragment {

    private List<Wishlist> myWishlist;
    RecyclerView recycler_wishlist;

    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        recycler_wishlist = view.findViewById(R.id.recycler_wishlist);
        myWishlist = new ArrayList<>();

        wishlistView();

        LinearLayoutManager mLinearLM = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recycler_wishlist.setLayoutManager(mLinearLM);
        return view;
    }

    private void wishlistView() {
        SessionHandler session = new SessionHandler(getActivity());
        User user = session.getUserDetails();

        HttpConfigs httpConfigs = new HttpConfigs();
        final String URL = httpConfigs.viewWishlistAPI()+user.getUsername();

      //  final String URL ="http://192.168.8.102/Lebook/Admin/api/view_wishlist.php?username="+user.getUsername();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);
                        Wishlist wishlist = new Wishlist();
                        wishlist.setProduct_id("product_id");
                        wishlist.setProduct_name(jsonObject.getString("product_name"));
                        wishlist.setWishlist_imageurl(jsonObject.getString("product_image"));
                      //  wishlist.setRating(jsonObject.getDouble("rating"));
                        myWishlist.add(wishlist);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                WishlistAdapter wishlistAdapter = new WishlistAdapter(getActivity(), myWishlist);
                recycler_wishlist.setAdapter(wishlistAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        /**
         *  RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
         *  requestQueue.add(jsonArrayRequest);
         */

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);


    }

}
