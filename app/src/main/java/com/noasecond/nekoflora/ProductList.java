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
        ObjectMapper objectMapper = new ObjectMapper();
        Product[] products = new Product[0];
        try {
            InputStream inputStream = context.getAssets().open("productList.json");
            products = objectMapper.readValue(inputStream, Product[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Product product : products) {
            int drawableId = context.getApplicationContext().getResources().getIdentifier(product.getProductName(), "drawable", context.getApplicationContext().getPackageName());
            product.setProductDrawableID(drawableId);
            productList.add(product);
        }
    }
}