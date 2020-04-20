package com.techuva.councellorapp.contusfly_corporate.chooseContactsDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/2/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * The Constant TAG.
     */
    public static final String TAG = "DBHelper";

    /**
     * The Constant DATABASE_NAME.
     */
    private static final String DATABASE_NAME = "MSVE 2017";

    /**
     * The Constant DATABASE_VERSION.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * The Constant DATABASE_VERSION.
     */
    private static final String TABLE_CATEGORY_CONTACT = "selected_contacts";
    /**
     * The Constant DATABASE_VERSION.
     */
    private static final String TABLE_CATEGORY_GROUP = "selected_groups";

    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String CONTACT_ROASTER_ID = "roaster_id";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String GROUP_ROASTER_ID = "roaster_id";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String CONTACT_ROASTER_NAME = "roaster_name";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String GROUP_ROASTER_NAME = "roaster_name";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String CONTACT_IS_ACTIVE = "is_active";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String GROUP_IS_ACTIVE = "is_active";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String IS_FIELD_ACTIVE = "is_field_active";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String GROUP_IS_FIELD_ACTIVE = "group_is_field_active";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String IS_DONE = "is_done";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String ID = "id";
    /**
     * The Constant CATEGORY_AUTOINCREMENT_ID.
     */
    public static final String TYPE = "type";
    /**
     * The Constant SQL_CREATE_TABLE_CATEGORY.
     */
    private static final String SQL_CREATE_TABLE_CONTACT = "CREATE TABLE "
            + TABLE_CATEGORY_CONTACT + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACT_ROASTER_ID
            + " TEXT NOT NULL,  " + CONTACT_ROASTER_NAME
            + " TEXT NOT NULL," + TYPE
            + " TEXT NOT NULL," + IS_FIELD_ACTIVE
            + " INTEGER ," + CONTACT_IS_ACTIVE
            + " INTEGER NOT NULL," + IS_DONE
            + " INTEGER NOT NULL " + ");";
    /**
     * The Constant SQL_CREATE_TABLE_CATEGORY.
     */
    private static final String SQL_CREATE_TABLE_GROUP = "CREATE TABLE "
            + TABLE_CATEGORY_GROUP + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GROUP_ROASTER_ID
            + " TEXT NOT NULL,  " + GROUP_ROASTER_NAME
            + " TEXT NOT NULL," + TYPE
            + " TEXT NOT NULL," + GROUP_IS_FIELD_ACTIVE
            + " INTEGER ," + GROUP_IS_ACTIVE
            + " INTEGER NOT NULL " + ");";

    //    private static final String SQL_CREATE_PROFILE_PIC = "CREATE TABLE "
//            + TABLE_CATEGORY_GROUP + "(" + ID
//            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "PROFILE_PIC_BYTE_ARRAY"
//            + " TEXT NOT NULL);";
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + "image_cache" + "(" +
            "image_name" + " TEXT UNIQUE," +
            " image" + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CREATING TABLE FOR PRODUCT INFORMATION
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CONTACT);
        // CREATING TABLE FOR PRODUCT INFORMATION
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_GROUP);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       /* sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "
                + SQL_CREATE_TABLE_CONTACT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ SQL_CREATE_TABLE_GROUP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "
                + CREATE_TABLE_IMAGE);*/
    }


    public void insertImageCache(String imageName, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image_name", imageName);
        values.put("image", imageByteArray);
        db.insertWithOnConflict("image_cache", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteImageCache(String imageName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("image_cache", "image_name" + " = ?",
                new String[]{String.valueOf(imageName)});
        db.close();
    }

    public Bitmap getImageCache(String imageUrl) {
        Bitmap imageBitmap = null;
        String[] columns = new String[]{"image_name", "image"};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("image_cache", columns, "image_name = ? ", new String[]{imageUrl}, null, null, null);
//        Cursor cursor= db.rawQuery("select * from image_cache where image_name="+imageUrl,null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            byte[] imageByte = cursor.getBlob(cursor.getColumnIndex("image"));
            if(imageByte!=null)
            imageBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }
        cursor.close();
        return imageBitmap;
    }

    public void addContact(String rosterID, String rosterName, String type, int is_fieled_active, int isActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACT_ROASTER_ID, rosterID); // Contact Name
        values.put(CONTACT_ROASTER_NAME, rosterName);
        values.put(TYPE, type);// Contact Phone
        values.put(IS_FIELD_ACTIVE, is_fieled_active);
        values.put(CONTACT_IS_ACTIVE, isActive);
        // Inserting Row
        db.insert(TABLE_CATEGORY_CONTACT, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // Deleting single contact
    public void deleteContact(String rosterID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY_CONTACT, CONTACT_ROASTER_ID + " = ?",
                new String[]{String.valueOf(rosterID)});
        db.close();
    }

    // Deleting single contact
    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY_CONTACT, null, null);
    }


    /**
     * Update.
     */
    public void update(String roassterId, String name, String type, int is_fieled_active, int value) {
        Log.e("roassterId", roassterId + "");
        Log.e("is_fieled_active", is_fieled_active + "");
        Log.e("value", value + "");
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_ROASTER_ID, roassterId); // Contact Name
        contentValues.put(CONTACT_ROASTER_NAME, name); // Contact Phone
        contentValues.put(TYPE, type); // Contact Phone
        contentValues.put(IS_FIELD_ACTIVE, is_fieled_active);
        contentValues.put(CONTACT_IS_ACTIVE, value);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_CATEGORY_CONTACT, contentValues,
                CONTACT_ROASTER_ID + "=" + roassterId, null);
    }

    // code to get all contacts in a list view
    public List<ChooseContactsModelClass> getAllContacts() {
        List<ChooseContactsModelClass> contactList = new ArrayList<ChooseContactsModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY_CONTACT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChooseContactsModelClass contact = new ChooseContactsModelClass();
                contact.setId(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setIsFiledActive(cursor.getInt(3));
                contact.setIsActive(cursor.getInt(4));
                contact.setIsDone(cursor.getInt(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to get all contacts in a list view
    public List<ChooseContactsModelClass> getAllRemoveContactsDetails(int i, int s) {
        List<ChooseContactsModelClass> contactList = new ArrayList<ChooseContactsModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY_CONTACT + " WHERE " + IS_FIELD_ACTIVE + "=" + i + " AND " + CONTACT_IS_ACTIVE + "=" + s;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChooseContactsModelClass contact = new ChooseContactsModelClass();
                contact.setId(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setIsFiledActive(cursor.getInt(3));
                contact.setIsActive(cursor.getInt(4));
                contact.setIsDone(cursor.getInt(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    // code to get all contacts in a list view
    public List<ChooseContactsModelClass> getAllRemoveGroupDetails(int i, int s) {
        List<ChooseContactsModelClass> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY_GROUP + " WHERE " + GROUP_IS_FIELD_ACTIVE + "=" + i + " AND " + GROUP_IS_ACTIVE + "=" + s;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChooseContactsModelClass contact = new ChooseContactsModelClass();
                contact.setId(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setIsFiledActive(cursor.getInt(3));
                contact.setIsActive(cursor.getInt(4));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    // code to get all contacts in a list view
    public List<ChooseContactsModelClass> getAllContactsDetails(int s) {
        List<ChooseContactsModelClass> contactList = new ArrayList<ChooseContactsModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY_CONTACT + " WHERE " + CONTACT_IS_ACTIVE + "=" + s;
        Log.e("test dfdsf", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChooseContactsModelClass contact = new ChooseContactsModelClass();
                contact.setId(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setIsFiledActive(cursor.getInt(3));
                contact.setIsActive(cursor.getInt(4));
                contact.setIsDone(cursor.getInt(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to get all contacts in a list view
    public List<ChooseContactsModelClass> getAllGroupDetails(int s) {
        List<ChooseContactsModelClass> contactList = new ArrayList<ChooseContactsModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY_GROUP + " WHERE " + GROUP_IS_ACTIVE + "=" + s;
        Log.e("test dfdsf         ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChooseContactsModelClass contact = new ChooseContactsModelClass();
                contact.setId(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setIsFiledActive(cursor.getInt(3));
                contact.setIsActive(cursor.getInt(4));
                contact.setIsDone(cursor.getInt(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    /**
     * Check event.
     *
     * @param productId the product id
     * @return true, if successful
     */
    public boolean checkEvent(String productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY_CONTACT,
                new String[]{CONTACT_ROASTER_ID}, CONTACT_ROASTER_ID + "="
                        + productId, null, null, null, null);

        if (cursor.moveToFirst())

            return true;
        else
            return false;

    }

    public void addGroup(String rosterID, String rosterName, String type, int isActive, int valuw) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUP_ROASTER_ID, rosterID); // Contact Name
        values.put(GROUP_ROASTER_NAME, rosterName);
        values.put(TYPE, type);// Contact Phone
        values.put(GROUP_IS_FIELD_ACTIVE, isActive);
        values.put(GROUP_IS_ACTIVE, valuw);
        // Inserting Row
        db.insert(TABLE_CATEGORY_GROUP, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // Deleting single contact
    public void deleteGroup(String rosterID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY_GROUP, GROUP_ROASTER_ID + " = ?",
                new String[]{String.valueOf(rosterID)});
        db.close();
    }

    // Deleting single contact
    public void deleteGroupTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY_GROUP, null, null);
    }


    /**
     * Update.
     */
    public void updateGroup(String roassterId, String name, String type, int value, int active) {
        Log.e("1", value + "");
        Log.e("roassterId", roassterId + "");
        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_ROASTER_ID, roassterId); // Contact Name
        contentValues.put(GROUP_ROASTER_NAME, name); // Contact Phone
        contentValues.put(TYPE, type); // Contact Phone
        contentValues.put(GROUP_IS_FIELD_ACTIVE, value);
        contentValues.put(GROUP_IS_ACTIVE, active);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_CATEGORY_GROUP, contentValues,
                CONTACT_ROASTER_ID + "=" + roassterId, null);
    }

    // code to get all contacts in a list view
    public List<ChooseContactsModelClass> getAllGroup() {
        List<ChooseContactsModelClass> contactList = new ArrayList<ChooseContactsModelClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY_GROUP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChooseContactsModelClass contact = new ChooseContactsModelClass();
                contact.setId(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setIsFiledActive(cursor.getInt(3));
                contact.setIsActive(cursor.getInt(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    /**
     * Check event.
     *
     * @param productId the product id
     * @return true, if successful
     */
    public boolean checkEventGroup(String productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY_GROUP,
                new String[]{GROUP_ROASTER_ID}, GROUP_ROASTER_ID + "="
                        + productId, null, null, null, null);

        if (cursor.moveToFirst())

            return true;
        else
            return false;

    }

}
