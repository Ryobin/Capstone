/*
package capstone.sda.com.literatures.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import capstone.sda.com.literatures.R;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = DbContract.DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;

    */
/* Try .. *//*


    private Resources mResources;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
        mContentResolver = context.getContentResolver();

     db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        final String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + DbContract.LiteraturesEntry.TABLE_NAME + " (" +
                DbContract.LiteraturesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.LiteraturesEntry.COLUMN_PRODUCT_NAME + " TEXT UNIQUE NOT NULL, " +
                DbContract.LiteraturesEntry.COLUMN_PRODUCT_DESCRIPTION + " TEXT NOT NULL, " +
                DbContract.LiteraturesEntry.COLUMN_PRODUCT_IMAGE + "TEXT NOT NULL, " +
                DbContract.LiteraturesEntry.COLUMN_PRODUCT_PRICE + "REAL NOT NULL " + " );";

        final String SQL_CREATE_CART_TABLE = "CREATE TABLE " + DbContract.LiteraturesEntry.TABLE_CART + " (" +
                DbContract.LiteraturesEntry._CARTID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContract.LiteraturesEntry.COLUMN_CART_PRODUCT_NAME + "TEXT UNIQUE NOT NULL, " +
                DbContract.LiteraturesEntry.COLUMN_CART_PRODUCT_IMG + "TEXT NOT NULL, " +
                DbContract.LiteraturesEntry.COLUMN_CART_PRODUCT_QUANTITY + "INTEGER NOT NULL, " +
                DbContract.LiteraturesEntry.COLUMN_CART_PRODUCT_TOTAL_PRICE + "REAL NOT NULL " + " );";

     db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
     db.execSQL(SQL_CREATE_CART_TABLE);
        Log.d(TAG, "Database successfully created.");
    }

    */
/* Some try catch here. *//*


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO: Handle database version upgrade.

        db.execSQL("DROP TABLE IF EXISTS " + DbContract.LiteraturesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.LiteraturesEntry.TABLE_CART);
        onCreate(db);
    }


    private void readProductsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        // db = this.getWritableDatabase();
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.products);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
            while((line = reader.readLine()) != null){
                builder.append(line + "\n");
            }

        */
/* JSON Parsing int KEY, VALUES *//*


        final String rawJson = builder.toString();

        final String BGS_LITERATURES = "literatures";

        final String BGS_PRODUCTNAME = "productName";
        final String BGS_DESCRIPTION = "description";
        final String BGS_IMAGEURL = "imageUrl";
        final String BGS_PRICE = "price";

        try {
            JSONObject productsJson = new JSONObject(rawJson);
            JSONArray productArray = productsJson.getJSONArray(BGS_LITERATURES);

            for (int i = 0; i < productArray.length(); i++){
                String productName;
                String description;
                String imageUrl;
                Double price;

                JSONObject productDetails = productArray.getJSONObject(i);

                productName = productDetails.getString(BGS_PRODUCTNAME);
                description = productDetails.getString(BGS_DESCRIPTION);
                imageUrl = productDetails.getString(BGS_IMAGEURL);
                price = productDetails.getDouble(BGS_PRICE);

             ContentValues productContentValues = new ContentValues();
                productContentValues.put(DbContract.LiteraturesEntry.COLUMN_PRODUCT_NAME, productName);
                productContentValues.put(DbContract.LiteraturesEntry.COLUMN_PRODUCT_DESCRIPTION, description);
                productContentValues.put(DbContract.LiteraturesEntry.COLUMN_PRODUCT_IMAGE, imageUrl);
                productContentValues.put(DbContract.LiteraturesEntry.COLUMN_PRODUCT_PRICE, price);

              db.insert(DbContract.LiteraturesEntry.TABLE_NAME, null, productContentValues);
              Log.d(TAG, "Inserted successfully." + productContentValues);
            }
            Log.d(TAG, "Inserted successfully." + productArray.length() );

        }catch (JSONException e){
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }


    */
/*
    private static final String CREATE_TABLE = "create table "+DbContract.LiteraturesEntry.TABLE_CART+
            "(id integer primary key autoincrement,"+DbContract.TOKEN+
            " text,"+DbContract.PRODUCT_NAME+" text,"+DbContract.PRODUCT_IMG+" text,"+
            DbContract.PRODUCT_PRICE+" real);";

    private static final String DROP_TABLE = "drop table if exists "+DbContract.LiteraturesEntry.TABLE_CART;

    public DbHelper(Context context){
        super(context, DbContract.DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_TABLE);
            onCreate(db);
    }

    public void saveToLocalCart(String token, String product_name, String product_image, String product_price, int sync_status, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
           contentValues.put(DbContract.TOKEN, token);
           contentValues.put(DbContract.PRODUCT_NAME, product_name);
           contentValues.put(DbContract.PRODUCT_IMG, product_image);
           contentValues.put(DbContract.PRODUCT_PRICE, product_price);
           contentValues.put(DbContract.SYNC_STATUS, sync_status);

        database.insert(DbContract.LiteraturesEntry.TABLE_CART, null, contentValues);

    }

    public Cursor readFromLocalCart(SQLiteDatabase database){

        String[] projection = {DbContract.TOKEN,DbContract.PRODUCT_NAME,DbContract.PRODUCT_IMG,DbContract.PRODUCT_PRICE,DbContract.SYNC_STATUS};

        return (database.query(DbContract.LiteraturesEntry.TABLE_CART,projection,null,null,null,null,null));

    }

    public void updateLocalCart(String token, int sync_status, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
            contentValues.put(DbContract.SYNC_STATUS, sync_status);
                String selection = DbContract.TOKEN+ "LIKE ?";
                String product_name_selection = DbContract.PRODUCT_NAME+ "LIKE ?";
                String product_img_selection = DbContract.PRODUCT_IMG+ "LIKE ?";
                String product_price_selection = DbContract.PRODUCT_PRICE+ "LIKE ?";

            String[] selection_args = {token};
            database.update(DbContract.LiteraturesEntry.TABLE_CART, contentValues, selection, selection_args);
    }*//*

}
*/
