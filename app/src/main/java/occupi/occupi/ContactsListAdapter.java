/*
 * Copyright (c) 2017, Team OccuPi - Erik Brown, Tony Klingele, Alexander Taylor, Ethan Wright
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package occupi.occupi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import java.util.ArrayList;

public class ContactsListAdapter extends BaseAdapter {

    Context context;
    ContactsList contactsList, filteredContactsList, selectedContactsList;
    String filterContactName;

    ContactsListAdapter(Context context, ContactsList contactsList) {

        super();
        this.context = context;
        this.contactsList = contactsList;
        this.filteredContactsList = new ContactsList();
        this.selectedContactsList = new ContactsList();
        this.filterContactName = "";
    }

    //Search functionality to filter list based on user input
    public void filter(String filterContactName) {

        filteredContactsList.contactArrayList.clear();
        if (filterContactName.isEmpty() || filterContactName.length() < 1) {
            filteredContactsList.contactArrayList.addAll(contactsList.contactArrayList);
            this.filterContactName = "";
        } else {
            this.filterContactName = filterContactName.toLowerCase().trim();
            for (int i = 0; i < contactsList.contactArrayList.size(); i++) {

                if (contactsList.contactArrayList.get(i).name.toLowerCase().contains(filterContactName))
                    filteredContactsList.addContact(contactsList.contactArrayList.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public void addContacts(ArrayList<Contact> contacts) {
        this.contactsList.contactArrayList.addAll(contacts);
        this.filter(this.filterContactName);
    }

    //Return total number of contacts in the list
    @Override
    public int getCount() {
        return filteredContactsList.getCount();
    }

    //Return contact based on their position in the list
    @Override
    public Contact getItem(int position) {
        return filteredContactsList.contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.getItem(position).id);
    }

    //Determine which contacts have been selected from the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            convertView = inflater.inflate(R.layout.contact_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.chkContact = (CheckBox) convertView.findViewById(R.id.chk_contact);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.chkContact.setText(this.filteredContactsList.contactArrayList.get(position).toString());
        viewHolder.chkContact.setId(Integer.parseInt(this.filteredContactsList.contactArrayList.get(position).id));
        viewHolder.chkContact.setChecked(alreadySelected(filteredContactsList.contactArrayList.get(position)));

        viewHolder.chkContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Contact contact = filteredContactsList.getContact(buttonView.getId());

                if (contact != null && isChecked && !alreadySelected(contact)) {
                    selectedContactsList.addContact(contact);
                } else if (contact != null && !isChecked) {
                    selectedContactsList.removeContact(contact);
                }
            }
        });

        return convertView;
    }

    public boolean alreadySelected(Contact contact) {
        if (this.selectedContactsList.getContact(Integer.parseInt(contact.id)) != null)
            return true;
        return false;
    }

    public static class ViewHolder {
        CheckBox chkContact;
    }
}