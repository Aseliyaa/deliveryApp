package by.ruban.deliveryapp.Activity;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import by.ruban.deliveryapp.Adapter.RecommendedAdapter;
import by.ruban.deliveryapp.Adapter.RestaurantAdapter;

import by.ruban.deliveryapp.Models.Restaurant;
import by.ruban.deliveryapp.Api.SearchResponse;
import by.ruban.deliveryapp.Interface.YelpApi;
import by.ruban.deliveryapp.Domain.FoodDomain;
import by.ruban.deliveryapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RestaurantAdapter adapter;
    ProgressBar progressBar;
    CoordinatorLayout coordinatorLayout;
    ScrollView scrollView;
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        searchBar = findViewById(R.id.searchBar);
        progressBar = findViewById(R.id.progressBar);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        scrollView = findViewById(R.id.scrollView);

        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.restaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter = new RestaurantAdapter(this);
        recyclerView.setAdapter(adapter);

        restaurantsApi();
        getUser();
        recyclerViewPopular();
        bottomNavigation();
        searchBarFiltering();
    }

    private void searchBarFiltering() {
    }

    private void getUser() {
        TextView userName = findViewById(R.id.userName);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference nameRef = database.getReference("Users").child(userId).child("name");
        nameRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                userName.setText("Hi, " + name + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                startActivity(new Intent(MainActivity.this, IntroActivity.class));
            }
        });
    }

    private void restaurantsApi() {
        String apiKey = "nEOWyDa3hO2cnvOgUNn8ngKfKixHIH8AkbfIusNAwJWi61j7YYn2oeYy60cTdFVJbZdt73c8vzJDgti840WjQEcUkjO-mb9_qa0KCD8Vwl3sD2LZnibC25ENoQldZHYx";

        YelpApi api = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(YelpApi.class);

        Call<SearchResponse> call = api.searchBusinesses(
                "Bearer " + apiKey,
                "food",
                "New York",
                "restaurants"
        );

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    List<Restaurant> businesses = response.body().getBusinesses();
                    adapter.setData(businesses);

                    adapter.setOnClickListener(new RestaurantAdapter.OnClickListener() {
                        @Override
                        public void onItemClick(String businessId) {
                            Intent intent = new Intent(MainActivity.this, ProductPage.class);
                            intent.putExtra("business_id", businessId);
                            startActivity(intent);
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                    coordinatorLayout.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bottomNavigation() {
        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        LinearLayout cartBtn=findViewById(R.id.cartBtn);
        LinearLayout profileBtn = findViewById(R.id.profile);
        LinearLayout restaurant = findViewById(R.id.newRestaurant);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Profile.class));
            }
        });
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewPopularList = findViewById(R.id.view2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodlist = new ArrayList<>();
        foodlist.add(new FoodDomain("Shrimp", "shrimp1_foreground", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, shrimps", 13.0, 5, 20, 1000));
        foodlist.add(new FoodDomain("Beef", "fish_foreground", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 11.20, 4, 18, 1500));
        foodlist.add(new FoodDomain("Fish", "beef_foreground", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, fish", 11.0, 3, 20, 800));
        foodlist.add(new FoodDomain("Potato", "potato_foreground", "fish ,tomatoes, fresh oregano,  ground black pepper, potato", 10.0, 5, 20, 945));
        foodlist.add(new FoodDomain("Seafood", "seafood_foreground", "fish, shrimp, Special sauce, Lettuce, midis ", 14.40, 4, 45, 600));
        foodlist.add(new FoodDomain("Cake", "cake_foreground", "strawberry, oil, sugar, chocolate, blackberry", 9.0, 3, 16, 800));

        RecyclerView.Adapter adapter2 = new RecommendedAdapter(foodlist);
        recyclerViewPopularList.setAdapter(adapter2);
    }

}