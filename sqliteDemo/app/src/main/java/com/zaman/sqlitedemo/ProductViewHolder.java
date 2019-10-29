package com.zaman.sqlitedemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView name ,price ,quantity;
    public ImageView deleteProduct;
    public  ImageView editProduct;


    public ProductViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.product_name);
        quantity = (TextView)itemView.findViewById(R.id.product_quantity);
        price = (TextView)itemView.findViewById(R.id.product_totalPrice);

        deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
        editProduct = (ImageView)itemView.findViewById(R.id.edit_product);
    }
}
