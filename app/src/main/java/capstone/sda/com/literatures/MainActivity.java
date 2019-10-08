package capstone.sda.com.literatures;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import capstone.sda.com.literatures.Activity.CartActivity;
import capstone.sda.com.literatures.Activity.LoginActivity;
import capstone.sda.com.literatures.Activity.OrderDetail;
import capstone.sda.com.literatures.Activity.SignInActivity;
import capstone.sda.com.literatures.Activity.SignUpActivity;
import capstone.sda.com.literatures.Activity.SplashActivity;
import capstone.sda.com.literatures.Fragment.CartFragment;
import capstone.sda.com.literatures.Fragment.DashboardFragment;
import capstone.sda.com.literatures.Fragment.ProductFragment;
import capstone.sda.com.literatures.Fragment.PurchaseHistroyFragment;
import capstone.sda.com.literatures.Fragment.ViewProfileFragment;
import capstone.sda.com.literatures.Fragment.WishlistFragment;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.Utils.HttpConfigs;
import capstone.sda.com.literatures.Utils.SessionHandler;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionHandler session;
    private Realm realm;

    private final String KEY_EMAIL = "email";
    private final String KEY_UID = "UID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tbTitle = toolbar.findViewById(R.id.toolbar_title);
        tbTitle.setText("Literatures");
        session = new SessionHandler(getApplicationContext());
        realm = Realm.getDefaultInstance();
        HttpConfigs httpConfigs;

        //auth = FirebaseAuth.getInstance();
        //firebaseUser = auth.getCurrentUser();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Checks if user is logged in or not then set drawer items.
            NavigationView navigationView = findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();
                if(session.isLoggedIn()){

                    updateNavHeaderLoggedIn();
                    menu.findItem(R.id.logout).setVisible(true);
                    menu.findItem(R.id.menu_login).setVisible(false);

                }else if(!session.isLoggedIn()){

                    updateNavHeaderNotLoggedIn();
                    menu.findItem(R.id.logout).setVisible(false);
                    menu.findItem(R.id.menu_login).setVisible(true);

                }

            navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame_main,new ProductFragment());
        ft.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
       // ExitDialog();
    }

    private void cancelCartConfirmation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you want to empty your cart?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.frame_main, new CartFragment());
                ft.addToBackStack(null);
                ft.commit();

                Toast.makeText(MainActivity.this,"All items in cart is removed.",Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){

            case R.id.notification:
                Toast.makeText(this,"Notifications",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_cancel: {
                    cancelCart();
                }
                break;
            case R.id.action_remove_wishlist: {
                emptyWishlist();
            }
            break;
            default:
                break;

        }



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_cancel) {

            return true;
        }*/
         /*else if (id == R.id.increment){
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_main,new IncrementFragment());
            ft.addToBackStack(null);
            ft.commit();

        }
*/
        return super.onOptionsItemSelected(item);
    }

    private void emptyWishlist() {
        if(session.isLoggedIn()){
            final User user = session.getUserDetails();
            final String CART_URL = "http://192.168.8.102/Lebook/Admin/api/empty_wishlist.php?username="+user.getUsername();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    cancelCartConfirmation();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("user_id", String.valueOf(session.getUserDetails().getUID()));
                    params.put("username", user.getUsername());

                    return params;
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);

        }
        else {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelCart() {
        if(session.isLoggedIn()){
              final User user = session.getUserDetails();
              final String CART_URL = "http://192.168.8.102/Lebook/Admin/api/cancel_cart.php?username="+user.getUsername();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    cancelCartConfirmation();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("user_id", String.valueOf(session.getUserDetails().getUID()));
                    params.put("username", user.getUsername());

                    return params;
                }


            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);

        }
        else {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_main, new ProductFragment());
           // ft.replace(R.id.frame_main,new HomeFragment());
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_categories) {
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_main,new DashboardFragment());
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_profile) {
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_main,new ViewProfileFragment());
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_cart){
            Intent i = new Intent(MainActivity.this, CartActivity.class);
            startActivity(i);
            /*android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_main,new CartFragment());
            ft.addToBackStack(null);
            ft.commit();*/
        } else if (id == R.id.nav_wishlist){
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_main,new WishlistFragment());
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_purchase) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, new PurchaseHistroyFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.logout){
                /*Logout code*/
            //Intent i = new Intent(MainActivity.this, SignInActivity.class);
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            session.logoutUser();
            startActivity(i);
        //    deleteRecords();
            finish();

/*
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(KEY_EMAIL);
                Log.d("LoggedOut",KEY_EMAIL);
                editor.remove(KEY_USER_ID);
                Log.d("Key",firebaseUser.getUid());
                editor.apply();

                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();*/

            }else if (id == R.id.menu_login){
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            }

        /*} else if (id == R.id.nav_graid){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_main,new GraidFragment());
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_upload){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_main,new UploadFragment());
            transaction.addToBackStack(null);
            transaction.commit();*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*private void deleteRecords() {
        RealmResults<User> newUser = realm.where(User.class).findAll();
        String op="";
        for (User user : newUser) {
                Log.d("Fields", user.getUsername());
            Log.d("CheckRecordifDeleted",op+=user.toString());
            }
            realm.delete(User.class);
        }*/

    public void updateNavHeaderLoggedIn(){
     //   int uid = getIntent().getIntExtra(KEY_UID,0);
     //   Realm realm = Realm.getDefaultInstance();
     //   RealmResults<User> u = realm.where(User.class).equalTo("UID",uid).findAll();
     //   User exist = resultUser.get(resultUser.size()-1);
     //   uid = exist.getUID()+1;

        SessionHandler session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView navUserName = headerView.findViewById(R.id.nav_user_full_name);
        final TextView navUserMail = headerView.findViewById(R.id.nav_user_email);
        final TextView notLogged = headerView.findViewById(R.id.not_logged_in);

        navUserName.setText(user.getFullName());
        navUserMail.setText(user.getEmail());
        notLogged.setVisibility(View.GONE);
    }

    public void updateNavHeaderNotLoggedIn(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        final ImageView iv = headerView.findViewById(R.id.nav_profile_image);
        final TextView tv1 = headerView.findViewById(R.id.nav_user_full_name);
        final TextView tv2 = headerView.findViewById(R.id.nav_user_email);

            iv.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
            tv1.setVisibility(View.GONE);
    }

}
