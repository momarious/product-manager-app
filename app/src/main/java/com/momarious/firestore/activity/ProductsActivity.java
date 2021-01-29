package com.momarious.firestore.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.momarious.firestore.Product;
import com.momarious.firestore.R;
import com.momarious.firestore.adapter.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    public static final String PRODUCTS = "products";
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private List<Product> productList;
    private ProgressBar progressBar;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        progressBar = findViewById(R.id.progressbar);

        productList = new ArrayList<>();
        adapter = new ProductsAdapter(this, productList);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection(PRODUCTS).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressBar.setVisibility(View.GONE);
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot documentSnapshot : list) {
                                Product product = documentSnapshot.toObject(Product.class);
                                product.setId(documentSnapshot.getId());
                                productList.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
