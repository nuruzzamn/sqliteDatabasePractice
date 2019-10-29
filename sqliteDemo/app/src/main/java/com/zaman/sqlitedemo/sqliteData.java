package com.zaman.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class sqliteData extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	37;
    private	static final String	DATABASE_NAME = "product";
    private	static final String TABLE_PRODUCTS = "products";

    private static final String COLUMN_ID = "_id";
    private static final	String COLUMN_PRODUCTNAME = "productname";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRICE = "price";

    private Context context;


    public sqliteData(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String	CREATE_PRODUCTS_TABLE = "CREATE	TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME + " TEXT," + COLUMN_QUANTITY + " INTEGER" + ")";


        try {

            String CREATE_PRODUCTS_TABLE = "CREATE	TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME + " TEXT," + COLUMN_QUANTITY + " INTEGER," + COLUMN_PRICE + " INTEGER)";
            db.execSQL(CREATE_PRODUCTS_TABLE);

        } catch (Exception e)
        {
            Toast.makeText(context,"Exception :"+ e,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
       // onCreate(db);


        try {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
            onCreate(db);

        } catch (Exception e)
        {
            Toast.makeText(context,"Exception :"+ e,Toast.LENGTH_LONG).show();
        }
    }

    public List<Product> listProducts(){
        String sql = "select * from " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> storeProducts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                int quantity = Integer.parseInt(cursor.getString(2));
                int price = Integer.parseInt(cursor.getString(3));
                storeProducts.add(new Product(id, name, quantity, price));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public void addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_PRICE, product.getPrice());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public void updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_PRICE, product.getPrice());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(product.getId())});
    }

    public Product findProduct(String name){
        String query = "Select * FROM "	+ TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " = " + "name" + COLUMN_QUANTITY + " = " + "quantity"  + COLUMN_PRICE + " = " + "price"  ;
        SQLiteDatabase db = this.getWritableDatabase();
        Product mProduct = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String productName = cursor.getString(1);
            int productQuantity = Integer.parseInt(cursor.getString(2));
            int productPrice = Integer.parseInt(cursor.getString(3));
            mProduct = new Product(id, productName, productQuantity ,productPrice);
        }
        cursor.close();
        return mProduct;
    }

    public void deleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
