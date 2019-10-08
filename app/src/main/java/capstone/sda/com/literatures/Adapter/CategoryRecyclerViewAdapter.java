package capstone.sda.com.literatures.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import capstone.sda.com.literatures.Pojo.CategoryModel;
import capstone.sda.com.literatures.R;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private Context context;
    private List<CategoryModel> categoryModels;



    public CategoryRecyclerViewAdapter(Context context, List<CategoryModel> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categoryitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.categoryName.setText(categoryModels.get(position).getCat_name());

        holder.categoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: on Category: " + categoryModels.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
