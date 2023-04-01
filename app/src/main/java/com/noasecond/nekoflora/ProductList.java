package com.noasecond.nekoflora;

import android.content.Context;

import java.util.ArrayList;

public class ProductList {
    private Context context;
    public ArrayList productList = new ArrayList<Product>();

    public ProductList(Context context) {
        this.context = context;
        this.initList();
    }

    private void initList() {
        //Product1
        int drawableId1 = context.getApplicationContext().getResources().getIdentifier("an", "drawable", context.getApplicationContext().getPackageName());
        Product p1 = new Product("an", 9.9, "C'est vraiment pas mal.", drawableId1);
        productList.add(p1);
        //Product2
        int drawableId2 = context.getApplicationContext().getResources().getIdentifier("jon", "drawable", context.getApplicationContext().getPackageName());
        Product p2 = new Product("jon", 9.9, "C'est vraiment pas mal.", drawableId1);
        productList.add(p2);
    }
}