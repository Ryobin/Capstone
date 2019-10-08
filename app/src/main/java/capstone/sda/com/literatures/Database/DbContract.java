/*
package capstone.sda.com.literatures.Database;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    private  DbContract(){

    }

    public static final String CONTENT_AUTHORITY = "capstone.sda.com.literatures";
    public static final Uri BASE_CONTENT_URI = android.net.Uri.parse("content://" +CONTENT_AUTHORITY);

    public static final String PATH_LITERATURES = "literatures-path";
    public static final String PATH_CART = "cart-path";


    public static final class LiteraturesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_LITERATURES);
        public static final Uri CONTENT_URI_CART = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CART);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LITERATURES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LITERATURES;

        */
/* DATABASE TABLES *//*


        public static  final String TABLE_NAME = "literatures";
        public static  final String TABLE_CREDENTIALS = "credentials";
        public static  final String TABLE_CART = "cart";
        public static  final String TABLE_WISHLIST = "wishlist";

        */
/*  IDs :: Type INT *//*

        public static final String _ID = BaseColumns._ID;
        public static final String _CARTID = BaseColumns._ID;

        */
/* Columns for Products *//*

        public static  final String COLUMN_PRODUCT_NAME = "product_name";
        public static  final String COLUMN_PRODUCT_DESCRIPTION = "product_description";
        public static  final String COLUMN_PRODUCT_IMAGE = "product_image";
        public static  final String COLUMN_PRODUCT_PRICE = "product_price";

        */
/* Columns for Cart *//*

        public static  final String COLUMN_CART_TOKEN = "cart_token";
        public static  final String COLUMN_CART_PRODUCT_NAME = "cart_name";
        public static  final String COLUMN_CART_PRODUCT_IMG = "cart_image";
        public static  final String COLUMN_CART_PRODUCT_QUANTITY = "cart_quantity";
        public static  final String COLUMN_CART_PRODUCT_TOTAL_PRICE = "cart_total_price";

    }

    public static  final String DATABASE_NAME = "literatures.db";

    public static final int SYNC_STATUS_OK = 1;
    public static final int SYNC_STATUS_FAILED = 0;



    public static  final String SYNC_STATUS = "syncstatus";

}
*/
