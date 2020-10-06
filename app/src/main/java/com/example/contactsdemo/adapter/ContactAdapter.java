package com.example.contactsdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsdemo.R;
import com.example.contactsdemo.modal.ContactExpert;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder>
{
    public Context context;
    public ContactExpert contactExpert;

    public ContactAdapter(Context context, ContactExpert contactExpert)
    {
        this.context = context;
        this.contactExpert = contactExpert;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.listitem_contact, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        View view = holder.view;
        TextView contactName = view.findViewById(R.id.contactName);
        TextView phoneNumber = view.findViewById(R.id.ContactPhoneNumber);

        contactName.setText(contactExpert.getContactName(position));
        phoneNumber.setText(contactExpert.getContactPhoneNumber(position));
    }

    @Override
    public int getItemCount()
    {
        return contactExpert.getContactsCount();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.view = itemView;
        }
    }
}
