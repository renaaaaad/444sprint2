package com.example.renad.exchangeit;

public class user_Requests {

int id ;
String initial_user , recive_user;
String initial_product , recive_product ;
String status ;
    requestProductDetails requestProductDetails ;

public user_Requests(){

}



    public user_Requests(int id, String initial_user, String recive_user, String initial_product, String recive_product , String s , requestProductDetails request ) {
        this.id = id;
        this.initial_user = initial_user;
        this.recive_user = recive_user;
        this.initial_product = initial_product;
        this.recive_product = recive_product;
        status = s;
        requestProductDetails = request ;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitial_user() {
        return initial_user;
    }

    public void setInitial_user(String initial_user) {
        this.initial_user = initial_user;
    }

    public String getRecive_user() {
        return recive_user;
    }

    public void setRecive_user(String recive_user) {
        this.recive_user = recive_user;
    }

    public String getInitial_product() {
        return initial_product;
    }

    public void setInitial_product(String initial_product) {
        this.initial_product = initial_product;
    }

    public String getRecive_product() {
        return recive_product;
    }

    public void setRecive_product(String recive_product) {
        this.recive_product = recive_product;
    }

    public com.example.renad.exchangeit.requestProductDetails getRequestProductDetails() {
        return requestProductDetails;
    }

    public void setRequestProductDetails(com.example.renad.exchangeit.requestProductDetails requestProductDetails) {
        this.requestProductDetails = requestProductDetails;
    }
}
