package occupi.occupi;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsLoader extends AsyncTask<String,Void,Void> {

    ContactsListAdapter contactsListAdapter;
    Context context;
    private ArrayList<Contact> contactList;
    TextView txtProgress;
    int totalContactsCount,loadedContactsCount;

    ContactsLoader(Context context,ContactsListAdapter contactsListAdapter){
        this.context = context;
        this.contactsListAdapter = contactsListAdapter;
        this.contactList= new ArrayList<>();
        loadedContactsCount=0;
        totalContactsCount=0;
        txtProgress=null;
    }

    @Override
    protected Void doInBackground(String[] filters) {

        String filter = filters[0];
        ContentResolver contentResolver = context.getContentResolver();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        Cursor cursor;
        //Determining if the user searching for a specific contact
        if(filter.length()>0) {
            cursor = contentResolver.query(
                    uri,
                    projection,
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                    new String[]{filter},
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            );
        }else {
            cursor = contentResolver.query(
                    uri,
                    projection,
                    null,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            );
        }
        //Add phone contacts based on the previous query to a list
        totalContactsCount = cursor.getCount();
        if(cursor!=null && cursor.getCount()>0){

            while(cursor.moveToNext()) {
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{id},
                            null
                    );

                    if (phoneCursor != null && phoneCursor.getCount() > 0) {

                        while (phoneCursor.moveToNext()) {
                            String phId = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                            String phNo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contactList.add(new Contact(phId, name, phNo));
                        }
                        phoneCursor.close();
                    }
                }
                loadedContactsCount++;
                publishProgress();
            }
            cursor.close();
        }
        return null;
    }

    //Display a loading screen if more than 150 contacts are found in the user's phone
    @Override
    protected void onProgressUpdate(Void[] v){

        if(this.contactList.size()>=150) {
            contactsListAdapter.addContacts(contactList);
            this.contactList.clear();
            if(txtProgress!=null){
                txtProgress.setVisibility(View.VISIBLE);
                txtProgress.setText("Loading...("+loadedContactsCount+"/"+totalContactsCount+")");
            }
        }
    }

    //After the contacts are all pulled from the user's phone, the loading screen disappears
    @Override
    protected void onPostExecute(Void v){

        contactsListAdapter.addContacts(contactList);
        contactList.clear();

        if(txtProgress!=null) {
            txtProgress.setText("");
            txtProgress.setVisibility(View.GONE);
        }
    }
}
