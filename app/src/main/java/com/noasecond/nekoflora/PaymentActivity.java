package com.noasecond.nekoflora;

import static com.noasecond.nekoflora.MainActivity.selectedProducts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button btn_payment;
    private EditText et_emailPayment;
    private EditText et_cvv;
    private EditText et_expirationDate;
    private EditText et_cardNumber;
    private ConstraintLayout cl_payment;

    private TextView load1;
    private TextView load2;

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
        btn_payment = findViewById(R.id.btn_payment);
        et_emailPayment = findViewById(R.id.et_emailPayment);
        et_cardNumber = findViewById(R.id.et_cardNumber);
        et_cvv = findViewById(R.id.et_cvv);
        et_expirationDate = findViewById(R.id.et_expirationDate);
        cl_payment = findViewById(R.id.cl_payment);
        load1 = findViewById(R.id.load1);
        load1.setVisibility(View.INVISIBLE);
        load2 = findViewById(R.id.load2);
        load2.setVisibility(View.INVISIBLE);

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
            cl_payment.setBackgroundColor(ContextCompat.getColor(this, R.color.dayBackground));
            tv_titlePayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_recapPayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_firstnamePayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_lastnamePayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_purchasePayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
            tv_addressPayment.setTextColor(ContextCompat.getColor(this, R.color.dayText));
        } else {
            cl_payment.setBackgroundColor(ContextCompat.getColor(this, R.color.nightBackground));
            tv_titlePayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_recapPayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_firstnamePayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_lastnamePayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_purchasePayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
            tv_addressPayment.setTextColor(ContextCompat.getColor(this, R.color.nightText));
        }

        //Bind checkout_button
        btn_payment.setOnClickListener(e -> {
            if(et_emailPayment.getText().toString().trim().isEmpty() || et_expirationDate.getText().toString().trim().isEmpty() || et_cvv.getText().toString().trim().isEmpty() || et_cardNumber.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez renseigner les champs.", Toast.LENGTH_SHORT).show();
            } else {

                    load1.setVisibility(View.VISIBLE);
                    load1.startAnimation(AnimationUtils.loadAnimation(PaymentActivity.this, R.anim.load));
                    load1.setVisibility(View.INVISIBLE);
                    load2.setVisibility(View.VISIBLE);
                    load2.startAnimation(AnimationUtils.loadAnimation(PaymentActivity.this, R.anim.load2));
                    load2.setVisibility(View.INVISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                        selectedProducts.clear();
                        Toast.makeText(getApplicationContext(), "Commande validée !", Toast.LENGTH_LONG).show();
                        startActivity(intentMain);
                    }
                }, 5000);

            }
        });
    }
}