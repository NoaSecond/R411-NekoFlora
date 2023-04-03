package com.noasecond.nekoflora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private double totalPrice;
    private boolean delivery;
    private String firstname;
    private String lastname;
    private String postalAddress;
    private TextView tv_titlePayment;
    private TextView tv_recapPayment;
    private TextView tv_firstnamePayment;
    private TextView tv_lastnamePayment;
    private TextView tv_purchasePayment;
    private TextView tv_addressPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Intent
        Intent intent = getIntent();
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        delivery = getIntent().getBooleanExtra("delivery", false);
        firstname = getIntent().getStringExtra("firstname");
        lastname = getIntent().getStringExtra("lastname");
        if (delivery) {
            postalAddress = getIntent().getStringExtra("postalAddress");
        }

        //Init
        tv_titlePayment = findViewById(R.id.tv_titlePayment);
        tv_firstnamePayment = findViewById(R.id.tv_firstnamePayment);
        tv_lastnamePayment = findViewById(R.id.tv_lastnamePayment);
        tv_purchasePayment = findViewById(R.id.tv_purchasePayment);
        tv_addressPayment = findViewById(R.id.tv_addressPayment);
        tv_recapPayment = findViewById(R.id.tv_recapPayment);

        //Define
        tv_recapPayment.setText("Récapitulatif de la commande :");
        tv_titlePayment.setText("Paiement :");
        tv_firstnamePayment.setText("Prénom : "+firstname);
        tv_lastnamePayment.setText("Nom : "+lastname);
        if (delivery) {
            tv_purchasePayment.setText("Mode : Livraison");
            tv_addressPayment.setText("Adresse : "+postalAddress);
        } else {
            tv_purchasePayment.setText("Mode : Retrait");
            tv_addressPayment.setText("");
        }

        //DarkMode & DayMode
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags != Configuration.UI_MODE_NIGHT_YES) {
            tv_titlePayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_recapPayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_firstnamePayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_lastnamePayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_purchasePayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_addressPayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
        } else {
            tv_titlePayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_recapPayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_firstnamePayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_lastnamePayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_purchasePayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_addressPayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
        }
    }
}