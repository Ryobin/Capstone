package capstone.sda.com.literatures.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Pojo.Customermodel;
import capstone.sda.com.literatures.Utils.MySingleton;
import capstone.sda.com.literatures.Utils.SessionHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfileFragment extends Fragment {


    ImageView update;
    TextView tv_name,tv_phone,tv_address,tv_email, tv_session;
    FirebaseAuth auth;
    FirebaseUser user;
    private List<User> userModel;
  //  String Profile_URL = "https://capstone-sda-literatures.000webhostapp.com/api/api/profile.php";
    String Profile_URL = "http://192.168.8.102/Lebook/Admin/api/profile.php";
  //  String Profile_URL = "http://192.168.1.18/loginapi/profile.php";
    String name,phone,address;

    private SessionHandler session;
   // SharedPreferences preferences;
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";




    public ViewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);
        tv_name = view.findViewById(R.id.tv_name);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_address = view.findViewById(R.id.tv_address);
       // tv_pincode = view.findViewById(R.id.tv_pincode);
        tv_email = view.findViewById(R.id.tv_email);
        tv_session = view.findViewById(R.id.tv_session);
        session = new SessionHandler(getActivity());

  //      auth = FirebaseAuth.getInstance();
  //      user = FirebaseAuth.getInstance().getCurrentUser();



        update = view.findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.frame_main,new ProfileFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        User user = session.getUserDetails();
        tv_name.setText(user.getFullName());
        tv_address.setText(user.getAddress());
        tv_email.setText(user.getEmail());
        tv_phone.setText(user.getPhone());
        tv_session.setText("Hello "+user.getFullName()+", Your session will expire on " +
                            "\n"+user.getSessionExpiryDate());

/*

        preferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        tv_email.setText(preferences.getString(KEY_EMAIL,null));
*/

        profileData();
        return view;
    }

    private void profileData() {

      final Context context = getActivity();
      final SessionHandler session;

      session = new SessionHandler(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Profile_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
//                        preferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

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

       // RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
       // requestQueue.add(jsonArrayRequest);

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }
}