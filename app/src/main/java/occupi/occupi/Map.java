package occupi.occupi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Map extends AppCompatActivity {

    Context mContext;
    Activity mActivity;
    RelativeLayout mRelativeLayout;
    Button mButton;
    PopupWindow mPopupWindow;
    Spinner floor;
    View room1, room2, room3, room4, room5, room6, room7, room8, room9, room10, room11, room12, room13, room14, room15,
            room16, room17, room18, room19, room20, room21, room22, room23, room24, room25, room26, room27, room28, room29,
            room30, room31, room32, room33, room34, room35, room36, room37, room38, room39;
    TextView floorText;

    public Map() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        if (height < 1000) {
            setContentView(R.layout.map_small);
        } else if (height >= 1000 && height < 2000) {
            setContentView(R.layout.map_medium);
        } else {
            setContentView(R.layout.map_large);
        }

        floorText = (TextView) findViewById(R.id.floorText);
        room1 = (View) findViewById(R.id.room01);
        room2 = (View) findViewById(R.id.room02);
        room3 = (View) findViewById(R.id.room03);
        room4 = (View) findViewById(R.id.room04);
        room5 = (View) findViewById(R.id.room05);
        room6 = (View) findViewById(R.id.room06);
        room7 = (View) findViewById(R.id.room07);
        room8 = (View) findViewById(R.id.room08);
        room9 = (View) findViewById(R.id.room09);
        room10 = (View) findViewById(R.id.room10);
        room11 = (View) findViewById(R.id.room11);
        room12 = (View) findViewById(R.id.room12);
        room13 = (View) findViewById(R.id.room13);
        room14 = (View) findViewById(R.id.room14);
        room15 = (View) findViewById(R.id.room15);
        room16 = (View) findViewById(R.id.room16);
        room17 = (View) findViewById(R.id.room17);
        room18 = (View) findViewById(R.id.room18);
        room19 = (View) findViewById(R.id.room19);
        room20 = (View) findViewById(R.id.room20);
        room21 = (View) findViewById(R.id.room21);
        room22 = (View) findViewById(R.id.room22);
        room23 = (View) findViewById(R.id.room23);
        room24 = (View) findViewById(R.id.room24);
        room25 = (View) findViewById(R.id.room25);
        room26 = (View) findViewById(R.id.room26);
        room27 = (View) findViewById(R.id.room27);
        room28 = (View) findViewById(R.id.room28);
        room29 = (View) findViewById(R.id.room29);
        room30 = (View) findViewById(R.id.room30);
        room31 = (View) findViewById(R.id.room31);
        room32 = (View) findViewById(R.id.room32);
        room33 = (View) findViewById(R.id.room33);
        room34 = (View) findViewById(R.id.room34);
        room35 = (View) findViewById(R.id.room35);
        room36 = (View) findViewById(R.id.room36);
        room37 = (View) findViewById(R.id.room37);
        room38 = (View) findViewById(R.id.room38);
        room39 = (View) findViewById(R.id.room39);

        DataBaseHelper db = new DataBaseHelper(Map.this);
        db.saveAppState(this);
        roomVisibility(true, true, true, true, true, true, "empty", db.getRoomMap("2"));
        room9.setVisibility(View.INVISIBLE);
        room10.setVisibility(View.INVISIBLE);

        mContext = Map.this;
        mActivity = Map.this;
        mRelativeLayout = (RelativeLayout) findViewById(R.id.baseMap);
        mButton = (Button) findViewById(R.id.sort_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                final View customView = inflater.inflate(R.layout.map_filter, null);
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
                final CheckBox loungeButton = (CheckBox) customView.findViewById(R.id.loungeButton);
                final CheckBox mediaButton = (CheckBox) customView.findViewById(R.id.mediaButton);
                final CheckBox officeButton = (CheckBox) customView.findViewById(R.id.officeButton);
                final CheckBox outlookButton = (CheckBox) customView.findViewById(R.id.outlookButton);
                final CheckBox treadmillButton = (CheckBox) customView.findViewById(R.id.treadmillButton);
                final CheckBox whiteBoardButton = (CheckBox) customView.findViewById(R.id.whiteboardButton);
                floor = (Spinner) customView.findViewById(R.id.floorSelection);
                Button resetButton = (Button) customView.findViewById(R.id.resetButton);
                resetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataBaseHelper db = new DataBaseHelper(Map.this);
                        roomVisibility(true, true, true, true, true, true, "empty", db.getRoomMap("2"));
                        mPopupWindow.dismiss();
                    }
                });
                Button sortButton = (Button) customView.findViewById(R.id.sortButton);
                sortButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (floor.getSelectedItem().toString().equals("Select Floor")) {
                            Toast.makeText(Map.this, "Please select a floor", Toast.LENGTH_SHORT).show();
                        } else {
                            if (loungeButton.isChecked() || mediaButton.isChecked() || officeButton.isChecked() || outlookButton.isChecked()
                                    || treadmillButton.isChecked() || whiteBoardButton.isChecked()) {
                                sortRooms(loungeButton.isChecked(), mediaButton.isChecked(), officeButton.isChecked(), outlookButton.isChecked(), treadmillButton.isChecked(), whiteBoardButton.isChecked(), "type", floor.getSelectedItem().toString());
                            } else {
                                sortRooms(loungeButton.isChecked(), mediaButton.isChecked(), officeButton.isChecked(), outlookButton.isChecked(), treadmillButton.isChecked(), whiteBoardButton.isChecked(), "empty", floor.getSelectedItem().toString());
                            }
                            mPopupWindow.dismiss();
                        }
                    }
                });
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
            }
        });

    }

    public void sortRooms(boolean loungeButton, boolean mediaButton, boolean officeButton, boolean outlookButton, boolean treadmillButton, boolean whiteboardButton, String type, String floor) {
        DataBaseHelper db = new DataBaseHelper(Map.this);
        ArrayList<HashMap<String, String>> roomList = null;
        roomList = db.getRoomMap(floor);
        if (roomList.size() > 0) {
            floorText.setText(floor);
            roomVisibility(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, type, roomList);
        } else {
            if (type.equals("empty")) {
                Toast.makeText(Map.this, "There are no empty rooms on " + floor.toLowerCase(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Map.this, "There are no empty rooms of this type on " + floor.toLowerCase(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Boolean roomTypeCheck(boolean loungeButton, boolean mediaButton, boolean officeButton, boolean outlookButton, boolean treadmillButton, boolean whiteboardButton, int num, String type, ArrayList<HashMap<String, String>> unoccupiedRooms) {
        boolean lou = ((loungeButton && unoccupiedRooms.get(num).get("type").equals("Lounge")));
        boolean med = ((mediaButton && unoccupiedRooms.get(num).get("type").equals("Media")));
        boolean off = ((officeButton && unoccupiedRooms.get(num).get("type").equals("Office")));
        boolean out = ((outlookButton && unoccupiedRooms.get(num).get("type").equals("Outlook")));
        boolean tre = ((treadmillButton && unoccupiedRooms.get(num).get("type").equals("Treadmill")));
        boolean whi = ((whiteboardButton && unoccupiedRooms.get(num).get("type").equals("Whiteboard")));
        if ((type.equals("empty") || (lou || med || off || out || tre || whi)) && (unoccupiedRooms.get(num).get("occupancy").equals("0"))) {
            return true;
        } else return false;
    }

    public void roomVisibility(boolean loungeButton, boolean mediaButton, boolean officeButton, boolean outlookButton, boolean treadmillButton, boolean whiteboardButton, String type, ArrayList<HashMap<String, String>> unoccupiedRooms) {

        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 0, type, unoccupiedRooms)) {
            room1.setVisibility(View.VISIBLE);
        } else {
            room1.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 1, type, unoccupiedRooms)) {
            room2.setVisibility(View.VISIBLE);
        } else {
            room2.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 2, type, unoccupiedRooms)) {
            room3.setVisibility(View.VISIBLE);
        } else {
            room3.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 3, type, unoccupiedRooms)) {
            room4.setVisibility(View.VISIBLE);
        } else {
            room4.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 4, type, unoccupiedRooms)) {
            room5.setVisibility(View.VISIBLE);
        } else {
            room5.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 5, type, unoccupiedRooms)) {
            room6.setVisibility(View.VISIBLE);
        } else {
            room6.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 6, type, unoccupiedRooms)) {
            room7.setVisibility(View.VISIBLE);
        } else {
            room7.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 7, type, unoccupiedRooms)) {
            room8.setVisibility(View.VISIBLE);
        } else {
            room8.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 8, type, unoccupiedRooms)) {
            room11.setVisibility(View.VISIBLE);
        } else {
            room11.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 9, type, unoccupiedRooms)) {
            room12.setVisibility(View.VISIBLE);
        } else {
            room12.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 10, type, unoccupiedRooms)) {
            room13.setVisibility(View.VISIBLE);
        } else {
            room13.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 11, type, unoccupiedRooms)) {
            room14.setVisibility(View.VISIBLE);
        } else {
            room14.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 12, type, unoccupiedRooms)) {
            room15.setVisibility(View.VISIBLE);
        } else {
            room15.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 13, type, unoccupiedRooms)) {
            room16.setVisibility(View.VISIBLE);
        } else {
            room16.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 14, type, unoccupiedRooms)) {
            room17.setVisibility(View.VISIBLE);
        } else {
            room17.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 15, type, unoccupiedRooms)) {
            room18.setVisibility(View.VISIBLE);
        } else {
            room18.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 16, type, unoccupiedRooms)) {
            room19.setVisibility(View.VISIBLE);
        } else {
            room19.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 17, type, unoccupiedRooms)) {
            room20.setVisibility(View.VISIBLE);
        } else {
            room20.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 18, type, unoccupiedRooms)) {
            room21.setVisibility(View.VISIBLE);
        } else {
            room21.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 19, type, unoccupiedRooms)) {
            room22.setVisibility(View.VISIBLE);
        } else {
            room22.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 20, type, unoccupiedRooms)) {
            room23.setVisibility(View.VISIBLE);
        } else {
            room23.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 21, type, unoccupiedRooms)) {
            room24.setVisibility(View.VISIBLE);
        } else {
            room24.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 22, type, unoccupiedRooms)) {
            room25.setVisibility(View.VISIBLE);
        } else {
            room25.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 23, type, unoccupiedRooms)) {
            room26.setVisibility(View.VISIBLE);
        } else {
            room26.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 24, type, unoccupiedRooms)) {
            room27.setVisibility(View.VISIBLE);
        } else {
            room27.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 25, type, unoccupiedRooms)) {
            room28.setVisibility(View.VISIBLE);
        } else {
            room28.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 26, type, unoccupiedRooms)) {
            room29.setVisibility(View.VISIBLE);
        } else {
            room29.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 27, type, unoccupiedRooms)) {
            room30.setVisibility(View.VISIBLE);
        } else {
            room30.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 28, type, unoccupiedRooms)) {
            room31.setVisibility(View.VISIBLE);
        } else {
            room31.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 29, type, unoccupiedRooms)) {
            room32.setVisibility(View.VISIBLE);
        } else {
            room32.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 30, type, unoccupiedRooms)) {
            room33.setVisibility(View.VISIBLE);
        } else {
            room33.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 31, type, unoccupiedRooms)) {
            room34.setVisibility(View.VISIBLE);
        } else {
            room34.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 32, type, unoccupiedRooms)) {
            room35.setVisibility(View.VISIBLE);
        } else {
            room35.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 33, type, unoccupiedRooms)) {
            room36.setVisibility(View.VISIBLE);
        } else {
            room36.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 34, type, unoccupiedRooms)) {
            room37.setVisibility(View.VISIBLE);
        } else {
            room37.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 35, type, unoccupiedRooms)) {
            room38.setVisibility(View.VISIBLE);
        } else {
            room38.setVisibility(View.INVISIBLE);
        }
        if (roomTypeCheck(loungeButton, mediaButton, officeButton, outlookButton, treadmillButton, whiteboardButton, 36, type, unoccupiedRooms)) {
            room39.setVisibility(View.VISIBLE);
        } else {
            room39.setVisibility(View.INVISIBLE);
        }
    }

    public void goRoomStatus(View room) {
        if(room.getVisibility() == View.VISIBLE){
            String roomID = "";
            roomID += Integer.parseInt(floorText.getText().toString().replaceAll("[\\D]", ""));
            roomID += getResources().getResourceName(room.getId()).replaceAll("[\\D]", "");
            Intent objIndent = new Intent(Map.this, RoomStatus.class);
            objIndent.putExtra("room_Id", Integer.parseInt(roomID));
            startActivity(objIndent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.map).setEnabled(false);
        menu.findItem(R.id.map).setIcon(R.drawable.map_focused);
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
