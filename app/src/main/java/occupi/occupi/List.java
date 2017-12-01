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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class List extends AppCompatActivity {
    TextView room_Id;
    CheckBox loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteBoardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        DataBaseHelper db = new DataBaseHelper(this);
        db.saveAppState(this);

        ArrayList<HashMap<String, String>> roomList = null;
        final ListView list = (ListView) findViewById(R.id.roomList);
        Button reset = (Button) findViewById(R.id.resetButton);
        Button sort = (Button) findViewById(R.id.sortButton);
        //Attempting to pull of the empty rooms from the database
        try {
            roomList = db.getEmptyRoomList();
        } catch (Exception e) {
            db.createDatabase();
            roomList = db.getEmptyRoomList();
        }
        //Populating the list with the appropriate information for each unocccupied room from the database
        if (roomList.size() != 0) {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    room_Id = (TextView) view.findViewById(R.id.room_Id);
                    String roomId = room_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(), RoomStatus.class);
                    objIndent.putExtra("room_Id", Integer.parseInt(roomId));
                    startActivity(objIndent);
                }
            });
            list.setAdapter(new SimpleAdapter(List.this, roomList, R.layout.view_room_entry, new String[]{"id", "formattedData"}, new int[]{R.id.room_Id, R.id.room_Num}));
        } else {
            Toast.makeText(this, "No Rooms", Toast.LENGTH_SHORT).show();
        }
        loungeButton = (CheckBox) findViewById(R.id.loungeButton);
        mediaButton = (CheckBox) findViewById(R.id.mediaButton);
        officeButton = (CheckBox) findViewById(R.id.officeButton);
        outlookButton = (CheckBox) findViewById(R.id.outlookButton);
        treadmillButton = (CheckBox) findViewById(R.id.treadmillButton);
        whiteBoardButton = (CheckBox) findViewById(R.id.whiteboardButton);
        //Determining which checkboxes have been checked. Similarly, building the appropriate query string based on those
        //checkboxes
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numChecked = 0;
                String queryString = "";
                ArrayList<HashMap<String, String>> roomList = null;
                if (loungeButton.isChecked() || mediaButton.isChecked() || officeButton.isChecked() || outlookButton.isChecked() || treadmillButton.isChecked() || whiteBoardButton.isChecked()) {
                    if (loungeButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Lounge'" : " OR " + Room.KEY_type + "= 'Lounge'";
                    }
                    if (mediaButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Media'" : " OR " + Room.KEY_type + "= 'Media'";
                    }
                    if (officeButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Office'" : " OR " + Room.KEY_type + "= 'Office'";
                    }
                    if (outlookButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Outlook'" : " OR " + Room.KEY_type + "= 'Outlook'";
                    }
                    if (treadmillButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Treadmill'" : " OR " + Room.KEY_type + "= 'Treadmill'";
                    }
                    if (whiteBoardButton.isChecked()) {
                        queryString += (numChecked++ == 0) ? " AND (" + Room.KEY_type + "= 'Whiteboard'" : " OR " + Room.KEY_type + "= 'Whiteboard'";
                    }
                    filterList(roomList, list, queryString);
                } else {
                    Toast.makeText(getApplicationContext(), "No Filter Selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Returning the list to display all empty rooms regardless of room type
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loungeButton.setChecked(false);
                mediaButton.setChecked(false);
                officeButton.setChecked(false);
                outlookButton.setChecked(false);
                treadmillButton.setChecked(false);
                whiteBoardButton.setChecked(false);
                recreate();
            }
        });
    }

    //Calling the appropriate query on the database and repopulating the list
    public void filterList(ArrayList<HashMap<String, String>> roomList, ListView list, String query) {
        DataBaseHelper db = new DataBaseHelper(List.this);
        roomList = db.filterRoomList(query);
        list.setAdapter(new SimpleAdapter(List.this, roomList, R.layout.view_room_entry, new String[]{"id", "formattedData"}, new int[]{R.id.room_Id, R.id.room_Num}));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.list).setEnabled(false);
        menu.findItem(R.id.list).setIcon(R.drawable.list_focused);
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

