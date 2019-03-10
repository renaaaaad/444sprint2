package com.example.renad.exchangeit;

public class SystemProduct {

    private String name , discription , category  ,path,useID,productID ;

public SystemProduct(){

}
    public SystemProduct(String name, String discription, String category, String path, String useID, String productID) {
        this.name = name;
        this.discription = discription;
        this.category = category;
        this.path = path;
        this.useID = useID;
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUseID() {
        return useID;
    }

    public void setUseID(String useID) {
        this.useID = useID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
