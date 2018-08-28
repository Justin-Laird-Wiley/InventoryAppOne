package com.example.justin.inventoryappone.data;

import android.provider.BaseColumns;

public final class InventoryContract {

    public static final class InventoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "inventory";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ITEM = "item";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER = "supplier";
        public static final String COLUMN_SUPPLIER_PHONE_NO = "phone";

        public static final int ITEM_NOT_IN_STOCK = 0;
        public static final int ITEM_IN_STOCK = 1;

    }
}
