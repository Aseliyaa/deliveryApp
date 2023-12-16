package by.ruban.deliveryapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Business {
    private String id;
    private String name;
    private String image_url;
    private String rating;
    private String price;
    private List<Category> categories;
    private Location location;
    private List<Review> reviews;
    private String phone;
    private Coordinates coordinates;
    private List<String> photos;
    private List<Menu> menus;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }


    public List<String> getPhotos() {
        return photos;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public static class Menu {
        private String title;
        private List<MenuSection> sections;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<MenuSection> getSections() {
            return sections;
        }

        public void setSections(List<MenuSection> sections) {
            this.sections = sections;
        }
    }

    public static class MenuSection {
        private String name;
        private List<MenuItem> menuItems;

        public String getName() {
            return name;
        }

        public List<MenuItem> getMenuItems() {
            return menuItems;
        }
    }

    public static class MenuItem {
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("price")
        private String price;

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getPrice() {
            return price;
        }
    }
    // Other methods
    public  static class Coordinates{
        private String latitude;
        private String longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(latitude).append('\'');
            sb.append(longitude).append('\'');
            return sb.toString();
        }
    }

    public static class Category {
        private String alias;
        private String title;

        // Getters and setters

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {

            return this.getTitle() + "\n" ;
        }
    }

    public static class Location {
        private String address1;
        private String address2;
        private String address3;
        private String city;
        private String state;
        private String country;
        private String zip_code;

        // Getters and setters

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        @Override
        public String toString() {
            return
                    this.getAddress1() + ", " + this.getCity() + "\n";
        }
    }

    public static class Review {
        @Override
        public String toString() {
            return
                    "text: " + this.getText() + "\n" +
                    "rating: " + this.getRating() + "\n" +
                    "user: " + this.getUser() + "\n";
        }

        private String text;
        private double rating;
        private User user;

        // Getters and setters

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class User {
        private String name;
        private String image_url;

        // Getters and setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("User{");
            sb.append("name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}