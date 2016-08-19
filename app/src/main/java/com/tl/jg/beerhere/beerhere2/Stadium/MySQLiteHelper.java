package com.tl.jg.beerhere.beerhere2.Stadium;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 6/30/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 12;
    // Database Name
    private static final String DATABASE_NAME = "stadiums";
    // Stadiums table name
    private static final String TABLE_STADIUMS = "stadiums";
    // Vendors table name
    private static final String TABLE_VENDORS = "vendors";
    // Stadiums Table Columns names
    private static final String STADIUM_ID = "id";
    private static final String STADIUM_NAME = "name";
    // Vendors Table Columns names
    private static final String VENDOR_ID = "id";
    private static final String VENDOR_NAME = "name";
    private static final String VENDOR_NUMBER = "number";
    private static final String VENDOR_STADIUM = "stadium";
    private static final String VENDOR_BALANCE = "balance";
    private static final String VENDOR_PH_NUMBER = "phoneNumber";



    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String CREATE_STADIUMS_TABLE = "CREATE TABLE " + TABLE_STADIUMS + "("
            + STADIUM_ID + " INTEGER PRIMARY KEY," + STADIUM_NAME + " TEXT" + ")";


    String CREATE_VENDORS_TABLE = "CREATE TABLE " + TABLE_VENDORS + "("
            + VENDOR_ID + " INTEGER PRIMARY KEY," + VENDOR_NAME + " TEXT," + VENDOR_NUMBER + " TEXT," +
            VENDOR_STADIUM + " TEXT," + VENDOR_BALANCE + " TEXT," + VENDOR_PH_NUMBER + " TEXT" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STADIUMS_TABLE);
        db.execSQL(CREATE_VENDORS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STADIUMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDORS);
// Creating tables again
        onCreate(db);
    }

    // Adding new stadium
    public void addStadiums(Stadiums stadium) {
        Log.d("Stadium Added: ", stadium.getName().toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STADIUM_NAME, stadium.getName()); // Stadiums Name

// Inserting Row
        db.insert(TABLE_STADIUMS, null, values);
        db.close(); // Closing database connection
    }
    // Getting one stadium
    public Stadiums getStadiums(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STADIUMS, new String[]{STADIUM_ID,
                        STADIUM_NAME}, STADIUM_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Stadiums contact = new Stadiums(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
// return stadium
        return contact;
    }
    // Getting All Stadiums
    public List<Stadiums> getAllStadiums() {
        List<Stadiums> stadiumList = new ArrayList<Stadiums>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_STADIUMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Stadiums stadium = new Stadiums();
                stadium.setId(Integer.parseInt(cursor.getString(0)));
                stadium.setName(cursor.getString(1));
// Adding contact to list
                stadiumList.add(stadium);
            } while (cursor.moveToNext());
        }

// return contact list
        return stadiumList;
    }

    // Getting stadiums Count
    public int getStadiumsCount() {
        String countQuery = "SELECT * FROM " + TABLE_STADIUMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

// return count
        return cursor.getCount();
    }
    // Updating a stadium
    public int updateStadiums(Stadiums stadium) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STADIUM_NAME, stadium.getName());

// updating row
        return db.update(TABLE_STADIUMS, values, STADIUM_ID + " = ?",
                new String[]{String.valueOf(stadium.getId())});
    }

    // Deleting a stadium
    public void deleteStadiums(Stadiums stadium) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STADIUMS, STADIUM_ID + " = ?",
                new String[]{String.valueOf(stadium.getId())});
        db.close();
    }
    // Deleting all stadiums
    public void deleteAllStadiums() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_STADIUMS);
    }
    // Adding new vendor
    public void addVendors(Vendors vendor) {
        Log.d("Vendor Added: ", vendor.getName().toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VENDOR_NAME, vendor.getName()); // Vendor Name
        values.put(VENDOR_NUMBER, vendor.getNumber()); //Vendor Number
        values.put(VENDOR_STADIUM, vendor.getStadium()); // Stadium Name
        values.put(VENDOR_BALANCE, vendor.getBalance());
        values.put(VENDOR_PH_NUMBER, vendor.getPhoneNumber());

// Inserting Row
        db.insert(TABLE_VENDORS, null, values);
        db.close(); // Closing database connection
    }
    // Getting one stadium
    public Vendors getVendor(int id) {
        SQLiteDatabase dbVendor = this.getReadableDatabase();

        Cursor cursor = dbVendor.query(TABLE_VENDORS, new String[]{VENDOR_ID,
                        VENDOR_NAME, VENDOR_NUMBER, VENDOR_STADIUM, VENDOR_BALANCE, VENDOR_PH_NUMBER},
                VENDOR_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Vendors contact = new Vendors(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5));
// return stadium
        return contact;

    }

    // Getting one stadium
    public Vendors getVendorByNumber(String id) {

        String selectQuery = "SELECT * FROM vendors WHERE number LIKE " + "'" + id + "'";

        SQLiteDatabase dbVendor = this.getWritableDatabase();

        Cursor cursor = dbVendor.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        Vendors contact = new Vendors(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5));
// return stadium
        return contact;

    }
    // Getting All Stadiums
    public List<Vendors> getAllVendors(String Stadium) {
        List<Vendors> vendorList = new ArrayList<Vendors>();
// Select All Query
//        String selectQuery = "SELECT * FROM " + TABLE_VENDORS + " WHERE " + VENDOR_STADIUM + " LIKE " + Stadium;
        String selectQuery = "SELECT * FROM vendors WHERE stadium LIKE " + "'" + Stadium + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Vendors vendor = new Vendors();
                vendor.setId(Integer.parseInt(cursor.getString(0)));
                vendor.setName(cursor.getString(1));
                vendor.setNumber(cursor.getString(2));
                vendor.setStadium(cursor.getString(3));
                vendor.setBalance(cursor.getInt(4));
                vendor.setPhoneNumber(cursor.getString(5));
                Log.d("Vendor Numbers are: ", vendor.getNumber().toString());
// Adding contact to list
                if(vendor.getStadium().equals(Stadium)){
                    vendorList.add(vendor);
                }
            } while (cursor.moveToNext());
        }

// return contact list
        return vendorList;
    }


    // Getting vendor Count
    public int getVendorCount() {
        String countQuery = "SELECT * FROM " + TABLE_VENDORS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

// return count
        return cursor.getCount();
    }
    // Updating a vendor
    public int updateVendors(Vendors vendor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(VENDOR_NAME, vendor.getName());
        values.put(VENDOR_NUMBER, vendor.getNumber());
        values.put(VENDOR_STADIUM, vendor.getStadium());
        values.put(VENDOR_BALANCE, vendor.getBalance());
        values.put(VENDOR_PH_NUMBER, vendor.getPhoneNumber());

// updating row
        return db.update(TABLE_VENDORS, values, VENDOR_ID + " = ?",
                new String[]{String.valueOf(vendor.getId())});
    }

    // Deleting a vendor
    public void deleteVendor(Vendors vendor) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VENDORS, VENDOR_ID + " = ?",
                new String[]{String.valueOf(vendor.getId())});
        db.close();
    }
    // Deleting all vendors
    public void deleteAllVendors() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_VENDORS);
    }

    public int editBalance(Vendors vendor, float sale){
        SQLiteDatabase db = this.getWritableDatabase();
        SmsManager smsManager = SmsManager.getDefault();



        ContentValues values = new ContentValues();
        values.put(VENDOR_NAME, vendor.getName());
        values.put(VENDOR_NUMBER, vendor.getNumber());
        values.put(VENDOR_STADIUM, vendor.getStadium());
        values.put(VENDOR_BALANCE, vendor.getBalance() + sale);
        values.put(VENDOR_PH_NUMBER, vendor.getPhoneNumber());
        Log.d("Vendor balance is: ", String.valueOf(vendor.getBalance()));
        String sms = "A Payment of " + String.valueOf(sale) + " has been processed";
        Log.d("The Vendor Phone is", vendor.getPhoneNumber());
        Log.d("The SMS shoudl be:", sms);
        smsManager.sendTextMessage(vendor.getPhoneNumber(), null, sms, null, null);


// updating row
        return db.update(TABLE_VENDORS, values, VENDOR_ID + " = ?",
                new String[]{String.valueOf(vendor.getId())});
    }


    public void goStadiums(){

        addStadiums(new Stadiums(0, "Wrigley Field"));
        addStadiums(new Stadiums(1, "US Cellular Field"));
        addStadiums(new Stadiums(2, "AT&T Park"));
        addStadiums(new Stadiums(3, "Marlins Park"));
        addStadiums(new Stadiums(4, "Chase Field"));
        addStadiums(new Stadiums(5, "Citi Field"));
        addStadiums(new Stadiums(6, "Angel Stadium of Anaheim"));
        addStadiums(new Stadiums(7, "Citizens Bank Park"));
        addStadiums(new Stadiums(8, "Comerica Park"));
        addStadiums(new Stadiums(9, "Coors Field"));
        addStadiums(new Stadiums(10, "Miller Park"));
        addStadiums(new Stadiums(11, "Minute Maid Park"));
    }

    public void goVendors(){
        addVendors(new Vendors(0, "Jim Jeffs", "KVLFI2", "Wrigley Field", 0, "5556"));
        addVendors(new Vendors(1, "Tom Tompson", "KCLDB9", "US Cellular Field", 0, "5555555501"));
        addVendors(new Vendors(2, "Tim Timothy", "YVCGB5", "Marlins Park", 0, "5555555502"));
        addVendors(new Vendors(3, "John Johnson", "KAEDP4", "Marlins Park", 0, "5555555503"));
        addVendors(new Vendors(4, "Tina Tinaton", "YXZSD1", "Wrigley Field", 0, "5555555504"));
        addVendors(new Vendors(5, "Jack Daniels", "KOREF2", "US Cellular Field", 0, "5555555505"));
        addVendors(new Vendors(6, "Tom Collins", "KOPDF2", "Minute Made Park", 0, "5555555506"));
        addVendors(new Vendors(7, "Jim Beam", "YNVGT8", "Miller Park", 0, "5555555507"));
        addVendors(new Vendors(8, "Johnny Walker", "KZXDS3", "Wrigley Field", 0, "5555555508"));
        addVendors(new Vendors(9, "Tia Maria", "YCBVM0", "US Cellular Field", 0, "5555555509"));
        addVendors(new Vendors(10, "Tellemore Dew", "KEWQE5", "Miller Park", 0, "5555555510"));
        addVendors(new Vendors(11, "John Daley", "YLKOS5", "Minute Made Park", 0, "5555555511"));
    }

}