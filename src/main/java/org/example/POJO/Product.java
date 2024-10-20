package org.example.POJO;

public class Product {
    int productID;
    String name;
    boolean isCoocked;
    String image;
    String categoryName;
    Nutrients nutrients;

    public Product(String name, boolean isCoocked, String image, String categoryName, Nutrients nutrients) {
        this.name = name;
        this.isCoocked = isCoocked;
        this.image = image;
        this.categoryName = categoryName;
        this.nutrients = nutrients;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCoocked() {
        return isCoocked;
    }

    public void setCoocked(boolean coocked) {
        isCoocked = coocked;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }
}
