package com.zaman.sqlitedemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

    private Context context;
    private List<Product> listProducts;

    private sqliteData mDatabase;

    public ProductAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new sqliteData(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product singleProduct = listProducts.get(position);

        holder.name.setText(singleProduct.getName());


        holder.price.setText(singleProduct.getPrice());

        holder.quantity.setText(singleProduct.getQuantity());



        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*
                //delete row from database

                mDatabase.deleteProduct(singleProduct.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
                */

              showDialog();


            }

            //code for alertdialog
            private void showDialog() {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Please Confirm");
                alertDialogBuilder.setMessage("Do you want to delete this?");

                        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        mDatabase.deleteProduct(singleProduct.getId());
                                        // Toast.makeText(context,"You clicked yes button",Toast.LENGTH_LONG).show();

                                        ((Activity)context).finish();
                                        context.startActivity(((Activity) context).getIntent());
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                  // Override
                    public void onClick(DialogInterface dialog, int which) {
                       // ((Activity)context).finish();
                        context.startActivity(((Activity) context).getIntent());
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            //colse alertDialog

        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }


    private void editTaskDialog(final Product product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final  EditText priceField = (EditText)subView.findViewById(R.id.enter_Price);

        if(product != null){
            nameField.setText(product.getName());
            quantityField.setText(String.valueOf(product.getQuantity()));
            priceField.setText(String.valueOf(product.getPrice()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final int quantity = Integer.parseInt(quantityField.getText().toString());
                final int price = Integer.parseInt(priceField.getText().toString());


                if(TextUtils.isEmpty(name) || quantityField.getText().equals("") || priceField.getText().equals("")){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();

                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
                else{
                    mDatabase.updateProduct(new Product(product.getId(), name, quantity ,price));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}

