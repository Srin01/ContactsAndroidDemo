package com.example.contactsdemo.modal;

import java.util.ArrayList;

public class ContactExpert
{
    private final ArrayList<Contact> contactList = new ArrayList<>();

    public void addContact(Contact contact)
    {
        contactList.add(contact);
    }

    public String getContactName(int index)
    {
        return contactList.get(index).getName();
    }

    public String getContactPhoneNumber(int index)
    {
        return contactList.get(index).getPhoneNumber();
    }

    public int getContactsCount()
    {
        return contactList.size();
    }
}
