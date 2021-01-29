package com.momarious.firestore.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.momarious.firestore.Product;
import com.momarious.firestore.R;

public class UpdateActivity extends AppCompatActivity {

    private static String PRODUCTS = "products";
    private EditText editTextName, editTextBrand, editTextDescription, editTextPrice, editTextQuantity;
    private Button buttonSave, buttonDelete;

    private FirebaseFirestore db;
    private Product getProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getProduct = (Product) getIntent().getSerializableExtra("product");

        db = FirebaseFirestore.getInstance();

        editTextName = findViewById(R.id.update_name_et);
        editTextBrand = findViewById(R.id.update_brand_et);
        editTextDescription = findViewById(R.id.update_description_et);
        editTextPrice = findViewById(R.id.update_price_et);
        editTextQuantity = findViewById(R.id.update_quantity_et);

        editTextName.setText(getProduct.getName());
        editTextBrand.setText(getProduct.getBrand());
        editTextDescription.setText(getProduct.getDescription());
        editTextPrice.setText(String.valueOf(getProduct.getPrice()));
        editTextQuantity.setText(String.valueOf(getProduct.getQuantity()));

        buttonSave = findViewById(R.id.update_bt);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        buttonDelete = findViewById(R.id.delete_bt);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

    }

    private void update() {
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

        db.collection(PRODUCTS)
                .document(getProduct.getId())
                .update(
                        "brand", product.getBrand(),
                        "description", product.getDescription(),
                        "name", product.getName(),
                        "price", product.getPrice(),
                        "quantity", product.getQuantity()
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateActivity.this, "Product Updated", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void delete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure about this?");
        builder.setMessage("Deletion is permanent...");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection(PRODUCTS)
                        .document(getProduct.getId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateActivity.this, "Product deleted", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(UpdateActivity.this, ProductsActivity.class));
                                }
                            }
                        });
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
