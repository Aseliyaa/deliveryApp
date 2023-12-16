package by.ruban.deliveryapp.Interface;

import by.ruban.deliveryapp.Models.Business;
import by.ruban.deliveryapp.Api.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpApi {
        @GET("businesses/search")
        Call<SearchResponse> searchBusinesses(
                @Header("Authorization") String authorization,
                @Query("term") String term,
                @Query("location") String location,
                 @Query("categories") String categories
        );


        @GET("businesses/{id}")
        Call<Business> getBusiness(
                @Header("Authorization") String authorization,
                @Path("id") String id
        );

        @GET("businesses/{id}/menu")
        Call<Business> getMenus(
                @Header("Authorization") String authorization,
                @Path("id") String id
        );
}
