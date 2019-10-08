package capstone.sda.com.literatures.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import capstone.sda.com.literatures.Fragment.ProductFragment;
import capstone.sda.com.literatures.Fragment.ProfileFragment;
import capstone.sda.com.literatures.Pojo.User;
import capstone.sda.com.literatures.Pojo.Wishlist;
import capstone.sda.com.literatures.R;
//import capstone.sda.com.literatures.ProductFragment;
import capstone.sda.com.literatures.Pojo.CartModel;
import capstone.sda.com.literatures.Utils.SessionHandler;

public class Cartadapter extends RecyclerView.Adapter<Cartadapter.ViewHolder> {

    Context context;
    List<CartModel> cartModels;
    String Delete_URL = "https://capstone-sda-literatures.000webhostapp.com/api/api/delete_cart.php";
    String product_id;
    OnItemClickListener mListener;
    OnClick onClick;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public interface OnClick {
        void onEvent(CartModel cartModel, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Cartadapter(Context context, List<CartModel> cartModels, OnClick listener) {
        this.context = context;
        this.cartModels = cartModels;
        this.onClick = listener;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cart_image;
        //   TextView  cart_id,
        TextView cart_title, cart_price, cart_quantity;
        TextView edit_quantity;
        Button cart_remove;


        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            //     cart_id = itemView.findViewById(R.id.tv_cart_id);
            cart_image = itemView.findViewById(R.id.cart_image);
            cart_title = itemView.findViewById(R.id.cart_title);
            cart_price = itemView.findViewById(R.id.cart_price);
            // cart_quantity = itemView.findViewById(R.id.tv_quantity);
            edit_quantity = itemView.findViewById(R.id.edit_quantity);
            cart_remove = itemView.findViewById(R.id.btn_buy);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            /*cart_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position =getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });*/

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row_layout, parent, false);
        ViewHolder vh = new ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final CartModel cartModel = cartModels.get(position);

        //   holder.cart_id.setText(cartModels.get(position).getProduct_id());
        holder.cart_title.setText(cartModels.get(position).getProduct_name());
        holder.cart_price.setText(String.valueOf(cartModels.get(position).getProduct_price()) + "0");
//        holder.cart_quantity.setText(cartModels.get(position).getCart_qty());
        Glide.with(context).load(cartModels.get(position).getCart_imageurl()).into(holder.cart_image);

        holder.edit_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    AddQuantity();
            }
        });

        holder.cart_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onEvent(cartModel, position);
                //  Toast.makeText(this, "Item removed to cart.", Toast.LENGTH_SHORT).show();
            }
        });


        product_id = cartModels.get(position).getProduct_id();
    }



    /*open dialogbox in edit quantity*/

    /*private void AddQuantity() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.edittext_dialog,null);
        final EditText edit_dialog = view.findViewById(R.id.edit_dialog);
        Button dialog_btn = view.findViewById(R.id.dialog_btn);
        builder.setTitle("Enter Quantity");
        builder.setView(view);
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
*/
     /*   dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CartModel cartModel = new CartModel();
               String qty = edit_dialog.getText().toString();
                cartModel.setCart_qty(qty);
                alertDialog.dismiss();

               *//*View cart_quantity = (TextView) view.findViewById(R.id.tv_quantity);
               ((TextView) cart_quantity).setText(cartModel.getCart_qty());*//*
            }
        });*/

    //}


    //     TODO:: removeFromCart function__
    private void removeFromCart(){

        final String URL ="http://192.168.8.101/Lebook/Admin/api/remove_cart.php?pid="+product_id;

        FragmentManager manager =((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame_main,new ProductFragment());
        ft.addToBackStack(null);
        ft.commit();

}

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

}
