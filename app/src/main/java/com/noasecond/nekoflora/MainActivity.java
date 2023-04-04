package com.noasecond.nekoflora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Product> selectedProducts = new ArrayList<>();
    public ProductList productList;
    private SearchView sv_searchBar;
    private ImageView iv_shoppingCart;
    private GridView gv_productList;
    private ConstraintLayout cl_main;
    private String queryText;
    private Context contextRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ProductList(this);

        //Adapter
        ProductAdapter adapter = new ProductAdapter(this, productList.productList);
        GridView display = findViewById(R.id.gv_productList);
        display.setAdapter(adapter);

        //Init
        sv_searchBar = findViewById(R.id.sv_searchBar);
        iv_shoppingCart = findViewById(R.id.iv_shoppingCart);
        gv_productList = findViewById(R.id.gv_productList);
        cl_main = findViewById(R.id.cl_main);

        //Define
        this.contextRef = this;


        //DarkMode & DayMode
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags != Configuration.UI_MODE_NIGHT_YES) {
            iv_shoppingCart.setColorFilter(ContextCompat.getColor(this, R.color.dayText), PorterDuff.Mode.SRC_IN);
            cl_main.setBackgroundColor(ContextCompat.getColor(this, R.color.dayBackground));
        } else {
            iv_shoppingCart.setColorFilter(ContextCompat.getColor(this, R.color.nightText), PorterDuff.Mode.SRC_IN);
            cl_main.setBackgroundColor(ContextCompat.getColor(this, R.color.nightBackground));
        }

        //Bind shopping cart
        iv_shoppingCart.setOnClickListener(e -> {
            if(selectedProducts.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez choisir au moins 1 produit.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.putExtra("SelectedProducts", selectedProducts);
                startActivity(intent);
            }
        });

        //Bind search bar
        sv_searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryText = query;
                ArrayList<Product> queryProductList = new ArrayList<>();
                for (Object obj : productList.productList) {
                    Product queryProduct = (Product) obj;
                    if (queryProduct.getProductName().startsWith(queryText)) {
                        queryProductList.add(queryProduct); //ADD PRODUCT WITH SEARCH NAME
                    }
                }
                //CREATE NEW ADAPTER WITH THE NEW LIST
                ProductAdapter adapterQuery = new ProductAdapter(contextRef, queryProductList);
                display.setAdapter(adapterQuery);
                return false;
            }
            //UPDATE IF SEARCH CHANGE
            @Override
            public boolean onQueryTextChange(String newText) {
                queryText = newText;
                ArrayList<Product> queryProductList = new ArrayList<>();
                for (Object obj : productList.productList) {
                    Product queryProduct = (Product) obj;
                    if (queryProduct.getProductName().startsWith(queryText)) {
                        queryProductList.add(queryProduct);
                    }
                }
                ProductAdapter adapterQuery = new ProductAdapter(contextRef, queryProductList);
                display.setAdapter(adapterQuery);
                return false;
            }
        });
    }

    public void onResume() {

        super.onResume();
        ImageView rondRouge = findViewById(R.id.rondRougeMain);
        TextView nbCard = findViewById(R.id.nbCardMain);
        if (selectedProducts.size()>9){
            nbCard.setText("9+");

        }
        else{
            nbCard.setText(""+ selectedProducts.size());
        }



    }
    public void openProductActivity(Product choosedProduct) {
        Bundle bundle = new Bundle();
        bundle.putString("mainContext", this.getApplicationContext().toString());



        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
        intent.putExtra("ChoosedProduct", choosedProduct);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public String getMainActivityId() {
        return Integer.toHexString(System.identityHashCode(this));
    }
}