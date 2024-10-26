package org.example.POJO;

public class Product {
    int productID;
    Category category;
    String name;
    boolean isCoocked;
    String categoryName;
    Nutrients nutrients;

    public Product(){}
    public Product(int id, Category category, String name, boolean isCoocked, String categoryName, Nutrients nutrients) {
        this.productID = id;
        this.name = name;
        this.isCoocked = isCoocked;
        this.categoryName = categoryName;
        this.nutrients = nutrients;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
