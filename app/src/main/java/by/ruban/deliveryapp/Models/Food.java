package by.ruban.deliveryapp.Models;

import java.util.List;

public class Food {
    String price;
    String name;
    String calories;
    String compound;
    String image_url;
    String timeOfCoocking;

    public Food(String price, String name, String calories, String compound, String image_url, String timeOfCoocking) {
        this.price = price;
        this.name = name;
        this.calories = calories;
        this.compound = compound;
        this.image_url = image_url;
        this.timeOfCoocking = timeOfCoocking;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTimeOfCoocking() {
        return timeOfCoocking;
    }

    public void setTimeOfCoocking(String timeOfCoocking) {
        this.timeOfCoocking = timeOfCoocking;
    }
}
