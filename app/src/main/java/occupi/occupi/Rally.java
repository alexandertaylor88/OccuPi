package occupi.occupi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.telephony.SmsMessage.MAX_USER_DATA_BYTES;

public class Rally extends AppCompatActivity {

//    TextView contactsDisplay;
    EditText contactsDisplay;
    Button pickContacts;
    final int CONTACT_PICK_REQUEST = 1000;
    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;
    Spinner floor;
    Spinner room;
    TimePicker time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rally);

        //////////////////////////////////////////////////////////
        floor = (Spinner) findViewById(R.id.floor_menu);
        room = (Spinner) findViewById(R.id.room_menu);
        time = (TimePicker) findViewById(R.id.timePicker);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText) findViewById(R.id.editTextSMS);

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String sms = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (!Objects.equals(textPhoneNo.getText().toString(), "") && !Objects.equals(floor.getSelectedItem().toString(), "Select Floor") && !Objects.equals(room.getSelectedItem().toString(), "Select Room")) {
                        String phoneNo = textPhoneNo.getText().toString();
                        if(!Objects.equals(textSMS.getText().toString(), "")) {
                            sms = "Enterprise Meeting:" + "\n" + formatTime(time) + "\n" + floor.getSelectedItem().toString() + "\n" + room.getSelectedItem().toString() + "\n" + textSMS.getText().toString();
                        }
                        else{sms = "Enterprise Meeting:" + "\n" + formatTime(time) + "\n" + floor.getSelectedItem().toString() + "\n" + room.getSelectedItem().toString();}
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            List<String> numbers = Arrays.asList(phoneNo.split("\\s*,\\s*"));
                            ArrayList<String> messagefragments = smsManager.divideMessage(sms);
                            for (int phonenumber = 0; phonenumber < numbers.size(); phonenumber++) {
                                smsManager.sendMultipartTextMessage(numbers.get(phonenumber), null, messagefragments, null, null);
                            }
                            Toast.makeText(getApplicationContext(), "Message Sent",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Message Failed",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        InputMethodManager imm = (InputMethodManager) getSystemService(Rally.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(textPhoneNo.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(textSMS.getWindowToken(), 0);
                    }
                    else{   Toast.makeText(getApplicationContext(),
                            "Error: Please answer all fields",
                            Toast.LENGTH_LONG).show();}
                }
            }
        });
        ////////////////////////////////////////////////////
        contactsDisplay = (EditText) findViewById(R.id.editTextPhoneNo);
        pickContacts = (Button) findViewById(R.id.btn_pick_contacts);

        pickContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContactPick = new Intent(Rally.this,ContactsPickerActivity.class);
                Rally.this.startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK){
            ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
            String display="";
            for(int i=0;i<selectedContacts.size();i++){
//                display += (i+1)+". "+selectedContacts.get(i).toString()+"\n";
                display += selectedContacts.get(i).number();
                if(i<selectedContacts.size() - 1){
                    display += ", ";
                }
            }
            contactsDisplay.setText(display);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.search:
            startActivity(new Intent(this, Search.class));
            return(true);
        case R.id.login:
            startActivity(new Intent(this, Login.class));
            return(true);
        case R.id.rally:
            startActivity(new Intent(this, Rally.class));
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    public String formatTime(TimePicker time){
        int intHour = time.getCurrentHour();
        int intMinute = time.getCurrentMinute();
        String stringMinute = "";
        if(intMinute < 10){
            stringMinute = "0" + intMinute;
        }
        else {stringMinute = Integer.toString(intMinute);}
        if(intHour < 12){
            return intHour + ":" + stringMinute + " AM";
        }
        else{return (intHour - 12) + ":" + stringMinute + " PM";}
    }

}
