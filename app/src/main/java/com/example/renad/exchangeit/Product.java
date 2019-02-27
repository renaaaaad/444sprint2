package com.example.renad.exchangeit;

public class Product {



    private String name , discription , category  ,path ,id , product_number ;



    public Product(){

    }

    public Product( String n ,String d ,String c  ,String p , String i , String pn){
        name = n;
        discription = d;
        category = c;
        path = p;
        id = i ;
        product_number = pn ;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDiscription() {
        return discription;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

