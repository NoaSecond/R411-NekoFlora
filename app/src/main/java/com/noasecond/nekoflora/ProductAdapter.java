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

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private ArrayList<Product> productList;
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

        //(1) : Réutilisation des layouts
        layoutItem = convertView == null ? mInflater.inflate(R.layout.product_layout, parent, false) : convertView;

        //(2) : Récupération des elements de notre layout
        ImageView display_productImage = layoutItem.findViewById(R.id.iv_productImage);
        TextView display_productName = layoutItem.findViewById(R.id.tv_productName);
        TextView display_productPrice = layoutItem.findViewById(R.id.tv_productPrice);

        //(3) : Renseignement des valeurs
        int drawableId = ((Product) productList.get(position)).getProductDrawableID();
        Drawable drawable = ContextCompat.getDrawable(layoutItem.getContext(), drawableId);
        display_productImage.setImageDrawable(drawable);
        display_productName.setText(((Product) productList.get(position)).getProductName());
        display_productPrice.setText(((Product) productList.get(position)).getProductPrice()+" €");

        //(5) : Evenement click
        display_productImage.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Vous avez cliqué sur : "+((Product) productList.get(position)).getProductName());
            builder.setTitle("Produit");
            builder.setCancelable(false);
            builder.setPositiveButton("Oui", (DialogInterface.OnClickListener) (dialog, which) -> {
                MainActivity mainActivity = (MainActivity) this.context;
                mainActivity.selectedProducts.add(productList.get(position));
                dialog.cancel();
            });
            builder.setNegativeButton("Non", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        return layoutItem; //On retourne l'item créé.
    }
}