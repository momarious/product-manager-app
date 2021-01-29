package com.momarious.firestore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.momarious.firestore.Product;
import com.momarious.firestore.R;
import com.momarious.firestore.activity.UpdateActivity;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{

    private static final String PRODUCT = "product";
    private Context context;
    private List<Product> productList;

    public ProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductViewHolder(
                LayoutInflater.from(context).inflate(R.layout.listview_products, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductViewHolder productViewHolder, int i) {
        Product product = productList.get(i);
        productViewHolder.textViewName.setText(product.getName());
        productViewHolder.textViewBrand.setText(product.getBrand());
        productViewHolder.textViewDescription.setText(product.getDescription());
        productViewHolder.textViewPrice.setText("INR " + product.getPrice());
        productViewHolder.textViewQuantity.setText("Available Units: " + product.getQuantity());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewBrand, textViewDescription, textViewPrice, textViewQuantity;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.products_name_tv);
            textViewBrand = itemView.findViewById(R.id.products_brand_tv);
            textViewDescription = itemView.findViewById(R.id.products_description_tv);
            textViewPrice = itemView.findViewById(R.id.products_price_tv);
            textViewQuantity = itemView.findViewById(R.id.products_quantity_tv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Product product = productList.get(getAdapterPosition());
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra(PRODUCT, product);
            context.startActivity(intent);
        }
    }
}
