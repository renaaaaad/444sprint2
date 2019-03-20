package com.example.renad.exchangeit;

public class requestProductDetails {

    String intiate_path , recive_path ;
    String intiater_name , p_name  , p_des ;


    public requestProductDetails(){

    }


    public requestProductDetails(String intiate_path, String recive_path, String recive_name, String p_name, String p_des) {
        this.intiate_path = intiate_path;
        this.recive_path = recive_path;
        this.intiater_name = recive_name;
        this.p_name = p_name;
        this.p_des = p_des;
    }

    public String getIntiate_path() {
        return intiate_path;
    }

    public void setIntiate_path(String intiate_path) {
        this.intiate_path = intiate_path;
    }

    public String getRecive_path() {
        return recive_path;
    }

    public void setRecive_path(String recive_path) {
        this.recive_path = recive_path;
    }

    public String getRecive_name() {
        return intiater_name;
    }

    public void setRecive_name(String recive_name) {
        this.intiater_name = recive_name;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_des() {
        return p_des;
    }

    public void setP_des(String p_des) {
        this.p_des = p_des;
    }
}
