package com.girish.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.girish.contactmanager.R;
import com.girish.contactmanager.model.Contact;
import com.girish.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    // We create database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" +
                Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Util.KEY_NAME + " TEXT," +
                Util.KEY_PHONE_NUMBER + " TEXT" +
                ")";

        // creating table
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.drop_db);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        // create table again
        onCreate(db);
    }

    // Add contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        db.insert(Util.TABLE_NAME, null, values);
        // closing db connection
        db.close();
    }

    // Get contact
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Contact contact = new Contact();
        contact.setId(cursor.getInt(cursor.getColumnIndex(Util.KEY_ID)));
        contact.setName(cursor.getString(cursor.getColumnIndex(Util.KEY_NAME)));
        contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(Util.KEY_PHONE_NUMBER)));
        cursor.close();
        return contact;

    }

    // Get all contacts
    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_ALL = "SELECT * FROM " + Util.TABLE_NAME;

        Cursor cursor = db.rawQuery(SELECT_ALL, null);

        if(cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(Util.KEY_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(Util.KEY_NAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(Util.KEY_PHONE_NUMBER)));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return contactList;

    }

    // Update Contact
    public int updateContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        return db.update(Util.TABLE_NAME, values, Util.KEY_ID +"=?",
                new String[]{String.valueOf(contact.getId())});
    }

    // Delete Single Contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    // Contacts Count
    private int getCount(){
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

}
