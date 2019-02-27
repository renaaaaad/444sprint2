package com.example.renad.exchangeit;

public class User {
    private String id;
    private String Fname;
    private String Lname;
    private String phoneNumber;
    private String city;
    private String type ;
    private String image ;

    private String email;
    private String imageurl;
    private int product_number ;

public User(){

}

    public User(String id, String fname, String lname, String phoneNumber, String city, String email, String imageurl , String t , int num) {
        this.id = id;
        Fname = fname;
        Lname = lname;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.email = email;
        this.imageurl = imageurl;
        type =t;
        product_number = num;
    }



    public void setId(String id) {
        this.id = id;}


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }



    public void setType(String type) {
        this.type = type;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


    public int getNumber() {
        return product_number;
    }

    public void setNumber(int number) {
        this.product_number = number;
    }
}// the class


