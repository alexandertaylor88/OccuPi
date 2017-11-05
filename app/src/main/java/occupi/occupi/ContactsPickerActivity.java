package occupi.occupi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsPickerActivity extends AppCompatActivity {

    ListView contactsChooser;
    Button btnDone;
    EditText txtFilter;
    TextView txtLoadInfo;
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.rally).setEnabled(false);
        menu.findItem(R.id.rally).setIcon(R.drawable.rally_focused);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                Intent intentMap = new Intent(this, occupi.occupi.Map.class);
                startActivity(intentMap);
                finish();
                return (true);
            case R.id.list:
                Intent intentList = new Intent(this, occupi.occupi.List.class);
                startActivity(intentList);
                finish();
                return (true);
            case R.id.rally:
                Intent intentRally = new Intent(this, occupi.occupi.Rally.class);
                startActivity(intentRally);
                finish();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_picker);

        contactsChooser = (ListView) findViewById(R.id.lst_contacts_chooser);
        btnDone = (Button) findViewById(R.id.btn_done);
        txtFilter = (EditText) findViewById(R.id.txt_filter);
        txtLoadInfo = (TextView) findViewById(R.id.txt_load_progress);
        contactsListAdapter = new ContactsListAdapter(this, new ContactsList());
        contactsChooser.setAdapter(contactsListAdapter);

        loadContacts("");

        txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //As the user enters a search, begin filtering the phone contacts
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsListAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //Return the list of contacts that have been selected by the user
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contactsListAdapter.selectedContactsList.contactArrayList.isEmpty()) {
                    setResult(RESULT_CANCELED);
                } else {
                    Intent resultIntent = new Intent();

                    resultIntent.putParcelableArrayListExtra("SelectedContacts", contactsListAdapter.selectedContactsList.contactArrayList);
                    setResult(RESULT_OK, resultIntent);
                }
                finish();
            }
        });
    }

    //Pulling contacts from phone on a separate thread
    private void loadContacts(String filter) {
        if (contactsLoader != null && contactsLoader.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                contactsLoader.cancel(true);
            } catch (Exception e) {
            }
        }
        if (filter == null) filter = "";

        try {
            contactsLoader = new ContactsLoader(this, contactsListAdapter);
            contactsLoader.txtProgress = txtLoadInfo;
            contactsLoader.execute(filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.isAppForeground = true;
        if ((MainActivity.scanTime + 120000) < System.currentTimeMillis() && MainActivity.bluetoothEnabled) {
            MainActivity.scanTime = System.currentTimeMillis();
            startService(MainActivity.bluetooth);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.isAppForeground = false;
    }

    @Override
    public void onBackPressed() {
    }

}
