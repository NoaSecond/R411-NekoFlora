package com.noasecond.nekoflora;

import android.content.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProductList {
    private Context context;
    public ArrayList productList = new ArrayList<Product>();

    public ProductList(Context context) {
        this.context = context;
        this.initList();
    }

    private void initList() {
        //Liste générée en brut
        //Product1
        int drawableId1 = context.getApplicationContext().getResources().getIdentifier("an", "drawable", context.getApplicationContext().getPackageName());
        Product p1 = new Product("an", 9.9, "C'est vraiment pas mal.", drawableId1);
        productList.add(p1);
        //Product2
        int drawableId2 = context.getApplicationContext().getResources().getIdentifier("jon", "drawable", context.getApplicationContext().getPackageName());
        Product p2 = new Product("jon", 9.9, "C'est vraiment pas mal.", drawableId1);
        productList.add(p2);
        //Product3
        productList.add(p1);
        //Product4
        productList.add(p2);

        //TODO générer la liste à partir d'un Json
        //ObjectMapper objectMapper = new ObjectMapper();
        //InputStream inputStream = new FileInputStream("/productList.json");
    }
}