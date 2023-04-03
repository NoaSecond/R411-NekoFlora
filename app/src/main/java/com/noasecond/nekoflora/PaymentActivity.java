package com.noasecond.nekoflora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private double totalPrice;
    private boolean delivery;
    private String firstname;
    private String lastname;
    private String postalAddress;

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
    }
}