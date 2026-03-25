package com.example.ecommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "ecommerce.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, category TEXT, price REAL)");

        addProduct(db, "iPhone 15", "Electronics", 999.99);
        addProduct(db, "Samsung Galaxy S24", "Electronics", 849.99);
        addProduct(db, "Sony Headphones", "Electronics", 349.99);
        addProduct(db, "MacBook Air", "Electronics", 1099.00);
        addProduct(db, "Nike Air Max", "Footwear", 150.00);
        addProduct(db, "Adidas Ultraboost", "Footwear", 190.00);
        addProduct(db, "Levi's Jeans", "Clothing", 69.50);
        addProduct(db, "Puffer Jacket", "Clothing", 250.00);
        addProduct(db, "Polo Shirt", "Clothing", 98.50);
        addProduct(db, "Atomic Habits", "Books", 16.99);
        addProduct(db, "Sapiens", "Books", 18.99);
        addProduct(db, "Instant Pot", "Kitchen", 89.95);
        addProduct(db, "Stand Mixer", "Kitchen", 329.99);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    private void addProduct(SQLiteDatabase db, String name, String category, double price) {
        ContentValues v = new ContentValues();
        v.put("name", name);
        v.put("category", category);
        v.put("price", price);
        db.insert("products", null, v);
    }

    public List<Product> searchProducts(String query, String category, float minPrice, float maxPrice) {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM products WHERE price >= ? AND price <= ?";
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(minPrice));
        args.add(String.valueOf(maxPrice));

        if (query != null && !query.isEmpty()) {
            sql += " AND (name LIKE ? OR category LIKE ?)";
            args.add("%" + query + "%");
            args.add("%" + query + "%");
        }
        if (category != null && !category.equals("All")) {
            sql += " AND category = ?";
            args.add(category);
        }

        Cursor c = db.rawQuery(sql, args.toArray(new String[0]));
        while (c.moveToNext()) {
            list.add(new Product(c.getInt(0), c.getString(1), c.getString(2), c.getDouble(3)));
        }
        c.close();
        return list;
    }

    public List<String> getCategories() {
        List<String> list = new ArrayList<>();
        list.add("All");
        Cursor c = getReadableDatabase().rawQuery("SELECT DISTINCT category FROM products", null);
        while (c.moveToNext()) list.add(c.getString(0));
        c.close();
        return list;
    }
}
