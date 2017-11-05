package occupi.occupi;

import java.util.ArrayList;

public class ContactsList {

    public ArrayList<Contact> contactArrayList;

    ContactsList() {
        contactArrayList = new ArrayList<Contact>();
    }

    //Return the total number of contacts currently displayed in the list
    public int getCount() {
        return contactArrayList.size();
    }

    //Altering the list of contacts displayed based on the search entered
    public void addContact(Contact contact) {
        contactArrayList.add(contact);
    }
    public void removeContact(Contact contact) {
        contactArrayList.remove(contact);
    }

    //Return a contact based that contacts position in the list
    public Contact getContact(int id) {
        for (int i = 0; i < this.getCount(); i++) {
            if (Integer.parseInt(contactArrayList.get(i).id) == id)
                return contactArrayList.get(i);
        }
        return null;
    }

}