package capstone.sda.com.literatures.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import capstone.sda.com.literatures.Pojo.OrderDetailsModel;
import capstone.sda.com.literatures.R;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    Context context;
    String product_id;
    List<OrderDetailsModel> orderDetailList;

    public OrderDetailAdapter(Context context, List<OrderDetailsModel> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final OrderDetailsModel orderDetailsModel = orderDetailList.get(position);

        holder.orderDetailTitle.setText(orderDetailList.get(position).getProduct_name());
        holder.orderDetailPrice.setText(String.valueOf(orderDetailList.get(position).getProduct_price())+"0");
        holder.orderDetailQty.setText(String.valueOf(orderDetailList.get(position).getQuantity()));

        Glide.with(context).load(orderDetailList.get(position).getOrd_imageurl()).into(holder.orderDetailImage);

        product_id = orderDetailList.get(position).getProduct_id();
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView orderDetailImage;
        TextView orderDetailTitle, orderDetailPrice, orderDetailQty;


        public ViewHolder(View itemView) {
            super(itemView);

            orderDetailImage = itemView.findViewById(R.id.ord_detail_image);
            orderDetailTitle = itemView.findViewById(R.id.ord_detail_title);
            orderDetailPrice = itemView.findViewById(R.id.ord_detail_price);
            orderDetailQty = itemView.findViewById(R.id.ord_detail_tv_quantity);

        }
    }
}
