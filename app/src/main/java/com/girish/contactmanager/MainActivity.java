package com.girish.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.girish.contactmanager.data.DatabaseHandler;
import com.girish.contactmanager.model.Contact;
import com.girish.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        Contact girish = databaseHandler.getContact(1);
        databaseHandler.deleteContact(girish);

        List<Contact> contactList = databaseHandler.getAllContacts();
        for(Contact contact: contactList){
            Log.d("MainActivity", contact.getName() + " " + contact.getId());
        }
        Log.d("MainActivity", String.valueOf(contactList.size()));

    }
}