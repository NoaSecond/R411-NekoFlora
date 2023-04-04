package com.noasecond.nekoflora;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    ImageView display_productImage;
    TextView display_productName;
    TextView display_productPrice;
    private ArrayList<Product> productList;//LIST OF ALL PRODUCT
    private Context context;
    private LayoutInflater mInflater;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem;

        layoutItem = convertView == null ? mInflater.inflate(R.layout.product_layout, parent, false) : convertView;

        //Init
        display_productImage = layoutItem.findViewById(R.id.iv_productImage);
        display_productName = layoutItem.findViewById(R.id.tv_productName);
        display_productPrice = layoutItem.findViewById(R.id.tv_productPrice);

        //Define
        int drawableId = ((Product) productList.get(position)).getProductDrawableID();
        Drawable drawable = ContextCompat.getDrawable(layoutItem.getContext(), drawableId);
        display_productImage.setImageDrawable(drawable);
        display_productName.setText(((Product) productList.get(position)).getProductName());
        display_productPrice.setText(((Product) productList.get(position)).getProductPrice()+" €");

        //Bind click
        display_productImage.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Aller sur la page produit ?");
            builder.setTitle("Produit : "+((Product) productList.get(position)).getProductName());
            builder.setCancelable(false);
            builder.setPositiveButton("Consulter", (DialogInterface.OnClickListener) (dialog, which) -> {
                MainActivity mainActivity = (MainActivity) this.context;
                mainActivity.openProductActivity((Product) productList.get(position));
                dialog.cancel();
            });
            builder.setNegativeButton("Annuler", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        return layoutItem; //RETURN NEW ITEM.
    }
}