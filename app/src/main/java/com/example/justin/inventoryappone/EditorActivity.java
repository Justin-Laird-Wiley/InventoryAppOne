package com.example.justin.inventoryappone;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.justin.inventoryappone.data.InventoryContract;
import com.example.justin.inventoryappone.data.InventoryDbHelper;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    //  EditText field for item
    private EditText mItemEditText;

    //  EditText field to enter the pet's breed
    private EditText mPriceEditText;

    //  EditText field for quantity
    private EditText mQuantityEditText;

    //  EditText field to enter the pet's breed
    private EditText mSupplierEditText;

    //  EditText field for quantity
    private EditText mSupplierPhoneNoEditText;

    //  EditText field to enter the pet's weight
//    private EditText mWeightEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mInStockSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mItemInStock = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mItemEditText = (EditText) findViewById(R.id.edit_item_name);
        mPriceEditText = (EditText) findViewById(R.id.edit_price_field);
        mQuantityEditText = (EditText) findViewById(R.id.edit_item_quantity);
        mSupplierEditText = (EditText) findViewById(R.id.edit_supplier_name);
        mSupplierPhoneNoEditText = (EditText) findViewById(R.id.edit_supplier_phone_number);

        mInStockSpinner = (Spinner) findViewById(R.id.spinner_in_stock);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mInStockSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mInStockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.item_in_stock))) {
                        mItemInStock = 1; //  Item in-stock
                    } else {
                        mItemInStock = 0; //  Item not in-stock
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mItemInStock = 0; // Unknown
            }
        });
    }

    private long insertPet() {
        
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String nameString = mItemEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supplierString = mSupplierEditText.getText().toString().trim();
        String supplierPhoneNoString = mSupplierPhoneNoEditText.getText().toString().trim();

        int priceInt = Integer.parseInt(priceString);
        int quantityInt = Integer.parseInt(quantityString);

//        String breedString = mBreedEditText.getText().toString().trim();
//        String weightString = mWeightEditText.getText().toString().trim();
//        int weightInt = Integer.parseInt(weightString);

        ContentValues values = new ContentValues();

        values.put(InventoryContract.InventoryEntry.COLUMN_ITEM, nameString);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, priceInt);
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantityInt);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER, supplierString);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE_NO, supplierPhoneNoString);

        return db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);


//        return 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:

                long insertId = insertPet();
                if (insertId == -1) {
                    Toast.makeText(this, "Error with saving pet", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Pet saved with id: " + insertId, Toast.LENGTH_LONG).show();
                }
                finish();

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}