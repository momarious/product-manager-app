package com.momarious.firestore.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.momarious.firestore.Product;
import com.momarious.firestore.R;

public class MainActivity extends AppCompatActivity {

    private static final String PRODUCTS = "products";
    private EditText editTextName, editTextBrand, editTextDescription, editTextPrice, editTextQuantity;
    private TextView textViewProduct;
    private Button buttonSave;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.main_name_et);
        editTextBrand = findViewById(R.id.main_brand_et);
        editTextDescription = findViewById(R.id.main_description_et);
        editTextPrice = findViewById(R.id.main_price_et);
        editTextQuantity = findViewById(R.id.main_quantity_et);

        textViewProduct = findViewById(R.id.main_products_tv);
        textViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
            }
        });

        buttonSave = findViewById(R.id.main_save_bt);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSave();
            }
        });

    }

    private void doSave() {
        final String name = editTextName.getText().toString();
        final String brand = editTextBrand.getText().toString();
        final String description = editTextDescription.getText().toString();
        final String price = editTextPrice.getText().toString();
        final String quantity = editTextQuantity.getText().toString();

        if (name.isEmpty()) {
            editTextName.setError("This field is required");
            editTextName.requestFocus();
            return;
        }

        if (brand.isEmpty()) {
            editTextBrand.setError("This field is required");
            editTextBrand.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            editTextDescription.setError("This field is required");
            editTextDescription.requestFocus();
            return;
        }

        if (price.isEmpty()) {
            editTextPrice.setError("This field is required");
            editTextPrice.requestFocus();
            return;
        }

        if (quantity.isEmpty()) {
            editTextQuantity.setError("This field is required");
            editTextQuantity.requestFocus();
            return;
        }

        Product product = new Product(
                name,
                brand,
                description,
                Double.parseDouble(price),
                Integer.parseInt(quantity)
        );

        CollectionReference collectionProducts = db.collection(PRODUCTS);
        collectionProducts.add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Product added successFully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
