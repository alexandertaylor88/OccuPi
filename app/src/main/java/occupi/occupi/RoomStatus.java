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
import android.widget.ImageView;
import android.widget.TextView;

public class RoomStatus extends AppCompatActivity {

    TextView textType;
    TextView textID;
    ImageView roomType;
    private int _Room_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_status);

        textID = (TextView) findViewById(R.id.text_num);
        textType = (TextView) findViewById(R.id.text_type);
        roomType = (ImageView) findViewById(R.id.roomType);

        _Room_Id = 0;
        Intent intent = getIntent();
        _Room_Id = intent.getIntExtra("room_Id", 0);
        DataBaseHelper db = new DataBaseHelper(this);
        Room room = new Room();
        room = db.getRoomById(_Room_Id);

        //Display the floor and room numbers along with the room type
        textType.setText(room.type);
        textID.setText(roomNameFormatting(_Room_Id));

        //Display the corresponding room image based on the room type
        switch (textType.getText().toString()) {
            case "Lounge":
                roomType.setImageResource(R.drawable.lounge);
                return;
            case "Media":
                roomType.setImageResource(R.drawable.media);
                return;
            case "Office":
                roomType.setImageResource(R.drawable.office);
                return;
            case "Outlook":
                roomType.setImageResource(R.drawable.outlook);
                return;
            case "Treadmill":
                roomType.setImageResource(R.drawable.treadmill);
                return;
            case "Whiteboard":
                roomType.setImageResource(R.drawable.whiteboard);
                return;
        }
        roomType.setImageResource(R.drawable.lounge);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //Format the floor number and room number based on the roomID
    private String roomNameFormatting(int roomNum) {
        int floor = roomNum / 100;
        return "Floor " + floor + " Room " + (roomNum - (100 * floor));
    }

    public void goRally(View v) {
        Intent intent = new Intent(this, Rally.class);
        intent.putExtra("id", Integer.toString(_Room_Id));
        startActivity(intent);
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

}