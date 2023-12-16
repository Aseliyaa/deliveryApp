package by.ruban.deliveryapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import by.ruban.deliveryapp.Models.Business;
import by.ruban.deliveryapp.Interface.YelpApi;

import by.ruban.deliveryapp.R;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ProductPage extends AppCompatActivity {
    private TextView nameView;
    private TextView ratingView;
    private TextView priceView;
    private ImageView imageView;
    private TextView phoneView;
    private TextView locationView;
    private TextView titleView;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    private ImageView photoView;
    private ImageView photoView2;
    private ImageView photoView3;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.productLayout);

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.menu);
        titleView = findViewById(R.id.titleTextView);
        locationView = findViewById(R.id.locationTextView);
        nameView = findViewById(R.id.nameText);
        ratingView = findViewById(R.id.ratingTextView);
        priceView = findViewById(R.id.priceTextView);
        imageView = findViewById(R.id.item_image);
        phoneView = findViewById(R.id.phoneTextView);
        photoView = findViewById(R.id.photoView);
        photoView2 = findViewById(R.id.photoView2);
        photoView3 = findViewById(R.id.photoView3);
        String businessId = getIntent().getStringExtra("business_id");


        YelpApi api = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(YelpApi.class);

        String apiKey = "nEOWyDa3hO2cnvOgUNn8ngKfKixHIH8AkbfIusNAwJWi61j7YYn2oeYy60cTdFVJbZdt73c8vzJDgti840WjQEcUkjO-mb9_qa0KCD8Vwl3sD2LZnibC25ENoQldZHYx";
        Call<Business> call = api.getBusiness("Bearer " + apiKey, businessId);

        call.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                if (response.isSuccessful()) {
                    Business business = response.body();
                    List<Business.Category> categories =business.getCategories();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Business.Category category : categories) {
                        String title = category.getTitle();
                        stringBuilder.append(title).append("\n");
                    }

                    nameView.setText(business.getName());
                    ratingView.setText(business.getRating());
                    priceView.setText(business.getRating() + "" + business.getPrice());
                    phoneView.setText(business.getPhone());
                    locationView.setText(business.getLocation().toString());
                    titleView.setText(stringBuilder.toString());

                    Glide.with(imageView)
                            .load(business.getImage_url())
                            .into(imageView);
                    Glide.with(photoView)
                            .load(business.getPhotos().get(0))
                            .into(photoView);
                    Glide.with(photoView2)
                            .load(business.getPhotos().get(1))
                            .into(photoView2);
                    Glide.with(photoView3)
                            .load(business.getPhotos().get(2))
                            .into(photoView3);
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {
                Toast.makeText(ProductPage.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}