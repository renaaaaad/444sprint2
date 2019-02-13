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
    public User(){

    }
    public User(String i  ,String f,String l,String p, String e ,String c , String t ,String im){
        id = i;
        Fname=f;
        Lname=l;
        phoneNumber=p;
        email=e;
        city=c;
        type =t;
        image=  im ;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFname() {
        return Fname;
    }



    public String getLname() {
        return Lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public void setLname(String lname) {
        Lname = lname;
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

    public void setId(String id) {
        this.id = id;
    }
}// the class

