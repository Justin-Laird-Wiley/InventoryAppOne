package com.example.justin.inventoryappone;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.justin.inventoryappone.data.InventoryContract;
import com.example.justin.inventoryappone.data.InventoryDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class InventoryActivity extends AppCompatActivity {

    private static final String LOG_TAG = InventoryActivity.class.getSimpleName();

    InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new InventoryDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void insertPet() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        displayDatabaseInfo();

        ContentValues values = new ContentValues();

        values.put(InventoryContract.InventoryEntry.COLUMN_ITEM, "Toto");
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, 400);
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, 10);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER, "Techno");
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NO, "555");

        long insertRow = db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);

        Log.i(LOG_TAG, "Insert row of pet " + InventoryContract.InventoryEntry.COLUMN_ITEM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:

                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayDatabaseInfo() {

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
//        Cursor cursor = db.rawQuery("SELECT * FROM " + InventoryContract.InventoryEntry.TABLE_NAME, null);

        String[] projection = {InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_ITEM,
                InventoryContract.InventoryEntry.COLUMN_PRICE,
                InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NO
        };

        Cursor cursor = db.query(InventoryContract.InventoryEntry.TABLE_NAME, projection, null, null,null, null,null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).



//            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
//            displayView.setText("Number of rows in pets database table: " + cursor.getCount());


            TextView displayView = (TextView) findViewById(R.id.text_view_pet);

            try {
                // Create a header in the Text View that looks like this:
                //
                // The pets table contains <number of rows in Cursor> pets.
                // _id - name - breed - gender - weight
                //
                // In the while loop below, iterate through the rows of the cursor and display
                // the information from each column in this order.
                displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
                displayView.append(InventoryContract.InventoryEntry._ID + " - " +
                        InventoryContract.InventoryEntry.COLUMN_ITEM + " - " +
                        InventoryContract.InventoryEntry.COLUMN_PRICE + " - " +
                        InventoryContract.InventoryEntry.COLUMN_QUANTITY + " - " +
                        InventoryContract.InventoryEntry.COLUMN_SUPPLIER + " - " +
                        InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NO +
                        "\n");

                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry._ID);
                int itemColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_ITEM);
                int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
                int genderColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);
                int weightColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_SUPPLIER);
                int supplierPhoneIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NO);

                // Iterate through all the returned rows in the cursor
                while (cursor.moveToNext()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentItem = cursor.getString(itemColumnIndex);
                    int currentPrice = cursor.getInt(priceColumnIndex);
                    int currentQuantity = cursor.getInt(genderColumnIndex);
                    String currentSupplier = cursor.getString(weightColumnIndex);
                    String currentSupplierPhone = cursor.getString(supplierPhoneIndex);
                    // Display the values from each column of the current row in the cursor in the TextView
                    displayView.append(("\n" + currentID + " - " +
                            currentItem + " - " +
                            currentPrice + " - " +
                            currentQuantity + " - " +
                            currentSupplier + " - " +
                            currentSupplierPhone
                    ));
                }
            } finally {
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }


        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
