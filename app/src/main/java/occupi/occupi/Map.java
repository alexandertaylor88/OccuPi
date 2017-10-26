package occupi.occupi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
            room16, room17, room18, room19, room20, room21, room22, room23, room24, room25, room26, room27, room28, room29, room30,
            room31, room32, room33, room34, room35, room36, room37, room38, room39;
    TextView floorText;

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
        roomVisibility("2", "empty", db.getRoomListByType());
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

                final View customView = inflater.inflate(R.layout.map_sort, null);
                customView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                mPopupWindow = new PopupWindow(
                        customView,
                        customView.getMeasuredWidth(),
                        customView.getMeasuredHeight()
                );

                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(5.0f);
                }
                final RadioButton loungeButton = (RadioButton) customView.findViewById(R.id.loungeButton);
                final RadioButton mediaButton = (RadioButton) customView.findViewById(R.id.mediaButton);
                final RadioButton officeButton = (RadioButton) customView.findViewById(R.id.officeButton);
                final RadioButton outlookButton = (RadioButton) customView.findViewById(R.id.outlookButton);
                final RadioButton treadmillButton = (RadioButton) customView.findViewById(R.id.treadmillButton);
                final RadioButton whiteBoardButton = (RadioButton) customView.findViewById(R.id.whiteboardButton);
                floor = (Spinner) customView.findViewById(R.id.floorSelection);
                final RadioGroup rg = (RadioGroup) customView.findViewById(R.id.radioGroup);
                Button resetButton = (Button) customView.findViewById(R.id.resetButton);
                resetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataBaseHelper db = new DataBaseHelper(Map.this);
                        roomVisibility("2", "empty", db.getRoomListByType());
                        mPopupWindow.dismiss();
                    }
                });
                Button sortButton = (Button) customView.findViewById(R.id.sortButton);
                sortButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (rg.getCheckedRadioButtonId() == -1) {
//                            //nothing's selected
                        } else {
                            try {
                                if (loungeButton.isChecked()) {
                                    sortRooms(String.valueOf(loungeButton.getText()), floor.getSelectedItem().toString());
                                } else if (mediaButton.isChecked()) {
                                    sortRooms(String.valueOf(mediaButton.getText()), floor.getSelectedItem().toString());
                                } else if (officeButton.isChecked()) {
                                    sortRooms(String.valueOf(officeButton.getText()), floor.getSelectedItem().toString());
                                } else if (outlookButton.isChecked()) {
                                    sortRooms(String.valueOf(outlookButton.getText()), floor.getSelectedItem().toString());
                                } else if (treadmillButton.isChecked()) {
                                    sortRooms(String.valueOf(treadmillButton.getText()), floor.getSelectedItem().toString());
                                } else if (whiteBoardButton.isChecked()) {
                                    sortRooms(String.valueOf(whiteBoardButton.getText()), floor.getSelectedItem().toString());
                                }
                            } catch (Exception e) {
                                // Toast.makeText(Map.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                            mPopupWindow.dismiss();
                        }
                    }
                });
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
            }
        });

    }

    public void sortRooms(String type, String floor) {
        DataBaseHelper db = new DataBaseHelper(Map.this);
        ArrayList<HashMap<String, String>> roomList = null;
        roomList = db.getRoomListByType();
        if (roomList.size() != 0) {
            roomVisibility(floor, type, roomList);
        } else {
            Toast.makeText(Map.this, "No Rooms", Toast.LENGTH_SHORT).show();
        }
    }

    public void roomVisibility(String floor, String type, ArrayList<HashMap<String, String>> unoccupiedRooms) {

        if ((type.equals("empty") || unoccupiedRooms.get(0).get("type").equals(type)) && (unoccupiedRooms.get(0).get("occupancy").equals("0"))) {
            room1.setVisibility(View.VISIBLE);
        } else {
            room1.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(1).get("type").equals(type)) && (unoccupiedRooms.get(1).get("occupancy").equals("0"))) {
            room2.setVisibility(View.VISIBLE);
        } else {
            room2.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(2).get("type").equals(type)) && (unoccupiedRooms.get(2).get("occupancy").equals("0"))) {
            room3.setVisibility(View.VISIBLE);
        } else {
            room3.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(3).get("type").equals(type)) && (unoccupiedRooms.get(3).get("occupancy").equals("0"))) {
            room4.setVisibility(View.VISIBLE);
        } else {
            room4.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(4).get("type").equals(type)) && (unoccupiedRooms.get(4).get("occupancy").equals("0"))) {
            room5.setVisibility(View.VISIBLE);
        } else {
            room5.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(5).get("type").equals(type)) && (unoccupiedRooms.get(5).get("occupancy").equals("0"))) {
            room6.setVisibility(View.VISIBLE);
        } else {
            room6.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(6).get("type").equals(type)) && (unoccupiedRooms.get(6).get("occupancy").equals("0"))) {
            room7.setVisibility(View.VISIBLE);
        } else {
            room7.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(7).get("type").equals(type)) && (unoccupiedRooms.get(7).get("occupancy").equals("0"))) {
            room8.setVisibility(View.VISIBLE);
        } else {
            room8.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(8).get("type").equals(type)) && (unoccupiedRooms.get(8).get("occupancy").equals("0"))) {
            room11.setVisibility(View.VISIBLE);
        } else {
            room11.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(9).get("type").equals(type)) && (unoccupiedRooms.get(9).get("occupancy").equals("0"))) {
            room12.setVisibility(View.VISIBLE);
        } else {
            room12.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(10).get("type").equals(type)) && (unoccupiedRooms.get(10).get("occupancy").equals("0"))) {
            room13.setVisibility(View.VISIBLE);
        } else {
            room13.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(11).get("type").equals(type)) && (unoccupiedRooms.get(11).get("occupancy").equals("0"))) {
            room14.setVisibility(View.VISIBLE);
        } else {
            room14.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(12).get("type").equals(type)) && (unoccupiedRooms.get(12).get("occupancy").equals("0"))) {
            room15.setVisibility(View.VISIBLE);
        } else {
            room15.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(13).get("type").equals(type)) && (unoccupiedRooms.get(13).get("occupancy").equals("0"))) {
            room16.setVisibility(View.VISIBLE);
        } else {
            room16.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(14).get("type").equals(type)) && (unoccupiedRooms.get(14).get("occupancy").equals("0"))) {
            room17.setVisibility(View.VISIBLE);
        } else {
            room17.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(15).get("type").equals(type)) && (unoccupiedRooms.get(15).get("occupancy").equals("0"))) {
            room18.setVisibility(View.VISIBLE);
        } else {
            room18.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(16).get("type").equals(type)) && (unoccupiedRooms.get(16).get("occupancy").equals("0"))) {
            room19.setVisibility(View.VISIBLE);
        } else {
            room19.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(17).get("type").equals(type)) && (unoccupiedRooms.get(17).get("occupancy").equals("0"))) {
            room20.setVisibility(View.VISIBLE);
        } else {
            room20.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(18).get("type").equals(type)) && (unoccupiedRooms.get(18).get("occupancy").equals("0"))) {
            room21.setVisibility(View.VISIBLE);
        } else {
            room21.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(19).get("type").equals(type)) && (unoccupiedRooms.get(19).get("occupancy").equals("0"))) {
            room22.setVisibility(View.VISIBLE);
        } else {
            room22.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(20).get("type").equals(type)) && (unoccupiedRooms.get(20).get("occupancy").equals("0"))) {
            room23.setVisibility(View.VISIBLE);
        } else {
            room23.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(21).get("type").equals(type)) && (unoccupiedRooms.get(21).get("occupancy").equals("0"))) {
            room24.setVisibility(View.VISIBLE);
        } else {
            room24.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(22).get("type").equals(type)) && (unoccupiedRooms.get(22).get("occupancy").equals("0"))) {
            room25.setVisibility(View.VISIBLE);
        } else {
            room25.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(23).get("type").equals(type)) && (unoccupiedRooms.get(23).get("occupancy").equals("0"))) {
            room26.setVisibility(View.VISIBLE);
        } else {
            room26.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(24).get("type").equals(type)) && (unoccupiedRooms.get(24).get("occupancy").equals("0"))) {
            room27.setVisibility(View.VISIBLE);
        } else {
            room27.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(25).get("type").equals(type)) && (unoccupiedRooms.get(25).get("occupancy").equals("0"))) {
            room28.setVisibility(View.VISIBLE);
        } else {
            room28.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(26).get("type").equals(type)) && (unoccupiedRooms.get(26).get("occupancy").equals("0"))) {
            room29.setVisibility(View.VISIBLE);
        } else {
            room29.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(27).get("type").equals(type)) && (unoccupiedRooms.get(27).get("occupancy").equals("0"))) {
            room30.setVisibility(View.VISIBLE);
        } else {
            room30.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(28).get("type").equals(type)) && (unoccupiedRooms.get(28).get("occupancy").equals("0"))) {
            room31.setVisibility(View.VISIBLE);
        } else {
            room31.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(29).get("type").equals(type)) && (unoccupiedRooms.get(29).get("occupancy").equals("0"))) {
            room32.setVisibility(View.VISIBLE);
        } else {
            room32.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(30).get("type").equals(type)) && (unoccupiedRooms.get(30).get("occupancy").equals("0"))) {
            room33.setVisibility(View.VISIBLE);
        } else {
            room33.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(31).get("type").equals(type)) && (unoccupiedRooms.get(31).get("occupancy").equals("0"))) {
            room34.setVisibility(View.VISIBLE);
        } else {
            room34.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(32).get("type").equals(type)) && (unoccupiedRooms.get(32).get("occupancy").equals("0"))) {
            room35.setVisibility(View.VISIBLE);
        } else {
            room35.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(33).get("type").equals(type)) && (unoccupiedRooms.get(33).get("occupancy").equals("0"))) {
            room36.setVisibility(View.VISIBLE);
        } else {
            room36.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(34).get("type").equals(type)) && (unoccupiedRooms.get(34).get("occupancy").equals("0"))) {
            room37.setVisibility(View.VISIBLE);
        } else {
            room37.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(35).get("type").equals(type)) && (unoccupiedRooms.get(35).get("occupancy").equals("0"))) {
            room38.setVisibility(View.VISIBLE);
        } else {
            room38.setVisibility(View.INVISIBLE);
        }
        if ((type.equals("empty") || unoccupiedRooms.get(36).get("type").equals(type)) && (unoccupiedRooms.get(36).get("occupancy").equals("0"))) {
            room39.setVisibility(View.VISIBLE);
        } else {
            room39.setVisibility(View.INVISIBLE);
        }
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
