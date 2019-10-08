package capstone.sda.com.literatures.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Fragment.ProductdetailFragment;
import capstone.sda.com.literatures.Pojo.Product;

public class Productadapter extends RecyclerView.Adapter<Productadapter.ViewHolder> {

    private Context context;
    private List<Product> products;

    public Productadapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_products,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.product_name.setText(products.get(position).getProduct_name());
            holder.product_price.setText(String.valueOf(products.get(position).getProduct_price()) + "0");
            holder.ratings.setText(String.valueOf(products.get(position).getRating()));
//            holder.product_desc.setText(products.get(position).getProduct_desc());
            Glide.with(context).load(products.get(position).getProduct_image()).into(holder.product_image);

        holder.card_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new ProductdetailFragment();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_main,fragment)
                        .addToBackStack(null)
                        .commit();

                Bundle bundle = new Bundle();
                bundle.putString("product_id",products.get(position).getProduct_id());
                bundle.putString("product_name",products.get(position).getProduct_name());
                bundle.putDouble("product_price",products.get(position).getProduct_price());
                bundle.putDouble("product_rating",products.get(position).getRating());
                bundle.putString("product_desc",products.get(position).getProduct_desc());
                bundle.putString("product_image",products.get(position).getProduct_image());
                fragment.setArguments(bundle);


            }
        });

        /*holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+productmodels.get(position).getProduct_name(),Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    private void AddQuantity() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.edittext_dialog,null);
        final EditText edit_dialog = view.findViewById(R.id.edit_dialog);
        Button dialog_btn = view.findViewById(R.id.dialog_btn);
        builder.setTitle("Enter Quantity");

        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_dialog.setText("1");
            }
        });

        builder.setView(view);
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

            TextView product_name;
            TextView product_price;
            ImageView product_image;
            TextView ratings;
            TextView product_desc;

            Button wishlist;
            // ImageButton favorite;
            CardView card_click;



        public ViewHolder(View itemView) {
                super(itemView);

                product_name = itemView.findViewById(R.id.product_name);
                product_price = itemView.findViewById(R.id.product_price);
                ratings = itemView.findViewById(R.id.textViewRating);
                product_image = itemView.findViewById(R.id.product_image);
                product_desc = itemView.findViewById(R.id.detail_desc);
                //  wishlist =itemView.findViewById(R.id.wishlist);
                // favorite = itemView.findViewById(R.id.favorite);
                card_click = itemView.findViewById(R.id.card_click);

            /*    // replace spaces.
                String web = "";
                web = web.replace("", "%20");
                Glide.with(itemView.getContext()).load(web).into(product_image);
*/

            }

    }
}
