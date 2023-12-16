package by.ruban.deliveryapp.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import by.ruban.deliveryapp.Activity.MainActivity;
import by.ruban.deliveryapp.Models.Restaurant;
import by.ruban.deliveryapp.R;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private List<Restaurant> businesses = new ArrayList<>();
    private OnClickListener listener;
    private Activity activity;


    public RestaurantAdapter(MainActivity activity) {
        this.activity = activity;
    }
    public void setData(List<Restaurant> businesses) {
        this.businesses = businesses;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onItemClick(String businessId);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant business = businesses.get(position);
        holder.bind(business);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(business.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }


    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView nameView;
        private TextView ratingView;
        private TextView priceView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurantImage);
            nameView = itemView.findViewById(R.id.restaurantName);
        }

        public void bind(Restaurant business) {
            nameView.setText(business.getName());
            //ratingView.setText(String.valueOf(business.getRating()));

            Glide.with(imageView)
                    .load(business.getImage_url())
                    .into(imageView);
        }
    }
}