package com.example.fa_pavit_868150_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private DatabaseHelper databaseHelper;

    public ProductDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long insertProduct(Product product) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, product.getName());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, product.getDescription());
        values.put(DatabaseHelper.COLUMN_PRICE, product.getPrice());
        values.put(DatabaseHelper.COLUMN_LATITUDE, product.getLatitude());
        values.put(DatabaseHelper.COLUMN_LONGITUDE, product.getLongitude());

        long id = db.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);

        db.close();

        return id;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCTS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                if (idIndex != -1) {
                    product.setId(cursor.getInt(idIndex));
                }

                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                if (nameIndex != -1) {
                    product.setName(cursor.getString(nameIndex));
                }

                int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION);
                if (descriptionIndex != -1) {
                    product.setDescription(cursor.getString(descriptionIndex));
                }

                int priceIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE);
                if (priceIndex != -1) {
                    product.setPrice(cursor.getDouble(priceIndex));
                }

                int latitudeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE);
                if (latitudeIndex != -1) {
                    product.setLatitude(cursor.getDouble(latitudeIndex));
                }

                int longitudeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE);
                if (longitudeIndex != -1) {
                    product.setLongitude(cursor.getDouble(longitudeIndex));
                }

                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return products;
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, product.getName());
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, product.getDescription());
        values.put(DatabaseHelper.COLUMN_PRICE, product.getPrice());
        values.put(DatabaseHelper.COLUMN_LATITUDE, product.getLatitude());
        values.put(DatabaseHelper.COLUMN_LONGITUDE, product.getLongitude());

        int rowsAffected = db.update(DatabaseHelper.TABLE_PRODUCTS, values,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});

        db.close();

        return rowsAffected;
    }

    public int deleteProduct(Product product) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsAffected = db.delete(DatabaseHelper.TABLE_PRODUCTS,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});

        db.close();

        return rowsAffected;
    }
}
