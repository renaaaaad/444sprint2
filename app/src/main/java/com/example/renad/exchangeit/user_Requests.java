package com.example.renad.exchangeit;

public class user_Requests {

int id ;
User initial_user , recive_user;
Product initial_product , recive_product ;

public user_Requests(){

}



    public user_Requests(int id, User initial_user, User recive_user, Product initial_product, Product recive_product) {
        this.id = id;
        this.initial_user = initial_user;
        this.recive_user = recive_user;
        this.initial_product = initial_product;
        this.recive_product = recive_product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getInitial_user() {
        return initial_user;
    }

    public void setInitial_user(User initial_user) {
        this.initial_user = initial_user;
    }

    public User getRecive_user() {
        return recive_user;
    }

    public void setRecive_user(User recive_user) {
        this.recive_user = recive_user;
    }

    public Product getInitial_product() {
        return initial_product;
    }

    public void setInitial_product(Product initial_product) {
        this.initial_product = initial_product;
    }

    public Product getRecive_product() {
        return recive_product;
    }

    public void setRecive_product(Product recive_product) {
        this.recive_product = recive_product;
    }
}
