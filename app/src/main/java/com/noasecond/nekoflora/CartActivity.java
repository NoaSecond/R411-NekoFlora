package com.noasecond.nekoflora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private TextView tv_titleCart;
    private LinearLayout display;
    private TabLayout tabLayout;
    private ConstraintLayout cl_cart;
    private Intent intent;
    private LinearLayout ll_livraison;
    private EditText et_firstnameRetrait;
    private EditText et_lastnameRetrait;
    private EditText et_firstnameLivraison;
    private EditText et_lastnameLivraison;
    private EditText et_postalAddress;
    private LinearLayout ll_retrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Intent
        intent = getIntent();
        selectedProducts = (ArrayList<Product>) intent.getSerializableExtra("SelectedProducts");

        //Init
        ll_tabWrap = findViewById(R.id.ll_tabWrap);
        mapView = findViewById(R.id.map_view);
        tv_titleCart = findViewById(R.id.tv_titlePayment);
        display = findViewById(R.id.linearLayoutProductList);
        tabLayout = findViewById(R.id.simpleTabLayout);
        cl_cart = findViewById(R.id.cl_cart);
        btn_checkout = findViewById(R.id.btn_checkout);
        ll_livraison = findViewById(R.id.ll_livraison);
        ll_retrait = findViewById(R.id.ll_retrait);
        et_firstnameRetrait = findViewById(R.id.et_firstnameRetrait);
        et_lastnameRetrait = findViewById(R.id.et_lastnameRetrait);
        et_firstnameLivraison = findViewById(R.id.et_firstnameLivraison);
        et_lastnameLivraison = findViewById(R.id.et_lastnameLivraison);
        et_postalAddress = findViewById(R.id.et_postalAddress);

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
            if(delivery) {
                //Mode livraison
                if (et_firstnameLivraison.getText().toString().trim().isEmpty() || et_lastnameLivraison.getText().toString().trim().isEmpty() || et_postalAddress.getText().toString().trim().isEmpty()) {
                    //Champs non remplis
                    Toast.makeText(getApplicationContext(), "Veuillez renseigner les informations.", Toast.LENGTH_SHORT).show();
                } else {
                    //Champs remplis
                    for (Product product : selectedProducts) {
                        totalPrice+= product.getProductPrice();
                    }
                    Intent intentPayment = new Intent(CartActivity.this, PaymentActivity.class);
                    intentPayment.putExtra("totalPrice", totalPrice);
                    intentPayment.putExtra("delivery", delivery);
                    intentPayment.putExtra("firstname", et_firstnameLivraison.getText().toString());
                    intentPayment.putExtra("lastname", et_lastnameLivraison.getText().toString());
                    intentPayment.putExtra("postalAddress", et_postalAddress.getText().toString());
                    startActivity(intentPayment);
                }
            } else {
                //Mode retrait
                if(et_firstnameRetrait.getText().toString().trim().isEmpty() || et_lastnameRetrait.getText().toString().trim().isEmpty()) {
                    //Champs non remplis
                    Toast.makeText(getApplicationContext(), "Veuillez renseigner les informations.", Toast.LENGTH_SHORT).show();
                } else {
                    //Champs remplis
                    for (Product product : selectedProducts) {
                        totalPrice+= product.getProductPrice();
                    }
                    Intent intentPayment = new Intent(CartActivity.this, PaymentActivity.class);
                    intentPayment.putExtra("totalPrice", totalPrice);
                    intentPayment.putExtra("delivery", delivery);
                    intentPayment.putExtra("firstname", et_firstnameRetrait.getText().toString());
                    intentPayment.putExtra("lastname", et_lastnameRetrait.getText().toString());
                    startActivity(intentPayment);
                }
            }
        });

        //Bind Tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ll_tabWrap.removeAllViews();
                    ll_tabWrap.addView(ll_retrait);
                    delivery = false;
                } else {
                    ll_tabWrap.removeAllViews();
                    ll_tabWrap.addView(ll_livraison);
                    delivery = true;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    //MapView
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