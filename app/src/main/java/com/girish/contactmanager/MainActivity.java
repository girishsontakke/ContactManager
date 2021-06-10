package com.girish.contactmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.girish.contactmanager.data.DatabaseHandler;
import com.girish.contactmanager.databinding.ActivityMainBinding;
import com.girish.contactmanager.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> contactArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        contactArrayList = new ArrayList<>();

        List<Contact> contactList = databaseHandler.getAllContacts();
        for(Contact contact: contactList){
            contactArrayList.add(contact.getName());
        }
        binding.listView.setOnItemClickListener((parent, view, position, id) -> {

        });


        contactArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                contactArrayList);
        binding.listView.setAdapter(contactArrayAdapter);


    }
}