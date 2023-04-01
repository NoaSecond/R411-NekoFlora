package com.noasecond.nekoflora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ArrayList<Product> selectedProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Intent
        Intent intent = getIntent();
        selectedProducts = (ArrayList<Product>) intent.getSerializableExtra("SelectedProducts");

        //Init
        LinearLayout display = findViewById(R.id.linearLayoutProductList);

        //Add products
        for (Product product : selectedProducts) {
            View productView = getLayoutInflater().inflate(R.layout.product_layout, null);

            ImageView iv_productImage = productView.findViewById(R.id.iv_productImage);
            int drawableId = product.getProductDrawableID();
            Drawable drawable = ContextCompat.getDrawable(this, drawableId);
            iv_productImage.setImageDrawable(drawable);

            TextView tv_productName = productView.findViewById(R.id.tv_productName);
            tv_productName.setText(product.getProductName());

            TextView tv_productPrice = productView.findViewById(R.id.tv_productPrice);
            tv_productPrice.setText(product.getProductPrice()+" €");

            display.addView(productView);
        }
    }
}