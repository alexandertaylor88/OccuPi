package occupi.occupi;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.BoolRes;

import java.util.ArrayList;
import java.util.HashMap;

public class List extends AppCompatActivity {

    TextView room_Id;
    private DataBaseConn dbHelper;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //This is a function that filters the RoomType by the filterType passed into the function.
    public ArrayList<HashMap<String, String>> getFilterRoomList(String filter) {
        String id;
        String type;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_type + "," +
                Room.KEY_occupied +
                " FROM " + Room.TABLE
                + " WHERE " +
                Room.KEY_type + "=" + filter;
        ArrayList<HashMap<String, String>> roomList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> room = new HashMap<String, String>();
                id = cursor.getString(cursor.getColumnIndex(Room.KEY_ID));
                type = cursor.getString(cursor.getColumnIndex(Room.KEY_type));
                room.put("id", id);
                room.put("type", type);
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }//End getFilterRoomList


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //This functions job is to filter the rooms in the database
    public void filterRoomType(View view){
        //This variable will store the users filter option
        String filterChoice = "";

        //Check to see if the button is checked
        boolean checked = ((RadioButton) view).isChecked();

        //Check which radio button was clicked
        switch (view.getId()) {
            case R.id.loungeButton:
                if (checked)
                    //filterChoice = "Lounge";
                    //Toast.makeText(getApplicationContext(), "Lounge has been selected.", Toast.LENGTH_SHORT).show();
                    getFilterRoomList("Lounge");
                break;
            case R.id.mediaButton:
                if (checked)
                    //filterChoice = "Media";
                    //Toast.makeText(getApplicationContext(), "Media has been selected.", Toast.LENGTH_SHORT).show();
                    getFilterRoomList("Media");
                break;
            case R.id.officeButton:
                if (checked)
                    //filterChoice = "Office";
                    //Toast.makeText(getApplicationContext(), "Office has been selected.", Toast.LENGTH_SHORT).show();
                    getFilterRoomList("Office");
                break;
            case R.id.outlookButton:
                if (checked)
                    //filterChoice = "Outlook";
                    //Toast.makeText(getApplicationContext(), "Outlook has been selected.", Toast.LENGTH_SHORT).show();
                    getFilterRoomList("Outlook");
                break;
            case R.id.treadmillButton:
                if (checked)
                    //filterChoice = "Treadmill";
                    //Toast.makeText(getApplicationContext(), "Treadmill has been selected.", Toast.LENGTH_SHORT).show();
                    getFilterRoomList("Treadmill");
                break;
            case R.id.whiteboardButton:
                if (checked)
                    //filterChoice = "Whiteboard";
                    //Toast.makeText(getApplicationContext(), "Whiteboard has been selected.", Toast.LENGTH_SHORT).show();
                    getFilterRoomList("Whiteboard");
                break;

            //Toast.makeText(getApplicationContext(), "Please select a filer option.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        DataBaseHelper db = new DataBaseHelper(this);
        db.saveAppState(this);

        Button reset = (Button) findViewById (R.id.resetButton);
        Button sort = (Button) findViewById(R.id.sortButton);

        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Call getEmptyRoomList in DataBaseHelper Class
                Toast.makeText(getApplicationContext(), "Reset Button Clicked.", Toast.LENGTH_SHORT).show();

            }
        });
        sort.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Call FilterRoomType();
                filterRoomType(v);
                Toast.makeText(getApplicationContext(), "Sort Button Clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<HashMap<String, String>> roomList = null;
        try {
            roomList = db.getEmptyRoomList();
        } catch (Exception e) {
            db.createDatabase();
            roomList = db.getEmptyRoomList();
        }
        if (roomList.size() != 0) {
            ListView lv = (ListView) findViewById(R.id.roomList);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    room_Id = (TextView) view.findViewById(R.id.room_Id);
                    String roomId = room_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(), RoomStatus.class);
                    objIndent.putExtra("room_Id", Integer.parseInt(roomId));
                    startActivity(objIndent);
                }
            });

            lv.setAdapter(new SimpleAdapter(List.this, roomList, R.layout.view_room_entry, new String[]{"id", "formattedData"}, new int[]{R.id.room_Id, R.id.room_Num}));
        } else {
            Toast.makeText(this, "No Rooms", Toast.LENGTH_SHORT).show();
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
                intentMap.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentMap);
                return (true);
            case R.id.list:
                Intent intentList = new Intent(this, occupi.occupi.List.class);
                intentList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentList);
                //startActivity(new Intent(this, occupi.occupi.List.class));
                return (true);
            case R.id.rally:
                Intent intentRally = new Intent(this, occupi.occupi.Rally.class);
                intentRally.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentRally);
//            startActivity(new Intent(this, occupi.occupi.Rally.class));
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.isAppForeground = true;
        if ((MainActivity.scanTime + 120000) < System.currentTimeMillis()) {
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

