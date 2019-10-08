package capstone.sda.com.literatures.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import capstone.sda.com.literatures.Pojo.Wishlist;
import capstone.sda.com.literatures.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>{

    private Context context;
    private List<Wishlist> wishlist;
    String product_id;

    public WishlistAdapter(Context context, List<Wishlist> wishlist){
        this.context = context;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.wishlistName.setText(wishlist.get(position).getProduct_name());
     //   holder.mRating.setText(String.valueOf(wishlist.get(position).getRating()));
        Glide.with(context).load(wishlist.get(position).getWishlist_imageurl()).into(holder.wishlistImage);

        holder.removeWishlistBrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromWishlist();
            }
        });

        product_id = wishlist.get(position).getProduct_id();
    }

    // TODO :: Remove from wishlist function.
    private void removeFromWishlist() {

    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView wishlistImage;
        TextView wishlistName;
      //  TextView mRating;
        Button removeWishlistBrn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlistImage = itemView.findViewById(R.id.wishlist_image);
            wishlistName = itemView.findViewById(R.id.wishlist_name);
          //  mRating = itemView.findViewById(R.id.wtextViewRating);
            removeWishlistBrn = itemView.findViewById(R.id.remove_wishlist);

        }
    }
}
