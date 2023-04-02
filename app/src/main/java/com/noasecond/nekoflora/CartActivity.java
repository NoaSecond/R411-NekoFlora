package com.noasecond.nekoflora;

import static com.noasecond.nekoflora.MainActivity.selectedProducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap mMap;
    ArrayList<Product> selectedProducts = new ArrayList<>();
    private LinearLayout ll_tabWrap;
    private Button btn_checkout;
    private double totalPrice = 0.00;
    private boolean delivery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Intent
        Intent intent = getIntent();
        selectedProducts = (ArrayList<Product>) intent.getSerializableExtra("SelectedProducts");

        //Init
        ll_tabWrap = findViewById(R.id.ll_tabWrap);
        mapView = findViewById(R.id.map_view);
        TextView tv_titleCart = findViewById(R.id.tv_titleCart);
        LinearLayout display = findViewById(R.id.linearLayoutProductList);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        ConstraintLayout cl_cart = (ConstraintLayout) findViewById(R.id.cl_cart);
        btn_checkout = (Button) findViewById(R.id.btn_checkout);

        //Define
        tv_titleCart.setText("Pannier");
        tabLayout.addTab(tabLayout.newTab().setText("Retrait"));
        tabLayout.addTab(tabLayout.newTab().setText("Livraison"));
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //DarkMode & DayMode
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags != Configuration.UI_MODE_NIGHT_YES) {
            tv_titleCart.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            cl_cart.setBackgroundColor(ContextCompat.getColor(this, R.color.dayBackground));
        } else {
            tv_titleCart.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            cl_cart.setBackgroundColor(ContextCompat.getColor(this, R.color.nightBackground));
        }

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

        //Bind checkout_button
        btn_checkout.setOnClickListener(e -> {
            for (Product product : selectedProducts) {
                totalPrice+= product.getProductPrice();
            }
            Intent intentPayment = new Intent(CartActivity.this, PaymentActivity.class);
            intentPayment.putExtra("totalPrice", totalPrice);
            intentPayment.putExtra("delivery", delivery);
            startActivity(intentPayment);
        });

        //Bind Tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ll_tabWrap.addView(mapView);
                    delivery = false;
                } else {
                    ll_tabWrap.removeAllViews();
                    delivery = true;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng nice = new LatLng(43.697914, 7.265528);
        mMap.addMarker(new MarkerOptions().position(nice).title("NekoFlora - Nice"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nice));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}