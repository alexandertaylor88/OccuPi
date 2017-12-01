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
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Rally extends AppCompatActivity {

    EditText contactsDisplay;
    Button pickContacts;
    final int CONTACT_PICK_REQUEST = 1000;
    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;
    Spinner floor;
    Spinner room;
    TimePicker time;
    DatePicker date;
    Context mContext;
    Activity mActivity;
    RelativeLayout mRelativeLayout;
    Button mButton;
    PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rally);

        floor = (Spinner) findViewById(R.id.floor_menu);
        room = (Spinner) findViewById(R.id.room_menu);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText) findViewById(R.id.editTextSMS);

        Intent intent = getIntent();
        Bundle roomData = intent.getExtras();

        //Populated the room and floor spinners with the appropriate data from the RoomStatus class
        if (roomData != null) {
            int roomNum = Integer.valueOf((String) roomData.get("id"));
            int floorNum = roomNum / 100;
            roomNum = roomNum - (floorNum * 100);
            floor.setSelection(getIndex(floor, Integer.toString(floorNum), "Floor"));
            room.setSelection(getIndex(room, Integer.toString(roomNum), "Room"));
        }


        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String sms, addInfo = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    if (!Objects.equals(textSMS.getText().toString(), "")) {
                        addInfo = "\n" + textSMS.getText().toString();
                    }
                    if (!Objects.equals(textPhoneNo.getText().toString(), "") && !Objects.equals(mButton.getText().toString(), "Select Date and Time") && !Objects.equals(floor.getSelectedItem().toString(), "Select Floor") && !Objects.equals(room.getSelectedItem().toString(), "Select Room")) {
                        String phoneNo = textPhoneNo.getText().toString();
                        sms = "Enterprise Meeting:" + "\n" + formatDate(date) + "\n" + formatTime(time) + "\n" + floor.getSelectedItem().toString() + "\n" + room.getSelectedItem().toString() + addInfo + "\n\nSent with OccuPi\nMsg & data rates may apply";
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            List<String> numbers = Arrays.asList(phoneNo.split("\\s*,\\s*"));
                            ArrayList<String> messageFragments = smsManager.divideMessage(sms);
                            for (int phoneNumber = 0; phoneNumber < numbers.size(); phoneNumber++) {
                                smsManager.sendMultipartTextMessage(numbers.get(phoneNumber), null, messageFragments, null, null);
                            }
                            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Message Failed", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        InputMethodManager imm = (InputMethodManager) getSystemService(Rally.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(textPhoneNo.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(textSMS.getWindowToken(), 0);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Error: Please answer all fields",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        contactsDisplay = (EditText) findViewById(R.id.editTextPhoneNo);
        pickContacts = (Button) findViewById(R.id.btn_pick_contacts);

        //Link to pull populate team list from phone contacts
        pickContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentContactPick = new Intent(Rally.this, ContactsPickerActivity.class);
                Rally.this.startActivityForResult(intentContactPick, CONTACT_PICK_REQUEST);
            }
        });

        //Popup window to specify the time and date of the upcoming meeting
        mContext = getApplicationContext();
        mActivity = Rally.this;
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rally);
        mButton = (Button) findViewById(R.id.dateTime);
        mButton.setBackgroundDrawable(null);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    View customView = inflater.inflate(R.layout.date_time, null);
                    customView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    mPopupWindow = new PopupWindow(
                            customView,
                            customView.getMeasuredWidth(),
                            customView.getMeasuredHeight()
                    );
                    mPopupWindow.setFocusable(true);

                    if (Build.VERSION.SDK_INT >= 21) {
                        mPopupWindow.setElevation(5.0f);
                    }

                    Button closeButton = (Button) customView.findViewById(R.id.set);
                    time = (TimePicker) customView.findViewById(R.id.timePicker);
                    date = (DatePicker) customView.findViewById(R.id.datePicker);
                    date.setMinDate(System.currentTimeMillis() - 1000);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPopupWindow.dismiss();
                            mButton.setText("Meeting Time:\n" + formatTime(time) + "\n" + formatDate(date));
                        }
                    });
                    mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //After pulling contacts from contact list, the "Contacts" bar display their phone numbers
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK) {
            ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
            String display = "";
            for (int i = 0; i < selectedContacts.size(); i++) {
                display += selectedContacts.get(i).number();
                if (i < selectedContacts.size() - 1) {
                    display += ", ";
                }
            }
            contactsDisplay.setText(display);
        }
    }

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

    //Format the time selected from the popup window to be in a 12 hour format
    public String formatTime(TimePicker time) {
        int intHour = time.getCurrentHour();
        int intMinute = time.getCurrentMinute();
        String stringMinute = "";
        if (intMinute < 10) {
            stringMinute = "0" + intMinute;
        } else {
            stringMinute = Integer.toString(intMinute);
        }
        if (intHour == 0) {
            return "12:" + stringMinute + " AM";
        } else if (intHour < 12) {
            return intHour + ":" + stringMinute + " AM";
        } else if (intHour == 12) {
            return "12:" + stringMinute + " PM";
        } else {
            return (intHour - 12) + ":" + stringMinute + " PM";
        }
    }

    //Format the date selected from the popup window
    public String formatDate(DatePicker date) {
        return date.getMonth() + "/" + date.getDayOfMonth() + "/" + date.getYear();
    }

    //Get the index of a room from the database
    public int getIndex(Spinner s, String value, String type) {
        for (int i = 0; i < s.getCount(); i++) {
            if (s.getItemAtPosition(i).equals(type + " " + value)) {
                return i;
            }
        }
        return 0;
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