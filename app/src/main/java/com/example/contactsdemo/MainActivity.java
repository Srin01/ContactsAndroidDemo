package com.example.contactsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.contactsdemo.adapter.ContactAdapter;
import com.example.contactsdemo.modal.Contact;
import com.example.contactsdemo.modal.ContactExpert;
import com.tomash.androidcontacts.contactgetter.entity.ContactData;
import com.tomash.androidcontacts.contactgetter.main.contactsGetter.ContactsGetterBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    private final int CONTACT_PERMISSION_REQUEST_CODE = 60;
    private ContactExpert contactExpert = new ContactExpert();
    RecyclerView recyclerView;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setUpIntializer();
    }

    private void setUpIntializer()
    {
        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(this, contactExpert);
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        readContacts();
    }

    private void bindViews()
    {
        toolbar = findViewById(R.id.toolBar);
        recyclerView = findViewById(R.id.recyclerView_contacts);
    }

    private Contact extractContact(ContactData contactData)
    {
        String name = contactData.getCompositeName();
        String phoneNumber = contactData.getPhoneList().get(0).getMainData();
        return new Contact(name, phoneNumber);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CONTACT_PERMISSION_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                readContacts();
            else
                Toast.makeText(this, "Contacts permission required", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseContact(List<ContactData> contactList)
    {
        for(int i = 0; i < 30; i++)
        {
            ContactData contactData = contactList.get(i);
            contactExpert.addContact(extractContact(contactData));
        }
        contactAdapter.notifyDataSetChanged();
    }

    private boolean isContactPermissionAvailable()
    {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void readContacts()
    {
        if(!isContactPermissionAvailable())
        {
            requestContactPermission();
            return;
        }
        List<ContactData> contactList = new ContactsGetterBuilder(this).onlyWithPhones().buildList();
        parseContact(contactList);
    }

    private void requestContactPermission()
    {
        String[] permissionToAsk = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, permissionToAsk ,CONTACT_PERMISSION_REQUEST_CODE );
    }
}