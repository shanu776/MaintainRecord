package com.opensis.shanu.maintainrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shanu on 8/24/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="MaintainData";
    private static final String TABLE_NAME="product";
    private static final String KEY_ID="id";
    private static final String FIREBASE_ID="keyid";
    private static final String PRODUCT_NAME="productname";
    private static final String PRODUCT_QUANTITY="productquantity";
    private static final String PRODUCT_PRICE="productprice";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
          /* context.deleteDatabase(DATABASE_NAME);*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE="CREATE TABLE " +TABLE_NAME+ "("+KEY_ID+ " INTEGER PRIMARY KEY," +FIREBASE_ID+ " TEXT UNIQUE," +PRODUCT_NAME+ " TEXT," +PRODUCT_QUANTITY+ " TEXT,"
                +PRODUCT_PRICE+ " TEXT "+")";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addProduct(ProductBean bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(FIREBASE_ID,bean.getKeyid());
        value.put(PRODUCT_NAME,bean.getProduct_name());
        value.put(PRODUCT_QUANTITY,bean.getProduct_quantity());
        value.put(PRODUCT_PRICE,bean.getProduct_price());
        db.insertWithOnConflict(TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public void updateProduct(ProductBean bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_ID,bean.getId());
        value.put(FIREBASE_ID,bean.getKeyid());
        value.put(PRODUCT_NAME,bean.getProduct_name());
        value.put(PRODUCT_QUANTITY,bean.getProduct_quantity());
        value.put(PRODUCT_PRICE,bean.getProduct_price());
        db.insertWithOnConflict(TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public List<ProductBean> getProductList(){
        List<ProductBean> productList = new ArrayList<>();
        String qurey = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(qurey,null);
        ProductBean bean;
        if(cursor.moveToFirst()){
            do{
                bean = new ProductBean();
                bean.setId(Integer.parseInt(cursor.getString(0)));
                bean.setKeyid(cursor.getString(1));
                bean.setProduct_name(cursor.getString(2));
                bean.setProduct_quantity(cursor.getString(3));
                bean.setProduct_price(cursor.getString(4));
                productList.add(bean);
            }while (cursor.moveToNext());
        }
        db.close();
        return productList;
    }

    public ProductBean getProduct(String id){
        ProductBean bean = null;
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+FIREBASE_ID+" = '"+id+"'";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            bean = new ProductBean();
            bean.setId(Integer.parseInt(cursor.getString(0)));
            bean.setKeyid(cursor.getString(1));
            bean.setProduct_name(cursor.getString(2));
            bean.setProduct_quantity(cursor.getString(3));
            bean.setProduct_price(cursor.getString(4));
        }
        db.close();
        return bean;
    }

}
