package by.ruban.deliveryapp.Api;

import java.util.List;

import by.ruban.deliveryapp.Models.Food;
import by.ruban.deliveryapp.Models.Restaurant;

public class SearchResponse {
    private List<Restaurant> businesses;

    public List<Restaurant> getBusinesses() {
        return businesses;
    }

}
