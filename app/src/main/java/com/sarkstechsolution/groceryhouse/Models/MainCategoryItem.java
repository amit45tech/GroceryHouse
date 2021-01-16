package com.sarkstechsolution.groceryhouse.Models;



public class MainCategoryItem {

    public MainCategoryItem() {

    }

    private String category;
    private int cimage;

    public MainCategoryItem(String category, int cimage) {
        this.category = category;
        this.cimage = cimage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCimage() {
        return cimage;
    }

    public void setCimage(int cimage) {
        this.cimage = cimage;
    }
}
